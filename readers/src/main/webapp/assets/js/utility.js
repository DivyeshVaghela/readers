/* All the month full names */
var months = ["January", "February", "March", "April", "May", "June", "July", "August", "Septmber", "October", "November", "December"];

/* Get the dates by particular month and year */
function getDaysInMonth(year, month) {
	var date = new Date(year, month, 1);
	var days = [];
	var count = 1;
	while (date.getMonth() === month) {
		days.push(count);
		date.setDate(date.getDate() + 1);
		count++;
	}
	return days;
}
