<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <title>Produkt</title>
    <div class="pull-right" style="padding-right:50px">
		<a href="?id=${product.productID}&language=pl" >Polski </a>|<a href="?id=${product.productID}&language=nl">Holenderski</a>
	</div>
</head>

<body>
    <section>
        <div class="jumbotron">
            <div class="container">
                <h1>Produkt</h1>
            </div>
        </div>
    </section>
    <section class="container">
        <div class="row">
            <div class="col-md-5">
                <img src="<c:url value="/resource/images/${product.productID}.jpg"></c:url>" alt="image" style="width:100%"/>
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p>
                    <strong><spring:message code="product.productID.label"/></strong>
                    <span class="label label-warning">${product.productID}</span>
                </p>
                <p>
                    <strong><spring:message code="product.Manufacturer.label"/></strong>: ${product.manufacturer}
                </p>
                <p>
                    <strong><spring:message code="product.Category.label"/></strong>: ${product.category}
                </p>
                <p>
                    <strong><spring:message code="product.UnitsInStock.label"/></strong>:${product.unitsInStock}
                </p>
                <h4>${product.unitPrice} <spring:message code="product.Currency.label"/></h4>
                <p>
                    <a href="<spring:url value="/products" />" class="btn btndefault">
                        <span class="glyphicon-hand-left glyphicon"></span> <spring:message code="product.Back.label"/>
                    </a>
                    <a href="#" class="btn btn-warning btn-large">
                        <span class="glyphicon-shopping-cart glyphicon"></span>
                        <spring:message code="product.OrderNow.label"/>
                    </a>
                </p>
            </div>
        </div>
    </section>
</body>

</html>