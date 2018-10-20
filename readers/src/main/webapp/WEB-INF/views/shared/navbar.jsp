<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${contextRoot}">Readers'</a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<security:authorize access="isAuthenticated()">
				<!-- <ul class="nav navbar-nav">
					<li><a href="#">About</a></li>
					<li><a href="#">Services</a></li>
					<li><a href="#">Contact</a></li>
				</ul> -->
			</security:authorize>


			<ul class="nav navbar-nav navbar-right">
				<security:authorize access="!isAuthenticated()">
					<li><a href="${contextRoot}/login"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
					<li><a href="${contextRoot}/signup-reader"><span class="glyphicon glyphicon-user"></span> Sign up</a></li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
				
					<li class="dropdown">
						
						<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							${userModel.username}
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="javascript:void(0)" onclick="performLogout()">
								<span class="glyphicon glyphicon-log-out" id="navMenu_logout"></span> Logout</a>
		
								<form action="${contextRoot}/logout" id="form_logout"
									method="POST">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</form>
							</li>
						</ul>
					</li>
				</security:authorize>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container -->
</nav>

<script type="text/javascript">
	function performLogout(){
		$("#form_logout").submit();
	}
</script>