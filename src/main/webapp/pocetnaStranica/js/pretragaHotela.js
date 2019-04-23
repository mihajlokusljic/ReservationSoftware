
$(document).ready(function(e) {
	
	$("#hotelSearchForm").submit(function(e) {
		e.preventDefault();
		
		let nazivOdredista = $("#hotelNaziv").val();
		let dolazak = $("#input-start").val();
		let odlazak = $("#input-end").val();
		
		let pretragaLetova = {
				nazivHotelaIliDestinacije: nazivOdredista,
				datumDolaska: dolazak,
				datumOdlaska: odlazak,
				potrebneSobe: []
		}
		
		$.ajax({
			type: "POST",
			url: "../hoteli/pretrazi",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(pretragaLetova),
			success: function(response) {
				refreshHotels(response);
			},
		});
		
	});
	
});

function refreshHotels(hotels) {
	let hotelsTableBody = $("#hotelsView");
	hotelsTableBody.empty();
	let hotel = null;
	for(i in hotels) {
		hotel = hotels[i];
		let noviRed = $("<tr></tr>");
		noviRed.append("<td>" + (i + 1) + "</td>");
		noviRed.append("<td>" + hotel.naziv + "</td>");
		noviRed.append("<td>" + hotel.adresa.punaAdresa + "</td>");
		if(hotel.brojOcjena > 0) {
			noviRed.append("<td>" + (hotel.sumaOcjena / hotel.brojOcjena) + "</td>");
		} else {
			noviRed.append("<td>Nema ocjena</td>");
		}
		noviRed.append('<td><a href="#">Pogledaj</a></td>');
		hotelsTableBody.append(noviRed);
	}
}