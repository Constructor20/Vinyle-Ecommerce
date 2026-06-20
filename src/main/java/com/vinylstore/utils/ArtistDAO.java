package com.vinylstore.utils;

import com.vinylstore.models.Artist;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO pour les artistes
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class ArtistDAO {

    public static List<Artist> recupererTous() {

        List<Artist> artistes = new ArrayList<>();
        String requete = "SELECT id, name FROM artist ORDER BY name";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery(requete);

            while (resultats.next()) {
                Artist a = new Artist();
                a.setId(resultats.getInt("id"));
                a.setNom(resultats.getString("name"));
                artistes.add(a);
            }

            resultats.close();
            statement.close();
            connexion.close();

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans recupererTous : " + e.getMessage());
            e.printStackTrace();
        }

        return artistes;
    }

    public static void ajouter(Artist a) {

        String requete = "INSERT INTO artist (name) VALUES (?)";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requetePreparee = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            requetePreparee.setString(1, a.getNom());
            requetePreparee.executeUpdate();

            ResultSet cles = requetePreparee.getGeneratedKeys();
            if (cles.next()) {
                a.setId(cles.getInt(1));
            }

            cles.close();
            requetePreparee.close();
            connexion.close();

            System.out.println("Artiste ajouté : " + a.getNom());

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans ajouter : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void modifier(Artist a) {

        String requete = "UPDATE artist SET name = ? WHERE id = ?";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requetePreparee = connexion.prepareStatement(requete);
            requetePreparee.setString(1, a.getNom());
            requetePreparee.setInt(2, a.getId());
            requetePreparee.executeUpdate();
            requetePreparee.close();
            connexion.close();

            System.out.println("Artiste modifié : " + a.getNom());

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans modifier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void supprimer(int id) {

        String requete = "DELETE FROM artist WHERE id = ?";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requetePreparee = connexion.prepareStatement(requete);
            requetePreparee.setInt(1, id);
            requetePreparee.executeUpdate();
            requetePreparee.close();
            connexion.close();

            System.out.println("Artiste supprimé : ID " + id);

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans supprimer : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
