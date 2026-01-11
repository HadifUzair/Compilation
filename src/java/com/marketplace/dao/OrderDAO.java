package com.marketplace.dao;

import com.marketplace.models.CartBean;
import com.marketplace.models.Order;
import com.marketplace.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /**
     * Requirement: Information Management (Create)
     * Create a new order in the database and return the generated Order ID.
     */
    public int createOrder(int userId, double totalAmount, String shippingAddress, String paymentMethod) {
        String sql = "INSERT INTO orders (user_id, total_amount, shipping_address, payment_method, status, order_date) "
                   + "VALUES (?, ?, ?, ?, 'Pending', CURRENT_TIMESTAMP)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, userId);
            stmt.setDouble(2, totalAmount);
            stmt.setString(3, shippingAddress);
            stmt.setString(4, paymentMethod);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the new Order ID
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Requirement: Information Management (Create)
     * Add items from the cart into the order_items table.
     */
    public void addOrderItems(int orderId, List<CartItem> cartItems) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (CartBean item : cartItems) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, item.getProduct().getId()); // Accessing Product ID via Composition
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getProduct().getPrice());
                stmt.addBatch(); // Batch processing for performance
            }
            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Requirement: Information Management (Update)
     * Mark products as 'sold' so they don't appear in the marketplace anymore.
     */
    public void markProductsAsSold(List<CartItem> cartItems) {
        String sql = "UPDATE products SET status = 'sold' WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (CartBean item : cartItems) {
                stmt.setInt(1, item.getProduct().getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Requirement: Dashboard/Purchase History (Read)
     * Retrieve a list of past orders for a specific user.
     */
    public List<Order> getHistory(int userId) {
        List<Order> list = new ArrayList<>();
        // Note: Column names match the SQL Schema from Part 1
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setPaymentMethod(rs.getString("payment_method"));
                list.add(o);
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }
}