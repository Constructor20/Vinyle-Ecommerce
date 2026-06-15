package com.vinylstore.controllers;

import com.vinylstore.models.User;
import com.vinylstore.utils.SessionUtilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Contrôleur pour la page d'accueil après connexion
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class HomeController {

    @FXML private Label labelBienvenue;  // "Bienvenue, Christophe !"

    private User utilisateurConnecte;

    /**
     * Affiche le nom de l'utilisateur connecté dans le label
     */
    public void afficherUtilisateur(User utilisateur) {
        this.utilisateurConnecte = utilisateur;
        labelBienvenue.setText("Bienvenue, " + utilisateur.getNomComplet() + " !");
    }

    /**
     * Va vers le catalogue des vinyles
     */
    @FXML
    private void voirCatalogue(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/catalog.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1200, 700));
            fenetre.setTitle("Vinyl Store - Catalogue");
            fenetre.setResizable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Va vers le panier
     */
    @FXML
    private void voirPanier(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/cart.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 900, 600));
            fenetre.setTitle("Vinyl Store - Panier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Va vers l'historique des commandes
     */
    @FXML
    private void voirCommandes(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/commandes.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 900, 700));
            fenetre.setTitle("Vinyl Store - Mes commandes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Déconnexion : retour à l'écran de connexion
     */
    @FXML
    private void seDeconnecter(ActionEvent event) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage fenetre = (Stage) labelBienvenue.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 400, 400));
            fenetre.setTitle("Vinyl Store - Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
