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
			Share History
		</h2>
		<hr>
			
		<!-- Main Content -->
		<c:if test="${not empty shareHistoryList or fn:length(shareHistoryList) > 0}">
			
			<div class="container-fluid">
			
				<spring:eval var="totalSlides" expression="T(java.lang.Math).ceil(T(java.lang.Double).parseDouble(${fn:length(shareHistoryList)}) div BOOK_PER_CAROUSEL_SCREEN) - 1"></spring:eval>
				<c:forEach begin="0" end="${totalSlides}" var="count">
					
					<!-- row -->
					<div class="row">
						<!-- col-md-12 -->
						<div class="col-md=12">
							<div class="carousel slide">
								<div class="carousel-inner">
									<div class="item active">
										<div class="row">
											<c:forEach items="${shareHistoryList}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="book" >
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
		
		<c:if test="${empty shareHistoryList or fn:length(shareHistoryList) == 0}">
			
			<div class="row text-center">
				<br><br><br>
				<div class="col-sm-offset-3 col-sm-6">
					<img src="${image}/open-book.png" alt="No Book" title="No Book" class="img-responsive" />
				</div>
				<div class="clearfix"></div>
				
				<br>
				<div class="col-sm-offset-3 col-sm-6">
					<small>
						Your share history is empty.<br/>
						You can share the books to your friends and that share history will be displayed here.
					</small>
				</div>
				<br><br>
			</div>
			
		</c:if>
		
		<!-- / Main Content -->
	
	</div>

</div>

<script type="text/javascript">

	$(function(){
		$('#subMenu_share').addClass('in');
		$('#subMenuLink_share').removeClass('collapsed');
		$('#sideNav_sharedHistory').addClass("active");
	});

</script>