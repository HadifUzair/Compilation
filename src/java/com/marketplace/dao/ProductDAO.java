package com.marketplace.dao;

import com.marketplace.models.Product;
import com.marketplace.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // 1. ADD PRODUCT
    public boolean addProduct(Product p) {
        String sql = "INSERT INTO products (user_id, category_id, title, description, price, " +
                     "image_url, product_size, condition, status, created_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Available', CURRENT_TIMESTAMP)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, p.getUserId());
            ps.setInt(2, p.getCategoryId());
            ps.setString(3, p.getTitle());
            ps.setString(4, p.getDescription());
            ps.setDouble(5, p.getPrice());
            ps.setString(6, p.getImageUrl());
            ps.setString(7, p.getSize());
            ps.setString(8, p.getCondition());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY created_at DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = mapResultSetToProduct(rs);
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3. GET PRODUCT BY ID
    public Product getProductById(int id) {
        Product p = null;
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    // Helper to avoid duplicate code
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setUserId(rs.getInt("user_id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setTitle(rs.getString("title"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setImageUrl(rs.getString("image_url"));
        p.setSize(rs.getString("product_size"));
        p.setCondition(rs.getString("condition"));
        p.setStatus(rs.getString("status"));
        p.setCreatedAt(rs.getTimestamp("created_at"));
        return p;
    }
}