<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="${js}/custom/bookForm.js"></script>

<spring:eval var="BOOK_DIR" expression="T(com.learning.readers.util.ConstantUtil).BOOK_DIR" scope="request" />

<c:set var="book" value="${bookShare.book}"></c:set>

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
			<c:if test="${(userModel.userId == book.userId) and (not empty book.source and (book.source.type.id == 2 or book.source.type.id == 3))}">
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
			<c:if test="${userModel.userId == book.userId}">
				<a href="${contextRoot.concat('/book/edit/').concat(book.id)}" class="btn btn-link"><span class="glyphicon glyphicon-pencil"></span></a>
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
					<c:if test="${userModel.userId == book.userId}">
						<button class="btn btn-link btn-xs" data-toggle="modal" data-target="#editBookTypeForm">
							<span class="glyphicon glyphicon-edit"></span>
						</button>
						
						<div id="editBookTypeForm" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<!-- Model dialog content -->
								<div class="modal-content">
								
									<!-- Modal header -->
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Change Book Source</h4>
									</div>
									<!-- / Model header -->
									
									<!-- Modal body -->
									<div class="modal-body">
										<div class="row">
											<div class="col-sm-12">
												<sf:form method="POST" enctype="multipart/form-data" action="${contextRoot}/book/edit/bookSource" modelAttribute="bookSource" class="form-horizontal">
													<jsp:include page="./shared/bookSourceForm.jsp">
														<jsp:param value="0" name="colMDOffset"/>
														<jsp:param value="12" name="colMD"/>
														<jsp:param value="0" name="colSMOffset"/>
														<jsp:param value="12" name="colSM"/>
													</jsp:include>
													
													<div class="form-group">
														<div class="col-md-offset-0 col-md-12 col-sm-offset-0 col-sm-12">
															<c:if test="${empty bookSource or empty bookSource.id}">
																<sf:hidden path="id" value="${book.id}"/>
															</c:if>
															
															<button type="submit" class="btn btn-primary pull-right">Update</button>
														</div>
													</div>
												</sf:form>
											</div>
										</div>
									</div>
									
									<!-- Model footer -->
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
									</div>
								</div>
								<!-- / Model dialog content -->
							</div>
						</div>

						<c:if test="${not empty updateError}">
							<script type="text/javascript">
								$(function() {
									$("#editBookTypeForm").modal('show');
								});
							</script>
						</c:if>
					</c:if>
					<br/><br/>
					
					<strong>Shared:</strong><br/>
					<span><fmt:formatDate value="${bookShare.creationTime}"/></span>
					<br><br>
					
					<strong>Action:</strong><br/>
					<c:choose>
						<c:when test="${not empty bookShare.action}">
							${bookShare.action.value}
						</c:when>
						<c:when test="${empty readStatusValue}">
							<sf:form modelAttribute="shareAction" method="POST" class="form-inline">
								<sf:hidden path="bookShareId"/>
								<sf:select path="shareActionId" class="form-control" onchange="this.form.submit()">
									<sf:option value="">Take action..</sf:option>
									<sf:options items="${shareAction.shareActionList}" itemLabel="value" itemValue="id" />
								</sf:select>
							</sf:form>
							<!-- <small><i>Not Specified</i></small> -->
						</c:when>
					</c:choose>
					
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
