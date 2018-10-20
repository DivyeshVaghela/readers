<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="col-sm-4">
	<div class="well well-sm">

		<div class="row">

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="carousel-image-wrapper">
					<img
						src="${contextRoot.concat(BOOK_COVER_DIR.concat(book.coverPhoto))}"
						alt="${book.fullName}" title="${book.fullName}"
						class="img-responsive carousel-image" />
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="row">
					<div class="col-sm-12">
						<h5 title="${book.name.concat(' ').concat(book.edition)}">
							<c:choose>
								<c:when test="${not empty book.bookShare and not empty book.bookShare.id}">
									<c:choose>
										<c:when test="${empty book.bookShare.senderId and empty book.bookShare.receiverId}">
											<a href="${contextRoot.concat('/book/shared/').concat(''+book.bookShare.id)}">
												${book.fullName} </a>
										</c:when>
										<c:when test="${book.bookShare.senderId eq userModel.userId}">
											<a href="${contextRoot.concat('/book/').concat(''+book.id)}">
												${book.fullName} </a>
										</c:when>
										<c:when test="${book.bookShare.receiverId eq userModel.userId}">
											<a href="${contextRoot.concat('/book/shared/').concat(''+book.bookShare.id)}">
												${book.fullName} </a>
										</c:when>
									</c:choose>
								</c:when>
								<c:when test="${empty book.bookShare or empty book.bookShare.id}">
									<a href="${contextRoot.concat('/book/').concat(''+book.id)}">
										${book.fullName} </a>
								</c:when>
							</c:choose>
						</h5>
					</div>
				</div>

				<c:if test="${not empty book.bookShare and (not empty book.bookShare.sender or not empty book.bookShare.receiver)}">
					<!-- Book share info -->
					<div class="row">
						<div class="col-xs-12">
							<small>
								<c:if test="${book.bookShare.senderId ne userModel.userId}">
									From: <em>${book.bookShare.sender}</em>
								</c:if>
								<c:if test="${book.bookShare.receiverId ne userModel.userId}">
									To: <em>${book.bookShare.receiver}</em>
								</c:if>
							</small>
						</div>
					</div>
					<!-- /Book share info -->
					
					<c:if test="${not empty book.bookShare.shareDateTime}">
						<!-- Share Date time -->
						<div class="row">
							<div class="col-xs-12">
								<small>
									<em><fmt:formatDate value="${book.bookShare.shareDateTime}" pattern="dd MMM, yyyy (hh:mm aa)"/></em>
								</small>
							</div>
						</div>
						<!-- /Share Date time -->
					</c:if>
				</c:if>

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