<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row">

	<div class="col-md-3">
		<%@ include file="../shared/side-navbar.jsp" %>
	</div>

	<div class="col-md-9">
	
		<h2>
			Readlists
			<c:choose>
				<c:when test="${not empty readlists or fn:length(readlists) > 0}">
					<small>(${fn:length(readlists)})</small>
				</c:when>
				<c:when test="${empty readlists or fn:length(readlists) == 0}">
					<small>(Empty)</small>
				</c:when>
			</c:choose>
		</h2>
		<hr>
	
		<!-- Main Content -->
		<c:if test="${not empty readlists or fn:length(readlists) ne 0}">
		
			<div class="container-fluid">
			
				<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(readlists)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
				<c:forEach begin="0" end="${totalSlides}" var="count">
					
					<!-- row -->
					<div class="row">
						<!-- col-md-12 -->
						<div class="col-md=12">
							<div class="carousel slide">
								<div class="carousel-inner">
									<div class="item active">
										<div class="row">
											<c:forEach items="${readlists}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="readlist" >
												<%@include file="../shared/readlist-widget.jsp"%>
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
		
		<c:if test="${(empty readlists or fn:length(readlists) eq 0)}">
			<div class="row text-center">
				<br><br><br>
				<div class="col-sm-offset-3 col-sm-6">
					<img src="${image}/open-book.png" alt="No Book" title="No Book" class="img-responsive" />
				</div>
				<div class="clearfix"></div>
				
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<small>You haven't created any readlists yet, <br/><a class="btn btn-default" href="${contextRoot}/readlist/create"><span class="glyphicon glyphicon-plus"></span> Create Now</a></small>
				</div>
				<br><br>
			</div>
		</c:if>
		
		<!-- /Main Content -->
	</div>	

</div>


<script type="text/javascript">
	$(function(){
		$('#subMenu_readlist').addClass('in');
		$('#subMenuLink_readlist').removeClass('collapsed');
		$('#sideNav_readlist').addClass("active");
	});
</script>