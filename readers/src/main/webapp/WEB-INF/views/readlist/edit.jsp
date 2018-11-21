<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>

	<h2 class="text-center">New Readlist</h2>
	<hr>
	
	<c:if test="${not empty readlistCreateError}">
		<div class="col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
			${readlistCreateError}
		</div>
	</c:if>
	
	<sf:form modelAttribute="createReadlistModel" method="POST" class="form-horizontal">
	
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<fieldset>
					<legend>Name</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:input path="name" placeholder="Readlist Name" title="Readlist Name" class="form-control"/>
				<sf:errors path="name" class="help-block" />
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:textarea path="details" placeholder="Details like which kind of books this list will have.." title="Details" class="form-control only-vertical"/>
				<sf:errors path="details" class="help-block" />
			</div>
		</div>
		
		<br/>
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<fieldset>
					<legend>Books</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:select path="selectedBookIds" id="selectedBookIds"
					multiple="true">
					<c:forEach items="${createReadlistModel.bookList}" var="book">
						<sf:option value="${book.id}"> ${book.fullName}</sf:option>
					</c:forEach>
				</sf:select>

				<script type="text/javascript">
					$("#selectedBookIds").multipleSelect({
						selectAll : false,
						placeholder : "Select Book(s) to add in readlist..",
						filter : true,
						minimumCountSelected: 0,
						countSelected: "# selected"
					});
				</script>
				<c:if test="${empty createReadlistModel.selectedBookIds or fn:length(createReadlistModel.selectedBookIds) eq 0}">
					<script type="text/javascript">
						$("#selectedBookIds").multipleSelect("uncheckAll");
					</script>
				</c:if>
				<sf:errors path="selectedBookIds" class="help-block" />
			</div>
		</div>
		
		<br/>
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<button type="Update" class="btn btn-primary pull-right">Create Group</button>
			</div>
		</div>
		
	
	</sf:form>
	
</div>