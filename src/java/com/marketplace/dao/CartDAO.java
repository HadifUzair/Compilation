package com.marketplace.dao;

import com.marketplace.models.CartBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            return DriverManager.getConnection(
                "jdbc:derby://localhost:1527/Ujek", "app", "app");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }
    
    // Get user's cart items
    public List<CartBean> getCartItems(int userId) throws SQLException {
        List<CartBean> cartItems = new ArrayList<>();
        
        String sql = "SELECT c.cart_id, c.product_id, c.quantity, "
                   + "p.title, p.price, p.image_url, p.product_size "
                   + "FROM cart c "
                   + "JOIN products p ON c.product_id = p.product_id "
                   + "WHERE c.user_id = ? AND p.status = 'Available'";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                CartBean item = new CartBean();
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("title"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setImageUrl(rs.getString("image_url"));
                item.setSize(rs.getString("product_size"));
                cartItems.add(item);
            }
        }
        return cartItems;
    }
    
    // Add item to cart
    public void addToCart(int userId, int productId) throws SQLException {
        try (Connection conn = getConnection()) {
            // Check if already in cart
            String checkSql = "SELECT quantity FROM cart WHERE user_id = ? AND product_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, productId);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Update quantity
                String updateSql = "UPDATE cart SET quantity = quantity + 1 WHERE user_id = ? AND product_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, userId);
                updateStmt.setInt(2, productId);
                updateStmt.executeUpdate();
            } else {
                // Insert new
                String insertSql = "INSERT INTO cart (user_id, product_id, quantity, added_at) "
                                 + "VALUES (?, ?, 1, CURRENT_TIMESTAMP)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, productId);
                insertStmt.executeUpdate();
            }
        }
    }
    
    // Update cart quantity
    public void updateCart(int userId, int productId, int quantity) throws SQLException {
        if (quantity <= 0) {
            removeFromCart(userId, productId);
            return;
        }
        
        String sql = "UPDATE cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, userId);
            stmt.setInt(3, productId);
            stmt.executeUpdate();
        }
    }
    
    // Remove from cart
    public void removeFromCart(int userId, int productId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);
            stmt.executeUpdate();
        }
    }
    
    // Clear user's cart
    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
}