<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>

	<h2 class="text-center">New Readers' Group</h2>
	<hr>
	
	<c:if test="${not empty groupCreateError}">
		<div class="col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
			${groupCreateError}
		</div>
	</c:if>
	
	<sf:form modelAttribute="readerGroupModel" method="POST" class="form-horizontal">
		
		<br/>
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<fieldset>
					<legend>Group Name</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:input path="name" placeholder="Group Name" title="Group Name" class="form-control"/>
				<sf:errors path="name" class="help-block" />
			</div>
		</div>

		<br/>		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<fieldset>
					<legend>Group Members</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:select path="selectedMemberdId" id="selectedMemberdId"
					multiple="true">
					<c:forEach items="${readerGroupModel.userList}" var="user">
						<sf:option value="${user.userId}"> ${user.email} (${user.username})</sf:option>
					</c:forEach>
				</sf:select>

				<script type="text/javascript">
					$("#selectedMemberdId").multipleSelect({
						selectAll : false,
						placeholder : "Select Reader(s) to add in group..",
						filter : true,
						minimumCountSelected: 0,
						countSelected: "# selected"
					});
				</script>
				<c:if test="${empty readerGroupModel.selectedMemberdId or fn:length(readerGroupModel.selectedMemberdId) eq 0}">
					<script type="text/javascript">
						$("#selectedMemberdId").multipleSelect("uncheckAll");
					</script>
				</c:if>
				<sf:errors path="selectedMemberdId" class="help-block" />
			</div>
		</div>
		<br/>		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<fieldset>
					<legend>Group Members</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
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
					$("#selectedBookId").multipleSelect({
						selectAll : false,
						placeholder : "Select Book(s) to add in group..",
						filter : true,
						minimumCountSelected: 0,
						countSelected: "# selected"
					});
				</script>
				<c:if test="${empty readerGroupModel.selectedBookId or fn:length(readerGroupModel.selectedBookId) eq 0}">
					<script type="text/javascript">
						$("#selectedBookId").multipleSelect("uncheckAll");
					</script>
				</c:if>
				<sf:errors path="selectedBookId" class="help-block" />
			</div>
		</div>
		
		<br/>
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<button type="submit" class="btn btn-primary pull-right">Create Group</button>
			</div>
		</div>
		
	</sf:form>
	
</div>