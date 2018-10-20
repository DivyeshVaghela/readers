<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="BOOK_PER_CAROUSEL_SCREEN" expression="T(com.learning.readers.util.ConstantUtil).BOOK_PER_CAROUSEL_SCREEN" scope="request" />

<div class="row">

	<div class="col-md-3">
	
		<%@ include file="../shared/side-navbar.jsp" %>
		
	</div>

	<div class="col-md-9">

		<!-- Main Content -->

		<c:if test="${not empty model.resentlyAdded or fn:length(model.resentlyAdded) ne 0}">
		
			<h2>Recently added</h2>
			<hr>
	
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<div id="resentlyAdded" class="carousel slide">
	
							<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(model.resentlyAdded)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
							<c:if test="${totalSlides ne 0}">
								<ol class="carousel-indicators">
									<c:forEach begin="0" end="${totalSlides}" var="count">
										<li data-target="#resentlyAdded" data-slide-to="${count}" class="${(count eq 0) ? 'active': ''}"></li>
									</c:forEach>
								</ol>
							</c:if>
	
							<!-- Carousel items -->
							<div class="carousel-inner">
	
								<c:forEach begin="0" end="${totalSlides}" var="count">
								
									<div class="item ${count == 0 ? 'active' : ''}">
										<div class="row">
								
											<c:forEach items="${model.resentlyAdded}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="book" >
												<%@include file="../shared/item-widget.jsp"%>
												<%-- <jsp:include page="../shared/item-widget.jsp"></jsp:include> --%>
											</c:forEach>
								
										</div>
										<!-- /.row -->
									</div>
									<!-- /.item -->
								</c:forEach>
							</div>
							<!--.carousel-inner-->
							
							<c:if test="${totalSlides ne 0}">
								<a data-slide="prev" href="#resentlyAdded"
									class="left carousel-control" style="margin-top:auto;margin-bottom:auto;">&lt;</a>
								<a data-slide="next"
									href="#resentlyAdded" class="right carousel-control" style="margin-top:auto;margin-bottom:auto;">&gt;</a>
							</c:if>
						</div>
						<!--.Carousel-->
	
					</div>
				</div>
			</div>
			<!--.container-->
		</c:if>
		
		<!-- wishlist -->
		<c:if test="${not empty model.wishList or fn:length(model.wishList) ne 0}">
		
			<h2>Wish List</h2>
			<hr>
	
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<div id="wishList" class="carousel slide">
	
							<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(model.wishList)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
							<c:if test="${totalSlides ne 0}">
								<ol class="carousel-indicators">
									<c:forEach begin="0" end="${totalSlides}" var="count">
										<li data-target="#wishList" data-slide-to="${count}" class="${(count eq 0) ? 'active': ''}"></li>
									</c:forEach>
								</ol>
							</c:if>
	
							<!-- Carousel items -->
							<div class="carousel-inner">
	
								<c:forEach begin="0" end="${totalSlides}" var="count">
								
									<div class="item ${count == 0 ? 'active' : ''}">
										<div class="row">
								
											<c:forEach items="${model.wishList}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="book" >
												<%@include file="../shared/item-widget.jsp"%>
												<%-- <jsp:include page="../shared/item-widget.jsp"></jsp:include> --%>
											</c:forEach>
								
										</div>
										<!-- /.row -->
									</div>
									<!-- /.item -->
								</c:forEach>
							</div>
							<!--.carousel-inner-->
							
							<c:if test="${totalSlides ne 0}">
								<a data-slide="prev" href="#wishList"
									class="left carousel-control" style="margin-top:auto;margin-bottom:auto;">&lt;</a>
								<a data-slide="next"
									href="#wishList" class="right carousel-control" style="margin-top:auto;margin-bottom:auto;">&gt;</a>
							</c:if>
						</div>
						<!--.Carousel-->
	
					</div>
				</div>
			</div>
			<!--.container-->
		</c:if>
		<!-- /wishlist -->
		
		<!-- shared -->
		<c:if test="${not empty model.sharedToMeBooks or fn:length(model.sharedToMeBooks) ne 0}">
		
			<h2>Shared</h2>
			<hr>
	
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-12">
						<div id="sharedToMeBooks" class="carousel slide">
	
							<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(model.sharedToMeBooks)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
							<c:if test="${totalSlides ne 0}">
								<ol class="carousel-indicators">
									<c:forEach begin="0" end="${totalSlides}" var="count">
										<li data-target="#sharedToMeBooks" data-slide-to="${count}" class="${(count eq 0) ? 'active': ''}"></li>
									</c:forEach>
								</ol>
							</c:if>
	
							<!-- Carousel items -->
							<div class="carousel-inner">
	
								<c:forEach begin="0" end="${totalSlides}" var="count">
								
									<div class="item ${count == 0 ? 'active' : ''}">
										<div class="row">
								
											<c:forEach items="${model.sharedToMeBooks}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="book" >
												<%@include file="../shared/item-widget.jsp"%>
												<%-- <jsp:include page="../shared/item-widget.jsp"></jsp:include> --%>
											</c:forEach>
								
										</div>
										<!-- /.row -->
									</div>
									<!-- /.item -->
								</c:forEach>
							</div>
							<!--.carousel-inner-->
							
							<c:if test="${totalSlides ne 0}">
								<a data-slide="prev" href="#sharedToMeBooks"
									class="left carousel-control" style="margin-top:auto;margin-bottom:auto;">&lt;</a>
								<a data-slide="next"
									href="#sharedToMeBooks" class="right carousel-control" style="margin-top:auto;margin-bottom:auto;">&gt;</a>
							</c:if>
						</div>
						<!--.Carousel-->
	
					</div>
				</div>
			</div>
			<!--.container-->
		</c:if>
		<!-- shared -->
		
		<c:if test="${(empty model.resentlyAdded or fn:length(model.resentlyAdded) eq 0) and (empty model.wishList or fn:length(model.wishList) eq 0) and (empty model.sharedToMeBooks or fn:length(model.sharedToMeBooks) eq 0)}">
			<div class="row text-center">
				<br><br><br>
				<div class="col-sm-offset-3 col-sm-6">
					<img src="${image}/open-book.png" alt="No Book" title="No Book" class="img-responsive" />
				</div>
				<div class="clearfix"></div>
				
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<small>There is no any book in your library, <br/><a class="btn btn-default" href="${contextRoot}/book/create"><span class="glyphicon glyphicon-plus"></span> Add Now</a></small>
				</div>
				<br><br>
			</div>
		</c:if>
		
	</div>

</div>
<!-- /.row -->

<script type="text/javascript">

	$(function(){
		$('.carousel').carousel({
			interval:false
		});
		
		$('#sideNav_home').addClass("active");
	});

</script>