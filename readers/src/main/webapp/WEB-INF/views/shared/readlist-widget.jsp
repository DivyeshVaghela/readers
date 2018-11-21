<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-sm-4">
	<div class="well well-sm">

		<div class="row">

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="carousel-image-wrapper">
					<img src="${contextRoot.concat(BOOK_COVER_DIR).concat(BOOK_DEFAULT_COVER)}"
						alt="${readlist.name}" title="${readlist.name}"
						class="img-responsive carousel-image" />
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="row">
					<div class="col-sm-12">
						<h5 title="${readlist.name}">
							<c:choose>
								<c:when test="${readlist.creatorId eq userModel.userId}">
									<a href="${contextRoot.concat('/readlist/my/').concat(''+readlist.id)}">
										${readlist.name}
									</a>
								</c:when>
								<c:when test="${readlist.creatorId ne userModel.userId}">
									<a href="${contextRoot.concat('/readlist/').concat(''+readlist.id)}">
										${readlist.name}
									</a>
								</c:when>
							</c:choose>
							
						</h5>
					</div>
				</div>

				<div class="row text-center text-italic">
					<c:if test="${not empty readlist.totalBooks}">
						<div class="col-xs-6">
							<small>
								Books: ${readlist.totalBooks}
							</small>
						</div>
					</c:if>
				</div>
				
				<div class="clearfix"></div>
			</div>

		</div>
		<!-- /.row -->
	</div>
	<!-- /.well -->
</div>
<!-- /.col -->