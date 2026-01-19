package com.marketplace.dao;

import com.marketplace.models.Product;
import com.marketplace.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // =========================
    // ADD NEW PRODUCT (SELL)
    // =========================
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (user_id, category_id, title, description, price, "
                   + "image_url, product_size, condition, status, created_at) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Available', CURRENT_TIMESTAMP)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, product.getUserId());
            ps.setInt(2, product.getCategoryId());
            ps.setString(3, product.getTitle());
            ps.setString(4, product.getDescription());
            ps.setDouble(5, product.getPrice());
            ps.setString(6, product.getImageUrl());
            ps.setString(7, product.getSize());
            ps.setString(8, product.getCondition());
            
            int rows = ps.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // GET ALL PRODUCTS
    // =========================
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE status = 'Available'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // GET PRODUCT BY ID
    // =========================
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapRowToProduct(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // =========================
    // NEW: FILTER BY CATEGORY
    // =========================
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ? AND status = 'Available'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRowToProduct(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // NEW: SORT PRODUCTS
    // =========================
    public List<Product> getProductsSorted(String sortType) {
        List<Product> list = new ArrayList<>();
        String orderBy = "product_id DESC"; // Default
        
        if ("price_asc".equals(sortType)) orderBy = "price ASC";
        else if ("price_desc".equals(sortType)) orderBy = "price DESC";
        else if ("name_asc".equals(sortType)) orderBy = "title ASC";
        
        String sql = "SELECT * FROM products WHERE status = 'Available' ORDER BY " + orderBy;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(mapRowToProduct(rs)); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // HELPER: MAP RESULTSET
    // =========================
    private Product mapRowToProduct(ResultSet rs) throws SQLException {
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
        return p;
    }
}