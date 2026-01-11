package com.marketplace.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class Product implements Serializable {
    private int productId;
    private int userId;
    private int categoryId;
    private String title;
    private String description;
    private double price;
    private String imageUrl;
    private String size;      // matches DB column: product_size
    private String condition;
    private String status;
    private Timestamp createdAt;

    public Product() {}

    // Getters
    public int getProductId() { return productId; }
    public int getUserId() { return userId; }
    public int getCategoryId() { return categoryId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getSize() { return size; }
    public String getCondition() { return condition; }
    public String getStatus() { return status; }
    public Timestamp getCreatedAt() { return createdAt; }

    // Setters
    public void setProductId(int productId) { this.productId = productId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setSize(String size) { this.size = size; }
    public void setCondition(String condition) { this.condition = condition; }
    public void setStatus(String status) { this.status = status; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}