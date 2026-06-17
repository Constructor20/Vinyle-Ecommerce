package com.vinylstore.controllers;

import com.vinylstore.models.User;
import com.vinylstore.utils.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Contrôleur pour la page d'inscription
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class RegisterController {

    // ========== Les champs du formulaire ==========
    @FXML private TextField champNom;          // Nom complet
    @FXML private TextField champEmail;        // Email
    @FXML private PasswordField champMotDePasse;    // Mot de passe
    @FXML private PasswordField champConfirmation; // Confirmation du mot de passe
    @FXML private Label messageErreur;         // Pour les messages d'erreur

    /**
     * Se déclenche quand on clique sur "S'inscrire"
     */
    @FXML
    private void sinscrire() {
        
        // Étape 1 : On récupère ce que l'utilisateur a tapé
        String nom = champNom.getText().trim();
        String email = champEmail.getText().trim();
        String motDePasse = champMotDePasse.getText();
        String confirmation = champConfirmation.getText();
        
        // Étape 2 : On vérifie que tous les champs obligatoires sont remplis
        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty() || confirmation.isEmpty()) {
            messageErreur.setText("Veuillez remplir tous les champs.");
            messageErreur.setVisible(true);
            return;
        }
        
        // Étape 3 : On vérifie que les deux mots de passe sont identiques
        if (!motDePasse.equals(confirmation)) {
            messageErreur.setText("Les mots de passe ne sont pas identiques.");
            messageErreur.setVisible(true);
            return;
        }
        
        // Étape 4 : On vérifie que le mot de passe fait au moins 6 caractères
        if (motDePasse.length() < 6) {
            messageErreur.setText("Le mot de passe doit faire au moins 6 caractères.");
            messageErreur.setVisible(true);
            return;
        }
        
        // Étape 5 : On vérifie que l'email n'est pas déjà utilisé
        User existant = UtilisateurDAO.findByEmail(email);
        if (existant != null) {
            messageErreur.setText("Cet email est déjà utilisé par un autre compte.");
            messageErreur.setVisible(true);
            return;
        }
        
        // Étape 6 : On crée l'utilisateur
        User nouvelUtilisateur = new User();
        nouvelUtilisateur.setNomComplet(nom);
        nouvelUtilisateur.setEmail(email);
        
        // On hash le mot de passe avant de le stocker dans la BDD
        String motDePasseHashe = User.hasherMotDePasse(motDePasse);
        nouvelUtilisateur.setMotDePasse(motDePasseHashe);
        nouvelUtilisateur.setEstAdmin(false);
        
        // Étape 7 : On sauvegarde dans la BDD
        UtilisateurDAO.creer(nouvelUtilisateur);
        
        // Étape 8 : On affiche un message de succès
        Alert succes = new Alert(Alert.AlertType.INFORMATION);
        succes.setTitle("Inscription réussie !");
        succes.setHeaderText(null);
        succes.setContentText("Votre compte a été créé avec succès !\nVous pouvez maintenant vous connecter.");
        succes.showAndWait();
        
        // Étape 9 : On retourne vers la page de connexion
        retournerVersConnexion();
    }

    /**
     * Retourne à la page de connexion
     */
    @FXML
    private void retournerVersConnexion() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage fenetre = (Stage) champNom.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 420, 480));
            fenetre.setTitle("Vinyl Store - Connexion");
        } catch (Exception e) {
            messageErreur.setText("Erreur technique : " + e.getMessage());
            messageErreur.setVisible(true);
            e.printStackTrace();
        }
    }
}
