package com.marketplace.dao;

import com.marketplace.models.SaleRecord;
import com.marketplace.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDAO {

    // 1. Get Summary Statistics
    public Map<String, Object> getSellerStats(int sellerId) {
        Map<String, Object> stats = new HashMap<>();
        stats.put("revenue", 0.0);
        stats.put("itemsSold", 0);
        stats.put("activeListings", 0);

        String sqlSales = "SELECT SUM(oi.price_at_purchase) as total_rev, COUNT(oi.product_id) as total_sold " +
                          "FROM order_items oi " +
                          "JOIN products p ON oi.product_id = p.product_id " +
                          "WHERE p.user_id = ?";
        
        String sqlActive = "SELECT COUNT(*) FROM products WHERE user_id = ? AND status = 'Available'";

        try (Connection conn = DBConnection.getConnection()) {
            
            // Get Sales Stats
            try (PreparedStatement ps = conn.prepareStatement(sqlSales)) {
                ps.setInt(1, sellerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    stats.put("revenue", rs.getDouble("total_rev"));
                    stats.put("itemsSold", rs.getInt("total_sold"));
                }
            }

            // Get Active Listings Count
            try (PreparedStatement ps = conn.prepareStatement(sqlActive)) {
                ps.setInt(1, sellerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    stats.put("activeListings", rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }

    // 2. Get Detailed Sales History
    public List<SaleRecord> getSalesHistory(int sellerId) {
        List<SaleRecord> list = new ArrayList<>();
        String sql = "SELECT o.order_id, p.title, p.image_url, u.full_name, oi.price_at_purchase, o.order_date, o.order_status " +
                     "FROM order_items oi " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "JOIN orders o ON oi.order_id = o.order_id " +
                     "JOIN users u ON o.user_id = u.user_id " + // Link to Buyer
                     "WHERE p.user_id = ? " +
                     "ORDER BY o.order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SaleRecord rec = new SaleRecord();
                rec.setOrderId(rs.getInt("order_id"));
                rec.setProductName(rs.getString("title"));
                rec.setProductImage(rs.getString("image_url"));
                rec.setBuyerName(rs.getString("full_name"));
                rec.setSoldPrice(rs.getDouble("price_at_purchase"));
                rec.setSaleDate(rs.getTimestamp("order_date"));
                rec.setStatus(rs.getString("order_status"));
                list.add(rec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}