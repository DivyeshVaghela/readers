<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="${js}/custom/bookForm.js"></script>

<spring:eval var="BOOK_DIR" expression="T(com.learning.readers.util.ConstantUtil).BOOK_DIR" scope="request" />

<c:set var="book" value="${groupBook.book}"></c:set>

<div>

	<h2>
		${book.name}
		<c:if test="${not empty book.edition}">
			<small>(${book.edition})</small>
		</c:if>
	</h2>
	<hr/>
	
	<!-- Heading Row -->
	<div class="row">
		<div class="col-sm-4 text-center">
			<div class="image-warpper">
				<img class="img-responsive img-rounded img-thumbnail"
					src="${contextRoot.concat(BOOK_COVER_DIR).concat(book.coverPhoto)}"
					alt="">
			</div>
			
			<c:if test="${not empty book.source and (book.source.type.id == 2 or book.source.type.id == 3)}">
				<c:choose>
					<c:when test="${book.source.type.id == 2}">
						<a class="btn btn-primary"
							href="${contextRoot.concat(BOOK_DIR).concat(book.source.value)}" target="_blank">Read
							Now</a>
					</c:when>
					<c:when test="${book.source.type.id == 3}">
						<a class="btn btn-primary" href="${book.source.value}"
							target="_blank">Read Now</a>
					</c:when>
				</c:choose>
			</c:if>
		</div>

		<!-- /.col-md-8 -->
		<div class="col-sm-8">
		
			<div class="row">
				<br/>
				<div class="col-sm-6">
					<strong>Title:</strong><br>
					<span>${book.name}</span>
					<br/><br/>
					
					<strong>Edition:</strong><br>
					<c:choose>
						<c:when test="${not empty book.edition}">
							${book.edition}
						</c:when>
						<c:when test="${empty book.edition}">
							<small><i>Not specified</i></small>
						</c:when>
					</c:choose>
					<br/><br/>
				
					<strong>Publication:</strong><br> 
					<c:if test="${not empty book.publication}">
						<span>${book.publication.name}</span>
					</c:if>
					<c:if test="${empty book.publication}">
						<small><i>Not Specified</i></small>
					</c:if>

					<br />
					<br /> <strong>Author(s):</strong><br />
					<c:choose>
						<c:when
							test="${empty book.authors or fn:length(book.authors) eq 0}">
							<small><i>Not specified</i></small>
						</c:when>
						<c:when
							test="${not empty book.authors and fn:length(book.authors) gt 0}">
							<c:forEach items="${book.authors}" var="author" varStatus="loop">
								<c:if test="${loop.index ne 0}">, </c:if>
								<span>${author.fullName}</span>
							</c:forEach>
						</c:when>
					</c:choose>
					
					<br/><br/>
				</div>
				
				<div class="col-sm-6">
					
					<strong>ISBN:</strong><br>
					<c:choose>
						<c:when test="${not empty book.ISBN and not empty book.ISBN}">
							${book.ISBN}
						</c:when>
						<c:when test="${empty book.ISBN or empty book.ISBN}">
							<small><i>Not Specified</i></small>
						</c:when>
					</c:choose>
					<br><br>
					
					<strong>
						Book Type:
					</strong><br>
					<c:choose>
						<c:when test="${not empty book.source and not empty book.source.type}">
							${book.source.type.value}
						</c:when>
						<c:when test="${empty book.source or empty book.source.type}">
							<small><i>Not Specified</i></small>
						</c:when>
					</c:choose>
					<br><br>
					
					<strong>Added:</strong><br/>
					<span><fmt:formatDate value="${groupBook.creationTime}" pattern="dd MMM, yyyy (hh:mm a)"/></span>
					
				</div>
			</div>
			<!-- /.row -->
			
		</div>
		<!-- /.col-md-4 -->
	</div>
	<!-- /.row -->

	<hr/>
	
	<!-- .row -->
	<div class="row">
		<c:if test="${not empty book.details}">
			<p class="col-lg-12">${book.details}</p>
		</c:if>
	</div>
	<!-- /.row -->

</div>
