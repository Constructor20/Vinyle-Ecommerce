package com.vinylstore.models;

import java.math.BigDecimal;

public class Vinyl {
    private int id;
    private String title;
    private int artistId;
    private int categoryId;
    private BigDecimal price;
    private int stock;
    private String description;
    private int releaseYear;
    private String label;
    private String genre;
    private String imageUrl;

    public Vinyl() {}

    public Vinyl(int id, String title, int artistId, int categoryId, BigDecimal price, int stock) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.categoryId = categoryId;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getArtistId() { return artistId; }
    public void setArtistId(int artistId) { this.artistId = artistId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
