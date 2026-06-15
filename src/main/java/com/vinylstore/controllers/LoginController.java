package com.vinylstore.controllers;

import com.vinylstore.models.User;
import com.vinylstore.utils.SessionUtilisateur;
import com.vinylstore.utils.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Contrôleur pour la page de connexion
 * Quand l'utilisateur clique sur "Se connecter", on vérifie ses infos dans la BDD
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class LoginController {

    // ========== Les éléments de l'interface (liés au FXML) ==========
    @FXML private TextField champEmail;       // champ où on tape l'email
    @FXML private PasswordField champMotDePasse; // champ où on tape le mot de passe
    @FXML private Label messageErreur;        // pour afficher les erreurs

    /**
     * Se déclenche quand on clique sur "Se connecter"
     */
    @FXML
    private void seConnecter() {
        
        // On récupère ce que l'utilisateur a tapé
        String email = champEmail.getText().trim();
        String motDePasse = champMotDePasse.getText();
        
        // Vérification : les champs ne doivent pas être vides
        if (email.isEmpty() || motDePasse.isEmpty()) {
            messageErreur.setText("Veuillez remplir tous les champs.");
            messageErreur.setVisible(true);
            return; // on s'arrête là
        }
        
        // On cherche l'utilisateur dans la BDD
        User utilisateur = UtilisateurDAO.findByEmail(email);
        
        if (utilisateur == null) {
            // L'email n'existe pas dans la BDD
            messageErreur.setText("Email ou mot de passe incorrect.");
            messageErreur.setVisible(true);
            return;
        }
        
        // On vérifie le mot de passe avec BCrypt
        boolean motDePasseCorrect = User.verifierMotDePasse(motDePasse, utilisateur.getMotDePasse());
        
        if (!motDePasseCorrect) {
            // Le mot de passe ne correspond pas
            messageErreur.setText("Email ou mot de passe incorrect.");
            messageErreur.setVisible(true);
            return;
        }
        
        // ===== Tout est bon, on connecte l'utilisateur ! =====
        SessionUtilisateur.connecter(utilisateur);
        System.out.println("Connexion réussie pour : " + utilisateur.getNomComplet());
        
        try {
            // On charge la page d'accueil
            FXMLLoader chargeur = new FXMLLoader(getClass().getResource("/views/home.fxml"));
            Parent racine = chargeur.load();
            
            // On passe l'utilisateur connecté au contrôleur de l'accueil
            HomeController accueil = chargeur.getController();
            accueil.afficherUtilisateur(utilisateur);
            
            // On change la fenêtre pour afficher l'accueil
            Stage fenetre = (Stage) champEmail.getScene().getWindow();
            fenetre.setScene(new Scene(racine));
            fenetre.setTitle("Vinyl Store - Accueil");
            
        } catch (Exception e) {
            messageErreur.setText("Erreur technique : " + e.getMessage());
            messageErreur.setVisible(true);
            e.printStackTrace();
        }
    }

    /**
     * Se déclenche quand on clique sur "S'inscrire"
     */
    @FXML
    private void allerVersInscription() {
        try {
            Parent racine = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
            Stage fenetre = (Stage) champEmail.getScene().getWindow();
            fenetre.setScene(new Scene(racine, 400, 450));
            fenetre.setTitle("Vinyl Store - Inscription");
        } catch (Exception e) {
            messageErreur.setText("Erreur : " + e.getMessage());
            messageErreur.setVisible(true);
            e.printStackTrace();
        }
    }
}
