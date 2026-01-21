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
                        <h3>Pickup Information</h3>
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
                            <label>Pickup Address *</label>
                            <input type="text" name="shippingAddress" 
                                   value="UiTM Marketplace Center" 
                                   readonly
                                   style="background-color: #f5f5f5; font-weight: bold;"
                                   required>
                            <small style="color: #666; display: block; margin-top: 5px;">
                                All items will be picked up at this location
                            </small>
                        </div>
                        <div class="form-group">
                            <label>Phone Number *</label>
                            <input type="tel" name="shippingPhone" 
                                   value="<%= session.getAttribute("phone") != null ? session.getAttribute("phone") : "" %>" 
                                   placeholder="012-3456789" required>
                        </div>
                        
                        <!-- Pickup Information Box -->
                        <div style="background-color: #f8f9fa; border-left: 4px solid #6a0dad; padding: 15px; margin-top: 20px; border-radius: 5px;">
                            <h4 style="color: #6a0dad; margin-top: 0;">📍 Pickup Location Details:</h4>
                            <p style="margin: 5px 0;"><strong>UiTM Marketplace Center</strong></p>
                            <p style="margin: 5px 0; color: #555;">Student Hub, Level 2, Main Building, UiTM Shah Alam</p>
                            <p style="margin: 5px 0; color: #555;">
                                <strong>Hours:</strong> Monday-Friday: 9:00 AM – 5:00 PM, Saturday: 10:00 AM – 2:00 PM
                            </p>
                            <p style="margin: 5px 0; color: #555;">
                                <strong>Note:</strong> Bring your student ID for verification when picking up items.
                            </p>
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
                                Cash on Pickup
                            </label>
                        </div>
                    </div>
                    
                    <div class="form-group" style="margin-top: 20px; display: flex; align-items: center; gap: 15px;">
                        <button type="submit" class="btn-submit" style="width: auto; padding: 12px 30px;">Place Order</button>
                        <a href="cart" style="background-color: #6c757d; color: white; padding: 12px 25px; border-radius: 8px; text-decoration: none; font-weight: bold; display: inline-block;">Back to Cart</a>
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
                    
                    <!-- How Campus Pickup Works -->
                    <div style="margin-top: 25px; padding-top: 15px; border-top: 1px dashed #ddd;">
                        <h4 style="color: #6a0dad; margin-bottom: 10px;">📋 How Campus Pickup Works:</h4>
                        <ol style="margin: 0; padding-left: 20px; color: #555;">
                            <li>Place your order and make payment</li>
                            <li>Seller delivers item to UiTM Marketplace Center after 3 days (working days only)</li>
                            <li>Pick up your item at the center during operating hours</li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>