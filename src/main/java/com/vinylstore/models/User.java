package com.vinylstore.models;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Un utilisateur du site Vinyl Store
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class User {
    
    // ========== Les attributs ==========
    private int id;
    private String nomComplet;  // ex: "Christophe Aleixo"
    private String email;
    private String motDePasse;  // mot de passe hashé avec BCrypt
    private boolean estAdmin;   // true = admin, false = client normal

    // Constructeur vide (nécessaire pour JavaFX)
    public User() {}

    // ========== Les getters et setters ==========
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * Assigne le mot de passe (déjà hashé ou pas selon le contexte)
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isEstAdmin() {
        return estAdmin;
    }

    public void setEstAdmin(boolean estAdmin) {
        this.estAdmin = estAdmin;
    }

    // ========== Méthodes utilitaires pour BCrypt ==========

    /**
     * Prend un mot de passe en clair et le hash avec BCrypt
     */
    public static String hasherMotDePasse(String motDePasseClair) {
        return BCrypt.hashpw(motDePasseClair, BCrypt.gensalt());
    }

    /**
     * Vérifie si le mot de passe en clair correspond au hash
     */
    public static boolean verifierMotDePasse(String motDePasseClair, String motDePasseHashe) {
        return BCrypt.checkpw(motDePasseClair, motDePasseHashe);
    }
}
