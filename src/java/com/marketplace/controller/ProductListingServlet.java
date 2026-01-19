package com.marketplace.controller;

import com.marketplace.dao.ProductDAO;
import com.marketplace.models.Product;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductListingServlet")
public class ProductListingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAO();
        List<Product> products;

        String category = request.getParameter("category");
        String sort = request.getParameter("sort");

        if (category != null) {
            products = dao.getProductsByCategory(Integer.parseInt(category));
        } else if (sort != null) {
            products = dao.getProductsSorted(sort);
        } else {
            products = dao.getAllProducts();
        }

        request.setAttribute("products", products);
        request.getRequestDispatcher("productListings.jsp").forward(request, response);
    }
}