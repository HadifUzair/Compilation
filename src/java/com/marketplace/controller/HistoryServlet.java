package com.marketplace.controller;

import com.marketplace.dao.OrderDAO;
import com.marketplace.models.User;  // <--- ADD THIS
import com.marketplace.models.Order; // <--- ADD THIS
import java.io.IOException;
import java.util.List; // <--- ADD THIS
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/HistoryServlet")
public class HistoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("loggedUser");
        
        if (user == null) { 
            response.sendRedirect("login.html"); 
            return; 
        }
        
        // Fetch the list of orders
        OrderDAO dao = new OrderDAO();
        List<Order> historyList = dao.getHistory(user.getUserId());
        
        // Pass the list to JSP
        request.setAttribute("history", historyList);
        request.getRequestDispatcher("purchase_history.jsp").forward(request, response);
    }
}