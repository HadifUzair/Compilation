package com.marketplace.controller;

import com.marketplace.dao.ProductDAO;
import java.io.IOException;
import java.util.Map;
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

        int id = 1; // default
        if (request.getParameter("id") != null) {
            id = Integer.parseInt(request.getParameter("id"));
        }

        Map<String, Object> product =
                ProductDAO.getProductById(id);

        request.setAttribute("product", product);
        request.getRequestDispatcher("productDetail.jsp")
               .forward(request, response);
    }
}
