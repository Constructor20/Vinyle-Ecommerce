package com.vinylstore.utils;

import com.vinylstore.models.Vinyl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet de récupérer les vinyles depuis la base de données
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class VinylDAO {

    /**
     * Récupère tous les vinyles (avec le nom de l'artiste)
     */
    public static List<Vinyl> recupererTous() {
        
        List<Vinyl> vinyles = new ArrayList<>();
        
        // Requête avec JOIN pour avoir le nom de l'artiste
        String requete = "SELECT v.id, v.title, a.name AS artiste, v.genre, v.release_year, v.price, v.quantity "
                       + "FROM vinyl v "
                       + "JOIN artist a ON v.id_artist = a.id "
                       + "WHERE v.is_for_sale = 1 "
                       + "ORDER BY v.title";
        
        try {
            Connection connexion = DatabaseConnection.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery(requete);
            
            while (resultats.next()) {
                Vinyl v = new Vinyl();
                v.setId(resultats.getInt("id"));
                v.setTitre(resultats.getString("title"));
                v.setNomArtiste(resultats.getString("artiste"));
                v.setGenre(resultats.getString("genre"));
                v.setAnneeSortie(resultats.getInt("release_year"));
                v.setPrix(resultats.getBigDecimal("price"));
                v.setQuantiteStock(resultats.getInt("quantity"));
                vinyles.add(v);
            }
            
            resultats.close();
            statement.close();
            connexion.close();
            
            System.out.println(vinyles.size() + " vinyles chargés depuis la BDD");
            
        } catch (SQLException e) {
            System.out.println("Erreur SQL dans recupererTous : " + e.getMessage());
            e.printStackTrace();
        }
        
        return vinyles;
    }

    /**
     * Cherche des vinyles par mot-clé (dans le titre ou l'artiste)
     */
    public static List<Vinyl> rechercher(String motCle) {
        
        List<Vinyl> vinyles = new ArrayList<>();
        
        String requete = "SELECT v.id, v.title, a.name AS artiste, v.genre, v.release_year, v.price, v.quantity "
                       + "FROM vinyl v "
                       + "JOIN artist a ON v.id_artist = a.id "
                       + "WHERE v.is_for_sale = 1 "
                       + "AND (v.title LIKE ? OR a.name LIKE ?) "
                       + "ORDER BY v.title";
        
        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requetePreparee = connexion.prepareStatement(requete);
            
            String motif = "%" + motCle + "%";
            requetePreparee.setString(1, motif);
            requetePreparee.setString(2, motif);
            
            ResultSet resultats = requetePreparee.executeQuery();
            
            while (resultats.next()) {
                Vinyl v = new Vinyl();
                v.setId(resultats.getInt("id"));
                v.setTitre(resultats.getString("title"));
                v.setNomArtiste(resultats.getString("artiste"));
                v.setGenre(resultats.getString("genre"));
                v.setAnneeSortie(resultats.getInt("release_year"));
                v.setPrix(resultats.getBigDecimal("price"));
                v.setQuantiteStock(resultats.getInt("quantity"));
                vinyles.add(v);
            }
            
            resultats.close();
            requetePreparee.close();
            connexion.close();
            
        } catch (SQLException e) {
            System.out.println("Erreur SQL dans rechercher : " + e.getMessage());
            e.printStackTrace();
        }
        
        return vinyles;
    }

    /**
     * Récupère la liste des genres disponibles (pour le filtre)
     */
    public static List<String> recupererGenres() {
        
        List<String> genres = new ArrayList<>();
        String requete = "SELECT DISTINCT genre FROM vinyl WHERE is_for_sale = 1 AND genre IS NOT NULL ORDER BY genre";
        
        try {
            Connection connexion = DatabaseConnection.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery(requete);
            
            while (resultats.next()) {
                genres.add(resultats.getString("genre"));
            }
            
            resultats.close();
            statement.close();
            connexion.close();
            
        } catch (SQLException e) {
            System.out.println("Erreur SQL dans recupererGenres : " + e.getMessage());
            e.printStackTrace();
        }
        
        return genres;
    }

    /**
     * Récupère un seul vinyle par son ID (avec le nom de l'artiste)
     */
    public static Vinyl recupererParId(int id) {

        String requete = "SELECT v.id, v.title, a.name AS artiste, v.genre, v.release_year, v.price, v.quantity "
                       + "FROM vinyl v "
                       + "JOIN artist a ON v.id_artist = a.id "
                       + "WHERE v.id = ?";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requetePreparee = connexion.prepareStatement(requete);
            requetePreparee.setInt(1, id);

            ResultSet resultats = requetePreparee.executeQuery();

            if (resultats.next()) {
                Vinyl v = new Vinyl();
                v.setId(resultats.getInt("id"));
                v.setTitre(resultats.getString("title"));
                v.setNomArtiste(resultats.getString("artiste"));
                v.setGenre(resultats.getString("genre"));
                v.setAnneeSortie(resultats.getInt("release_year"));
                v.setPrix(resultats.getBigDecimal("price"));
                v.setQuantiteStock(resultats.getInt("quantity"));
                resultats.close();
                requetePreparee.close();
                connexion.close();
                return v;
            }

            resultats.close();
            requetePreparee.close();
            connexion.close();

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans recupererParId : " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
