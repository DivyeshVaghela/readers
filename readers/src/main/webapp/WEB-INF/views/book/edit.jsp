<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<script type="text/javascript" src="${js}/custom/bookForm.js"></script>

<div>

	<h2 class="text-center">Edit Book</h2>
	<hr />

	<c:if test="${not empty bookUpdateError}">
		<div
			class="col-md-offset-3 col-md-6 col-sm-offset-3 col-sm-6 alert alert-warning text-center">
			${bookUpdateError}</div>
	</c:if>

	<sf:form modelAttribute="book" method="POST"
		enctype="multipart/form-data" class="form-horizontal">

		<jsp:include page="./shared/basicDetailsForm.jsp" />

		<%-- <!-- Basic-Details -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<fieldset>
					<legend>Basic Details</legend>
				</fieldset>
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:hidden path=""/>
				<sf:input path="name" type="text" placeholder="Name (Title)"
					title="Name (Title)" class="form-control" />
				<sf:errors path="name" class="help-block" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:input path="edition" type="text" placeholder="Edition"
					title="Edition" class="form-control" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:input path="bookCover" type="file" placeholder="Book Cover"
					title="Book Cover" class="form-control" />
				<sf:errors path="bookCover" class="help-block" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:textarea path="details" placeholder="Short book description.."
					title="Details" class="form-control only-vertical" />
			</div>
		</div>

		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<sf:input path="ISBN" type="text" placeholder="ISBN Code"
					title="ISBN" class="form-control" />
			</div>
		</div>
		<!-- /Basic-Details --> --%>

		<!-- Publication-Details -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<hr />
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
						<br />
						<sf:select path="publicationId" class="form-control">
							<sf:option value="">Select Publication..</sf:option>
							<sf:options items="${book.publicationList}" itemLabel="name"
								itemValue="id" />
						</sf:select>
					</div>

					<div id="newPublication" class="tab-pane fade">
						<br />
						<div class="form-group">
							<div class="col-sm-12">
								<sf:input path="publication.name" placeholder="Name"
									title="Name" class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<sf:textarea path="publication.details"
									placeholder="Publication Details.." title="Details"
									class="form-control" />
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>

		<!-- <div class="form-group">
			<div class="col-md-offset-3 col-md-2 col-sm-offset-2 col-sm-8">
				<select name="publishedYear" id="publishedYear"
					title="Published Year" class="form-control"
					onchange="populateDateInDropdown('publishedYear', 'publishedMonth', 'publishedDate')">
					<option value="">Published Year..</option>
				</select>
			</div>
			<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
				<select name="publishedMonth" id="publishedMonth"
					title="Published Month" class="form-control"
					onchange="populateDateInDropdown('publishedYear', 'publishedMonth', 'publishedDate')">
					<option value="">Published Month..</option>
				</select>
			</div>
			<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
				<select name="publishedDate" id="publishedDate"
					title="Published Date" class="form-control">
					<option value="">Published Date..</option>
				</select>
			</div>
		</div> -->
		<!-- /Publication-Details -->

		<!-- Author-Details -->
		<div class="form-group">
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<hr />
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
						<br />
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
									<input type="hidden" id="hdn_selectAuthorCount"
										name="hdn_selectAuthorCount" value="1" />
								</c:when>
								<c:when test="${fn:length(book.authorIds) gt 0}">
									<c:forEach items="${book.authorIds}" varStatus="author">


										<c:choose>
											<%-- for first author --%>
											<c:when test="${author.index == 0}">
												<div class="form-group">
													<div class="col-sm-12">
														<sf:select path="authorIds[${author.index}]"
															class="form-control">
															<sf:option value="">Select Author..</sf:option>
															<sf:options items="${book.authorList}"
																itemLabel="fullName" itemValue="id" />
														</sf:select>
													</div>
												</div>
											</c:when>

											<c:when test="${author.index != 0}">
												<div class="form-group">
													<div class="col-xs-9 col-sm-10">
														<sf:select path="authorIds[${author.index}]"
															class="form-control">
															<sf:option value="">Select Author..</sf:option>
															<sf:options items="${book.authorList}"
																itemLabel="fullName" itemValue="id" />
														</sf:select>
													</div>
													<div class="col-xs-3 col-sm-2">
														<button type="button" class="btn btn-link form-control"
															onclick="removeSelectAuthor(this)">
															<span class="glyphicon glyphicon-remove text-danger"></span>
														</button>
													</div>
												</div>
											</c:when>
										</c:choose>
									</c:forEach>
									<input type="hidden" id="hdn_selectAuthorCount"
										name="hdn_selectAuthorCount"
										value="${fn:length(book.authorIds)}" />
								</c:when>
							</c:choose>
						</div>
						<div>
							<button type="button" id="btn_addSelectAuthor"
								class="btn btn-info pull-right">
								<span class="glyphicon glyphicon-plus"></span> Add Author
							</button>
						</div>
					</div>

					<div id="newAuthor" class="tab-pane fade">
						<br />
						<div id="space_newAuthor">
							<c:choose>
								<c:when test="${fn:length(book.authors) eq 0}">
									<!-- <fieldset>
										<legend>New Author</legend> -->
									<div>
										<div class="form-group">
											<div class="col-sm-4">
												<input type="text" name="authors[0].firstName"
													placeholder="First Name" title="First Name"
													class="form-control" />
											</div>
											<div class="col-sm-4">
												<input type="text" name="authors[0].middleName"
													placeholder="Middle Name" title="Middle Name"
													class="form-control" />
											</div>
											<div class="col-sm-4">
												<input type="text" name="authors[0].lastName"
													placeholder="Last Name" title="Last Name"
													class="form-control" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-12">
												<textarea name="authors[0].details"
													placeholder="Author Details.." title="Author Details"
													class="form-control"></textarea>
											</div>
										</div>
										<hr />
									</div>
									<input type="hidden" id="hdn_newAuthorCount"
										name="hdn_newAuthorCount" value="1" />
									<!-- </fieldset> -->
								</c:when>
								<c:when test="${fn:length(book.authors) gt 0}">
									<c:forEach items="${book.authors}" varStatus="author">

										<c:choose>
											<c:when test="${author.index == 0}">
												<div>
													<div class="form-group">
														<div class="col-sm-4">
															<sf:input path="authors[${author.index}].firstName"
																placeholder="First Name" title="First Name"
																class="form-control" />
														</div>
														<div class="col-sm-4">
															<sf:input path="authors[${author.index}].middleName"
																placeholder="Middle Name" title="Middle Name"
																class="form-control" />
														</div>
														<div class="col-sm-4">
															<sf:input path="authors[${author.index}].lastName"
																placeholder="Last Name" title="Last Name"
																class="form-control" />
														</div>
													</div>
													<div class="form-group">
														<div class="col-sm-12">
															<sf:textarea path="authors[${author.index}].details"
																placeholder="Author Details.." title="Author Details"
																class="form-control" />
														</div>
													</div>
												</div>
												<hr />
											</c:when>

											<c:when test="${author.index != 0}">
												<div>
													<div class="form-group">
														<div class="col-sm-6">
															<sf:input path="authors[${author.index}].firstName"
																placeholder="First Name" title="First Name"
																class="form-control" />
														</div>
														<div class="col-sm-6">
															<sf:input path="authors[${author.index}].lastName"
																placeholder="Last Name" title="Last Name"
																class="form-control" />
														</div>
													</div>
													<div class="form-group">
														<div class="col-sm-12">
															<sf:textarea path="authors[${author.index}].details"
																placeholder="Author Details.." title="Author Details"
																class="form-control" />
														</div>
													</div>
													<div class="form-group">
														<button type="button" class="btn btn-link pull-right"
															onclick="removeNewAuthorForm(this)">
															<span class="glyphicon glyphicon-remove text-danger"></span>
														</button>
													</div>
												</div>
												<hr />
											</c:when>

										</c:choose>

									</c:forEach>
									<input type="hidden" id="hdn_newAuthorCount"
										name="hdn_newAuthorCount" value="${fn:length(book.authors)}" />
								</c:when>
							</c:choose>
						</div>
						<div>
							<button type="button" id="btn_addNewAuthor"
								class="btn btn-info pull-right">
								<span class="glyphicon glyphicon-plus"></span> Add Author
							</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<!-- /Author-Details -->

		<div class="form-group">
			<br>
			<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
				<button type="submit" class="btn btn-primary">Update</button>
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
				<button type="button" class="btn btn-link form-control"
					onclick="removeSelectAuthor(this)">
					<span class="glyphicon glyphicon-remove text-danger"></span>
				</button>
			</div>
		</div>

		<div id="newAuthorForm">
			<div class="form-group">
				<div class="col-sm-4">
					<input type="text" name="authors[0].firstName"
						placeholder="First Name" title="First Name" class="form-control" />
				</div>
				<div class="col-sm-4">
					<input type="text" name="authors[0].middleName"
						placeholder="Middle Name" title="Middle Name" class="form-control" />
				</div>
				<div class="col-sm-4">
					<input type="text" name="authors[0].lastName"
						placeholder="Last Name" title="Last Name" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12">
					<textarea name="authors[0].details" placeholder="Author Details.."
						title="Author Details" class="form-control"></textarea>
				</div>
			</div>
			<div class="form-group">
				<button type="button" class="btn btn-link pull-right"
					onclick="removeNewAuthorForm(this)">
					<span class="glyphicon glyphicon-remove text-danger"></span>
				</button>
			</div>
			<hr />
		</div>

	</div>
	<!-- /Hidden element for new element creation -->
	<br>
	<hr />
	<br>


</div>

<script type="text/javascript" src="${js}/custom/bookForm.js"></script>