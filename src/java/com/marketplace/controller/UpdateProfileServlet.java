package com.marketplace.controller;

import com.marketplace.dao.UserDAO; // Adjusted import
import com.marketplace.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        
        if (user != null) {
            // Update object with form data
            user.setEmail(request.getParameter("email"));
            user.setPhoneNumber(request.getParameter("phone"));
            user.setAddress(request.getParameter("address"));
            
            // Persist to DB
            UserDAO userDAO = new UserDAO();
            if (userDAO.updateProfile(user)) {
                // Update session with new info so it reflects immediately
                session.setAttribute("loggedUser", user);
                // Also update individual attributes if used elsewhere (like in checkout)
                session.setAttribute("address", user.getAddress());
                session.setAttribute("phone", user.getPhoneNumber());
                
                response.sendRedirect("profile.jsp?status=success");
            } else {
                response.sendRedirect("profile.jsp?status=error");
            }
        } else {
            response.sendRedirect("login.html");
        }
    }
}