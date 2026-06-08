package com.vinylstore.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Permet de se connecter à la base de données MySQL avec XAMPP
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class DatabaseConnection {

    // Infos de connexion à la BDD
    private static final String URL = "jdbc:mysql://localhost:3306/vinyl_store";
    private static final String UTILISATEUR = "root";
    private static final String MOT_DE_PASSE = ""; // pas de mot de passe sous XAMPP

    /**
     * Récupère une connexion à la BDD
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
    }
}
