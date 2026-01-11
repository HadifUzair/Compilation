package com.marketplace.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            // Updated to match the specific database name usually inferred from context
            return DriverManager.getConnection(
                "jdbc:derby://localhost:1527/UITM_Marketplace", "app", "app");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        }
    }
}