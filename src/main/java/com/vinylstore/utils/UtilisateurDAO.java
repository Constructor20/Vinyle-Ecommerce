package com.vinylstore.utils;

import com.vinylstore.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO = Data Access Object
 * Ici on met tout le code qui parle à la base de données pour les utilisateurs.
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class UtilisateurDAO {

    /**
     * Cherche un utilisateur par son email (pour la connexion)
     */
    public static User findByEmail(String email) {
        
        String requete = "SELECT * FROM users WHERE email = ?";
        User utilisateur = null;
        
        try {
            // 1. On se connecte à la BDD
            Connection connexion = DatabaseConnection.getConnection();
            
            // 2. On prépare la requête (le ? sera remplacé par l'email)
            PreparedStatement requetePreparee = connexion.prepareStatement(requete);
            requetePreparee.setString(1, email);
            
            // 3. On exécute la requête
            ResultSet resultats = requetePreparee.executeQuery();
            
            // 4. Si on a trouvé un utilisateur, on le transforme en objet User
            if (resultats.next()) {
                utilisateur = new User();
                utilisateur.setId(resultats.getInt("id"));
                utilisateur.setNomComplet(resultats.getString("name"));
                utilisateur.setEmail(resultats.getString("email"));
                utilisateur.setMotDePasse(resultats.getString("password"));
                utilisateur.setEstAdmin(resultats.getBoolean("is_admin"));
            }
            
            // 5. On ferme tout (important pour pas saturer la BDD)
            resultats.close();
            requetePreparee.close();
            connexion.close();
            
        } catch (SQLException e) {
            System.out.println("Erreur SQL dans findByEmail : " + e.getMessage());
            e.printStackTrace();
        }
        
        return utilisateur;
    }

    /**
     * Crée un nouvel utilisateur dans la BDD (pour l'inscription)
     */
    public static void creer(User utilisateur) {
        
        String requete = "INSERT INTO users (name, email, password, is_admin) VALUES (?, ?, ?, ?)";
        
        try {
            // 1. Connexion à la BDD
            Connection connexion = DatabaseConnection.getConnection();
            
            // 2. On prépare la requête
            PreparedStatement requetePreparee = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            
            // 3. On remplit les ? avec les valeurs de l'utilisateur
            requetePreparee.setString(1, utilisateur.getNomComplet());
            requetePreparee.setString(2, utilisateur.getEmail());
            requetePreparee.setString(3, utilisateur.getMotDePasse());
            requetePreparee.setBoolean(4, utilisateur.isEstAdmin());
            
            // 4. On exécute
            requetePreparee.executeUpdate();
            
            // 5. On récupère l'ID généré automatiquement par MySQL
            ResultSet cles = requetePreparee.getGeneratedKeys();
            if (cles.next()) {
                utilisateur.setId(cles.getInt(1));
            }
            
            System.out.println("Utilisateur créé avec succès ! ID = " + utilisateur.getId());
            
            // 6. On ferme tout
            cles.close();
            requetePreparee.close();
            connexion.close();
            
        } catch (SQLException e) {
            System.out.println("Erreur SQL dans creer : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Récupère tous les utilisateurs (pour l'admin)
     */
    public static List<User> recupererTous() {

        List<User> utilisateurs = new ArrayList<>();
        String requete = "SELECT * FROM users ORDER BY name";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery(requete);

            while (resultats.next()) {
                User u = new User();
                u.setId(resultats.getInt("id"));
                u.setNomComplet(resultats.getString("name"));
                u.setEmail(resultats.getString("email"));
                u.setMotDePasse(resultats.getString("password"));
                u.setEstAdmin(resultats.getBoolean("is_admin"));
                utilisateurs.add(u);
            }

            resultats.close();
            statement.close();
            connexion.close();

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans recupererTous : " + e.getMessage());
            e.printStackTrace();
        }

        return utilisateurs;
    }

    /**
     * Modifie un utilisateur (nom, email, admin)
     */
    public static void modifier(User u) {

        String requete = "UPDATE users SET name = ?, email = ?, is_admin = ? WHERE id = ?";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requetePreparee = connexion.prepareStatement(requete);
            requetePreparee.setString(1, u.getNomComplet());
            requetePreparee.setString(2, u.getEmail());
            requetePreparee.setBoolean(3, u.isEstAdmin());
            requetePreparee.setInt(4, u.getId());
            requetePreparee.executeUpdate();
            requetePreparee.close();
            connexion.close();

            System.out.println("Utilisateur modifié : " + u.getNomComplet());

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans modifier : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Supprime un utilisateur par son ID
     */
    public static void supprimer(int id) {

        String supprimerVentes = "DELETE FROM sale WHERE id_user = ?";
        String supprimerUtilisateur = "DELETE FROM users WHERE id = ?";

        try {
            Connection connexion = DatabaseConnection.getConnection();

            PreparedStatement reqVentes = connexion.prepareStatement(supprimerVentes);
            reqVentes.setInt(1, id);
            reqVentes.executeUpdate();
            reqVentes.close();

            PreparedStatement reqUser = connexion.prepareStatement(supprimerUtilisateur);
            reqUser.setInt(1, id);
            reqUser.executeUpdate();
            reqUser.close();

            connexion.close();

            System.out.println("Utilisateur supprimé : ID " + id);

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans supprimer : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
