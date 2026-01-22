/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author amirah izzah
 */
package com.marketplace.models;

import java.sql.Timestamp;

public class SaleRecord {
    private int orderId;
    private String productName;
    private String productImage;
    private String buyerName;
    private double soldPrice;
    private Timestamp saleDate;
    private String status;

    public SaleRecord() {}

    // Getters and Setters
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public String getBuyerName() { return buyerName; }
    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public double getSoldPrice() { return soldPrice; }
    public void setSoldPrice(double soldPrice) { this.soldPrice = soldPrice; }

    public Timestamp getSaleDate() { return saleDate; }
    public void setSaleDate(Timestamp saleDate) { this.saleDate = saleDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}