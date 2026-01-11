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
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedUser");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Retrieve form data
            String title = request.getParameter("title");
            double price = Double.parseDouble(request.getParameter("price"));
            int categoryId = Integer.parseInt(request.getParameter("category"));
            String size = request.getParameter("size");
            String condition = request.getParameter("condition");
            String img = request.getParameter("img");
            String desc = request.getParameter("desc");

            // Create Product Bean
            Product p = new Product();
            p.setUserId(user.getUserId());
            p.setTitle(title);
            p.setPrice(price);
            p.setCategoryId(categoryId);
            p.setSize(size);
            p.setCondition(condition);
            p.setImageUrl(img);
            p.setDescription(desc);
            
            // Save using DAO
            ProductDAO dao = new ProductDAO();
            if(dao.addProduct(p)) {
                response.sendRedirect("ProductListingServlet"); // Success
            } else {
                response.sendRedirect("sell.jsp?error=database");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("sell.jsp?error=input");
        }
    }
}