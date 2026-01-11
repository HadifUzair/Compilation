<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html>
<head>
  <title>Marketplace — Listing</title>

  <style>
    body{
      margin:0;
      font-family:Arial;
      background:linear-gradient(180deg,#6b1dbb,#7b2bd6);
    }
    header{
      color:white;
      padding:16px;
      font-size:20px;
      font-weight:bold;
    }
    main{
      background:white;
      max-width:1000px;
      margin:20px auto;
      padding:20px;
      border-radius:10px;
    }
    .grid{
      display:grid;
      grid-template-columns:repeat(auto-fill,200px);
      gap:15px;
    }
    .card{
      text-decoration:none;
      color:black;
      border:1px solid #ddd;
      border-radius:8px;
      padding:10px;
    }
    .card img{
      width:100%;
      height:140px;
      object-fit:cover;
      border-radius:6px;
    }
    .price{
      color:#6b1dbb;
      font-weight:bold;
      margin-top:6px;
    }
  </style>
</head>

<body>

<header>TMMarketplace</header>

<main>
  <div class="grid">

    <c:forEach items="${products}" var="p">
      <a class="card"
         href="ProductDetailServlet?id=${p.id}">
        <img src="${p.img}">
        <div><b>${p.title}</b></div>
        <div class="price">RM ${p.price}</div>
      </a>
    </c:forEach>

  </div>
</main>

</body>
</html>
