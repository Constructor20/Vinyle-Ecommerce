package com.vinylstore.models;

public class Artist {
    private int id;
    private String name;
    private String bio;
    private String country;

    public Artist() {}

    public Artist(int id, String name, String bio, String country) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.country = country;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
