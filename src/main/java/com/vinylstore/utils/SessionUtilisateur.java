package com.vinylstore.utils;

import com.vinylstore.models.User;

/**
 * Stocke l'utilisateur connecté (session)
 * Comme on a pas de vrai système de session, on le garde en mémoire statique
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class SessionUtilisateur {

    private static User utilisateurConnecte;

    /**
     * Sauvegarde l'utilisateur quand il se connecte
     */
    public static void connecter(User user) {
        utilisateurConnecte = user;
        System.out.println("Session ouverte pour : " + user.getNomComplet());
    }

    /**
     * Retourne l'utilisateur connecté
     */
    public static User getUtilisateur() {
        return utilisateurConnecte;
    }

    /**
     * Déconnecte l'utilisateur
     */
    public static void deconnecter() {
        utilisateurConnecte = null;
    }

    /**
     * Vérifie si quelqu'un est connecté
     */
    public static boolean estConnecte() {
        return utilisateurConnecte != null;
    }
}
