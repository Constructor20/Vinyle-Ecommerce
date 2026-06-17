package com.vinylstore.utils;

import com.vinylstore.utils.Panier.ArticlePanier;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Permet de créer une commande dans la base de données
 * 1. Crée la vente (sale)
 * 2. Ajoute les articles (sale_items)
 * 3. Met à jour le stock des vinyles
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class VenteDAO {

    /**
     * Crée une commande complète : sale + sale_items + mise à jour des stocks
     * Retourne true si tout s'est bien passé
     */
    public static boolean creerCommande(int idUtilisateur, BigDecimal total) {

        Connection connexion = null;

        try {
            connexion = DatabaseConnection.getConnection();

            // On désactive l'auto-commit pour tout faire en une transaction
            // (si une étape échoue, on annule tout)
            connexion.setAutoCommit(false);

            // ===== Étape 1 : Créer la vente (sale) =====
            String sqlSale = "INSERT INTO sale (id_user, total_amount) VALUES (?, ?)";
            PreparedStatement requeteSale = connexion.prepareStatement(sqlSale, Statement.RETURN_GENERATED_KEYS);
            requeteSale.setInt(1, idUtilisateur);
            requeteSale.setBigDecimal(2, total);
            requeteSale.executeUpdate();

            // Récupérer l'ID de la vente qui vient d'être créée
            ResultSet cle = requeteSale.getGeneratedKeys();
            int idVente;
            if (cle.next()) {
                idVente = cle.getInt(1);
            } else {
                throw new SQLException("Impossible de récupérer l'ID de la vente.");
            }
            cle.close();
            requeteSale.close();

            System.out.println("Vente créée avec l'ID : " + idVente);

            // ===== Étape 2 : Ajouter chaque article dans sale_items et mettre à jour le stock =====
            for (ArticlePanier article : Panier.getArticles()) {

                int idVinyle = article.getVinyle().getId();
                int quantite = article.getQuantite();
                BigDecimal prixUnitaire = article.getVinyle().getPrix();

                // Ajouter dans sale_items
                String sqlItem = "INSERT INTO sale_items (id_sale, id_vinyl, quantity, unit_price) VALUES (?, ?, ?, ?)";
                PreparedStatement requeteItem = connexion.prepareStatement(sqlItem);
                requeteItem.setInt(1, idVente);
                requeteItem.setInt(2, idVinyle);
                requeteItem.setInt(3, quantite);
                requeteItem.setBigDecimal(4, prixUnitaire);
                requeteItem.executeUpdate();
                requeteItem.close();

                // Mettre à jour le stock du vinyle
                String sqlStock = "UPDATE vinyl SET quantity = quantity - ? WHERE id = ?";
                PreparedStatement requeteStock = connexion.prepareStatement(sqlStock);
                requeteStock.setInt(1, quantite);
                requeteStock.setInt(2, idVinyle);
                requeteStock.executeUpdate();
                requeteStock.close();

                System.out.println("  -> Vinyle #" + idVinyle + " x" + quantite + " ajouté à la vente");
            }

            // Si tout s'est bien passé, on valide la transaction
            connexion.commit();
            System.out.println("Commande validée ! Total : " + total + " €");

            return true;

        } catch (SQLException e) {
            System.out.println("ERREUR lors de la création de la commande : " + e.getMessage());

            // En cas d'erreur, on annule tout ce qui a été fait
            if (connexion != null) {
                try {
                    connexion.rollback();
                    System.out.println("Transaction annulée (rollback)");
                } catch (SQLException ex) {
                    System.out.println("Erreur lors du rollback : " + ex.getMessage());
                }
            }

            e.printStackTrace();
            return false;

        } finally {
            // On remet l'auto-commit et on ferme la connexion
            if (connexion != null) {
                try {
                    connexion.setAutoCommit(true);
                    connexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
