<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Required global variable configuration -->
<c:set var="contextRoot" value="${pageContext.request.contextPath}" scope="request"/>

<spring:url var="css" value="/assets/css" scope="application"/>
<spring:url var="js" value="/assets/js" scope="application"/>
<spring:url var="image" value="/assets/images" scope="application"/>
<!-- END Required global variable configuration -->

<!-- CONSTANTS -->
<spring:eval var="APP_NAME" expression="T(com.learning.readers.util.ConstantUtil).APP_NAME" scope="request" />
<spring:eval var="BOOK_COVER_DIR" expression="T(com.learning.readers.util.ConstantUtil).BOOK_COVER_DIR" scope="request" />
<spring:eval var="BOOK_DEFAULT_COVER" expression="T(com.learning.readers.util.ConstantUtil).BOOK_DEFAULT_COVER" scope="request" />
<spring:eval var="BOOK_PER_CAROUSEL_SCREEN" expression="T(com.learning.readers.util.ConstantUtil).BOOK_PER_CAROUSEL_SCREEN" scope="request" />
<!-- /CONSTANTS -->

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><c:if test="${not empty pageTitle}">${pageTitle} - </c:if>
	${APP_NAME}</title>

<!-- Styles -->
<!-- Bootstrap Core CSS -->
<link href="${css}/bootstrap.css" rel="stylesheet">

<!-- Bootstrap readable theme -->
<link href="${css}/bootstrap-readable-theme.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="${css}/theme-shop.css" rel="stylesheet">
<link href="${css}/custom.css" rel="stylesheet">
<link href="${css}/multiple-select.css" rel="stylesheet">

<!-- Scripts -->
<!-- jQuery -->
<script src="${js}/jquery-3.1.1.js"></script>

<!-- Bootstrap Core JavaScript -->
<script src="${js}/bootstrap.js"></script>

<!-- General utility functions -->
<script src="${js}/utility.js"></script>

<script src="${js}/multiple-select.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->