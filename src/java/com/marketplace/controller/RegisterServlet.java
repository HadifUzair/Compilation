package com.marketplace.controller;

import com.marketplace.dao.UserDAO;
import com.marketplace.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Retrieve Form Data
        String fullName = request.getParameter("fullname");
        String studentID = request.getParameter("studentID");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // 2. Create User Object (Bean)
        User user = new User();
        user.setFullName(fullName);
        user.setStudentId(studentID);
        user.setEmail(email);
        user.setPassword(password); // In a real app, hash this password!
        
        // 3. Call DAO
        UserDAO dao = new UserDAO();
        boolean success = dao.registerUser(user);
        
        // 4. Handle Result
        if (success) {
            // Registration successful, redirect to login page
            response.sendRedirect("login.jsp?status=registered");
        } else {
            // Registration failed (likely duplicate Student ID or Email)
            request.setAttribute("errorMessage", "Registration failed. Student ID or Email might already exist.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}