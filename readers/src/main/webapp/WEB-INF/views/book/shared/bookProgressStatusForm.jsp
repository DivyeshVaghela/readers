<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:if test="${empty param.colMDOffset}">
	<c:set var="param.colMDOffset" value="3" />
</c:if>
<c:if test="${empty param.colMD}">
	<c:set var="param.colMD" value="6"></c:set>
</c:if>
<spring:eval expression="${param.colMD} div 3" var="colMD3rd" />

<c:if test="${empty param.colSMOffset}">
	<c:set var="param.colSMOffset" value="2" />
</c:if>
<c:if test="${empty param.colSM}">
	<c:set var="param.colSM" value="8"></c:set>
</c:if>

<c:choose>
	<c:when test="${empty param.pathPrefix}">
		<c:set var="param.pathPrefix" value=""></c:set>
		<c:set var="param.readDetails.pathPrefix" value="readDetails."></c:set>
	</c:when>
	<c:when test="${not empty param.pathPrefix}">
		<c:set var="param.pathPrefix" value="${param.pathPrefix}."></c:set>
		<c:set var="param.readDetails.pathPrefix" value="${param.pathPrefix}.readDetails."></c:set>
	</c:when>
</c:choose>


<!-- Read-Status -->
<div class="form-group">
	<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-8">
		<fieldset>
			<legend>Read Status</legend>
		</fieldset>
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
		<c:if test="${not empty readProgress.readDetails.id}">
			<sf:hidden path="readDetails.id"/>
		</c:if>
		
		<c:choose>
			<c:when test="${readProgress.readStatus == 3}">
				<h6 class="text-center">${readStatusValue}</h6>
			</c:when>
			<c:when test="${readProgress.readStatus != 3}">
				<sf:select path="${param.pathPrefix}readStatus" class="form-control"
					onchange="getTheReadStatusForm(this.value)">
					<sf:option value="">Select read status..</sf:option>
					<sf:options items="${readProgress.readStatusList}" itemLabel="value"
						itemValue="id" />
				</sf:select>
				<sf:errors path="${param.pathPrefix}readStatus" class="help-block" />
			</c:when>
		</c:choose>
	</div>
</div>

<div id="startReading"
	class="${(readProgress.readStatus == 2 || readProgress.readStatus == 3) ? '' : 'hidden'}">
	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<h6>When you started reading?</h6>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${colMD3rd} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:select path="readDetails.startYear" title="Start Year"
				class="form-control"
				onchange="populateDateInDropdown('readDetails.startYear', 'readDetails.startMonth', 'readDetails.startDate')">
				<sf:option value="">Start Year..</sf:option>
			</sf:select>
		</div>
		<div class="col-md-offset-0 col-md-${colMD3rd} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:select path="readDetails.startMonth" title="Start Month"
				class="form-control"
				onchange="populateDateInDropdown('readDetails.startYear', 'readDetails.startMonth', 'readDetails.startDate')">
				<sf:option value="">Start Month..</sf:option>
			</sf:select>
		</div>
		<div class="col-md-offset-0 col-md-${colMD3rd} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:select path="readDetails.startDate" title="Start Date"
				class="form-control">
				<sf:option value="">Start Date..</sf:option>
			</sf:select>
		</div>
	</div>
</div>
<div id="endReading" class="${readProgress.readStatus == 3 ? '' : 'hidden'}">
	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<h6>When you completed reading?</h6>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${colMD3rd} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:select path="readDetails.endYear" title="End Year"
				class="form-control"
				onchange="populateDateInDropdown('readDetails.endYear', 'readDetails.endMonth', 'readDetails.endDate')">
				<sf:option value="">End Year..</sf:option>
			</sf:select>
		</div>
		<div class="col-md-offset-0 col-md-${colMD3rd} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:select path="readDetails.endMonth" title="End Month"
				class="form-control"
				onchange="populateDateInDropdown('readDetails.endYear', 'readDetails.endMonth', 'readDetails.endDate')">
				<sf:option value="">End Month..</sf:option>
			</sf:select>
		</div>
		<div class="col-md-offset-0 col-md-${colMD3rd} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:select path="readDetails.endDate" title="End Date"
				class="form-control">
				<sf:option value="">End Date..</sf:option>
			</sf:select>
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:input path="readDetails.rating" type="number" start="1" end="5"
				placeholder="Rating" title="Rating" class="form-control" />
		</div>
	</div>

	<div class="form-group">
		<div class="col-md-offset-${param.colMDOffset} col-md-${param.colMD} col-sm-offset-${param.colSMOffset} col-sm-${param.colSM}">
			<sf:textarea path="readDetails.review"
				placeholder="Short reading experience.." title="Review"
				class="form-control only-vertical" />
		</div>
	</div>

</div>
<!-- /Read-Status -->

<script type="text/javascript">

	$(function(){
		readStartYear.val(${readProgress.readDetails.startYear});
		var readStartMonthSelected = ${readProgress.readDetails.startMonth}+"";
		if (readStartMonthSelected != ""){
			readStartMonth.val(readStartMonthSelected);		
		}
		populateDateInDropdown('readDetails.startYear', 'readDetails.startMonth', 'readDetails.startDate')
		readStartDate.val(${readProgress.readDetails.startDate});
		
		readEndYear.val(${readProgress.readDetails.endYear});
		var readEndMonthSelected = ${readProgress.readDetails.endMonth}+"";
		if (readEndMonthSelected != ""){
			readEndMonth.val(readEndMonthSelected);
		}
		populateDateInDropdown('readDetails.endYear', 'readDetails.endMonth', 'readDetails.endDate')
		readEndDate.val(${readProgress.readDetails.endDate});
	});
</script>