<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript" src="${js}/custom/bookForm.js"></script>

<spring:eval var="BOOK_DIR" expression="T(com.learning.readers.util.ConstantUtil).BOOK_DIR" scope="request" />

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
			<img class="img-responsive img-rounded img-thumbnail"
				src="${contextRoot.concat(BOOK_COVER_DIR).concat(book.coverPhoto)}"
				alt="">
			<br><br>
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
			<c:if test="${userModel.userId == book.readerID}">
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
					<c:if test="${userModel.userId == book.readerID}">
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
					<br><br>
					
					<strong>Progress Status:</strong><br/>
					<span>${book.status.value}</span>
					<c:if test="${not empty book.readDetails}">
					
						<!-- Book Read Progress details -->
						<button class="btn btn-link btn-xs" data-toggle="modal" data-target="#readProgressDetails">
							<span class="glyphicon glyphicon-eye-open"></span>
						</button>
						<div id="readProgressDetails" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<!-- Model dialog content -->
								<div class="modal-content">
								
									<!-- Modal header -->
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Progress Status</h4>
									</div>
									<!-- / Model header -->
									
									<!-- Modal body -->
									<div class="modal-body">
										<div class="row">
											<div class="col-sm-12">
												<h5 class="text-center">${book.status.value}</h5>
												<hr/>
												
												<h6 class="text-center">When I started reading?</h6>
												<div class="row text-center">
													<div class="col-xs-4">
														<strong>Year</strong><br/>
														<c:if test="${not empty book.readDetails.startYear}">
															${book.readDetails.startYear}
														</c:if>
														<c:if test="${empty book.readDetails.startYear}">
															<small><i>Not Specified</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Month</strong><br/>
														<c:if test="${not empty book.readDetails.startMonth}">
															${book.readDetails.startMonth}
														</c:if>
														<c:if test="${empty book.readDetails.startMonth}">
															<small><i>Not Specified</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Date</strong><br/>
														<c:if test="${not empty book.readDetails.startDate}">
															${book.readDetails.startDate}
														</c:if>
														<c:if test="${empty book.readDetails.startDate}">
															<small><i>Not Specified</i></small>
														</c:if>
													</div>
												</div>
												
												<h6 class="text-center">When I completed reading?</h6>
												<div class="row text-center">
													<div class="col-xs-4">
														<strong>Year</strong><br/>
														<c:if test="${not empty book.readDetails.endYear}">
															${book.readDetails.endYear}
														</c:if>
														<c:if test="${empty book.readDetails.endYear}">
															<small><i>Not Specified</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Month</strong><br/>
														<c:if test="${not empty book.readDetails.endMonth}">
															${book.readDetails.endMonth}
														</c:if>
														<c:if test="${empty book.readDetails.endMonth}">
															<small><i>Not Specified</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Date</strong><br/>
														<c:if test="${not empty book.readDetails.endDate}">
															${book.readDetails.endDate}
														</c:if>
														<c:if test="${empty book.readDetails.endDate}">
															<small><i>Not Specified</i></small>
														</c:if>
													</div>
												</div>
												
												<br/>
												<strong>Rating:</strong>
												<c:if test="${not empty book.readDetails.rating}">
													${book.readDetails.rating}
												</c:if>
												<c:if test="${empty book.readDetails.rating}">
													<small><i>Not Specified</i></small>
												</c:if>
												<br/><br/>
												
												<strong>Review:</strong><br/>
												<c:if test="${not empty book.readDetails.review}">
													${book.readDetails.review}
												</c:if>
												<c:if test="${empty book.readDetails.review}">
													<small><i>Not Specified</i></small>
												</c:if>
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
						<!-- / Book Read Progress details -->
					</c:if>
					
					<c:if test="${book.status.id != 3}">
					
						<button class="btn btn-link btn-xs" data-toggle="modal" data-target="#editProgressStatusForm">
							<span class="glyphicon glyphicon-edit"></span>
						</button>
						
						<div id="editProgressStatusForm" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<!-- Model dialog content -->
								<div class="modal-content">
								
									<!-- Modal header -->
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Change Progress Status</h4>
									</div>
									<!-- / Model header -->
									
									<!-- Modal body -->
									<div class="modal-body">
										<div class="row">
											<div class="col-sm-12">
												<sf:form method="POST" action="${contextRoot}/book/edit/readProgress" modelAttribute="readProgress" class="form-horizontal">
													<jsp:include page="./shared/bookProgressStatusForm.jsp">
														<jsp:param value="0" name="colMDOffset"/>
														<jsp:param value="12" name="colMD"/>
														<jsp:param value="0" name="colSMOffset"/>
														<jsp:param value="12" name="colSM"/>
													</jsp:include>
													
													<div class="form-group">
														<div class="col-md-offset-0 col-md-12 col-sm-offset-0 col-sm-12">
															<c:if test="${empty readProgress or empty readProgress.readDeatils or empty readProgress.readDeatils.id}">
																<sf:hidden path="readDeatils.id" value="${book.id}"/>
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
	
						<c:if test="${not empty progressUpdateError}">
							<script type="text/javascript">
								$(function() {
									$("#editProgressStatusForm").modal('show');
								});
							</script>
						</c:if>
					</c:if>
					<br/><br/>
					
					<strong>Added:</strong><br/>
					<span><fmt:formatDate value="${book.creationTime}"/></span>
					
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

	<!-- Content Row -->
	<div class="row">
		<div class="col-sm-4">
			<h2>Heading 1</h2>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
				Saepe rem nisi accusamus error velit animi non ipsa placeat.
				Recusandae, suscipit, soluta quibusdam accusamus a veniam quaerat
				eveniet eligendi dolor consectetur.</p>
			<a class="btn btn-default" href="#">More Info</a>
		</div>
		<!-- /.col-md-4 -->
		<div class="col-sm-4">
			<h2>Heading 2</h2>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
				Saepe rem nisi accusamus error velit animi non ipsa placeat.
				Recusandae, suscipit, soluta quibusdam accusamus a veniam quaerat
				eveniet eligendi dolor consectetur.</p>
			<a class="btn btn-default" href="#">More Info</a>
		</div>
		<!-- /.col-md-4 -->
		<div class="col-sm-4">
			<h2>Heading 3</h2>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
				Saepe rem nisi accusamus error velit animi non ipsa placeat.
				Recusandae, suscipit, soluta quibusdam accusamus a veniam quaerat
				eveniet eligendi dolor consectetur.</p>
			<a class="btn btn-default" href="#">More Info</a>
		</div>
		<!-- /.col-md-4 -->
	</div>
	<!-- /.row -->

</div>
