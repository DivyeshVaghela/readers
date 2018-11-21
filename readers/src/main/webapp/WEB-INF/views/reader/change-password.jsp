<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<h2 class="text-center">Change Password</h2>
<hr/>

<c:if test="${not empty changePasswordError}">
	<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
		${changePasswordError}
	</div>
</c:if>

<sf:form modelAttribute="changePasswordModel" method="POST" class="form-horizontal">

	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<sf:password path="currentPassword" placeholder="Current Password" class="form-control"/>
			<sf:errors path="currentPassword" class="help-block" />
		</div>
	</div>
	
	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<sf:password path="newPassword" placeholder="New Password" class="form-control"/>
			<sf:errors path="newPassword" class="help-block" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
			<sf:password path="confirmNewPassword" placeholder="Confirm New Password" class="form-control"/>
			<sf:errors path="confirmNewPassword" class="help-block" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6 text-center">
			<button type="submit" class="btn btn-primary">Change</button>
		</div>
	</div>
	
</sf:form>