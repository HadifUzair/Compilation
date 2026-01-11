package com.marketplace.controller;

import com.marketplace.dao.CartDAO;
import com.marketplace.dao.OrderDAO;
import com.marketplace.models.CartBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    
    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();
    
    // Show checkout page
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
            
            if (cartItems.isEmpty()) {
                response.sendRedirect("cart");
                return;
            }
            
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
            
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading checkout: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    
    // Process order
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Get form data
        String shippingName = request.getParameter("shippingName");
        // Student ID is usually immutable/from session, but if form submits it:
        String studentId = request.getParameter("studentId"); 
        String shippingAddress = request.getParameter("shippingAddress");
        String shippingPhone = request.getParameter("shippingPhone");
        String paymentMethod = request.getParameter("paymentMethod");
        
        // Validate
        if (shippingName == null || shippingName.trim().isEmpty() ||
            shippingAddress == null || shippingAddress.trim().isEmpty()) {
            
            request.setAttribute("error", "Please fill in all required fields");
            doGet(request, response);
            return;
        }
        
        try {
            // Get cart items
            List<CartBean> cartItems = cartDAO.getCartItems(userId);
            if (cartItems.isEmpty()) {
                request.setAttribute("error", "Your cart is empty");
                doGet(request, response);
                return;
            }
            
            // Calculate total
            double subtotal = 0;
            for (CartBean item : cartItems) {
                subtotal += item.getTotal();
            }
            double totalAmount = subtotal + 5.00;
            
            // FIX: Use 'orderDAO' (instance) instead of 'OrderDAO' (class)
            int orderId = orderDAO.createOrder(userId, totalAmount, 
                                              shippingName, shippingAddress, 
                                              shippingPhone, paymentMethod);
            
            if (orderId != -1) {
                // Add order items
                orderDAO.addOrderItems(orderId, cartItems);
                
                // Mark products as sold
                orderDAO.markProductsAsSold(cartItems);
                
                // Clear cart
                cartDAO.clearCart(userId);
                
                // Store in session
                session.setAttribute("lastOrderId", orderId);
                session.setAttribute("paymentMethod", paymentMethod);
                
                // Redirect to success page
                response.sendRedirect("orderSuccess.jsp");
            } else {
                throw new Exception("Failed to generate Order ID");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Checkout failed: " + e.getMessage());
            doGet(request, response);
        }
    }
}