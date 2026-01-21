package com.marketplace.controller;

import com.marketplace.dao.UserDAO;
import com.marketplace.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateAvatarServlet")
public class UpdateAvatarServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        
        if (user != null) {
            String avatarUrl = request.getParameter("avatarUrl");
            
            if (avatarUrl != null && !avatarUrl.trim().isEmpty()) {
                UserDAO dao = new UserDAO();
                if (dao.updateProfilePic(user.getUserId(), avatarUrl)) {
                    // Update session object
                    user.setProfilePic(avatarUrl);
                    session.setAttribute("loggedUser", user);
                    response.sendRedirect("profile.jsp?status=avatar_updated");
                } else {
                    response.sendRedirect("profile.jsp?error=database");
                }
            } else {
                response.sendRedirect("profile.jsp?error=invalid_url");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}