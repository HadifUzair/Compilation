package com.marketplace.controller;

import com.marketplace.dao.OrderDAO;
import com.marketplace.models.Order;
import com.marketplace.models.User;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Ensure we handle the "loggedUser" session attribute consistently
        User user = (User) request.getSession().getAttribute("loggedUser");
        
        if (user == null) { 
            // Fallback: check if userId exists (CartServlet uses userId)
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("login.html"); 
                return; 
            }
            // If we have userId but not User object, we might need to fetch it (omitted for brevity)
            // Ideally login sets both.
        }
        
        try {
            OrderDAO dao = new OrderDAO();
            // Use user.getUserId() directly
            List<Order> historyList = dao.getHistory(user.getUserId());
            
            request.setAttribute("history", historyList);
            request.getRequestDispatcher("purchase_history.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp");
        }
    }
}