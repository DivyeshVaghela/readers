<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<c:if test="${empty param.colMDOffset}">
	<c:set var="param.colMDOffset" value="3" />
</c:if>
<c:if test="${empty param.colMD}">
	<c:set var="param.colMD" value="6"></c:set>
</c:if>

<c:if test="${empty param.colSMOffset}">
	<c:set var="param.colSMOffset" value="2" />
</c:if>
<c:if test="${empty param.colSM}">
	<c:set var="param.colSM" value="8"></c:set>
</c:if>

<c:choose>
	<c:when test="${empty param.pathPrefix}">
		<c:set var="param.pathPrefix" value=""></c:set>
	</c:when>
	<c:when test="${not empty param.pathPrefix}">
		<c:set var="param.pathPrefix" value="${param.pathPrefix}."></c:set>
	</c:when>
</c:choose>

<!-- Book-Source-Details -->
<div class="form-group">
	<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
		<fieldset>
			<legend>Where can I find the book?</legend>
		</fieldset>
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
		<c:if test="${not empty bookSource.id}">
			<sf:hidden path="id"/>
		</c:if>
		
		<sf:select path="bookTypeId" class="form-control"
			onchange="changeBookSourceForm(this.value)">
			<sf:option value="">Select book type..</sf:option>
			<sf:options items="${bookSource.bookTypeList}" itemLabel="value"
				itemValue="id" />
		</sf:select>
	</div>
</div>

<div
	class="${(bookSource.bookTypeId eq null or bookSource.bookTypeId eq 2) ? 'hidden' : ''}">
	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:input path="value" id="bookSource.value"
				type="${(bookSource.bookTypeId eq 1) ? 'text' : 'url'}"
				placeholder="${(bookSource.bookTypeId eq 1) ? 'Where it is?' : 'eBook URL'}"
				class="form-control" />
			<sf:errors path="value" class="help-block" />
		</div>
	</div>
</div>

<!-- Book upload -->
<div
	class="${(bookSource.bookTypeId eq null or bookSource.bookTypeId ne 2) ? 'hidden' : ''}">
	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:input path="bookFile" type="file" id="bookSource.bookFile"
				placeholder="Book File" title="Book File" accept="application/pdf"
				class="form-control" />
			<sf:errors path="bookFile" class="help-block" />
		</div>
	</div>
</div>
<!-- /Book upload -->

<!-- /Book-Source-Details -->

