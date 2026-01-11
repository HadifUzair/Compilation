package com.marketplace.controller;

import com.marketplace.dao.CartDAO;
import com.marketplace.models.CartBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    
    private CartDAO cartDAO = new CartDAO();
    
    // Show cart page
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            List<CartBean> cartItems = cartDAO.getCartItems(userId);
            
            double subtotal = 0;
            for (CartBean item : cartItems) {
                subtotal += item.getTotal();
            }
            
            double shipping = 5.00;
            double total = subtotal + shipping;
            
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("shipping", shipping);
            request.setAttribute("total", total);
            
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading cart: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    // Handle cart operations: add/update/remove
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        String productIdStr = request.getParameter("productId");
        
        if (productIdStr == null || productIdStr.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }
        
        try {
            int productId = Integer.parseInt(productIdStr);
            
            switch (action) {
                case "add":
                    cartDAO.addToCart(userId, productId);
                    break;
                    
                case "update":
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    cartDAO.updateCart(userId, productId, quantity);
                    break;
                    
                case "remove":
                    cartDAO.removeFromCart(userId, productId);
                    break;
                    
                default:
                    request.setAttribute("error", "Invalid action");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error updating cart: " + e.getMessage());
        }
        
        response.sendRedirect("cart");
    }
}