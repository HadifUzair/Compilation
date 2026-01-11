<%@page import="com.marketplace.models.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>UiTM Marketplace — Listing</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

    <%-- Include the Navbar --%>
    <jsp:include page="navbar.jsp" />

    <section class="hero-small">
        <h1>Buy, Sell & Trade on Campus</h1>
        <p>The exclusive marketplace for students. Safe, fast, and local.</p>
    </section>

    <main class="app">
        <div class="layout">
            
            <aside class="filters">
                <h3>Filter</h3>
                <div class="filter-item">Price</div>
                <div class="filter-item">Stock: In stock</div>
                <div class="filter-item">Sort: A - Z</div>
                <div style="margin-top:10px;font-weight:700;color:#555">Categories</div>
                <div class="filter-item">Books</div>
                <div class="filter-item">Gadgets</div>
                <div class="filter-item">Clothing</div>
            </aside>

            <section class="catalog">
                <div class="grid" id="grid">
                    <%
                        // 1. Retrieve the list of Product objects
                        List<Product> products = (List<Product>) request.getAttribute("products");

                        // 2. Check if list is valid
                        if (products != null && !products.isEmpty()) {
                            for (Product p : products) {
                    %>
                        <a class="card" href="ProductDetailServlet?id=<%= p.getProductId() %>">
                            <div class="thumb">
                                <img src="<%= p.getImageUrl() %>" alt="<%= p.getTitle() %>" onerror="this.src='https://via.placeholder.com/150'">
                            </div>
                            <div class="title"><%= p.getTitle() %></div>
                            <div class="price">RM <%= String.format("%.2f", p.getPrice()) %></div>
                        </a>
                    <%
                            }
                        } else {
                    %>
                        <p style="padding: 20px;">No products found.</p>
                    <%
                        }
                    %>
                </div>
            </section>
        </div>
    </main>

</body>
</html>