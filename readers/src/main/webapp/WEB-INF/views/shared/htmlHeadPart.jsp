<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Required global variable configuration -->
<c:set var="contextRoot" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="applicationName" value="Readers'" scope="application" />

<spring:url var="css" value="resources/css" scope="application"/>
<spring:url var="js" value="resources/js" scope="application"/>
<!-- END Required global variable configuration -->


<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><c:if test="${not empty pageTitle}">${pageTitle} - </c:if>
	${applicationName}</title>

<!-- Styles -->
<!-- Bootstrap Core CSS -->
<link href="${css}/bootstrap.css" rel="stylesheet">

<!-- Bootstrap readable theme -->
<link href="${css}/bootstrap-readable-theme.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${css}/theme-shop.css" rel="stylesheet">
<link href="${css}/custom.css" rel="stylesheet">


<!-- Scripts -->
<!-- jQuery -->
<script src="${js}/jquery.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${js}/bootstrap.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->