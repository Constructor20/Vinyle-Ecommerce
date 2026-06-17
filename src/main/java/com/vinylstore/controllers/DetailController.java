package com.vinylstore.controllers;

import com.vinylstore.models.Vinyl;
import com.vinylstore.utils.Panier;
import com.vinylstore.utils.VinylSelection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Contrôleur pour la page détails d'un vinyle
 * Affiche toutes les infos du vinyle en grand
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class DetailController {

    @FXML private Label labelTitre;
    @FXML private Label labelArtiste;
    @FXML private Label labelGenre;
    @FXML private Label labelAnnee;
    @FXML private Label labelPrix;
    @FXML private Label labelStock;
    @FXML private Label messageErreur;
    @FXML private VBox bandeauCouleur;
    @FXML private Spinner<Integer> selecteurQuantite;
    @FXML private Button btnAjouter;

    private Vinyl vinyleActuel;

    @FXML
    public void initialize() {
        vinyleActuel = VinylSelection.getVinyle();

        if (vinyleActuel == null) {
            messageErreur.setText("Erreur : vinyle introuvable.");
            messageErreur.setVisible(true);
            return;
        }

        // Remplir les infos
        labelTitre.setText(vinyleActuel.getTitre());
        labelArtiste.setText(vinyleActuel.getNomArtiste());
        labelGenre.setText(vinyleActuel.getGenre());
        labelAnnee.setText(String.valueOf(vinyleActuel.getAnneeSortie()));
        labelPrix.setText(String.format("%.2f €", vinyleActuel.getPrix()));

        // Couleur du bandeau selon le genre
        String couleur = getCouleurGenre(vinyleActuel.getGenre());
        bandeauCouleur.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 12;");

        // Stock
        if (vinyleActuel.getQuantiteStock() <= 0) {
            labelStock.setText("Rupture de stock");
            labelStock.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px; -fx-font-weight: bold;");
            btnAjouter.setDisable(true);
            selecteurQuantite.setDisable(true);
        } else if (vinyleActuel.getQuantiteStock() < 5) {
            labelStock.setText("Plus que " + vinyleActuel.getQuantiteStock() + " exemplaires !");
            labelStock.setStyle("-fx-text-fill: #e67e22; -fx-font-size: 14px; -fx-font-weight: bold;");
            selecteurQuantite.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, vinyleActuel.getQuantiteStock(), 1));
        } else {
            labelStock.setText("En stock (" + vinyleActuel.getQuantiteStock() + " disponibles)");
            labelStock.setStyle("-fx-text-fill: #27ae60; -fx-font-size: 14px; -fx-font-weight: bold;");
        }

        selecteurQuantite.setPrefWidth(80);
    }

    @FXML
    private void ajouterAuPanier() {
        if (vinyleActuel == null) return;

        int quantite = selecteurQuantite.getValue();
        Panier.ajouter(vinyleActuel, quantite);
        messageErreur.setText("Ajouté au panier : " + vinyleActuel.getTitre() + " x" + quantite);
        messageErreur.setStyle("-fx-text-fill: #27ae60;");
        messageErreur.setVisible(true);
    }

    @FXML
    private void retourCatalogue() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/catalog.fxml"));
            Stage fenetre = (Stage) labelTitre.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Catalogue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voirPanier() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/cart.fxml"));
            Stage fenetre = (Stage) labelTitre.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Panier");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void retournerAccueil() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Stage fenetre = (Stage) labelTitre.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Accueil");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCouleurGenre(String genre) {
        if (genre == null) return "#3498db";
        return switch (genre.toLowerCase()) {
            case "rock" -> "#e74c3c";
            case "pop" -> "#e91e63";
            case "jazz" -> "#f39c12";
            case "electronic" -> "#9b59b6";
            case "soul" -> "#e67e22";
            case "reggae" -> "#2ecc71";
            case "grunge" -> "#34495e";
            case "progressive rock" -> "#1abc9c";
            case "modal jazz" -> "#f1c40f";
            case "jazz fusion" -> "#d35400";
            default -> "#3498db";
        };
    }
}
