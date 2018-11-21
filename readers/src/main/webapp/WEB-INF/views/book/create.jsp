<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="${js}/custom/bookForm.js"></script>

<div>

	<h2 class="text-center">New book</h2>
	<hr>
	
	<c:if test="${not empty bookCreateError}">
		<div class="col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
			${bookCreateError}
		</div>
	</c:if>

	<sf:form modelAttribute="book" method="POST" enctype="multipart/form-data" class="form-horizontal">

		<jsp:include page="./shared/basicDetailsForm.jsp" />
		
		<!-- Publication-Details -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<hr/>
				<fieldset>
					<legend>Publication Details</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
			
				<ul class="nav nav-tabs">
				    <li class="active"><a href="#existingPublication">Existing</a></li>
				    <li><a href="#newPublication">New</a></li>
			  	</ul>
				
				<div class="tab-content">
					<div id="existingPublication" class="tab-pane fade in active">
						<br/>
						<sf:select path="publicationId" class="form-control">
							<sf:option value="">Select Publication..</sf:option>
							<sf:options items="${book.publicationList}" itemLabel="name" itemValue="id"/>
						</sf:select>
					</div>
					
					<div id="newPublication" class="tab-pane fade">
						<br/>
						<div class="form-group">
							<div class="col-sm-12">
								<sf:input path="publication.name" placeholder="Name" title="Name" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<sf:textarea path="publication.details" placeholder="Publication Details.." title="Details" class="form-control"/>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		<!-- /Publication-Details -->
		
		<!-- Author-Details -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<hr/>
				<fieldset>
					<legend>Author Details</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				
				<ul class="nav nav-tabs">
				    <li class="active"><a href="#existingAuthor">Existing</a></li>
				    <li><a href="#newAuthor">New</a></li>
			  	</ul>
			  	
			  	<div class="tab-content">
					<div id="existingAuthor" class="tab-pane fade in active">
						<br/>
						<div id="space_selectAuthor">
							<c:choose>
								<c:when test="${fn:length(book.authorIds) eq 0}">
									<div class="form-group">
										<div class="col-sm-12">
											<select name="authorIds[0]" class="form-control">
												<option value="">Select Author..</option>
												<c:forEach items="${book.authorList}" var="author">
													<option value="${author.id}">${author.fullName}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<input type="hidden" id="hdn_selectAuthorCount" name="hdn_selectAuthorCount" value="1" />		
								</c:when>
								<c:when test="${fn:length(book.authorIds) gt 0}">
									<c:forEach items="${book.authorIds}" varStatus="author">
									
									
										<c:choose>
											<%-- for first author --%>
											<c:when test="${author.index == 0}">
												<div class="form-group">
													<div class="col-sm-12">
														<sf:select path="authorIds[${author.index}]" class="form-control">
															<sf:option value="">Select Author..</sf:option>
															<sf:options items="${book.authorList}" itemLabel="fullName" itemValue="id"/>
														</sf:select>
													</div>
												</div>
											</c:when>
											
											<c:when test="${author.index != 0}">
												<div class="form-group">
													<div class="col-xs-9 col-sm-10">
														<sf:select path="authorIds[${author.index}]" class="form-control">
															<sf:option value="">Select Author..</sf:option>
															<sf:options items="${book.authorList}" itemLabel="fullName" itemValue="id"/>
														</sf:select>
													</div>
													<div class="col-xs-3 col-sm-2">
														<button type="button" class="btn btn-link form-control" onclick="removeSelectAuthor(this)"><span class="glyphicon glyphicon-remove text-danger"></span></button>
													</div>
												</div>
											</c:when>
										</c:choose>
									</c:forEach>
									<input type="hidden" id="hdn_selectAuthorCount" name="hdn_selectAuthorCount" value="${fn:length(book.authorIds)}" />
								</c:when>
							</c:choose>
						</div>
						<div>
							<button type="button" id="btn_addSelectAuthor" class="btn btn-info pull-right" onclick="addAuthorSelection()"><span class="glyphicon glyphicon-plus"></span> Add Author</button>
						</div>
					</div>
					
					<div id="newAuthor" class="tab-pane fade">
						<br/>
						<div id="space_newAuthor">
							<c:choose>
								<c:when test="${fn:length(book.authors) eq 0}">
									<!-- <fieldset>
										<legend>New Author</legend> -->
									<div>
										<div class="form-group">
											<div class="col-sm-4">
												<input type="text" name="authors[0].firstName" placeholder="First Name" title="First Name" class="form-control"/>
											</div>
											<div class="col-sm-4">
												<input type="text" name="authors[0].middleName" placeholder="Middle Name" title="Middle Name" class="form-control"/>
											</div>
											<div class="col-sm-4">
												<input type="text" name="authors[0].lastName" placeholder="Last Name" title="Last Name" class="form-control"/>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-12">
												<textarea name="authors[0].details" placeholder="Author Details.." title="Author Details" class="form-control"></textarea>
											</div>
										</div>
										<hr/>
									</div>
									<input type="hidden" id="hdn_newAuthorCount" name="hdn_newAuthorCount" value="1" />
									<!-- </fieldset> -->
								</c:when>
								<c:when test="${fn:length(book.authors) gt 0}">
									<c:forEach items="${book.authors}" varStatus="author">
									
										<c:choose>
											<c:when test="${author.index == 0}">
												<div>
													<div class="form-group">
														<div class="col-sm-4">
															<sf:input path="authors[${author.index}].firstName" placeholder="First Name" title="First Name" class="form-control"/>
														</div>
														<div class="col-sm-4">
															<sf:input path="authors[${author.index}].middleName" placeholder="Middle Name" title="Middle Name" class="form-control"/>
														</div>
														<div class="col-sm-4">
															<sf:input path="authors[${author.index}].lastName" placeholder="Last Name" title="Last Name" class="form-control"/>
														</div>
													</div>
													<div class="form-group">
														<div class="col-sm-12">
															<sf:textarea path="authors[${author.index}].details" placeholder="Author Details.." title="Author Details" class="form-control"/>
														</div>
													</div>
												</div>
												<hr/>												
											</c:when>
										
											<c:when test="${author.index != 0}">
												<div>
													<div class="form-group">
														<div class="col-sm-6">
															<sf:input path="authors[${author.index}].firstName" placeholder="First Name" title="First Name" class="form-control"/>
														</div>
														<div class="col-sm-6">
															<sf:input path="authors[${author.index}].lastName" placeholder="Last Name" title="Last Name" class="form-control"/>
														</div>
													</div>
													<div class="form-group">
														<div class="col-sm-12">
															<sf:textarea path="authors[${author.index}].details" placeholder="Author Details.." title="Author Details" class="form-control"/>
														</div>
													</div>
													<div class="form-group">
														<button type="button" class="btn btn-link pull-right" onclick="removeNewAuthorForm(this)"><span class="glyphicon glyphicon-remove text-danger"></span></button>
													</div>
												</div>
												<hr/>
											</c:when>
											
										</c:choose>
									
									</c:forEach>
									<input type="hidden" id="hdn_newAuthorCount" name="hdn_newAuthorCount" value="${fn:length(book.authors)}" />
								</c:when>
							</c:choose>
						</div>
						<div>
							<button type="button" id="btn_addNewAuthor" class="btn btn-info pull-right" onclick="addNewAuthorForm()"><span class="glyphicon glyphicon-plus"></span> Add Author</button>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<!-- /Author-Details -->
		
		<!-- Book-Source-Details -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<hr/>
				<fieldset>
					<legend>Where can I find the book?</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:select path="bookSource.bookTypeId" class="form-control" onchange="changeBookSourceForm(this.value)">
					<sf:option value="">Select book type..</sf:option>
					<sf:options items="${book.bookSource.bookTypeList}" itemLabel="value" itemValue="id"/>
				</sf:select>
			</div>
		</div>
		
		<div class="${(book.bookSource.bookTypeId eq null or book.bookSource.bookTypeId eq 2) ? 'hidden' : ''}">
			<div class="form-group">
				<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
					<sf:input path="bookSource.value" type="${(book.bookSource.bookTypeId eq 1) ? 'text' : 'url'}" placeholder="${(book.bookSource.bookTypeId eq 1) ? 'Where it is?' : 'eBook URL'}" class="form-control"/>
					<sf:errors path="bookSource.value" class="help-block" />
				</div>
			</div>
		</div>
			
		<!-- Book upload -->
		<div class="${(book.bookSource.bookTypeId eq null or book.bookSource.bookTypeId ne 2) ? 'hidden' : ''}">
			<div class="form-group">
				<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
					<sf:input path="bookSource.bookFile" type="file" placeholder="Book File" title="Book File" accept="application/pdf" class="form-control"/>
					<sf:errors path="bookSource.bookFile" class="help-block" />
				</div>
			</div>
		</div>
		<!-- /Book upload -->
		
		<div class="form-group" id="infoPhysicalBook" style="display:none;">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8 alert alert-info">
				<strong><i>NOTE:</i></strong> Please make sure that if the book is a Physical Book, you'll not be able to share that book.
			</div>
		</div>
		<!-- /Book-Source-Details -->
		
		<!-- Read-Status -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<hr/>
				<fieldset>
					<legend>Read Status</legend>
				</fieldset>
			</div>
		</div>
		
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:select path="readStatus" class="form-control" onchange="getTheReadStatusForm(this.value)">
					<sf:option value="">Select read status..</sf:option>
					<sf:options items="${book.readStatusList}" itemLabel="value" itemValue="id"/>
				</sf:select>
				<sf:errors path="readStatus" class="help-block"/>
			</div>
		</div>
		
		<div id="startReading" class="${(book.readStatus == 2 || book.readStatus == 3) ? '' : 'hidden'}">
			<div class="form-group">
				<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
					<h6>When you started reading?</h6>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-offset-3 col-md-2 col-sm-offset-2 col-sm-8">
					<sf:select path="readDetails.startYear" title="Start Year" class="form-control" onchange="populateDateInDropdown('readDetails.startYear', 'readDetails.startMonth', 'readDetails.startDate')">
						<sf:option value="">Start Year..</sf:option>
					</sf:select>
				</div>
				<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
					<sf:select path="readDetails.startMonth" title="Start Month" class="form-control" onchange="populateDateInDropdown('readDetails.startYear', 'readDetails.startMonth', 'readDetails.startDate')">
						<sf:option value="">Start Month..</sf:option>
					</sf:select>
				</div>
				<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
					<sf:select path="readDetails.startDate" title="Start Date" class="form-control">
						<sf:option value="">Start Date..</sf:option>
					</sf:select>
				</div>
			</div>
		</div>
		<div id="endReading" class="${book.readStatus == 3 ? '' : 'hidden'}">
			<div class="form-group">
				<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
					<h6>When you completed reading?</h6>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-offset-3 col-md-2 col-sm-offset-2 col-sm-8">
					<sf:select path="readDetails.endYear" title="End Year" class="form-control" onchange="populateDateInDropdown('readDetails.endYear', 'readDetails.endMonth', 'readDetails.endDate')">
						<sf:option value="">End Year..</sf:option>
					</sf:select>
				</div>
				<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
					<sf:select path="readDetails.endMonth" title="End Month" class="form-control" onchange="populateDateInDropdown('readDetails.endYear', 'readDetails.endMonth', 'readDetails.endDate')">
						<sf:option value="">End Month..</sf:option>
					</sf:select>
				</div>
				<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
					<sf:select path="readDetails.endDate" title="End Date" class="form-control">
						<sf:option value="">End Date..</sf:option>
					</sf:select>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
					<sf:input path="readDetails.rating" type="number" start="1" end="5" placeholder="Rating" title="Rating" class="form-control"/>
				</div>
			</div>
		
			<div class="form-group">
				<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
					<sf:textarea path="readDetails.review" placeholder="Short reading experience.." title="Review" class="form-control only-vertical"/>
				</div>
			</div>
		
		</div>
		<!-- /Read-Status -->
		
		<div class="form-group">
			<br>
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<button type="submit" class="btn btn-primary">Add</button>
				<a href="${contextRoot}" class="btn btn-default">Cancel</a>
			</div>
		</div>
		
	</sf:form>

	<!-- Hidden element for new element creation -->
	<div class="hidden">
		<div class="form-group" id="selectAuthor">
			<div class="col-xs-9 col-sm-10">
				<select name="authorIds[_index_]" class="form-control">
					<option value="">Select Author..</option>
					<c:forEach items="${book.authorList}" var="author">
						<option value="${author.id}">${author.fullName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-xs-3 col-sm-2">
				<button type="button" class="btn btn-link form-control" onclick="removeSelectAuthor(this)"><span class="glyphicon glyphicon-remove text-danger"></span></button>
			</div>
		</div>
		
		<div id="newAuthorForm">
			<div class="form-group">
				<div class="col-sm-4">
					<input type="text" name="authors[0].firstName" placeholder="First Name" title="First Name" class="form-control"/>
				</div>
				<div class="col-sm-4">
					<input type="text" name="authors[0].middleName" placeholder="Middle Name" title="Middle Name" class="form-control"/>
				</div>
				<div class="col-sm-4">
					<input type="text" name="authors[0].lastName" placeholder="Last Name" title="Last Name" class="form-control"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<textarea name="authors[0].details" placeholder="Author Details.." title="Author Details" class="form-control"></textarea>
				</div>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-link pull-right" onclick="removeNewAuthorForm(this)"><span class="glyphicon glyphicon-remove text-danger"></span></button>
			</div>
			<hr/>
		</div>
		
	</div>
	<!-- /Hidden element for new element creation -->
	<br>
	<hr/>
	<br>
</div>

<script type="text/javascript">
	
	$(function(){
		readStartYear.val(${book.readDetails.startYear});
		var readStartMonthSelected = ${book.readDetails.startMonth}+"";
		if (readStartMonthSelected != ""){
			readStartMonth.val(readStartMonthSelected);		
		}
		populateDateInDropdown('readDetails.startYear', 'readDetails.startMonth', 'readDetails.startDate')
		readStartDate.val(${book.readDetails.startDate});
	
		readEndYear.val(${book.readDetails.endYear});
		var readEndMonthSelected = ${book.readDetails.endMonth}+"";
		if (readEndMonthSelected != ""){
			readEndMonth.val(readEndMonthSelected);		
		}
		populateDateInDropdown('readDetails.endYear', 'readDetails.endMonth', 'readDetails.endDate')
		readEndDate.val(${book.readDetails.endDate});
	});
	
</script>