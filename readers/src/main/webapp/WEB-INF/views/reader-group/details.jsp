<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div>

	<h2>
		${group.name}
	</h2>
	<hr/>
	
	<div class="row">
		
		<div class="col-sm-4 text-center">
			<div class="image-warpper">
				<img class="img-responsive img-rounded img-thumbnail"
					src="${contextRoot.concat(BOOK_COVER_DIR).concat(BOOK_DEFAULT_COVER)}"
					alt="">
			</div>
		</div>

		<!-- col-sm-8 -->
		<div class="col-sm-8">
		
			<!-- row -->
			<div class="row">
				<br/>
				<div class="col-sm-6">
					<strong>Name:</strong><br>
					<span>${group.name}</span>
					<br/><br/>
					
					<strong>Members:</strong>
					<span>${fn:length(group.members)}</span>
					<br/><br/>
					
					<strong>Books:</strong>
					<span>${fn:length(group.books)}</span>
					<br/><br/>
					
				</div>
				
				<div class="col-sm-6">
					<strong>Created by:</strong><br/>
					<span>${group.creator.username} (${group.creator.email})</span>
					<br/><br/>
					
					<strong>Created at:</strong><br/>
					<span><fmt:formatDate value="${group.creationTime}" pattern="dd MMM, yyyy (hh:mm a)"/></span>
					<br/><br/>
				</div>
			</div>
			<!-- /row -->
			
		</div>
		<!-- /col-sm-8 -->
		
	</div>	
	

	<br/>
	<h2>
		Books
		<c:if test="${group.creator.id eq userModel.userId}">
			<button class="btn btn-link btn-xs" data-toggle="modal" data-target="#editGroupBooks">
				<span class="glyphicon glyphicon-pencil"></span>
			</button>
		</c:if> 
	</h2>
	<c:if test="${group.creator.id eq userModel.userId}">
		<div id="editGroupBooks" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Model dialog content -->
				<div class="modal-content">
					<!-- Modal header -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Edit Books</h4>
					</div>
					<!-- / Model header -->

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-offset-2 col-sm-8">
	
								<sf:form modelAttribute="readerGroupModel"
									action="${contextRoot}/group/my/${group.id}/editGroupBooks/"
									method="POST">
									<div class="form-group">
									
										<fieldset>
											<legend>Select Books:</legend>
										</fieldset>
										<sf:hidden path="id" />
										<sf:hidden path="name" />
										<sf:select path="selectedBookId" id="selectedBookId"
											multiple="true">
											<c:forEach items="${readerGroupModel.bookList}" var="book">
												<sf:option value="${book.id}"> 
															${book.name}
															<c:if test="${not empty book.edition}"> 
																(${book.edition})
															</c:if>
												</sf:option>
											</c:forEach>
										</sf:select>
	
										<script type="text/javascript">
											$("#selectedBookId")
													.multipleSelect(
															{
																selectAll : false,
																placeholder : "Select Book(s) to add in group..",
																filter : true,
																minimumCountSelected : 0,
																countSelected : "# selected"
															});
										</script>
										<c:if
											test="${empty readerGroupModel.selectedBookId or fn:length(readerGroupModel.selectedBookId) eq 0}">
											<script type="text/javascript">
												$("#selectedBookId")
														.multipleSelect(
																"uncheckAll");
											</script>
										</c:if>
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-primary pull-right">Update</button>
									</div>
								</sf:form>
							</div>
						</div>
					</div>
	
				</div>
				<!--/ Model dialog content -->
			</div>
		</div>
	</c:if>
	<hr/>
	
	<div class="row">
	
		<div class="col-md-12">
			<c:if test="${not empty group.books or fn:length(group.books) ne 0}">
			
				<div class="container-fluid">
				
					<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(group.books)}) div (BOOK_PER_CAROUSEL_SCREEN + 1)) - 1" />
					
					<c:forEach begin="0" end="${totalSlides}" var="count">
						
						<!-- row -->
						<div class="row">
							<!-- col-md-12 -->
							<div class="col-md=12">
								<div class="carousel slide">
									<div class="carousel-inner">
										<div class="item active">
											<div class="row">
												<c:forEach items="${group.books}" begin="${count * (BOOK_PER_CAROUSEL_SCREEN + 1)}" end="${(count * (BOOK_PER_CAROUSEL_SCREEN + 1)) + (BOOK_PER_CAROUSEL_SCREEN + 1) - 1}" var="groupBook" >
													<%@include file="./shared/groupBook-widget.jsp"%>
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
			<c:if test="${empty group.books or fn:length(group.books) eq 0}">
				<div class="well">
					<small>
						<em>
							There is no any book shared in this group.
							<c:if test="${group.creator.id eq userModel.userId}">
								<br/>
								You can share your books to this group, and that will be displayed here.
							</c:if>
						</em>
					</small>
				</div>
			</c:if>
		</div>
	
	</div>
	
	<br/>
	<h2>
		Members
		
		<c:if test="${group.creator.id eq userModel.userId}">
			<button class="btn btn-link btn-xs" data-toggle="modal" data-target="#editGroupMembers">
				<span class="glyphicon glyphicon-pencil"></span>
			</button>
		</c:if> 
	</h2>
	<c:if test="${group.creator.id eq userModel.userId}">
		<div id="editGroupMembers" class="modal fade" role="dialog">
			<div class="modal-dialog">

				<!-- Model dialog content -->
				<div class="modal-content">
					<!-- Modal header -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Edit Members</h4>
					</div>
					<!-- / Model header -->

					<!-- Modal body -->
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-offset-2 col-sm-8">
	
								<sf:form modelAttribute="readerGroupModel"
									action="${contextRoot}/group/my/${group.id}/editGroupMembers/"
									method="POST">
									<div class="form-group">
									
										<fieldset>
											<legend>Select Readers</legend>
										</fieldset>
									
										<sf:hidden path="id" />
										<sf:hidden path="name" />
										
										<sf:select path="selectedMemberdId" id="selectedMemberdId"
											multiple="true">
											<c:forEach items="${readerGroupModel.userList}" var="user">
												<sf:option value="${user.userId}"> ${user.email} (${user.username})</sf:option>
											</c:forEach>
										</sf:select>
	
										<script type="text/javascript">
											$("#selectedMemberdId")
													.multipleSelect(
															{
																selectAll : false,
																placeholder : "Select Reader(s) to add in group..",
																filter : true,
																minimumCountSelected : 0,
																countSelected : "# selected"
															});
										</script>
										<c:if
											test="${empty readerGroupModel.selectedMemberdId or fn:length(readerGroupModel.selectedMemberdId) eq 0}">
											<script type="text/javascript">
												$("#selectedMemberdId")
														.multipleSelect(
																"uncheckAll");
											</script>
										</c:if>
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-primary pull-right">Update</button>
									</div>
								</sf:form>
							</div>
						</div>
					</div>
				
				</div>
				<!--/ Model dialog content -->
			</div>
		</div>
	</c:if>
	<hr/>
	
	<div class="row">
	
		<div class="col-md-12">
			<c:if test="${not empty group.members or fn:length(group.members) ne 0}">
			
				<div class="container-fluid">
				
					<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(group.members)}) div (BOOK_PER_CAROUSEL_SCREEN + 1)) - 1"></spring:eval>
					<c:forEach begin="0" end="${totalSlides}" var="count">
						
						<!-- row -->
						<div class="row">
							<!-- col-md-12 -->
							<div class="col-md=12">
								<div class="carousel slide">
									<div class="carousel-inner">
										<div class="item active">
											<div class="row">
												<c:forEach items="${group.members}" begin="${count * (BOOK_PER_CAROUSEL_SCREEN + 1)}" end="${(count * (BOOK_PER_CAROUSEL_SCREEN + 1)) + (BOOK_PER_CAROUSEL_SCREEN + 1) - 1}" var="groupMember" >
													<%@include file="./shared/groupMember-widget.jsp"%>
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
			<c:if test="${empty group.members or fn:length(group.members) eq 0}">
				<div class="well">
					<small>
						<em>
							There is no any member in this group.
							<c:if test="${group.creator.id eq userModel.userId}">
								<br/>
								You can add members in this group, and that will be displayed here.
							</c:if>
						</em>
					</small>
				</div>
			</c:if>
		</div>
	
	</div>
	
</div>