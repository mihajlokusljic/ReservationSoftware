$(document).ready(function() {
	
	$("#tabs-1").hide();
	$("#tabs-2").hide();
	$("#tabs-3").hide();
	$("#tabs-4").hide();
	$("#tabs-5").hide();
	
	$("#destinacije").click(function(e) {
		e.preventDefault();
		
		$("#tabs-1").show();
		$("#tabs-2").hide();
		$("#tabs-3").hide();
		$("#tabs-4").hide();
		$("#tabs-5").hide();
		

				
	});

	$("#dodavanjeDestinacijeForm").submit(function(e) {
		e.preventDefault();
		
		let naziv_destinacije = $("#nazivDestinacije").val();
		if (naziv_destinacije == ''){
			alert("Niste unijeli naziv destinacije.");
			return;
		}
		
		let puna_adresa = $("#punaAdresa").val();
		if (puna_adresa == ''){
			alert("Niste unijeli punu adresu destinacije.");
			return;
		}
		
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
					alert("Vec postoji destinacija sa takvom punom adresom.");
				} else {
					alert("Destinacija je uspjesno dodata.");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	
	$("#letovi").click(function(e) {
		e.preventDefault();

		$("#tabs-2").show();
		$("#tabs-1").hide();
		$("#tabs-3").hide();
		$("#tabs-4").hide();
		$("#tabs-5").hide();
	});
	
	$("#izvjestaj").click(function(e) {
		e.preventDefault();

		$("#tabs-3").show();
		$("#tabs-1").hide();
		$("#tabs-2").hide();
		$("#tabs-4").hide();
		$("#tabs-5").hide();

	});
	
	$("#profil").click(function(e) {
		e.preventDefault();

		$("#tabs-4").show();
		$("#tabs-1").hide();
		$("#tabs-2").hide();
		$("#tabs-3").hide();
		$("#tabs-5").hide();

	});
	
	$("#odjava").click(function(e) {
		e.preventDefault();

		$("#tabs-5").show();
		$("#tabs-1").hide();
		$("#tabs-2").hide();
		$("#tabs-3").hide();
		$("#tabs-4").hide();
	});
	
});

