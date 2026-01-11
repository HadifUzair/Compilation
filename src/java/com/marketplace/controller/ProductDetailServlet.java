package com.marketplace.controller;

import com.marketplace.dao.ProductDAO;
import com.marketplace.models.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductDetailServlet")
public class ProductDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = 1; 
        try {
            if (request.getParameter("id") != null) {
                id = Integer.parseInt(request.getParameter("id"));
            }
        } catch (NumberFormatException e) {
            id = 1;
        }

        ProductDAO dao = new ProductDAO();
        Product product = dao.getProductById(id); // Now returns Product object

        request.setAttribute("product", product);
        request.getRequestDispatcher("productDetail.jsp").forward(request, response);
    }
}