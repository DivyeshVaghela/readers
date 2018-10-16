<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<spring:eval var="BOOK_PER_CAROUSEL_SCREEN" expression="T(com.learning.readers.util.ConstantUtil).BOOK_PER_CAROUSEL_SCREEN" scope="request" />

<div class="row">

	<div class="col-md-3">
	
		<hr>
		<div class="panel-group" id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#quickLinks">Quick Links</a>
					</h4>
				</div>
				<div id="quickLinks" class="panel-collapse collapse in">
					<div class="list-group">
						<div class="list-group-item">
							<a href="${contextRoot}/book/create"><span class="glyphicon glyphicon-plus-sign"></span> New Book</a>
						</div>
						<div class="list-group-item">
							Deleted <span class="badge">5</span>
						</div>
						<div class="list-group-item">
							Warnings <span class="badge">3</span>
						</div>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapse2">Collapsible Group 2</a>
					</h4>
				</div>
				<div id="collapse2" class="panel-collapse collapse">
					<div class="panel-body">
					Lorem ipsum dolor sit amet,
						consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
						labore et dolore magna aliqua. Ut enim ad minim veniam, quis
						nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
						consequat.
						</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapse3">Collapsible Group 3</a>
					</h4>
				</div>
				<div id="collapse3" class="panel-collapse collapse">
					<div class="panel-body">Lorem ipsum dolor sit amet,
						consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
						labore et dolore magna aliqua. Ut enim ad minim veniam, quis
						nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
						consequat.</div>
				</div>
			</div>
		</div>
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
							<ol class="carousel-indicators">
								<c:forEach begin="0" end="${totalSlides}" var="count">
									<li data-target="#resentlyAdded" data-slide-to="${count}" class="${(count eq 0) ? 'active': ''}"></li>
								</c:forEach>
							</ol>
	
							<!-- Carousel items -->
							<div class="carousel-inner">
	
								<c:forEach begin="0" end="${totalSlides}" var="count">
								
									<div class="item ${count == 0 ? 'active' : ''}">
										<div class="row">
								
											<c:forEach items="${model.resentlyAdded}" begin="${count * BOOK_PER_CAROUSEL_SCREEN}" end="${(count * BOOK_PER_CAROUSEL_SCREEN) + BOOK_PER_CAROUSEL_SCREEN - 1}" var="book" >
												<div class="col-sm-4">
													<div class="well well-sm">
	
														<div class="row">
	
															<div class="col-xs-12 col-sm-12 col-md-12">
																<div class="carousel-image-wrapper">
																	<img
																		src="${contextRoot.concat(BOOK_COVER_DIR.concat(book.coverPhoto))}"
																		alt="${book.name.concat(' ').concat(book.edition)}" title="${book.name.concat(' ').concat(book.edition)}" class="img-responsive carousel-image" />
																</div>
															</div>
	
															<div class="col-xs-12 col-sm-12 col-md-12">
																<div class="row">
																	<div class="col-sm-12">
																		<h5 title="${book.name.concat(' ').concat(book.edition)}">
																			<a href="${contextRoot.concat('/book/').concat(''+book.id)}">
																				${book.name}
																				<c:if test="${not empty book.edition}">
																					<small>(${book.edition})</small>
																				</c:if>
																			</a>
																		</h5>
																	</div>
																	<%-- <div class="col-sm-12">
																		<p class="data-text-color">
																			${book.details}
																		</p>
																	</div> --%>
																</div>
	
																<div class="row">
																	<div class="col-xs-6">
																		<h5>Read</h5>
																	</div>
																	<div class="col-xs-6">
																		<h5 class="pull-right text-primary">Rating</h5>
																	</div>
																</div>
	
																<div class="clearfix"></div>
															</div>
	
														</div>
														<!-- /.row -->
													</div>
													<!-- /.well -->
												</div>
												<!-- /.col -->
											</c:forEach>
								
										</div>
										<!-- /.row -->
									</div>
									<!-- /.item -->
								</c:forEach>
								<%-- <div class="item active">
									<div class="row">
										<div class="col-sm-4">
											<div class="well well-sm">
	
												<div class="row">
												
													<div class="col-xs-12 col-sm-12 col-md-12">
														<div>
															<img
																src="${contextRoot.concat(BOOK_COVER_DIR.concat(BOOK_DEFAULT_COVER))}"
																alt="Readers'" class="img-responsive carousel-image" />
														</div>
													</div>
												
													<div class="col-xs-12 col-sm-12 col-md-12">
														<div class="row">
															<div class="col-sm-12">
																<h5><a href="#">Operating System Concepts (Edition)</a></h5>
															</div>
															<div class="col-sm-12">
																<p class="data-text-color">The small description about
																	the book</p>
															</div>
														</div>
			
														<div class="row">
															<div class="col-xs-6">
																<h5>Read</h5>
															</div>
															<div class="col-xs-6">
																<h5 class="pull-right text-primary">Rating</h5>
															</div>
														</div>
			
														<div class="clearfix"></div>
													</div>
													
												</div>
	
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="well well-sm">
	
												<div>
													<img
														src="${contextRoot.concat(BOOK_COVER_DIR.concat(BOOK_DEFAULT_COVER))}"
														alt="Readers'" class="img-responsive carousel-image" />
												</div>
	
												<div class="row">
													<div class="col-sm-12">
														<h5><a href="#">Operating System Concepts (Edition)</a></h5>
													</div>
													<div class="col-sm-12">
														<p class="data-text-color">The small description about
															the book</p>
													</div>
												</div>
	
												<div class="row">
													<div class="col-xs-6">
														<h5>Read</h5>
													</div>
													<div class="col-xs-6">
														<h5 class="pull-right text-primary">Rating</h5>
													</div>
												</div>
	
												<div class="clearfix"></div>
	
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="well well-sm">
	
												<div>
													<img
														src="${contextRoot.concat(BOOK_COVER_DIR.concat(BOOK_DEFAULT_COVER))}"
														alt="Readers'" class="img-responsive carousel-image" />
												</div>
	
												<div class="row">
													<div class="col-sm-12">
														<h5><a href="#">Operating System Concepts (Edition)</a></h5>
													</div>
													<div class="col-sm-12">
														<p class="data-text-color">The small description about
															the book</p>
													</div>
												</div>
	
												<div class="row">
													<div class="col-xs-6">
														<h5>Read</h5>
													</div>
													<div class="col-xs-6">
														<h5 class="pull-right text-primary">Rating</h5>
													</div>
												</div>
	
												<div class="clearfix"></div>
	
											</div>
										</div>
									</div>
									<!--.row-->
								</div>
								<!--.item-->
	
								<div class="item">
									<div class="row">
										<div class="col-sm-4">
											<div class="well well-sm">
	
												<div>
													<img
														src="${contextRoot.concat(BOOK_COVER_DIR.concat(BOOK_DEFAULT_COVER))}"
														alt="Readers'" class="img-responsive carousel-image" />
												</div>
	
												<div class="row">
													<div class="col-sm-12">
														<h5><a href="#">Operating System Concepts (Edition)</a></h5>
													</div>
													<div class="col-sm-12">
														<p class="data-text-color">The small description about
															the book</p>
													</div>
												</div>
	
												<div class="row">
													<div class="col-xs-6">
														<h5>Read</h5>
													</div>
													<div class="col-xs-6">
														<h5 class="pull-right text-primary">Rating</h5>
													</div>
												</div>
	
												<div class="clearfix"></div>
	
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="well well-sm">
	
												<div>
													<img
														src="${contextRoot.concat(BOOK_COVER_DIR.concat(BOOK_DEFAULT_COVER))}"
														alt="Readers'" class="img-responsive carousel-image" />
												</div>
	
												<div class="row">
													<div class="col-sm-12">
														<h5><a href="#">Operating System Concepts (Edition)</a></h5>
													</div>
													<div class="col-sm-12">
														<p class="data-text-color">The small description about
															the book</p>
													</div>
												</div>
	
												<div class="row">
													<div class="col-xs-6">
														<h5>Read</h5>
													</div>
													<div class="col-xs-6">
														<h5 class="pull-right text-primary">Rating</h5>
													</div>
												</div>
	
												<div class="clearfix"></div>
	
											</div>
										</div>
										
										<div class="col-sm-4">
											<div class="well well-sm">
	
												<div>
													<img
														src="${contextRoot.concat(BOOK_COVER_DIR.concat(BOOK_DEFAULT_COVER))}"
														alt="Readers'" class="img-responsive carousel-image" />
												</div>
	
												<div class="row">
													<div class="col-sm-12">
														<h5><a href="#">Operating System Concepts (Edition)</a></h5>
													</div>
													<div class="col-sm-12">
														<p class="data-text-color">The small description about
															the book</p>
													</div>
												</div>
	
												<div class="row">
													<div class="col-xs-6">
														<h5>Read</h5>
													</div>
													<div class="col-xs-6">
														<h5 class="pull-right text-primary">Rating</h5>
													</div>
												</div>
	
												<div class="clearfix"></div>
	
											</div>
										</div>
										
									</div>
									<!--.row-->
								</div>
								<!--.item-->
	
								<div class="item">
									<div class="row">
										<div class="col-md-4">
											<a href="#" class="thumbnail"><img
												src="http://placehold.it/250x250" alt="Image"
												style="max-width: 100%;"></a>
										</div>
										<div class="col-md-4">
											<a href="#" class="thumbnail"><img
												src="http://placehold.it/250x250" alt="Image"
												style="max-width: 100%;"></a>
										</div>
										<div class="col-md-4">
											<a href="#" class="thumbnail"><img
												src="http://placehold.it/250x250" alt="Image"
												style="max-width: 100%;"></a>
										</div>
									</div>
									<!--.row-->
								</div> --%>
								<!--.item-->
	
							</div>
							<!--.carousel-inner-->
							<a data-slide="prev" href="#resentlyAdded"
								class="left carousel-control" style="margin-top:auto;margin-bottom:auto;">&lt;</a>
							<a data-slide="next"
								href="#resentlyAdded" class="right carousel-control" style="margin-top:auto;margin-bottom:auto;">&gt;</a>
						</div>
						<!--.Carousel-->
	
					</div>
				</div>
			</div>
			<!--.container-->
		</c:if>
		
		<c:if test="${empty model.resentlyAdded or fn:length(model.resentlyAdded) eq 0}">
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
	});

</script>