package com.vinylstore.models;

import java.math.BigDecimal;

/**
 * Un vinyle dans le catalogue
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class Vinyl {

    private int id;
    private String titre;          // nom du vinyle
    private int idArtiste;         // ID de l'artiste (pour les insert/update)
    private String nomArtiste;     // nom de l'artiste (via jointure)
    private String genre;          // style de musique
    private int anneeSortie;       // année de sortie
    private BigDecimal prix;       // prix en €
    private int quantiteStock;     // combien il en reste

    public Vinyl() {}

    // ========== Getters et Setters ==========

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getIdArtiste() { return idArtiste; }
    public void setIdArtiste(int idArtiste) { this.idArtiste = idArtiste; }

    public String getNomArtiste() {
        return nomArtiste;
    }

    public void setNomArtiste(String nomArtiste) {
        this.nomArtiste = nomArtiste;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }
}
