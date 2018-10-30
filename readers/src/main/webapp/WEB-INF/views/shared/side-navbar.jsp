<br>
<br>


<style type="text/css">
.panel-heading .accordion-toggle:after {
    /* symbol for "opening" panels */
    font-family: 'Glyphicons Halflings';  /* essential for enabling glyphicon */
    content: "\e114";    /* adjust as needed, taken from bootstrap.css */
    float: right;        /* adjust as needed */
    color: grey;         /* adjust as needed */
}
.panel-heading .accordion-toggle.collapsed:after {
    /* symbol for "collapsed" panels */
    content: "\e080";    /* adjust as needed, taken from bootstrap.css */
}
</style>


<div class="panel">
	<!-- <div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion"
						href="#quickLinks">Quick Links</a>
				</h4>
			</div> -->
	<div class="panel-collapse collapse in">
		<div class="list-group">
			<a href="${contextRoot}" id="sideNav_home" class="list-group-item">
				<span class="glyphicon glyphicon-home"></span> Home
			</a>
			<a href="${contextRoot}/wishlist" id="sideNav_wishlist" class="list-group-item"> <span
				class="glyphicon glyphicon-th-list"></span> Wish List
			</a>
			
			<a href="${contextRoot}/book/create" id="sideNav_newBook"
				class="list-group-item"> <span
				class="glyphicon glyphicon-plus-sign"></span> New Book
			</a>
			
			<div class="panel inner">
				<div class="panel-heading inner">
					<a data-toggle="collapse" href="#subMenu_share" id="subMenuLink_share" class="list-group-item accordion-toggle collapsed">
						<span class="glyphicon glyphicon-transfer"></span> Shared
					</a>
				</div>
				<div id="subMenu_share" class="panel-collapse collapse">
					<div class="list-group inner">
						<a href="${contextRoot}/shared" id="sideNav_shared" class="list-group-item">To Me</a>
						<a href="${contextRoot}/shared/history" id="sideNav_sharedHistory" class="list-group-item">History</a>
					</div>
				</div>
			</div>
			
			<div class="panel inner">
				<div class="panel-heading inner">
					<a data-toggle="collapse" href="#subMenu_groups" id="subMenuLink_groups" class="list-group-item accordion-toggle collapsed">
						<span class="glyphicon glyphicon-tent"></span> Groups
					</a>
				</div>
				<div id="subMenu_groups" class="panel-collapse collapse">
					<div class="list-group inner">
						<a href="${contextRoot}/group" id="sideNav_groups" class="list-group-item">Groups</a>
						<a href="${contextRoot}/group/my" id="sideNav_myGroup" class="list-group-item">My Groups</a>
						<a href="${contextRoot}/group/create" id="sideNav_create" class="list-group-item">Create</a>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>

<%-- <div class="panel-group" id="accordion">
	<div class="panel">
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
	<div class="panel">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" href="#collapse2">Collapsible
					Group 2</a>
			</h4>
		</div>
		<div id="collapse2" class="panel-collapse collapse">
			<div class="panel-body">Lorem ipsum dolor sit amet, consectetur
				adipisicing elit, sed do eiusmod tempor incididunt ut labore et
				dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
				exercitation ullamco laboris nisi ut aliquip ex ea commodo
				consequat.</div>
		</div>
	</div>
	<div class="panel">
		<div class="panel-heading">
			<h4 class="panel-title">
				<a data-toggle="collapse" data-parent="#accordion" href="#collapse3">Collapsible
					Group 3</a>
			</h4>
		</div>
		<div id="collapse3" class="panel-collapse collapse">
			<div class="panel-body">Lorem ipsum dolor sit amet, consectetur
				adipisicing elit, sed do eiusmod tempor incididunt ut labore et
				dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
				exercitation ullamco laboris nisi ut aliquip ex ea commodo
				consequat.</div>
		</div>
	</div>
</div> --%>