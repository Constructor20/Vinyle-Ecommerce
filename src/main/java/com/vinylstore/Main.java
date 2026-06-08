package com.vinylstore;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Point d'entrée de l'application Vinyl Store
 * C'est ici que tout commence !
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class Main extends Application {

    @Override
    public void start(Stage fenetrePrincipale) throws Exception {
        
        // On charge la page de connexion (login.fxml)
        Parent racine = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        
        // On crée la scène avec la page chargée (400px de large, 400px de haut)
        Scene scene = new Scene(racine, 400, 400);
        
        // On configure la fenêtre principale
        fenetrePrincipale.setTitle("Vinyl Store - Connexion");
        fenetrePrincipale.setScene(scene);
        fenetrePrincipale.setResizable(false); // on ne peut pas redimensionner
        fenetrePrincipale.show(); // on affiche la fenêtre
    }

    public static void main(String[] args) {
        // Lancement de l'application JavaFX
        launch(args);
    }
}
