$.datepicker._gotoToday = function(id) {
	var target = $(id);
	var inst = this._getInst(target[0]);
	if (this._get(inst, 'gotoCurrent') && inst.currentDay) {
		inst.selectedDay = inst.currentDay;
		inst.drawMonth = inst.selectedMonth = inst.currentMonth;
		inst.drawYear = inst.selectedYear = inst.currentYear;
	} else {
		var date = new Date();
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		this._setDateDatepicker(target, date);
		this._selectDate(id, this._getDateDatepicker(target));
	}
	this._notifyChange(inst);
	this._adjustDate(target);
}
var datepicker_CurrentInput;
$.datepicker.setDefaults({
	dateFormat : "yy-mm-dd",
	changeMonth : true,
	changeYear : true,
	showAnim : 'fold',
	showButtonPanel : true,
	beforeShow : function(input, inst) {
		datepicker_CurrentInput = input;
	}
});
$(document).on("click", ".ui-datepicker-close", function() {
	datepicker_CurrentInput.value = "";
});