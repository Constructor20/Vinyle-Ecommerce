package com.vinylstore.controllers;

import com.vinylstore.models.Vinyl;
import com.vinylstore.utils.Panier;
import com.vinylstore.utils.Panier.ArticlePanier;
import com.vinylstore.utils.SessionUtilisateur;
import com.vinylstore.utils.VenteDAO;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;

/**
 * Contrôleur pour la page du panier
 * Affiche les articles ajoutés et permet de passer commande
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class CartController {

    @FXML private TableView<ArticlePanier> tableauPanier;
    @FXML private TableColumn<ArticlePanier, String> colonneTitre;
    @FXML private TableColumn<ArticlePanier, String> colonneArtiste;
    @FXML private TableColumn<ArticlePanier, BigDecimal> colonnePrixUnitaire;
    @FXML private TableColumn<ArticlePanier, Integer> colonneQuantite;
    @FXML private TableColumn<ArticlePanier, BigDecimal> colonneSousTotal;
    @FXML private Label labelTotal;
    @FXML private Label messageErreur;

    private ObservableList<ArticlePanier> donneesPanier = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liaison des colonnes
        colonneTitre.setCellValueFactory(cellData -> {
            Vinyl v = cellData.getValue().getVinyle();
            return new javafx.beans.property.SimpleStringProperty(v.getTitre());
        });
        colonneArtiste.setCellValueFactory(cellData -> {
            Vinyl v = cellData.getValue().getVinyle();
            return new javafx.beans.property.SimpleStringProperty(v.getNomArtiste());
        });
        colonnePrixUnitaire.setCellValueFactory(cellData -> {
            Vinyl v = cellData.getValue().getVinyle();
            return new javafx.beans.property.SimpleObjectProperty<>(v.getPrix());
        });
        colonneQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        colonneSousTotal.setCellValueFactory(new PropertyValueFactory<>("sousTotal"));

        // Format prix en euros
        String formatPrix = "-fx-font-size: 14px;";
        colonnePrixUnitaire.setCellFactory(col -> new TableCell<ArticlePanier, BigDecimal>() {
            @Override protected void updateItem(BigDecimal p, boolean vide) {
                super.updateItem(p, vide);
                setText(vide || p == null ? null : String.format("%.2f €", p));
                setStyle(formatPrix);
            }
        });
        colonneSousTotal.setCellFactory(col -> new TableCell<ArticlePanier, BigDecimal>() {
            @Override protected void updateItem(BigDecimal p, boolean vide) {
                super.updateItem(p, vide);
                setText(vide || p == null ? null : String.format("%.2f €", p));
                setStyle(formatPrix);
            }
        });

        // Bouton supprimer sur chaque ligne
        TableColumn<ArticlePanier, Void> colonneAction = new TableColumn<>("Action");
        colonneAction.setPrefWidth(100);
        colonneAction.setCellFactory(col -> new TableCell<ArticlePanier, Void>() {
            private final Button btnSupprimer = new Button("Supprimer");
            {
                btnSupprimer.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 11px;");
                btnSupprimer.setOnAction(e -> {
                    ArticlePanier article = getTableView().getItems().get(getIndex());
                    Panier.supprimer(Panier.getArticles().indexOf(article));
                    rafraichirPanier();
                });
            }
            @Override protected void updateItem(Void item, boolean vide) {
                super.updateItem(item, vide);
                setGraphic(vide ? null : btnSupprimer);
            }
        });
        tableauPanier.getColumns().add(colonneAction);

        // Chargement du panier
        rafraichirPanier();
    }

    /**
     * Recharge l'affichage du panier
     */
    private void rafraichirPanier() {
        donneesPanier.setAll(Panier.getArticles());
        tableauPanier.setItems(donneesPanier);
        labelTotal.setText(String.format("%.2f €", Panier.getTotal()));
    }

    /**
     * Vide le panier
     */
    @FXML
    private void viderPanier() {
        Panier.vider();
        rafraichirPanier();
        messageErreur.setVisible(false);
    }

    /**
     * Va vers l'historique des commandes
     */
    @FXML
    private void voirMesCommandes() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/commandes.fxml"));
            Stage fenetre = (Stage) labelTotal.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 900, 700));
            fenetre.setTitle("Vinyl Store - Mes commandes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne au catalogue
     */
    @FXML
    private void retourCatalogue() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/catalog.fxml"));
            Stage fenetre = (Stage) labelTotal.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1200, 700));
            fenetre.setTitle("Vinyl Store - Catalogue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Passe la commande : crée la vente en BDD + met à jour les stocks
     */
    @FXML
    private void passerCommande() {
        if (Panier.getArticles().isEmpty()) {
            messageErreur.setText("Votre panier est vide !");
            messageErreur.setVisible(true);
            return;
        }

        if (!SessionUtilisateur.estConnecte()) {
            messageErreur.setText("Vous devez être connecté pour commander.");
            messageErreur.setVisible(true);
            return;
        }

        messageErreur.setVisible(false);

        boolean succes = VenteDAO.creerCommande(
            SessionUtilisateur.getUtilisateur().getId(),
            Panier.getTotal()
        );

        if (succes) {
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("Commande réussie !");
            confirmation.setHeaderText(null);
            confirmation.setContentText("Votre commande a été validée !\nTotal : " + String.format("%.2f €", Panier.getTotal()));
            confirmation.showAndWait();

            Panier.vider();
            rafraichirPanier();
        } else {
            messageErreur.setText("Erreur lors de la commande. Vérifiez la console.");
            messageErreur.setVisible(true);
        }
    }
}
