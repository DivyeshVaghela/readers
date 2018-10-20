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
			<div class="image-warpper">
				<img class="img-responsive img-rounded img-thumbnail"
					src="${contextRoot.concat(BOOK_COVER_DIR).concat(book.coverPhoto)}"
					alt="">
			</div>
			
			<c:if test="${userModel.userId == book.userId}">
				<a href="${contextRoot.concat('/book/edit/').concat(book.id)}" class="btn btn-link"><span class="glyphicon glyphicon-pencil"></span></a>
			</c:if>
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
				
				<!-- Share book -->
				<button type="button" class="btn btn-link" data-toggle="modal" data-target="#bookShareForm">
					<span class="glyphicon glyphicon-share"></span>
				</button>
				<!-- / Share book -->
			</c:if>
		</div>

		<c:if test="${not empty book.source and (book.source.type.id == 2 or book.source.type.id == 3)}">
			<!-- Book Share Model dialog -->
			<div id="bookShareForm" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<!-- Model dialog content -->
					<div class="modal-content">
	
						<!-- Modal header -->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Share</h4>
						</div>
						<!-- / Model header -->
	
						<!-- Modal body -->
						<div class="modal-body">
							<div class="row">
								<div class="col-sm-12">
								
									<sf:form modelAttribute="shareBook" action="${contextRoot}/book/share" method="POST">
										<div class="form-group">
											<sf:hidden path="bookId"/>
											<sf:select path="selectedUsers" id="selectShareUsers" multiple="multiple">
												<c:forEach items="${shareBook.userNameEmailList}" var="user">
													<sf:option value="${user.userId}"> ${user.username} (${user.email})</sf:option>
												</c:forEach>
											</sf:select>
										</div>
										<div class="form-group">
											<button type="submit" class="btn btn-primary pull-right">Share</button>
										</div>
									</sf:form>
								
									<script type="text/javascript">
										$(function(){
											$(".ms-parent").css({width:"100%"});
										});
								        $("#selectShareUsers").multipleSelect({
								        	selectAll:false,
								        	placeholder:"Select Reader(s) to share..",
								            filter: true
								        });
								    </script>
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
			<!-- / Book Share Model dialog -->
		</c:if>
		
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
					<br><br>
					
					<strong>Progress Status:</strong><br/>
					<c:forEach items="${readProgress.readStatusList}" var="readStatus">
						<c:if test="${readStatus.id eq readProgress.readStatus}">
							<c:set var="readStatusValue" value="${readStatus.value}"></c:set>
						</c:if>
					</c:forEach>
					<c:choose>
						<c:when test="${not empty readStatusValue}">
							${readStatusValue}
						</c:when>
						<c:when test="${empty readStatusValue}">
							<small><i>Not Specified</i></small>
						</c:when>
					</c:choose>
					
					<c:if test="${not empty readProgress.readDetails}">
					
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
									<div class="modal-body text-center">
										<div class="row">
											<div class="col-sm-12">
												<h5>
													<c:choose>
														<c:when test="${not empty readStatusValue}">
															${readStatusValue}
														</c:when>
														<c:when test="${empty readStatusValue}">
															<small><i>Not Specified</i></small>
														</c:when>
													</c:choose>
												</h5>
												<hr/>
												
												<h6>When I started reading?</h6>
												<div class="row">
													<div class="col-xs-4">
														<strong>Year</strong><br/>
														<c:if test="${not empty readProgress.readDetails.startYear}">
															${readProgress.readDetails.startYear}
														</c:if>
														<c:if test="${empty readProgress.readDetails.startYear}">
															<small><i>-</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Month</strong><br/>
														<c:if test="${not empty readProgress.readDetails.startMonth}">
															${readProgress.readDetails.startMonth}
														</c:if>
														<c:if test="${empty readProgress.readDetails.startMonth}">
															<small><i>-</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Date</strong><br/>
														<c:if test="${not empty readProgress.readDetails.startDate}">
															${readProgress.readDetails.startDate}
														</c:if>
														<c:if test="${empty readProgress.readDetails.startDate}">
															<small><i>-</i></small>
														</c:if>
													</div>
												</div>
												<br/>
												
												<h6>When I completed reading?</h6>
												<div class="row">
													<div class="col-xs-4">
														<strong>Year</strong><br/>
														<c:if test="${not empty readProgress.readDetails.endYear}">
															${readProgress.readDetails.endYear}
														</c:if>
														<c:if test="${empty readProgress.readDetails.endYear}">
															<small><i>-</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Month</strong><br/>
														<c:if test="${not empty readProgress.readDetails.endMonth}">
															${readProgress.readDetails.endMonth}
														</c:if>
														<c:if test="${empty readProgress.readDetails.endMonth}">
															<small><i>-</i></small>
														</c:if>
													</div>
													<div class="col-xs-4">
														<strong>Date</strong><br/>
														<c:if test="${not empty readProgress.readDetails.endDate}">
															${readProgress.readDetails.endDate}
														</c:if>
														<c:if test="${empty readProgress.readDetails.endDate}">
															<small><i>-</i></small>
														</c:if>
													</div>
												</div>
												<br/>
												
												<strong>Rating:</strong>
												<br/>
												<c:if test="${not empty readProgress.readDetails.rating}">
													${readProgress.readDetails.rating}
												</c:if>
												<c:if test="${empty readProgress.readDetails.rating}">
													<small><i>Not Specified</i></small>
												</c:if>
												<br/><br/>
												
												<strong>Review:</strong><br/>
												<c:if test="${not empty readProgress.readDetails.review}">
													${readProgress.readDetails.review}
												</c:if>
												<c:if test="${empty readProgress.readDetails.review}">
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
														<c:if test="${empty readProgress or empty readProgress.readDetails or empty readProgress.readDetails.id}">
															<sf:hidden path="readDetails.id" value="${book.id}"/>
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
					<%-- <br/><br/>
					
					<strong>Added:</strong><br/>
					<span><fmt:formatDate value="${book.creationTime}"/></span> --%>
					
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
