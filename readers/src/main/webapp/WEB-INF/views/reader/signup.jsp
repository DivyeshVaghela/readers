<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
					<sf:input path="email" type="email" placeholder="Email" class="form-control"/>
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
					<span class="input-group-addon"><i class="glyphicon glyphicon-question-sign"></i></span>
					<sf:select path="selectedSecretQuestion" class="form-control" onchange="secretQuestionSelected(this.value)">
						<sf:option value="">Select Secret Question..</sf:option>
						<sf:options items="${ signUpReaderModel.secretQuestionList }" itemValue="id" itemLabel="question"/>
					</sf:select>
				</div>
				<sf:errors path="selectedSecretQuestion" class="help-block"/>
			</div>
		</div>
	
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><i class="glyphicon glyphicon-flag"></i></span>
					<sf:input path="secretQuestionAnswer" type="text" placeholder="Answer" class="form-control" disabled="true"/>
				</div>
				<sf:errors path="secretQuestionAnswer" class="help-block"/>
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
				<%-- <sf:errors path="" class="help-block"/> --%>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-4 col-md-4 col-sm-offset-3 col-sm-6">
				<button type="submit" class="btn btn-primary pull-left"><span class="glyphicon glyphicon-user"></span> Sign up</button>
				<a href="${contextRoot}/login" class="btn btn-success pull-right"><span class="glyphicon glyphicon-log-in"></span> Login</a>
			</div>
		</div>
	</sf:form>

</div>

<script type="text/javascript">

	function secretQuestionSelected(value){
		
		if (value==null || value==""){
			$("#secretQuestionAnswer").attr("disabled","disabled");
		} else {
			$("#secretQuestionAnswer").attr("disabled", false);
		}
	}

</script>