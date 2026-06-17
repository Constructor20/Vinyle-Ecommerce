package com.vinylstore.utils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Récupère l'historique des commandes d'un utilisateur
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class CommandesDAO {

    /**
     * Une commande avec ses articles
     */
    public static class Commande {
        private int id;
        private Timestamp date;
        private BigDecimal total;
        private List<ArticleCommande> articles = new ArrayList<>();

        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        public Timestamp getDate() { return date; }
        public void setDate(Timestamp date) { this.date = date; }
        public BigDecimal getTotal() { return total; }
        public void setTotal(BigDecimal total) { this.total = total; }
        public List<ArticleCommande> getArticles() { return articles; }
    }

    /**
     * Un article dans une commande
     */
    public static class ArticleCommande {
        private String titreVinyle;
        private String nomArtiste;
        private int quantite;
        private BigDecimal prixUnitaire;

        public String getTitreVinyle() { return titreVinyle; }
        public void setTitreVinyle(String titreVinyle) { this.titreVinyle = titreVinyle; }
        public String getNomArtiste() { return nomArtiste; }
        public void setNomArtiste(String nomArtiste) { this.nomArtiste = nomArtiste; }
        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }
        public BigDecimal getPrixUnitaire() { return prixUnitaire; }
        public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }
        public BigDecimal getSousTotal() {
            return prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        }
    }

    /**
     * Récupère toutes les commandes d'un utilisateur
     */
    public static List<Commande> recupererCommandes(int idUtilisateur) {

        List<Commande> commandes = new ArrayList<>();

        String sql = "SELECT id, sale_date, total_amount FROM sale WHERE id_user = ? ORDER BY sale_date DESC";

        try {
            Connection connexion = DatabaseConnection.getConnection();
            PreparedStatement requete = connexion.prepareStatement(sql);
            requete.setInt(1, idUtilisateur);
            ResultSet resultats = requete.executeQuery();

            while (resultats.next()) {
                Commande c = new Commande();
                c.setId(resultats.getInt("id"));
                c.setDate(resultats.getTimestamp("sale_date"));
                c.setTotal(resultats.getBigDecimal("total_amount"));
                c.articles = recupererArticles(c.getId(), connexion);
                commandes.add(c);
            }

            resultats.close();
            requete.close();
            connexion.close();

        } catch (SQLException e) {
            System.out.println("Erreur SQL dans recupererCommandes : " + e.getMessage());
            e.printStackTrace();
        }

        return commandes;
    }

    /**
     * Récupère les articles d'une commande
     */
    private static List<ArticleCommande> recupererArticles(int idVente, Connection connexion) throws SQLException {

        List<ArticleCommande> articles = new ArrayList<>();

        String sql = "SELECT si.quantity, si.unit_price, v.title, a.name AS artiste "
                   + "FROM sale_items si "
                   + "JOIN vinyl v ON si.id_vinyl = v.id "
                   + "JOIN artist a ON v.id_artist = a.id "
                   + "WHERE si.id_sale = ?";

        PreparedStatement requete = connexion.prepareStatement(sql);
        requete.setInt(1, idVente);
        ResultSet resultats = requete.executeQuery();

        while (resultats.next()) {
            ArticleCommande a = new ArticleCommande();
            a.setTitreVinyle(resultats.getString("title"));
            a.setNomArtiste(resultats.getString("artiste"));
            a.setQuantite(resultats.getInt("quantity"));
            a.setPrixUnitaire(resultats.getBigDecimal("unit_price"));
            articles.add(a);
        }

        resultats.close();
        requete.close();

        return articles;
    }
}
