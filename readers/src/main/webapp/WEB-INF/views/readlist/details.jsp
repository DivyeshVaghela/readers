<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div>

	<h2>${readlist.name}</h2>
	<hr />

	<div class="row">

		<div class="col-sm-4 text-center">
			<div class="image-warpper">
				<img class="img-responsive img-rounded img-thumbnail"
					src="${contextRoot.concat(BOOK_COVER_DIR).concat(BOOK_DEFAULT_COVER)}"
					alt="">
			</div>
			
			<c:if test="${userModel.userId == readlist.creatorId}">
				<script type="text/javascript">
					function confirmRemove(){
						return confirm("Please make sure that once you remove a readlist, you will not be able to get it back. Are you sure you want to remove this readlist?");
					}
				</script>
				
				<form action="${contextRoot}/readlist/remove/${readlist.id}" method="POST" onsubmit="return confirmRemove()" style="display:inline;">
					<button type="submit" class="btn btn-link"><span class="glyphicon glyphicon-trash text-danger"></span></button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				</form>
				<a href="${contextRoot.concat('/readlist/my/').concat(readlist.id).concat('/edit')}" class="btn btn-link"><span class="glyphicon glyphicon-pencil"></span></a>
			</c:if>
		</div>


		<!-- col-sm-8 -->
		<div class="col-sm-8">

			<!-- row -->
			<div class="row">
				<br />
				<div class="col-sm-6">
					<strong>Name:</strong><br> <span>${readlist.name}</span> <br />
					<br /> <strong>Books:</strong> <span>${fn:length(readlist.books)}</span>
					<br /> <br />

				</div>

				<div class="col-sm-6">
					<strong>Created by:</strong><br /> <span>${readlist.creator.username}
						(${readlist.creator.email})</span> <br /> <br /> <strong>Created
						at:</strong><br /> <span><fmt:formatDate
							value="${readlist.creationTime}" pattern="dd MMM, yyyy (hh:mm a)" /></span>
					<br /> <br />
				</div>
			</div>
			<!-- /row -->

		</div>
		<!-- /col-sm-8 -->
	</div>

	<br/>
	<div class="row">
		<div class="col-sm-12">
			${readlist.details}
		</div>
	</div>

	<br />
	<h2>
		Books
	</h2>
	<hr />

	<div class="row">

		<div class="col-md-12">
			<c:if
				test="${not empty readlist.books or fn:length(readlist.books) ne 0}">

				<div class="container-fluid">

					<spring:eval var="totalSlides"
						expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(readlist.books)}) div (BOOK_PER_CAROUSEL_SCREEN + 1)) - 1" />

					<c:forEach begin="0" end="${totalSlides}" var="count">

						<!-- row -->
						<div class="row">
							<!-- col-md-12 -->
							<div class="col-md=12">
								<div class="carousel slide">
									<div class="carousel-inner">
										<div class="item active">
											<div class="row">
												<c:forEach items="${readlist.books}"
													begin="${count * (BOOK_PER_CAROUSEL_SCREEN + 1)}"
													end="${(count * (BOOK_PER_CAROUSEL_SCREEN + 1)) + (BOOK_PER_CAROUSEL_SCREEN + 1) - 1}"
													var="readlistBook">
													<%@include file="./shared/readlistBook-widget.jsp"%>
												</c:forEach>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- /col-md-12 -->
						</div>
						<!-- /row -->

					</c:forEach>
				</div>

			</c:if>
			<c:if
				test="${empty readlist.books or fn:length(readlist.books) eq 0}">
				<div class="well">
					<small> <em> There is no any book in this readlist <c:if
								test="${readlist.creator.id eq userModel.userId}">
								<button type="button" class="btn btn-default"
									data-toggle="modal" data-target="#editReadlistBooks">
									<span class="glyphicon glyphicon-plus"></span> Add
								</button>
							</c:if>
					</em>
					</small>
				</div>
			</c:if>
		</div>

	</div>
</div>

</div>