let poslovnicaAdminaInputs = ["aviokompanijaAdmina", "hotelAdmina", "racServisAdmina"]
let poslovnicaInputs = ["aviokompanijaAdminaInp", "hotelAdminaInp", "racAdminaInp"]

function prikaziIzborPoslovnice(idPoslovnice) {
	$.each(poslovnicaAdminaInputs, function(i, tekucaPoslovnicaId) {
		if(idPoslovnice === tekucaPoslovnicaId) {
			$("#" + tekucaPoslovnicaId).show();
			$("#" + poslovnicaInputs[i]).attr("required", "required");
		}
		else
		{
			$("#" + tekucaPoslovnicaId).hide();
			$("#" + poslovnicaInputs[i]).removeAttr("required");
		}
	})
}

$(document).ready(function(e) {
	$("#adminAviokompanijeBtn").click(function() {
		if($("#adminAviokompanijeBtn").is(":checked")) { prikaziIzborPoslovnice("aviokompanijaAdmina"); }
	});
	
	$("#adminHotelaBtn").click(function() {
		if($("#adminHotelaBtn").is(":checked")) { prikaziIzborPoslovnice("hotelAdmina"); }
	});
	
	$("#racAdminBtn").click(function() {
		if($("#racAdminBtn").is(":checked")) { prikaziIzborPoslovnice("racServisAdmina"); }
	});
	
	$("#sysAdminBtn").click(function() {
		if($("#sysAdminBtn").is(":checked")) { prikaziIzborPoslovnice(""); }
	});
});