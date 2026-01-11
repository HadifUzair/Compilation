<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Sell Item | UiTM Marketplace</title>
  <link rel="stylesheet" href="css/styles.css">
</head>
<body>

  <jsp:include page="navbar.jsp" />

  <%-- Security Check: Redirect if not logged in --%>
  <% if(session.getAttribute("loggedUser") == null) { 
       response.sendRedirect("login.jsp"); 
       return;
     } 
  %>

  <div class="container">
    <div class="auth-card" style="max-width: 600px; margin: 0 auto;">
      <h2 style="color: #6a0dad;">List an Item</h2>
      
      <form action="SellServlet" method="post">
        
        <div class="form-group">
          <label>Item Title</label>
          <input type="text" name="title" placeholder="e.g. Calculus Textbook" required>
        </div>

        <div class="form-group" style="display:flex; gap:15px;">
           <div style="flex:1">
             <label>Price (RM)</label>
             <input type="number" step="0.01" name="price" placeholder="0.00" required>
           </div>
           <div style="flex:1">
             <label>Category</label>
             <select name="category" style="width:100%; padding:12px; border-radius:8px; border:2px solid #eee;">
               <option value="1">Books</option>
               <option value="2">Gadgets</option>
               <option value="3">Clothing</option>
               <option value="4">Stationery</option>
               <option value="5">Others</option>
             </select>
           </div>
        </div>

        <div class="form-group" style="display:flex; gap:15px;">
           <div style="flex:1">
             <label>Size (Optional)</label>
             <input type="text" name="size" placeholder="e.g. M, L, N/A">
           </div>
           <div style="flex:1">
             <label>Condition</label>
             <select name="condition" style="width:100%; padding:12px; border-radius:8px; border:2px solid #eee;">
               <option value="Used">Used</option>
               <option value="New">New</option>
               <option value="Refurbished">Refurbished</option>
             </select>
           </div>
        </div>

        <div class="form-group">
          <label>Image URL</label>
          <input type="text" name="img" placeholder="https://..." required>
        </div>

        <div class="form-group">
          <label>Description</label>
          <textarea name="desc" rows="4" style="width:100%; padding:12px; border:2px solid #eee; border-radius:8px;" placeholder="Describe your item..."></textarea>
        </div>

        <button type="submit" class="btn-submit">List Item</button>
      </form>
    </div>
  </div>

  <jsp:include page="footer.jsp" />

</body>
</html>