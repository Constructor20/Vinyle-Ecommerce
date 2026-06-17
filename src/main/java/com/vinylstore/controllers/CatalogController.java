package com.vinylstore.controllers;

import com.vinylstore.models.Vinyl;
import com.vinylstore.utils.Panier;
import com.vinylstore.utils.VinylDAO;
import com.vinylstore.utils.VinylSelection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

/**
 * Contrôleur pour le catalogue des vinyles
 * Affiche les vinyles sous forme de cartes (comme un vrai site e-commerce)
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class CatalogController {

    @FXML private TextField champRecherche;
    @FXML private ComboBox<String> filtreGenre;
    @FXML private TilePane grilleVinyles;
    @FXML private Label messageErreur;

    // Tous les vinyles chargés depuis la BDD
    private List<Vinyl> tousLesVinyles;

    /**
     * S'exécute automatiquement au chargement de la vue
     */
    @FXML
    public void initialize() {
        chargerGenres();
        chargerTousLesVinyles();

        // Recherche en temps réel
        champRecherche.textProperty().addListener((obs, ancien, nouveau) -> {
            appliquerFiltres();
        });

        // Filtre par genre
        filtreGenre.setOnAction(e -> appliquerFiltres());
    }

    /**
     * Remplit la liste des genres
     */
    private void chargerGenres() {
        List<String> genres = VinylDAO.recupererGenres();
        filtreGenre.getItems().clear();
        filtreGenre.getItems().add("Tous les genres");
        filtreGenre.getItems().addAll(genres);
        filtreGenre.setValue("Tous les genres");
    }

    /**
     * Charge tous les vinyles et les affiche
     */
    private void chargerTousLesVinyles() {
        tousLesVinyles = VinylDAO.recupererTous();
        afficherVinyles(tousLesVinyles);
    }

    /**
     * Filtre les vinyles selon la recherche et le genre
     */
    private void appliquerFiltres() {
        String recherche = champRecherche.getText().trim().toLowerCase();
        String genreSelectionne = filtreGenre.getValue();

        List<Vinyl> filtres = tousLesVinyles.stream()
            .filter(v -> {
                if (genreSelectionne != null && !genreSelectionne.equals("Tous les genres")) {
                    if (!v.getGenre().equals(genreSelectionne)) return false;
                }
                if (!recherche.isEmpty()) {
                    return v.getTitre().toLowerCase().contains(recherche)
                        || v.getNomArtiste().toLowerCase().contains(recherche);
                }
                return true;
            })
            .toList();

        afficherVinyles(filtres);
    }

    /**
     * Affiche la liste des vinyles sous forme de cartes
     */
    private void afficherVinyles(List<Vinyl> vinyles) {
        grilleVinyles.getChildren().clear();

        if (vinyles.isEmpty()) {
            Label aucun = new Label("Aucun vinyle trouvé.");
            aucun.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d; -fx-padding: 40;");
            grilleVinyles.getChildren().add(aucun);
            return;
        }

        for (Vinyl v : vinyles) {
            grilleVinyles.getChildren().add(creerCarteVinyle(v));
        }
    }

    /**
     * Crée une carte visuelle pour un vinyle
     */
    private VBox creerCarteVinyle(Vinyl v) {

        // Couleur de fond selon le genre
        String couleurGenre = getCouleurGenre(v.getGenre());

        // === La carte ===
        VBox carte = new VBox(6);
        carte.setPrefWidth(200);
        carte.setPrefHeight(260);
        carte.setPadding(new Insets(12));
        carte.setAlignment(Pos.TOP_CENTER);
        carte.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4); " +
            "-fx-border-radius: 10;"
        );

        // Effet au survol
        carte.setOnMouseEntered(e ->
            carte.setStyle(
                "-fx-background-color: white; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 15, 0, 0, 6); " +
                "-fx-border-radius: 10; -fx-scale-x: 1.02; -fx-scale-y: 1.02;"
            )
        );
        carte.setOnMouseExited(e ->
            carte.setStyle(
                "-fx-background-color: white; " +
                "-fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4); " +
                "-fx-border-radius: 10; -fx-scale-x: 1; -fx-scale-y: 1;"
            )
        );

        // Curseur main pour indiquer que c'est cliquable
        carte.setStyle(carte.getStyle() + "-fx-cursor: hand;");

        // Clic sur la carte → page détails
        carte.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                VinylSelection.setVinyle(v);
                ouvrirDetails();
            }
        });

        // === Bandeau coloré en haut (simule la pochette) ===
        Label bandeau = new Label("🎵");
        bandeau.setPrefWidth(176);
        bandeau.setPrefHeight(70);
        bandeau.setAlignment(Pos.CENTER);
        bandeau.setStyle(
            "-fx-background-color: " + couleurGenre + "; " +
            "-fx-background-radius: 8; " +
            "-fx-font-size: 28px;"
        );

        // === Titre ===
        Label titre = new Label(v.getTitre());
        titre.setFont(Font.font("System", FontWeight.BOLD, 14));
        titre.setWrapText(true);
        titre.setAlignment(Pos.CENTER);
        titre.setMaxWidth(190);

        // === Artiste ===
        Label artiste = new Label(v.getNomArtiste());
        artiste.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");
        artiste.setWrapText(true);
        artiste.setAlignment(Pos.CENTER);

        // === Infos (genre + année) ===
        Label infos = new Label(v.getGenre() + " · " + v.getAnneeSortie());
        infos.setStyle("-fx-font-size: 11px; -fx-text-fill: #bdc3c7;");

        // === Prix ===
        Label prix = new Label(String.format("%.2f €", v.getPrix()));
        prix.setFont(Font.font("System", FontWeight.BOLD, 18));
        prix.setStyle("-fx-text-fill: #27ae60;");

        // === Stock ===
        String texteStock;
        String couleurStock;
        if (v.getQuantiteStock() <= 0) {
            texteStock = "Rupture de stock";
            couleurStock = "#e74c3c";
        } else if (v.getQuantiteStock() < 5) {
            texteStock = "Plus que " + v.getQuantiteStock() + " exemplaires !";
            couleurStock = "#e67e22";
        } else {
            texteStock = "En stock (" + v.getQuantiteStock() + " dispo.)";
            couleurStock = "#27ae60";
        }
        Label stock = new Label(texteStock);
        stock.setStyle("-fx-font-size: 11px; -fx-text-fill: " + couleurStock + ";");

        // === Sélecteur de quantité + Bouton Ajouter au panier ===
        Spinner<Integer> selecteurQuantite = new Spinner<>(1, Math.max(1, v.getQuantiteStock()), 1);
        selecteurQuantite.setPrefWidth(60);
        selecteurQuantite.setEditable(true);

        Button btnAjouter = new Button("Ajouter au panier");
        btnAjouter.setPrefWidth(110);
        btnAjouter.setStyle(
            "-fx-background-color: #3498db; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 11px; " +
            "-fx-padding: 6; " +
            "-fx-background-radius: 5;"
        );
        btnAjouter.setOnAction(e -> {
            messageErreur.setVisible(false);
            int quantite = selecteurQuantite.getValue();
            Panier.ajouter(v, quantite);
            System.out.println("Ajouté au panier : " + v.getTitre() + " x" + quantite);
        });

        // On désactive si rupture de stock
        if (v.getQuantiteStock() <= 0) {
            btnAjouter.setDisable(true);
            selecteurQuantite.setDisable(true);
            btnAjouter.setStyle(
                "-fx-background-color: #bdc3c7; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 11px; " +
                "-fx-padding: 6; " +
                "-fx-background-radius: 5;"
            );
        }

        HBox zoneAjout = new HBox(5, selecteurQuantite, btnAjouter);
        zoneAjout.setAlignment(Pos.CENTER);

        carte.getChildren().addAll(bandeau, titre, artiste, infos, prix, stock, zoneAjout);

        return carte;
    }

    /**
     * Retourne une couleur selon le genre du vinyle
     */
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

    /**
     * Ouvre la page détails du vinyle sélectionné
     */
    private void ouvrirDetails() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/details.fxml"));
            Stage fenetre = (Stage) champRecherche.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Détails");
        } catch (Exception e) {
            messageErreur.setText("Erreur : " + e.getMessage());
            messageErreur.setVisible(true);
            e.printStackTrace();
        }
    }

    /**
     * Va vers l'historique des commandes
     */
    @FXML
    private void voirMesCommandes() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/commandes.fxml"));
            Stage fenetre = (Stage) champRecherche.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Mes commandes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Va vers la page du panier
     */
    @FXML
    private void voirPanier() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/cart.fxml"));
            Stage fenetre = (Stage) champRecherche.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Panier");
        } catch (Exception e) {
            messageErreur.setText("Erreur : " + e.getMessage());
            messageErreur.setVisible(true);
            e.printStackTrace();
        }
    }

    /**
     * Retourne à l'accueil
     */
    @FXML
    private void retournerAccueil() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
            Stage fenetre = (Stage) champRecherche.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 1000, 650));
            fenetre.setTitle("Vinyl Store - Accueil");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
