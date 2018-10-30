<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="book" value="${groupBook.book}"></c:set>

<div class="col-sm-3">
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
							<a href="${contextRoot.concat('/group/').concat(''+groupBook.group.id).concat('/book/').concat(''+groupBook.id)}">
										${book.fullName} </a>
						</h5>
					</div>
				</div>

				<div class="row">
					
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