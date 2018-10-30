<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="user" value="${groupMember.member}"></c:set>

<div class="col-sm-3">
	<div class="well well-sm">

		<div class="row">

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="carousel-image-wrapper">
					<img
						src="${contextRoot.concat(BOOK_COVER_DIR).concat(BOOK_DEFAULT_COVER)}"
						alt="${user.username}" title="${user.username}"
						class="img-responsive img-circle carousel-image" />
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="row">
					<div class="col-sm-12">
						<h5 class="text-center" title="${user.username}">
							<a href="${contextRoot.concat('/user/').concat(''+user.id)}">
										${user.username} </a>
						</h5>
						
						<br/>
						<span class="glyphicon glyphicon-envelope" title="Email"></span> ${user.email}
						<br/>
						<span class="glyphicon glyphicon-tent" title="Joined At"></span> <fmt:formatDate value="${groupMember.creationTime}" pattern="dd MMM, yyyy (hh:mm a)"/> 
					</div>
				</div>

				<%-- <c:if test="${empty book.bookShare}">
					<div class="row">
						<div class="col-xs-6">
							<h5>Read</h5>
						</div>
						<div class="col-xs-6">
							<h5 class="pull-right text-primary">Rating</h5>
						</div>
					</div>
				</c:if> --%>
				<div class="clearfix"></div>
			</div>

		</div>
		<!-- /.row -->
	</div>
	<!-- /.well -->
</div>
<!-- /.col -->