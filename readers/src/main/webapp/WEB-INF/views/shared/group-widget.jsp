<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-sm-4">
	<div class="well well-sm">

		<div class="row">

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="carousel-image-wrapper">
					<img src="${contextRoot.concat(BOOK_COVER_DIR).concat(BOOK_DEFAULT_COVER)}"
						alt="${group.name}" title="${group.name}"
						class="img-responsive carousel-image" />
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="row">
					<div class="col-sm-12">
						<h5 title="${group.name}">
							<c:choose>
								<c:when test="${group.creatorId eq userModel.userId}">
									<a href="${contextRoot.concat('/group/my/').concat(''+group.id)}">
										${group.name}
									</a>
								</c:when>
								<c:when test="${group.creatorId ne userModel.userId}">
									<a href="${contextRoot.concat('/group/').concat(''+group.id)}">
										${group.name}
									</a>
								</c:when>
							</c:choose>
							
						</h5>
					</div>
				</div>

				<div class="row text-center text-italic">
					<c:if test="${not empty group.totalMembers}">
						<div class="col-xs-6">
							<small>
								Members<br/>${group.totalMembers}
							</small>
						</div>
					</c:if>
					<c:if test="${not empty group.totalBooks}">
						<div class="col-xs-6">
							<small>
								Books<br/>${group.totalBooks}
							</small>
						</div>
					</c:if>
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