<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">

<head>

	<%@ include file="./shared/htmlHeadPart.jsp" %>
    
</head>

<body>

    <!-- Navigation -->
    <%@ include file="./shared/navbar.jsp" %>

    <!-- Page Content -->
    <div class="container" id="main-content">
    	
    	<jsp:include page="${contentPagePath}"></jsp:include>
    	
    </div>
    <!-- /.container -->

    <div class="container">

        <!-- Footer -->
        <%@ include file="./shared/footer.jsp" %>

    </div>
    <!-- /.container -->

</body>

</html>