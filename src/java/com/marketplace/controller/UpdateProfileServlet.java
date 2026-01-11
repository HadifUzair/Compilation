package com.marketplace.controller;
import com.marketplace.dao.UserDAO;
import com.marketplace.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        
        if (user != null) {
            user.setEmail(request.getParameter("email"));
            user.setPhoneNumber(request.getParameter("phone"));
            user.setAddress(request.getParameter("address"));
            
            if (new UserDAO().updateProfile(user)) {
                session.setAttribute("loggedUser", user); // Update session with new info
                response.sendRedirect("profile.jsp?success=1");
            }
        }
    }
}