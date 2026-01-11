<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Marketplace — Product Detail</title>

  <style>
    body{
      margin: 0;
      background: linear-gradient(180deg, #6b1dbb, #7b2bd6);
      font-family: Arial, Helvetica, sans-serif;
    }
    header{
      display: flex;
      justify-content: space-between;
      padding: 14px 22px;
      color: white;
    }
    .container{
      max-width: 1000px;
      margin: 26px auto;
      background: white;
      border-radius: 10px;
      padding: 18px;
    }
    .detail{
      display: flex;
      gap: 18px;
    }
    .thumb img{
      width: 300px;
      height: 300px;
      object-fit: cover;
      border-radius: 8px;
      border: 1px solid #eae6f6;
    }
    .price{
      color: #6b1dbb;
      font-weight: bold;
      font-size: 20px;
    }
    /* Simple button style */
    button {
        background-color: #6b1dbb;
        color: white;
        border: none;
        padding: 10px 20px;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        margin-top: 10px;
    }
    button:hover {
        background-color: #5a189a;
    }
  </style>
</head>

<body>

    <jsp:include page="navbar.jsp" />

    <div class="container">
      <div class="detail">

        <div class="thumb">
          <img src="${product.imageUrl}" alt="${product.title}" onerror="this.src='https://via.placeholder.com/300'">
        </div>

        <div class="info">
          <h2>${product.title}</h2>
          <div class="price">RM ${product.price}</div>
          
          <p>${product.description}</p>

          <p><strong>Size:</strong> ${product.size}</p>
          <p><strong>Condition:</strong> ${product.condition}</p>
          <p><strong>Status:</strong> ${product.status}</p>

          <form action="CartServlet" method="post">
              <input type="hidden" name="action" value="add">
              <input type="hidden" name="productId" value="${product.productId}">
              <button type="submit">Add to Cart</button>
          </form>
          
        </div>

      </div>
    </div>

</body>
</html>