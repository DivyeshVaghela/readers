<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">

	<div class="col-md-3">
		<%@ include file="../shared/side-navbar.jsp" %>
	</div>

	<div class="col-md-9">
		
		<h2>
			<c:if test="${readerGroups[0].creatorId eq userModel.userId}">
				My 
			</c:if>
			Groups
			<c:choose>
				<c:when test="${not empty readerGroups or fn:length(readerGroups) > 0}">
					<small>(${fn:length(readerGroups)})</small>
				</c:when>
				<c:when test="${empty readerGroups or fn:length(readerGroups) == 0}">
					<small>(Empty)</small>
				</c:when>
			</c:choose>
		</h2>
		<hr>
	
	
		<!-- Main Content -->
		<c:if test="${not empty readerGroups or fn:length(readerGroups) ne 0}">
		
			<div class="container-fluid">
			
				<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(readerGroups)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
				<c:forEach begin="0" end="${totalSlides}" var="count">
					
					<!-- row -->
					<div class="row">
						<!-- col-md-12 -->
						<div class="col-md=12">
							<div class="carousel slide">
								<div class="carousel-inner">
									<div class="item active">
										<div class="row">
											<c:forEach items="${readerGroups}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="group" >
												<%@include file="../shared/group-widget.jsp"%>
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
		
		<c:if test="${(empty readerGroups or fn:length(readerGroups) eq 0)}">
			<div class="row text-center">
				<br><br><br>
				<div class="col-sm-offset-3 col-sm-6">
					<img src="${image}/open-book.png" alt="No Book" title="No Book" class="img-responsive" />
				</div>
				<div class="clearfix"></div>
				
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<small>You haven't created any groups yet, <br/><a class="btn btn-default" href="${contextRoot}/book/group/create"><span class="glyphicon glyphicon-plus"></span> Create Now</a></small>
				</div>
				<br><br>
			</div>
		</c:if>
		
		<!-- /Main Content -->
	
	</div>
</div>

<script type="text/javascript">
	$(function(){
		$('#subMenu_groups').addClass('in');
		$('#subMenuLink_groups').removeClass('collapsed');
	});
</script>

<c:choose>
	<c:when test="${not empty group && group eq 'My'}">
		<script type="text/javascript">
			$(function(){
				$('#sideNav_myGroup').addClass("active");
			});
		</script>
	</c:when>
	<c:when test="${empty group || group ne 'My'}">
		<script type="text/javascript">
			$(function(){
				$('#sideNav_groups').addClass("active");
			});
		</script>
	</c:when>
</c:choose>