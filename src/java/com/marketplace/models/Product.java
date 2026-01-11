package com.marketplace.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private int userId;
    private int categoryId;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String size;
    private String condition;
    private String status;
    
    // Extra fields for UI
    private double stars = 5.0; 
    private int sold = 0;

    public Product() {}

    // --- FIX IS HERE ---
    // Renamed from getId() to getProductId() to match your JSP call
    public int getProductId() { return id; }
    public void setProductId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getStars() { return stars; }
    public void setStars(double stars) { this.stars = stars; }

    public int getSold() { return sold; }
    public void setSold(int sold) { this.sold = sold; }
}