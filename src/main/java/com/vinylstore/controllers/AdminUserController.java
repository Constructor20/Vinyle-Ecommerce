package com.vinylstore.controllers;

import com.vinylstore.models.User;
import com.vinylstore.utils.UtilisateurDAO;
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

public class AdminUserController {

    @FXML private TableView<User> tableauUsers;
    @FXML private TableColumn<User, Integer> colonneId;
    @FXML private TableColumn<User, String> colonneNom;
    @FXML private TableColumn<User, String> colonneEmail;
    @FXML private TableColumn<User, Boolean> colonneAdmin;
    @FXML private Label messageErreur;

    private ObservableList<User> donnees = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneNom.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        colonneEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colonneAdmin.setCellValueFactory(new PropertyValueFactory<>("estAdmin"));

        colonneAdmin.setCellFactory(col -> new TableCell<User, Boolean>() {
            @Override protected void updateItem(Boolean admin, boolean vide) {
                super.updateItem(admin, vide);
                setText(vide || admin == null ? null : admin ? "Oui" : "Non");
            }
        });

        chargerDonnees();
    }

    private void chargerDonnees() {
        donnees.setAll(UtilisateurDAO.recupererTous());
        tableauUsers.setItems(donnees);
    }

    @FXML
    private void modifierUser() {
        User selectionne = tableauUsers.getSelectionModel().getSelectedItem();
        if (selectionne == null) {
            messageErreur.setText("Veuillez sélectionner un utilisateur.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
            return;
        }

        User u = dialogUser(selectionne);
        if (u != null) {
            UtilisateurDAO.modifier(u);
            chargerDonnees();
            messageErreur.setText("Utilisateur modifié : " + u.getNomComplet());
            messageErreur.setStyle("-fx-text-fill: #27ae60;");
            messageErreur.setVisible(true);
        }
    }

    @FXML
    private void supprimerUser() {
        User selectionne = tableauUsers.getSelectionModel().getSelectedItem();
        if (selectionne == null) {
            messageErreur.setText("Veuillez sélectionner un utilisateur.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Supprimer l'utilisateur \"" + selectionne.getNomComplet() + "\" ?\nSes commandes seront aussi supprimées.");
        Optional<ButtonType> reponse = confirmation.showAndWait();

        if (reponse.isPresent() && reponse.get() == ButtonType.OK) {
            UtilisateurDAO.supprimer(selectionne.getId());
            chargerDonnees();
            messageErreur.setText("Utilisateur supprimé.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
        }
    }

    private User dialogUser(User existant) {

        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Modifier un utilisateur");
        dialog.setHeaderText(null);

        ButtonType btnValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnValider, ButtonType.CANCEL);

        TextField champNom = new TextField(existant.getNomComplet());
        champNom.setPromptText("Nom");

        TextField champEmail = new TextField(existant.getEmail());
        champEmail.setPromptText("Email");

        CheckBox checkAdmin = new CheckBox("Administrateur");
        checkAdmin.setSelected(existant.isEstAdmin());

        GridPane grille = new GridPane();
        grille.setHgap(10);
        grille.setVgap(10);
        grille.add(new Label("Nom :"), 0, 0);
        grille.add(champNom, 1, 0);
        grille.add(new Label("Email :"), 0, 1);
        grille.add(champEmail, 1, 1);
        grille.add(checkAdmin, 1, 2);

        dialog.getDialogPane().setContent(grille);

        dialog.setResultConverter(bouton -> {
            if (bouton == btnValider && !champNom.getText().trim().isEmpty() && !champEmail.getText().trim().isEmpty()) {
                existant.setNomComplet(champNom.getText().trim());
                existant.setEmail(champEmail.getText().trim());
                existant.setEstAdmin(checkAdmin.isSelected());
                return existant;
            }
            return null;
        });

        Optional<User> resultat = dialog.showAndWait();
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
