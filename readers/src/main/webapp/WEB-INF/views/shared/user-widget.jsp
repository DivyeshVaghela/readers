<div class="col-sm-3">
	<div class="well well-sm">

		<div class="row">

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="carousel-image-wrapper">
					<img
						src="${contextRoot.concat(contextRoot.concat(BOOK_COVER_DIR).concat(BOOK_DEFAULT_COVER))}"
						alt="${user.username}" title="${user.username}"
						class="img-responsive img-rounded carousel-image" />
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12">
				<div class="row">
					<div class="col-sm-12">
						<h5 title="${user.username}">
							<a href="${contextRoot.concat('/user/').concat(''+user.id)}">
										${user.username} </a>
						</h5>
					</div>
				</div>

				<div class="row">
					
				</div>

				<%-- <c:if test="${empty book.bookShare}">
					<div class="row">
						<div class="col-xs-6">
							<h5>Read</h5>
						</div>
						<div class="col-xs-6">
							<h5 class="pull-right text-primary">Rating</h5>
						</div>
					</div>
				</c:if> --%>
				<div class="clearfix"></div>
			</div>

		</div>
		<!-- /.row -->
	</div>
	<!-- /.well -->
</div>
<!-- /.col -->