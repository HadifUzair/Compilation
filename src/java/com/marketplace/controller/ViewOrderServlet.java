/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marketplace.controller;

import com.marketplace.dao.OrderDAO;
import com.marketplace.models.CartBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */


@WebServlet("/ViewOrder")
public class ViewOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            OrderDAO dao = new OrderDAO();
            List<CartBean> items = dao.getOrderItems(orderId);
            
            request.setAttribute("orderItems", items);
            request.setAttribute("orderId", orderId);
            
            request.getRequestDispatcher("orderDetail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("HistoryServlet");
        }
    }
}
