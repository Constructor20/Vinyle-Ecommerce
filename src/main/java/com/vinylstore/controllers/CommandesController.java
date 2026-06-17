package com.vinylstore.controllers;

import com.vinylstore.utils.CommandesDAO;
import com.vinylstore.utils.CommandesDAO.ArticleCommande;
import com.vinylstore.utils.CommandesDAO.Commande;
import com.vinylstore.utils.SessionUtilisateur;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Affiche l'historique des commandes de l'utilisateur connecté
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class CommandesController {

    @FXML private TableView<Commande> tableauCommandes;
    @FXML private TableColumn<Commande, Integer> colonneId;
    @FXML private TableColumn<Commande, String> colonneDate;
    @FXML private TableColumn<Commande, BigDecimal> colonneTotal;
    @FXML private TableView<ArticleCommande> tableauDetails;
    @FXML private TableColumn<ArticleCommande, String> colonneDetailTitre;
    @FXML private TableColumn<ArticleCommande, String> colonneDetailArtiste;
    @FXML private TableColumn<ArticleCommande, Integer> colonneDetailQuantite;
    @FXML private TableColumn<ArticleCommande, BigDecimal> colonneDetailPrix;
    @FXML private TableColumn<ArticleCommande, BigDecimal> colonneDetailTotal;
    @FXML private Label messageErreur;

    private ObservableList<Commande> listeCommandes = FXCollections.observableArrayList();
    private ObservableList<ArticleCommande> listeArticles = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Colonnes du tableau des commandes
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneDate.setCellValueFactory(cellData -> {
            Commande c = cellData.getValue();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return new SimpleStringProperty(fmt.format(c.getDate()));
        });
        colonneTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colonneTotal.setCellFactory(col -> new TableCell<Commande, BigDecimal>() {
            @Override protected void updateItem(BigDecimal t, boolean vide) {
                super.updateItem(t, vide);
                setText(vide || t == null ? null : String.format("%.2f €", t));
            }
        });

        // Colonnes des détails
        colonneDetailTitre.setCellValueFactory(new PropertyValueFactory<>("titreVinyle"));
        colonneDetailArtiste.setCellValueFactory(new PropertyValueFactory<>("nomArtiste"));
        colonneDetailQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colonneDetailPrix.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        colonneDetailPrix.setCellFactory(col -> new TableCell<ArticleCommande, BigDecimal>() {
            @Override protected void updateItem(BigDecimal p, boolean vide) {
                super.updateItem(p, vide);
                setText(vide || p == null ? null : String.format("%.2f €", p));
            }
        });
        colonneDetailTotal.setCellValueFactory(new PropertyValueFactory<>("sousTotal"));
        colonneDetailTotal.setCellFactory(col -> new TableCell<ArticleCommande, BigDecimal>() {
            @Override protected void updateItem(BigDecimal t, boolean vide) {
                super.updateItem(t, vide);
                setText(vide || t == null ? null : String.format("%.2f €", t));
            }
        });

        // Quand on clique sur une commande, on affiche ses articles
        tableauCommandes.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> {
            if (nouveau != null) {
                listeArticles.setAll(nouveau.getArticles());
                tableauDetails.setItems(listeArticles);
            }
        });

        // Charger les commandes
        chargerCommandes();
    }

    private void chargerCommandes() {
        if (!SessionUtilisateur.estConnecte()) {
            messageErreur.setText("Vous devez être connecté.");
            messageErreur.setVisible(true);
            return;
        }

        List<Commande> commandes = CommandesDAO.recupererCommandes(
            SessionUtilisateur.getUtilisateur().getId()
        );

        if (commandes.isEmpty()) {
            messageErreur.setText("Aucune commande pour le moment.");
            messageErreur.setVisible(true);
        }

        listeCommandes.setAll(commandes);
        tableauCommandes.setItems(listeCommandes);
    }

    @FXML
    private void retourAccueil() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Stage fenetre = (Stage) messageErreur.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Accueil");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
