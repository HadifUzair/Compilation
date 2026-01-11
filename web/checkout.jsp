<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.marketplace.models.*, java.text.DecimalFormat" %>
<%
    // Check login
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    List<CartBean> cartItems = (List<CartBean>) request.getAttribute("cartItems");
    Double subtotal = (Double) request.getAttribute("subtotal");
    Double shipping = (Double) request.getAttribute("shipping");
    Double total = (Double) request.getAttribute("total");
    
    if (cartItems == null || cartItems.isEmpty()) {
        response.sendRedirect("cart");
        return;
    }
    
    DecimalFormat df = new DecimalFormat("0.00");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout | UiTMMarketplace</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

    <jsp:include page="navbar.jsp" />

    <div class="container">
        <h1 class="page-title">Checkout</h1>
        
        <% if (error != null) { %>
            <div class="error-message" style="background: #ffebee; color: #c62828; padding: 10px; border-radius: 5px; margin-bottom: 20px;">
                <%= error %>
            </div>
        <% } %>

        <div class="checkout-grid">
            
            <!-- Left Column: Forms -->
            <div class="checkout-form">
                <form action="checkout" method="post">
                    <div class="section-box">
                        <h3>Shipping Information</h3>
                        <div class="form-group">
                            <label>Full Name *</label>
                            <input type="text" name="shippingName" 
                                   value="<%= session.getAttribute("fullName") != null ? session.getAttribute("fullName") : "" %>" 
                                   placeholder="Ali bin Abu" required>
                        </div>
                        <div class="form-group">
                            <label>Student ID *</label>
                            <input type="text" name="studentId" 
                                   value="<%= session.getAttribute("studentId") != null ? session.getAttribute("studentId") : "" %>" 
                                   placeholder="2023xxxxxx" required>
                        </div>
                        <div class="form-group">
                            <label>College/Faculty Address *</label>
                            <input type="text" name="shippingAddress" 
                                   value="<%= session.getAttribute("address") != null ? session.getAttribute("address") : "" %>" 
                                   placeholder="Kolej Mawar, UiTM Shah Alam..." required>
                        </div>
                        <div class="form-group">
                            <label>Phone Number *</label>
                            <input type="tel" name="shippingPhone" 
                                   value="<%= session.getAttribute("phone") != null ? session.getAttribute("phone") : "" %>" 
                                   placeholder="012-3456789" required>
                        </div>
                    </div>

                    <div class="section-box">
                        <h3>Payment Method</h3>
                        <div class="form-group">
                            <label>
                                <input type="radio" name="paymentMethod" value="FPX" checked> 
                                Online Transfer (FPX)
                            </label>
                            <br>
                            <label>
                                <input type="radio" name="paymentMethod" value="COD"> 
                                Cash on Delivery (Campus Meetup)
                            </label>
                        </div>
                    </div>
                    
                    <div class="form-group" style="margin-top: 20px;">
                        <button type="submit" class="btn-submit">Place Order</button>
                        <a href="cart" class="btn-cancel" style="background: #ccc; color: #333; padding: 10px 20px; border-radius: 5px; text-decoration: none;">Back to Cart</a>
                    </div>
                </form>
            </div>

            <!-- Right Column: Summary -->
            <div class="checkout-summary">
                <div class="section-box">
                    <h3>Your Order</h3>
                    <% for (CartBean item : cartItems) { %>
                    <div class="summary-row">
                        <span><%= item.getProductName() %> x <%= item.getQuantity() %></span>
                        <span>RM <%= df.format(item.getTotal()) %></span>
                    </div>
                    <% } %>
                    <div class="summary-row">
                        <span>Shipping</span>
                        <span>RM <%= df.format(shipping) %></span>
                    </div>
                    <div class="summary-row summary-total">
                        <span>Total Pay</span>
                        <span>RM <%= df.format(total) %></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>