package com.marketplace.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {

    private static final String URL = "jdbc:derby://localhost:1527/UITM_Marketplace";
    private static final String USER = "app";
    private static final String PASS = "app";

    // =========================
    // GET ALL PRODUCTS (LISTING)
    // =========================
    public static List<Map<String,Object>> getAllProducts() {

        List<Map<String,Object>> list = new ArrayList<>();

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            
            // Query selects all columns
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String,Object> p = new HashMap<>();
                p.put("id", rs.getInt("product_id"));
                p.put("title", rs.getString("title"));
                p.put("price", rs.getDouble("price"));
                p.put("img", rs.getString("image_url"));
                
                // ADDED: Fetch description and size for the UI
                p.put("desc", rs.getString("description"));
                p.put("size", rs.getString("product_size"));
                
                // Mock data for fields not in DB yet
                p.put("sold", 0); 
                p.put("stars", 5.0);
                
                list.add(p);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // =========================
    // GET SINGLE PRODUCT (DETAIL)
    // =========================
    public static Map<String,Object> getProductById(int id) {
        Map<String,Object> product = new HashMap<>();
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products WHERE product_id = ?");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                product.put("id", rs.getInt("product_id"));
                product.put("title", rs.getString("title"));
                product.put("price", rs.getDouble("price"));
                product.put("img", rs.getString("image_url"));
                product.put("desc", rs.getString("description"));
                product.put("size", rs.getString("product_size"));
                product.put("sold", 0);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }
}