package com.vinylstore.models;

/**
 * Un artiste dans la base de données
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class Artist {

    private int id;
    private String nom;

    public Artist() {}

    public Artist(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    @Override
    public String toString() {
        return nom;
    }
}
