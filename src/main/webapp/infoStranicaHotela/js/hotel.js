let podaciHotela = null;
let defaultSlika = "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg";


$(document).ready(function(e) {
	
	//dodavanje zaglavlja sa JWT tokenom u svaki zahtjev upucen ajax pozivom i obrada gresaka
	$.ajaxSetup({
	    headers: createAuthorizationTokenHeader(),
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	let statusCode = XMLHttpRequest.status;
	    	if(statusCode == 400) {
	    		//u slucaju neispravnih podataka (Bad request - 400) prikazuje se
	    		//poruka o greski koju je server poslao
	    		alert(XMLHttpRequest.responseText);
	    	}
	    	else {
	    		alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
	    	}
		}
	});
	
	
	ucitajPodatkeHotela();
});

function ucitajPodatkeHotela() {
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	var id = params_parser.get("id");
	
	$.ajax({
		type: "GET",
		url: "../hoteli/dobavi/" + id,
		success: function(response) {
			podaciHotela = response;
			$("#slikaHotela").attr("src", defaultSlika);
			$("#title").html(response.naziv);
			$("#hotelNaslov").html(response.naziv);
			$("#nazivHotela").val(response.naziv);
			$("#adresaHotela").val(response.adresa.punaAdresa);
			let sumaOcjena = response.sumaOcjena;
			sumaOcjena = parseFloat(sumaOcjena);
			let brOcjena = response.brojOcjena;
			brOcjena = parseInt(brOcjena);
			if(brOcjena > 0) {
				$("#ocjenaHotela").val(sumaOcjena / brOcjena);
			} else {
				$("#ocjenaHotela").val("Nema ocjena");
			}
			$("#opisHotela").val(response.promotivniOpis);
			
			let tabelaUsluga = $("#prikazUsluga");
			$.each(response.cjenovnikDodatnihUsluga, function(i, usluga) {
				let noviRed = $("<tr></tr>");
				noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
				noviRed.append('<td class="column1">' + usluga.opis + '</td>');
				noviRed.append('<td class="column1">' + usluga.cijena + '</td>');
				noviRed.append('<td class="column1">' + usluga.nacinPlacanja + '</td>');
				tabelaUsluga.append(noviRed);
			});
		},
	});

}
