$(document).ready(function() {

	$("#administratorAviokompanijeDestinacijeForm").submit(function(e) {
		e.preventDefault();
		
		let naziv_destinacije = $("#destinacijeNazivDestinacije").val();		
		let puna_adresa = $("#punaAdresa").val();
		
		let novaDestinacija = {
			naziv : naziv_destinacije,
			punaAdresa : puna_adresa
		};
		
		$.ajax({
			type: "POST",
			url: "../destinacije/dodaj/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(novaDestinacija),
			success: function(response) {
				if(response == null) {
					alert("Već postoji destinacija sa takvom punom adresom.");
				} else {
					alert("Destinacija je uspješno dodata.");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});

	
});
