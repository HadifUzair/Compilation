package com.marketplace.controller;

import com.marketplace.dao.ProductDAO;
import com.marketplace.models.Product;
import com.marketplace.models.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SellServlet")
public class SellServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Check if user is logged in
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // 2. Retrieve form data
        String title = request.getParameter("title");
        double price = Double.parseDouble(request.getParameter("price"));
        String categoryStr = request.getParameter("category");
        String size = request.getParameter("size");
        String condition = request.getParameter("condition");
        String img = request.getParameter("img");
        String desc = request.getParameter("desc");
        
        int categoryId = 1; // Default
        try { categoryId = Integer.parseInt(categoryStr); } catch(Exception e){}

        // 3. Create Product Object
        Product p = new Product();
        p.setUserId(user.getUserId()); // Link to logged-in user
        p.setTitle(title);
        p.setPrice(price);
        p.setCategoryId(categoryId);
        p.setSize(size);
        p.setCondition(condition);
        p.setImageUrl(img);
        p.setDescription(desc);
        
        // 4. Save to DB
        ProductDAO dao = new ProductDAO();
        boolean success = dao.addProduct(p);
        
        if (success) {
            response.sendRedirect("home?status=listed");
        } else {
            request.setAttribute("error", "Failed to list item. Try again.");
            request.getRequestDispatcher("sell.jsp").forward(request, response);
        }
    }
}