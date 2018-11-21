<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2 class="text-center">Login</h2>
<hr/>

<c:if test="${not empty loginError}">
	<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
		${loginError}
	</div>
</c:if>
<c:if test="${not empty logoutSuccess or not empty registerSuccessMessage}">
	<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 alert alert-success text-center">
		<c:choose>
			<c:when test="${not empty logoutSuccess}">${logoutSuccess}</c:when>
			<c:when test="${not empty registerSuccessMessage}">${registerSuccessMessage}</c:when>
		</c:choose>
	</div>
</c:if>

<form method="POST" action="${contextRoot}/login" class="form-horizontal">

	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
				<input type="text" name="username" placeholder="Username" class="form-control" />
			</div>
		</div>
	</div>
	
	<div class="form-group">	
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
				<input type="password" name="password" placeholder="Password" class="form-control" />
			</div>
		</div>
	</div>
		
	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<button type="submit" class="btn btn-primary pull-left"><span class="glyphicon glyphicon-log-in"></span> Login</button>
			<a href="${contextRoot}/forgot-password" class="btn btn-link pull-right">Forgot Password?</a>
		</div>
	</div>

</form>

<c:if test="${not empty passwordChanged and passwordChanged}">

	<script type="text/javascript">
	
		$(function(){
			alert("Email : ${email}\nUsername : ${username}\nPassword : ${password}\nWe recommend you to change the password after a successful login.");
		});
	
	</script>

</c:if>