$(document).ready(function (e) {
	registerWindowHandlers();
});

function registerWindowHandlers() {
	$(".trigger_popup_fricc").click(function(){
	       $('.hover_bkgr_fricc').show();
	    });
	    $('.popupCloseButton').click(function(){
	        $('.hover_bkgr_fricc').hide();
	});
}