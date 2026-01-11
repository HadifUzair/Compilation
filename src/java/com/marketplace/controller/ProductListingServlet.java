package com.marketplace.controller;

import com.marketplace.dao.ProductDAO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

        List<Map<String,Object>> products =
                ProductDAO.getAllProducts();

        request.setAttribute("products", products);
        request.getRequestDispatcher("productListing.jsp")
               .forward(request, response);
    }
}
