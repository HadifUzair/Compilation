package com.marketplace.models;
import java.sql.*;
import com.marketplace.models.User;
import com.marketplace.util.DBConnection;

public class UserDAO {
    public boolean updateProfile(User user) {
        String sql = "UPDATE users SET email=?, phone_number=?, address=? WHERE student_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getStudentId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }
}