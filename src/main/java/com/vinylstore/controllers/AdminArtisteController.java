package com.vinylstore.controllers;

import com.vinylstore.models.Artist;
import com.vinylstore.utils.ArtistDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Optional;

public class AdminArtisteController {

    @FXML private TableView<Artist> tableauArtistes;
    @FXML private TableColumn<Artist, Integer> colonneId;
    @FXML private TableColumn<Artist, String> colonneNom;
    @FXML private Label messageErreur;

    private ObservableList<Artist> donnees = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        chargerDonnees();
    }

    private void chargerDonnees() {
        donnees.setAll(ArtistDAO.recupererTous());
        tableauArtistes.setItems(donnees);
    }

    @FXML
    private void ajouterArtiste() {
        Artist a = dialogArtiste(null);
        if (a != null) {
            ArtistDAO.ajouter(a);
            chargerDonnees();
            messageErreur.setText("Artiste ajouté : " + a.getNom());
            messageErreur.setStyle("-fx-text-fill: #27ae60;");
            messageErreur.setVisible(true);
        }
    }

    @FXML
    private void modifierArtiste() {
        Artist selectionne = tableauArtistes.getSelectionModel().getSelectedItem();
        if (selectionne == null) {
            messageErreur.setText("Veuillez sélectionner un artiste.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
            return;
        }

        Artist a = dialogArtiste(selectionne);
        if (a != null) {
            ArtistDAO.modifier(a);
            chargerDonnees();
            messageErreur.setText("Artiste modifié : " + a.getNom());
            messageErreur.setStyle("-fx-text-fill: #27ae60;");
            messageErreur.setVisible(true);
        }
    }

    @FXML
    private void supprimerArtiste() {
        Artist selectionne = tableauArtistes.getSelectionModel().getSelectedItem();
        if (selectionne == null) {
            messageErreur.setText("Veuillez sélectionner un artiste.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Supprimer l'artiste \"" + selectionne.getNom() + "\" ?\n(Cela ne supprime pas ses vinyles)");
        Optional<ButtonType> reponse = confirmation.showAndWait();

        if (reponse.isPresent() && reponse.get() == ButtonType.OK) {
            ArtistDAO.supprimer(selectionne.getId());
            chargerDonnees();
            messageErreur.setText("Artiste supprimé.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
        }
    }

    private Artist dialogArtiste(Artist existant) {

        Dialog<Artist> dialog = new Dialog<>();
        dialog.setTitle(existant == null ? "Ajouter un artiste" : "Modifier un artiste");
        dialog.setHeaderText(null);

        ButtonType btnValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnValider, ButtonType.CANCEL);

        TextField champNom = new TextField();
        champNom.setPromptText("Nom de l'artiste");
        if (existant != null) {
            champNom.setText(existant.getNom());
        }

        GridPane grille = new GridPane();
        grille.setHgap(10);
        grille.setVgap(10);
        grille.add(new Label("Nom :"), 0, 0);
        grille.add(champNom, 1, 0);

        dialog.getDialogPane().setContent(grille);

        dialog.setResultConverter(bouton -> {
            if (bouton == btnValider && !champNom.getText().trim().isEmpty()) {
                Artist a = existant != null ? existant : new Artist();
                a.setNom(champNom.getText().trim());
                return a;
            }
            return null;
        });

        Optional<Artist> resultat = dialog.showAndWait();
        return resultat.orElse(null);
    }

    @FXML
    private void retourAdmin() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/admin.fxml"));
            Stage fenetre = (Stage) messageErreur.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Administration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
