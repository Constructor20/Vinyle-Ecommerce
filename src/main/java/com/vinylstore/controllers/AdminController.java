package com.vinylstore.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Contrôleur pour le panneau d'administration (CRUD à venir)
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class AdminController {

    @FXML private Button btnRetour;

    @FXML
    private void gererVinyles() {
        System.out.println("CRUD Vinyles - à implémenter");
    }

    @FXML
    private void gererArtistes() {
        System.out.println("CRUD Artistes - à implémenter");
    }

    @FXML
    private void gererUtilisateurs() {
        System.out.println("CRUD Utilisateurs - à implémenter");
    }

    @FXML
    private void retourAccueil() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Stage fenetre = (Stage) btnRetour.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Accueil");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
