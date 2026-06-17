package com.vinylstore.controllers;

import com.vinylstore.models.User;
import com.vinylstore.utils.SessionUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Contrôleur pour la page d'accueil après connexion
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class HomeController {

    @FXML private Label labelBienvenue;
    @FXML private Button btnAdmin;

    /**
     * S'exécute automatiquement à chaque chargement de la page d'accueil
     * Récupère l'utilisateur depuis la session pour restaurer l'affichage
     */
    @FXML
    public void initialize() {
        if (SessionUtilisateur.estConnecte()) {
            User user = SessionUtilisateur.getUtilisateur();
            labelBienvenue.setText("Bienvenue, " + user.getNomComplet() + " !");
            btnAdmin.setVisible(user.isEstAdmin());
        }
    }

    @FXML
    private void voirCatalogue(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/catalog.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Catalogue");
            fenetre.setResizable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voirPanier(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/cart.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Panier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voirCommandes(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/commandes.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Mes commandes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voirAdmin(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/admin.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Administration");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void seDeconnecter(ActionEvent event) {
        SessionUtilisateur.deconnecter();
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 420, 480));
            fenetre.setTitle("Vinyl Store - Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
