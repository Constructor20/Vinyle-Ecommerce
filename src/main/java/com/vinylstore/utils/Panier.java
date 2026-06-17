package com.vinylstore.utils;

import com.vinylstore.models.Vinyl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente le panier d'achat (stocké en mémoire le temps de la session)
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class Panier {

    // Liste des articles dans le panier
    private static List<ArticlePanier> articles = new ArrayList<>();

    /**
     * Un article dans le panier (vinyle + quantité)
     */
    public static class ArticlePanier {
        private Vinyl vinyle;
        private int quantite;

        public ArticlePanier(Vinyl vinyle, int quantite) {
            this.vinyle = vinyle;
            this.quantite = quantite;
        }

        public Vinyl getVinyle() { return vinyle; }
        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }

        /** Calcule le sous-total pour cet article */
        public BigDecimal getSousTotal() {
            return vinyle.getPrix().multiply(BigDecimal.valueOf(quantite));
        }
    }

    /** Ajoute un vinyle au panier (ou augmente la quantité si déjà présent) */
    public static void ajouter(Vinyl v, int quantite) {
        for (ArticlePanier a : articles) {
            if (a.getVinyle().getId() == v.getId()) {
                a.setQuantite(a.getQuantite() + quantite);
                return;
            }
        }
        articles.add(new ArticlePanier(v, quantite));
    }

    /** Supprime un article du panier */
    public static void supprimer(int index) {
        if (index >= 0 && index < articles.size()) {
            articles.remove(index);
        }
    }

    /** Vide le panier */
    public static void vider() {
        articles.clear();
    }

    /** Retourne tous les articles */
    public static List<ArticlePanier> getArticles() {
        return articles;
    }

    /** Calcule le total du panier */
    public static BigDecimal getTotal() {
        return articles.stream()
            .map(ArticlePanier::getSousTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Nombre d'articles */
    public static int getNombreArticles() {
        return articles.size();
    }
}
