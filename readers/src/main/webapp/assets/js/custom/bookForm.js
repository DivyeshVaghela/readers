	/* Populate the years to given select dropdown list */
	function populateYears(selectElmnt) {
	
		var yearOptions = "";
		for (var year = new Date().getFullYear(); year > 1900; year--) {
			yearOptions += '<option value="' + year + '">' + year + '</option>';
		}
		$(selectElmnt).append(yearOptions);
	}
	
	/* Populate the months to given select dropdown list */
	function populateMonths(selectElmnt){
		
		var monthOptions = "";
		$(months).each(function(index, month){
			monthOptions += '<option value="'+(index+1)+'">'+month+'</option>';
		});
		$(selectElmnt).append(monthOptions);
	}
	
	/* Populates date in the given dropdown list (also needs the id of Year and Month selection list) */
	function populateDateInDropdown(yearSelectElmntId, monthSelectElmntId, dateSelectElmntId){
		
		yearSelectElmntId = yearSelectElmntId.replace(".","\\.");
		monthSelectElmntId = monthSelectElmntId.replace(".","\\.");
		dateSelectElmntId = dateSelectElmntId.replace(".","\\.");
		
		var year = $("#"+yearSelectElmntId).val();
		var month = $("#"+monthSelectElmntId).val();
		
		var dateElmnt = $("#"+dateSelectElmntId);
		dateElmnt.val("");
		dateElmnt.children().first().nextAll().remove();
		if (year!=null && year!="" && month!=null && month!=""){
			
			var dateOptions = "";
			var dates = getDaysInMonth(year, month-1)
			$(dates).each(function(index, date){
				dateOptions += '<option value="'+date+'">'+date+'</option>';
			});
			dateElmnt.append(dateOptions);
		}
	}
	
	var publishedYear;
	var publishedMonth;
	var publishedDate;
	
	var readStartYear;
	var readStartMonth;
	var readStartDate;
	
	var readEndYear;
	var readEndMonth
	var readEndDate;
	
	$(function(){
		/* references to the Publication date */
		publishedYear = $("#publishedYear");
		publishedMonth = $("#publishedMonth");
		publishedDate = $("#publishedDate");
		
		/* references to the read start date */
		readStartYear = $("#readDetails\\.startYear");
		readStartMonth = $("#readDetails\\.startMonth");
		readStartDate = $("#readDetails\\.startDate");
		
		/* references to the read end date */
		readEndYear = $("#readDetails\\.endYear");
		readEndMonth = $("#readDetails\\.endMonth");
		readEndDate = $("#readDetails\\.endDate");
		
		//Populate and change the Year
		populateYears(publishedYear);
		populateYears(readStartYear);
		populateYears(readEndYear);
		
		//Populate publication months
		populateMonths(publishedMonth);
		populateMonths(readStartMonth);
		populateMonths(readEndMonth);
	});
	
	//Disable Publication
	$("#publicationId").on("change", function(){
		var disabled = ($(this).val()!=null && $(this).val()!="");
		$("#newPublication").find("input,textarea").each(function(index, input){
			$(input).attr("disabled", disabled);
		});
	});
	
	$("#newPublication").find("input,textarea").each(function(index, input){
		$(input).on("change", function(){
			var disabled = false;
			$("#newPublication").find("input,textarea").each(function(indexInner, inputInner){
				disabled = ($(inputInner).val()!=null && $(inputInner).val()!="");
				if (disabled){
					return false;
				}
			});
			$("#publicationId").attr("disabled", disabled);
		});
	});
	
	
	//Author selection
	var selectAuthor = $("#selectAuthor");
	var space_selectAuthor = $("#space_selectAuthor");
	var hdn_selectAuthorCount = $("#hdn_selectAuthorCount");	
	
	//Add author selection
	$("#btn_addSelectAuthor").on("click", function(){
		var selectAuthorCount = parseInt(hdn_selectAuthorCount.val());
		
		var newSelectAuthor = selectAuthor.clone();
		var selectBox = $(newSelectAuthor.find("select")[0]);
		selectBox.attr("name", selectBox.attr("name").replace("_index_", selectAuthorCount));
		space_selectAuthor.append(newSelectAuthor);
		
		hdn_selectAuthorCount.val(selectAuthorCount+1);
	});
	
	//remove author selection
	function removeSelectAuthor(elmnt){
		
		var container = $(elmnt).parent().parent();
		
		console.log(container);
		$(container).nextAll().each(function(index, element){
			$(element).find("select").each(function(index, input){
				var inputBox = $(input);
				var positionIndex = parseInt(inputBox.attr("name").substring(inputBox.attr("name").indexOf("\[")+1, inputBox.attr("name").indexOf("\]")));
				inputBox.attr("name", inputBox.attr("name").replace(/\[\d\]/, "["+(positionIndex-1)+"]"));
			});
		});
		
		container.remove();
		var selectAuthorCount = parseInt(hdn_selectAuthorCount.val());
		hdn_selectAuthorCount.val(selectAuthorCount-1);
	}
	
	//New authors
	var newAuthorForm = $("#newAuthorForm") 
	var space_newAuthor = $("#space_newAuthor");
	var hdn_newAuthorCount = $("#hdn_newAuthorCount");
	
	//Add new author form
	$("#btn_addNewAuthor").on("click", function(){
		
		var newAuthorCount = parseInt(hdn_newAuthorCount.val());
		var newAuthorFormClone = newAuthorForm.clone();
		
		newAuthorFormClone.find("input,textarea").each(function(index, element){
			$(element).attr("name", $(element).attr("name").replace(/\[\d?\]/, "["+newAuthorCount+"]"));
		});
		space_newAuthor.append(newAuthorFormClone);
		hdn_newAuthorCount.val(newAuthorCount+1);
	});
	
	//Remove the new author form
	function removeNewAuthorForm(elmnt){
		
		var container = $(elmnt).parent().parent();
		$(container).nextAll().each(function(index, element){
			$(element).find("input,textarea").each(function(index, input){
				var inputBox = $(input);
				var positionIndex = parseInt(inputBox.attr("name").substring(inputBox.attr("name").indexOf("\[")+1, inputBox.attr("name").indexOf("\]")));
				inputBox.attr("name", inputBox.attr("name").replace(/\[\d\]/, "["+(positionIndex-1)+"]"));
			});
		});
		
		container.remove();
		var newAuthorCount = parseInt(hdn_newAuthorCount.val());
		hdn_newAuthorCount.val(newAuthorCount-1);
	}
	
	
	
	//Book source form
	function changeBookSourceForm(value){
		
		var bookSourceValue = $("#bookSource\\.value");
		var bookFile = $("#bookSource\\.bookFile");
		
		var bookSourceContainer = bookSourceValue.parents().eq(2);
		var bookFileContainer = bookFile.parents().eq(2);
		
		bookSourceContainer.addClass("hidden");
		bookFileContainer.addClass("hidden");
		
		if (value == 1){ //If its a physical copy
			
			bookSourceValue.val(null).attr({
					"disabled":false,
					"type":"text",
					"placeholder":"Where it is?"
				});
			bookSourceContainer.removeClass("hidden");
		} else if (value == 2){ //If is an eBook and reader has to upload a file
			
			bookFileContainer.removeClass("hidden");
			
		} else if (value == 3){ //If its an eBook URL
			bookSourceValue.val(null).attr({
				"disabled":false,
				"type":"url",
				"placeholder":"eBook URL"
			});
			bookSourceContainer.removeClass("hidden");
		} else {
			bookSourceValue.attr("disabled",true);
		}
	}
	
	var startReading;
	var endReading;
	
	$(function(){
		//Book read status
		startReading = $("#startReading");
		endReading = $("#endReading");
	});
	
	function getTheReadStatusForm(value){
	
		console.log(startReading);
		startReading.addClass("hidden");
		endReading.addClass("hidden");
		if (value == 2){ //started reading
			startReading.removeClass("hidden");
		}
		else if (value == 3){ //reading completed
			startReading.removeClass("hidden");
			endReading.removeClass("hidden");
		}
	}
