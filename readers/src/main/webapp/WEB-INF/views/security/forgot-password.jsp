<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center">Forgot Password</h2>
<hr/>

<c:if test="${not empty forgotPasswordError}">
	<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
		${forgotPasswordError}
	</div>
</c:if>

<sf:form modelAttribute="forgotPasswordModel" class="form-horizontal" method="POST">

	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<div class="input-group">
				<span class="input-group-addon"><i class="glyphicon glyphicon-question-sign"></i></span>
				<sf:input path="email" type="email" placeholder="Email" class="form-control"/>
			</div>
			<sf:errors path="email" class="help-block" />
		</div>
	</div>
	
	<c:if test="${not empty forgotPasswordModel.secretQuestion}">
	
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<sf:hidden path="userId"/>
				<h5>${forgotPasswordModel.secretQuestion}</h5>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-flag"></i></span>
					<sf:input path="secretAnswer" type="password" placeholder="Answer" class="form-control"/>
				</div>
				<sf:errors path="secretAnswer" class="help-block" />
			</div>
		</div>
	
	</c:if>

	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 text-center">
			<button type="submit" class="btn btn-primary">Proceed</button>
		</div>
	</div>

</sf:form>