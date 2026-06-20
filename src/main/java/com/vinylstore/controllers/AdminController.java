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
        chargerPage("admin_vinyles.fxml", "Vinyl Store - Gestion vinyles");
    }

    @FXML
    private void gererArtistes() {
        chargerPage("admin_artistes.fxml", "Vinyl Store - Gestion artistes");
    }

    @FXML
    private void gererUtilisateurs() {
        chargerPage("admin_users.fxml", "Vinyl Store - Gestion utilisateurs");
    }

    private void chargerPage(String fxml, String titre) {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/" + fxml));
            Stage fenetre = (Stage) btnRetour.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle(titre);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
