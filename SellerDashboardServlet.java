package com.marketplace.controller;

import com.marketplace.dao.SellerDAO;
import com.marketplace.models.SaleRecord;
import com.marketplace.models.User;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SellerDashboardServlet", urlPatterns = {"/SellerDashboardServlet"})
public class SellerDashboardServlet extends HttpServlet {

    private SellerDAO sellerDAO = new SellerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");

        // Force Login
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Fetch Data
        Map<String, Object> stats = sellerDAO.getSellerStats(user.getUserId());
        List<SaleRecord> salesHistory = sellerDAO.getSalesHistory(user.getUserId());

        // Set Attributes
        request.setAttribute("revenue", stats.get("revenue"));
        request.setAttribute("itemsSold", stats.get("itemsSold"));
        request.setAttribute("activeListings", stats.get("activeListings"));
        request.setAttribute("salesHistory", salesHistory);

        // Forward to View
        request.getRequestDispatcher("seller_dashboard.jsp").forward(request, response);
    }
}