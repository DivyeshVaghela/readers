<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.learning.readers.util.ConstantUtil;"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>

	<%@ include file="./shared/htmlHeadPart.jsp" %>
    
</head>

<body>

    <!-- Navigation -->
    <%@ include file="./shared/navbar.jsp" %>

	<!-- breadcrumb -->
	<%@ include file="./shared/breadcrumb.jsp" %>
	
    <!-- Page Content -->
    <div class="container" id="main-content">
    	
    	<c:if test="${not empty successMessage}">
    		<div class="alert alert-success">
    			<strong>Hurray..!</strong> ${successMessage}
    		</div>
    	</c:if>
    	
    	<c:if test="${not empty errorMessage}">
    		<div class="alert alert-warning">
    			<strong>Sorry..!</strong> ${errorMessage}
    		</div>
    	</c:if>
    	
    	<c:if test="${not empty grettingMessage}">
    		<h2>${grettingMessage}</h2>
    		<p>${APP_NAME}</p>
    	</c:if>
    	
    	<jsp:include page="${contentPagePath}"></jsp:include>
    	
    </div>
    <!-- /.container -->

    <div class="container">

        <!-- Footer -->
        <%@ include file="./shared/footer.jsp" %>

    </div>
    <!-- /.container -->

	<script type="text/javascript">
		$(function(){
			
			$(".nav-tabs a").click(function(){
		        $(this).tab('show');
		    });
		});
	</script>

</body>

</html>