<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>${product.title} | Marketplace</title>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">

  <style>
    body {
      margin: 0;
      min-height: 100vh;
      background: linear-gradient(180deg, #6b1dbb, #7b2bd6);
      font-family: Arial, Helvetica, sans-serif;
      
      display: flex;              
      justify-content: center;    
      align-items: center;        
    }

    .container {
      width: 100%;
      max-width: 900px;
      background: white;
      border-radius: 12px;
      padding: 30px;
      box-shadow: 0 10px 25px rgba(0,0,0,0.2);
    }

    .detail {
      display: flex;
      gap: 30px;
      align-items: flex-start;
    }

    /* --- UPDATED CSS STARTS HERE --- */
    .thumb {
      /* Optional: defines the size of the container explicitly */
      width: 500px; 
      height: 500px;
      flex-shrink: 0; /* Prevents the image from shrinking if text is long */
    }

    .thumb img {
      width: 100%;
      height: 100%;
      object-fit: cover; /* Ensures image fills the square without distortion */
      border-radius: 8px;
      border: 1px solid #eae6f6;
      display: block;
    }
    /* --- UPDATED CSS ENDS HERE --- */

    .info {
      flex: 1;
    }

    .price {
      color: #6b1dbb;
      font-weight: bold;
      font-size: 24px;
      margin-bottom: 10px;
    }

    h2 { margin-top: 0; }

    /* BUTTON STYLES */
    .btn-group {
        display: flex;
        gap: 10px; 
        margin-top: 20px;
    }

    .btn-cart {
        background-color: #6b1dbb;
        color: white;
        border: none;
        padding: 12px 24px;
        border-radius: 6px;
        cursor: pointer;
        font-size: 16px;
        font-weight: bold;
        transition: background 0.2s;
    }
    .btn-cart:hover {
        background-color: #5a189a;
    }

    .btn-back {
        background-color: #e0e0e0; /* Light gray */
        color: #333;
        text-decoration: none; 
        padding: 12px 24px;
        border-radius: 6px;
        font-size: 16px;
        font-weight: bold;
        display: inline-block;
        transition: background 0.2s;
        border: none;
    }
    .btn-back:hover {
        background-color: #ccc;
    }

  </style>
</head>

<body>

    <div class="container">
      <div class="detail">

        <div class="thumb">
            <img src="${product.imageUrl}" 
                 alt="${product.title}" 
                 onerror="this.src='https://via.placeholder.com/500'"> </div>

        <div class="info">
          <h2>${product.title}</h2>
          <div class="price">RM ${product.price}</div>
          
          <p>${product.description}</p>

          <p><strong>Size:</strong> ${product.size}</p>
          <p><strong>Condition:</strong> ${product.condition}</p>
          <p><strong>Status:</strong> ${product.status}</p>

          <div class="btn-group">
              <form action="CartServlet" method="post">
                  <input type="hidden" name="action" value="add">
                  <input type="hidden" name="productId" value="${product.productId}">
                  <button type="submit" class="btn-cart">Add to Cart</button>
              </form>

              <a href="ProductListingServlet" class="btn-back">Back to Shop</a>
          </div>
          
        </div>

      </div>
    </div>

</body>
</html>