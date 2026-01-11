<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>Marketplace — Product Detail</title>

  <style>
    /* :root block removed to fix NetBeans token errors */
    
    body{
      margin: 0;
      /* Replaced var(--purple-1) and var(--purple-2) */
      background: linear-gradient(180deg, #6b1dbb, #7b2bd6);
      /* Moved font-family from root to body */
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
      /* Replaced var(--card-border) */
      border: 1px solid #eae6f6;
    }
    .price{
      color: #6b1dbb;
      font-weight: bold;
      font-size: 20px;
    }
  </style>
</head>

<body>

<header>
  <div><b>UiTMMarketplace</b></div>
</header>

<div class="container">
  <div class="detail">

    <div class="thumb">
      <img src="${product.img}" alt="Product Image">
    </div>

    <div class="info">
      <h2>${product.title}</h2>
      <div class="price">RM ${product.price}</div>
      <p>${product.desc}</p>

      <p>Size: ${product.size}</p>
      <p>Sold: ${product.sold}</p>

      <button>Add to Cart</button>
    </div>

  </div>
</div>

</body>
</html>