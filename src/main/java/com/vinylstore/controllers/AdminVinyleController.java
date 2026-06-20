package com.vinylstore.controllers;

import com.vinylstore.models.Artist;
import com.vinylstore.models.Vinyl;
import com.vinylstore.utils.ArtistDAO;
import com.vinylstore.utils.VinylDAO;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AdminVinyleController {

    @FXML private TableView<Vinyl> tableauVinyles;
    @FXML private TableColumn<Vinyl, Integer> colonneId;
    @FXML private TableColumn<Vinyl, String> colonneTitre;
    @FXML private TableColumn<Vinyl, String> colonneArtiste;
    @FXML private TableColumn<Vinyl, String> colonneGenre;
    @FXML private TableColumn<Vinyl, Integer> colonneAnnee;
    @FXML private TableColumn<Vinyl, BigDecimal> colonnePrix;
    @FXML private TableColumn<Vinyl, Integer> colonneStock;
    @FXML private Label messageErreur;

    private ObservableList<Vinyl> donnees = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colonneId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colonneTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colonneArtiste.setCellValueFactory(new PropertyValueFactory<>("nomArtiste"));
        colonneGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colonneAnnee.setCellValueFactory(new PropertyValueFactory<>("anneeSortie"));
        colonnePrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colonneStock.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));

        // Format prix
        colonnePrix.setCellFactory(col -> new TableCell<Vinyl, BigDecimal>() {
            @Override protected void updateItem(BigDecimal p, boolean vide) {
                super.updateItem(p, vide);
                setText(vide || p == null ? null : String.format("%.2f €", p));
            }
        });

        chargerDonnees();
    }

    private void chargerDonnees() {
        donnees.setAll(VinylDAO.recupererTousAdmin());
        tableauVinyles.setItems(donnees);
    }

    @FXML
    private void ajouterVinyle() {
        Vinyl v = dialogVinyle(null);
        if (v != null) {
            VinylDAO.ajouter(v);
            chargerDonnees();
            messageErreur.setText("Vinyle ajouté : " + v.getTitre());
            messageErreur.setStyle("-fx-text-fill: #27ae60;");
            messageErreur.setVisible(true);
        }
    }

    @FXML
    private void modifierVinyle() {
        Vinyl selectionne = tableauVinyles.getSelectionModel().getSelectedItem();
        if (selectionne == null) {
            messageErreur.setText("Veuillez sélectionner un vinyle.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
            return;
        }

        Vinyl v = dialogVinyle(selectionne);
        if (v != null) {
            VinylDAO.modifier(v);
            chargerDonnees();
            messageErreur.setText("Vinyle modifié : " + v.getTitre());
            messageErreur.setStyle("-fx-text-fill: #27ae60;");
            messageErreur.setVisible(true);
        }
    }

    @FXML
    private void supprimerVinyle() {
        Vinyl selectionne = tableauVinyles.getSelectionModel().getSelectedItem();
        if (selectionne == null) {
            messageErreur.setText("Veuillez sélectionner un vinyle.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Supprimer le vinyle \"" + selectionne.getTitre() + "\" ?");
        Optional<ButtonType> reponse = confirmation.showAndWait();

        if (reponse.isPresent() && reponse.get() == ButtonType.OK) {
            VinylDAO.supprimer(selectionne.getId());
            chargerDonnees();
            messageErreur.setText("Vinyle supprimé.");
            messageErreur.setStyle("-fx-text-fill: #e74c3c;");
            messageErreur.setVisible(true);
        }
    }

    private Vinyl dialogVinyle(Vinyl existant) {

        Dialog<Vinyl> dialog = new Dialog<>();
        dialog.setTitle(existant == null ? "Ajouter un vinyle" : "Modifier un vinyle");
        dialog.setHeaderText(null);

        ButtonType btnValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnValider, ButtonType.CANCEL);

        TextField champTitre = new TextField();
        champTitre.setPromptText("Titre");

        ComboBox<Artist> comboArtiste = new ComboBox<>();
        List<Artist> artistes = ArtistDAO.recupererTous();
        comboArtiste.setItems(FXCollections.observableArrayList(artistes));
        comboArtiste.setPromptText("Artiste");

        TextField champGenre = new TextField();
        champGenre.setPromptText("Genre");

        Spinner<Integer> spinnerAnnee = new Spinner<>(1900, 2030, 2024);

        TextField champPrix = new TextField();
        champPrix.setPromptText("Prix (ex: 29.99)");

        Spinner<Integer> spinnerStock = new Spinner<>(0, 9999, 1);

        if (existant != null) {
            champTitre.setText(existant.getTitre());
            for (Artist a : artistes) {
                if (a.getId() == existant.getIdArtiste()) {
                    comboArtiste.setValue(a);
                    break;
                }
            }
            champGenre.setText(existant.getGenre());
            spinnerAnnee.getValueFactory().setValue(existant.getAnneeSortie());
            champPrix.setText(String.valueOf(existant.getPrix()));
            spinnerStock.getValueFactory().setValue(existant.getQuantiteStock());
        }

        GridPane grille = new GridPane();
        grille.setHgap(10);
        grille.setVgap(10);
        grille.add(new Label("Titre :"), 0, 0);
        grille.add(champTitre, 1, 0);
        grille.add(new Label("Artiste :"), 0, 1);
        grille.add(comboArtiste, 1, 1);
        grille.add(new Label("Genre :"), 0, 2);
        grille.add(champGenre, 1, 2);
        grille.add(new Label("Année :"), 0, 3);
        grille.add(spinnerAnnee, 1, 3);
        grille.add(new Label("Prix :"), 0, 4);
        grille.add(champPrix, 1, 4);
        grille.add(new Label("Stock :"), 0, 5);
        grille.add(spinnerStock, 1, 5);

        dialog.getDialogPane().setContent(grille);

        dialog.setResultConverter(bouton -> {
            if (bouton == btnValider) {
                if (champTitre.getText().trim().isEmpty() || comboArtiste.getValue() == null
                    || champGenre.getText().trim().isEmpty() || champPrix.getText().trim().isEmpty()) {
                    return null;
                }
                Vinyl v = existant != null ? existant : new Vinyl();
                v.setTitre(champTitre.getText().trim());
                v.setIdArtiste(comboArtiste.getValue().getId());
                v.setNomArtiste(comboArtiste.getValue().getNom());
                v.setGenre(champGenre.getText().trim());
                v.setAnneeSortie(spinnerAnnee.getValue());
                try {
                    v.setPrix(new BigDecimal(champPrix.getText().trim().replace(",", ".")));
                } catch (Exception e) {
                    return null;
                }
                v.setQuantiteStock(spinnerStock.getValue());
                return v;
            }
            return null;
        });

        Optional<Vinyl> resultat = dialog.showAndWait();
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
