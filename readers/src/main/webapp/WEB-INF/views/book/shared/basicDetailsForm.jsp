<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Basic-Details -->
<div class="form-group">
	<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
		<fieldset>
			<legend>Basic Details</legend>
		</fieldset>
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
		<sf:input path="basicDetails.name" type="text"
			placeholder="Name (Title)" title="Name (Title)" class="form-control" />
		<sf:errors path="basicDetails.name" class="help-block" />
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
		<sf:input path="basicDetails.edition" type="text"
			placeholder="Edition" title="Edition" class="form-control" />
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
		<sf:input path="basicDetails.bookCover" type="file"
			placeholder="Book Cover" title="Book Cover" class="form-control" />
		<sf:errors path="basicDetails.bookCover" class="help-block" />
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
		<sf:textarea path="basicDetails.details"
			placeholder="Short book description.." title="Details"
			class="form-control only-vertical" />
	</div>
</div>

<div class="form-group">
	<div class="col-md-offset-3 col-md-6 col-sm-offset-2 col-sm-8">
		<sf:input path="basicDetails.ISBN" type="text" placeholder="ISBN Code"
			title="ISBN" class="form-control" />
	</div>
</div>
<!-- /Basic-Details -->

<div class="form-group">
	<div class="col-md-offset-3 col-md-2 col-sm-offset-2 col-sm-8">
		<select name="basicDetails.publishedYear" id="publishedYear"
			title="Published Year" class="form-control"
			onchange="populateDateInDropdown('publishedYear', 'publishedMonth', 'publishedDate')">
			<option value="">Published Year..</option>
		</select>
	</div>
	<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
		<select name="basicDetails.publishedMonth" id="publishedMonth"
			title="Published Month" class="form-control"
			onchange="populateDateInDropdown('publishedYear', 'publishedMonth', 'publishedDate')">
			<option value="">Published Month..</option>
		</select>
	</div>
	<div class="col-md-offset-0 col-md-2 col-sm-offset-2 col-sm-8">
		<select name="basicDetails.publishedDate" id="publishedDate"
			title="Published Date" class="form-control">
			<option value="">Published Date..</option>
		</select>
	</div>
</div>

<script>
	//select date time after submit and returning to this page
	publishedYear.val(${book.basicDetails.publishedYear});
	var publishedMonthSelected = ${book.basicDetails.publishedMonth}+"";
	if (publishedMonthSelected != ""){
		publishedMonth.val(publishedMonthSelected);		
	}
	populateDateInDropdown('publishedYear', 'publishedMonth', 'publishedDate');
	publishedDate.val(${book.basicDetails.publishedDate});
</script>