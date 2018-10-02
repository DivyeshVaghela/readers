<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div>

	<h2 class="text-center">Signup Reader</h2>
	<hr>

	<c:if test="${not empty signUpError}">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
			${signUpError}
		</div>
	</c:if>

	<sf:form modelAttribute="signUpReaderModel" method="POST" class="form-horizontal">

		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
					<sf:input path="email" type="text" placeholder="Email" class="form-control"/>
				</div>
				<sf:errors path="email" class="help-block"/>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
					<sf:input path="username" type="text" placeholder="Username" class="form-control"/>
				</div>
				<sf:errors path="username" class="help-block"/>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					<sf:input path="password" type="password" placeholder="Password" class="form-control"/>
				</div>
				<sf:errors path="password" class="help-block"/>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
					<sf:input path="confirmPassword" type="password" placeholder="Confirm Password" class="form-control"/>
				</div>
				<sf:errors path="confirmPassword" class="help-block"/>
				<sf:errors path="passwordMatching" class="help-block"/>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 text-center">
				<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-user"></span> Sign up</button>
			</div>
		</div>
	</sf:form>

</div>