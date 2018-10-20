<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="BOOK_PER_CAROUSEL_SCREEN" expression="T(com.learning.readers.util.ConstantUtil).BOOK_PER_CAROUSEL_SCREEN" scope="request" />

<div class="row">

	<div class="col-md-3">
	
		<%@ include file="../shared/side-navbar.jsp" %>
		
	</div>
	
	<div class="col-md-9">
	
		<h2>
			Wishlist 
			<c:choose>
				<c:when test="${not empty wishlist or fn:length(wishlist) > 0}">
					<small>(${fn:length(wishlist)})</small>
				</c:when>
				<c:when test="${empty wishlist or fn:length(wishlist) == 0}">
					<small>(Empty)</small>
				</c:when>
			</c:choose>
		</h2>
		<hr>
			
		<!-- Main Content -->
		<c:if test="${not empty wishlist or fn:length(wishlist) > 0}">
			
			<div class="container-fluid">
			
				<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(wishlist)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
				<c:forEach begin="0" end="${totalSlides}" var="count">
					
					<!-- row -->
					<div class="row">
						<!-- col-md-12 -->
						<div class="col-md=12">
							<div class="carousel slide">
								<div class="carousel-inner">
									<div class="item active">
										<div class="row">
											<c:forEach items="${wishlist}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="book" >
												<%@include file="../shared/item-widget.jsp"%>
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
		
		<c:if test="${empty wishlist or fn:length(wishlist) == 0}">
			
			<div class="row text-center">
				<br><br><br>
				<div class="col-sm-offset-3 col-sm-6">
					<img src="${image}/open-book.png" alt="No Book" title="No Book" class="img-responsive" />
				</div>
				<div class="clearfix"></div>
				
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<small>There is no any book in your wishlist, <br/><a class="btn btn-default" href="${contextRoot}/book/create"><span class="glyphicon glyphicon-plus"></span> Add Now</a></small>
				</div>
				<br><br>
			</div>
			
		</c:if>
		
		<!-- / Main Content -->
	
	</div>

</div>

<script type="text/javascript">

	$(function(){
		$('#sideNav_wishlist').addClass("active");
	});

</script>