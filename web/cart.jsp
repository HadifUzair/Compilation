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
    
    DecimalFormat df = new DecimalFormat("0.00");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Cart | UiTMMarketplace</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

    <jsp:include page="navbar.jsp" />

    <div class="container">
        <h1 class="page-title">Shopping Cart</h1>

        <% if (cartItems == null || cartItems.isEmpty()) { %>
            <div class="empty-cart" style="text-align: center; padding: 40px;">
                <h3>Your cart is empty</h3>
                <p>Add some items from the marketplace!</p>
                <a href="shopping.jsp" class="btn-submit">Continue Shopping</a>
            </div>
        <% } else { %>
            <div class="table-responsive">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>Product</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Subtotal</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (CartBean item : cartItems) { %>
                        <tr>
                            <td>
                                <img src="<%= item.getImageUrl() != null ? item.getImageUrl() : "images/default.jpg" %>" 
                                     alt="<%= item.getProductName() %>" class="item-img">
                                <%= item.getProductName() %>
                                <% if (item.getSize() != null && !item.getSize().equals("N/A")) { %>
                                    (Size <%= item.getSize() %>)
                                <% } %>
                            </td>
                            <td>RM <%= df.format(item.getPrice()) %></td>
                            <td>
                                <form action="cart" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                                    <input type="number" name="quantity" value="<%= item.getQuantity() %>" 
                                           min="1" max="10" class="qty-input">
                                    <button type="submit" class="btn-small" style="padding: 5px 10px;">Update</button>
                                </form>
                            </td>
                            <td>RM <%= df.format(item.getTotal()) %></td>
                            <td>
                                <form action="cart" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="remove">
                                    <input type="hidden" name="productId" value="<%= item.getProductId() %>">
                                    <button type="submit" class="btn-remove">Remove</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>

            <div class="cart-summary">
                <h3>Order Summary</h3>
                <div class="summary-row">
                    <span>Subtotal</span>
                    <span>RM <%= df.format(subtotal) %></span>
                </div>
                <div class="summary-row">
                    <span>Shipping (Campus Meetup)</span>
                    <span>RM <%= df.format(shipping) %></span>
                </div>
                <div class="summary-row summary-total">
                    <span>Total</span>
                    <span>RM <%= df.format(total) %></span>
                </div>
                <div>
                    <a href="checkout" class="btn-submit">Proceed to Checkout</a>
                </div>
            </div>
        <% } %>
    </div>

    <jsp:include page="footer.jsp" />

</body>
</html>