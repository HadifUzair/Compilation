package com.marketplace.dao;

import com.marketplace.models.CartBean;
import com.marketplace.models.Order;
import com.marketplace.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    /**
     * Requirement: Information Management (Create)
     * Create a new order in the database and return the generated Order ID.
     */
    public int createOrder(int userId, double totalAmount, String shippingName, String shippingAddress, String shippingPhone, String paymentMethod) {
        String sql = "INSERT INTO orders (user_id, total_amount, shipping_name, shipping_address, shipping_phone, payment_method, order_status, order_date) "
                   + "VALUES (?, ?, ?, ?, ?, ?, 'Pending', CURRENT_TIMESTAMP)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, userId);
            stmt.setDouble(2, totalAmount);
            stmt.setString(3, shippingName);
            stmt.setString(4, shippingAddress);
            stmt.setString(5, shippingPhone);
            stmt.setString(6, paymentMethod);
            
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
    public void addOrderItems(int orderId, List<CartBean> cartItems) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price_at_purchase) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (CartBean item : cartItems) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, item.getProductId()); 
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getPrice());
                stmt.addBatch();
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
    public void markProductsAsSold(List<CartBean> cartItems) {
        String sql = "UPDATE products SET status = 'Sold' WHERE product_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (CartBean item : cartItems) {
                stmt.setInt(1, item.getProductId());
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
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Order o = new Order();
                o.setOrderId(rs.getInt("order_id")); 
                o.setUserId(rs.getInt("user_id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("order_status"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setShippingName(rs.getString("shipping_name"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setShippingPhone(rs.getString("shipping_phone"));
                o.setPaymentMethod(rs.getString("payment_method"));
                
                o.setItemSummary(getItemSummaryForOrder(conn, o.getOrderId()));
                
                list.add(o);
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return list;
    }
    
    // Helper method to get item names for history display
    private String getItemSummaryForOrder(Connection conn, int orderId) {
        StringBuilder summary = new StringBuilder();
        String sql = "SELECT p.title FROM order_items oi JOIN products p ON oi.product_id = p.product_id WHERE oi.order_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (summary.length() > 0) summary.append(", ");
                summary.append(rs.getString("title"));
            }
        } catch (SQLException e) {
            return "Items unavailable";
        }
        String result = summary.toString();
        return result.isEmpty() ? "No items" : (result.length() > 50 ? result.substring(0, 47) + "..." : result);
    }

    // NEW METHOD: Required for ViewOrderServlet
    public List<CartBean> getOrderItems(int orderId) {
        List<CartBean> items = new ArrayList<>();
        String sql = "SELECT p.product_id, p.title, p.image_url, oi.quantity, oi.price_at_purchase " +
                     "FROM order_items oi " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "WHERE oi.order_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                CartBean item = new CartBean();
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("title"));
                item.setImageUrl(rs.getString("image_url"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price_at_purchase"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}