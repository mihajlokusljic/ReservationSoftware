var tokenKey = "jwtToken";
var idPutovanja = -1;
var inicijator = false;
var putovanje = null;
var korsinikId = null;

$(document).ready(function() {
	$.ajaxSetup({
		headers: createAuthorizationTokenHeader(tokenKey),
		error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	let statusCode = XMLHttpRequest.status;
	    	if(statusCode == 400) {
	    		//u slucaju neispravnih podataka (Bad request - 400) prikazuje se
	    		//poruka o greski koju je server poslao
	    		swal({
	    		  title: "Došlo je do greške!",
	  			  text: XMLHttpRequest.responseText,
	  			  icon: "warning",
	  			}).then(function() {
					window.location.replace("../login/login.html");
				});
	    	} else {
	    		swal({
		    		  title: "Došlo je do greške!",
		  			  icon: "warning",
		  			}).then(function() {
						window.location.replace("../login/login.html");
					});
	    	}
		}
	});
		
	$("#povratakNaPocetnuBtn").click(function(e) {
		e.preventDefault();
		$.ajax({
			type : 'GET',
			url : "../putovanja/potvrdaPutovanja/" + idPutovanja,
			dataType : "json"
		});

		window.location.replace("../registrovaniKorisnikPocetna/index.html");
	});
		
	//ocitavanje parametara putanje
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	idPutovanja = params_parser.get("idPutovanja");
	inicijator = params_parser.get("inicijator");
	korisnikId = params_parser.get("idKorisnika");
	
	if(inicijator == "false") {
		$("#povratakNaPocetnuBtn").hide();
		$("#odgovorNaPoziv").show();
	}
	
	ucitajPodatkePutovanja();
	ucitajPodatkeZaVozila();

	//  prihvatanje pozivnice
	$("#prihvatanjePozivnice").click(function(e) {
		e.preventDefault();
		
		let odgovorNaPozivnicu = {
			idPozvanogPrijatelja : korisnikId,
			idPutovanja : idPutovanja
		};
		
		$.ajax({
			type: "POST",
			url : "../putovanja/prihvatiPoziv",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(odgovorNaPozivnicu),
			async: false,
			success: function(response) {
				if (response) {
					swal({
						  title: "Uspješno ste prihvatili pozivnicu za putovanje.",
						  icon: "success"
					}).then(function() {
						window.location.replace("../login/login.html");
						});
				} else {
					swal({
						  title: "Došlo je do greške.",
						  text: "Istekao je rok za prihvatanje pozivnice.",
						  icon: "error"
						}).then(function() {
							window.location.replace("../login/login.html");
						});
				}
			},
		});
		
	});
	
	//  odbijanje pozivnice
	$("#odbijanjePozivnice").click(function(e) {
		e.preventDefault();
		
		let odgovorNaPozivnicu = {
			idPozvanogPrijatelja : korisnikId,
			idPutovanja : idPutovanja
		};
		
		$.ajax({
			type: "DELETE",
			url : "../putovanja/odbijPoziv",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(odgovorNaPozivnicu),
			async: false,
			success: function(response) {
				if (response) {
					swal({
						  title: "Uspješno ste odbili pozivnicu za putovanje.",
						  icon: "success"
					}).then(function() {
						window.location.replace("../login/login.html");
						});
				} else {
					swal({
						  title: "Došlo je do greške.",
						  text: "Istekao je rok za odbijanje pozivnice.",
						  icon: "error"
						}).then(function() {
							window.location.replace("../login/login.html");
						});
				}
			},
		});
		
	});
	
});	

function ucitajPodatkePutovanja() {
	
	$.ajax({
		type : 'GET',
		async : false,
		url : "../putovanja/dobaviSve/" + idPutovanja,
		dataType : "json",
		success: function(data){
			putovanje = data;
			prikaziRezervisaneLetove(putovanje.rezervacijeSjedista);
			prikaziRezervisaneSobe(putovanje.rezervacijeSoba);			
		},
	});
}

function ucitajPodatkeZaVozila() {
	$.ajax({
		type : 'GET',
		async : false,
		url : "../putovanja/dobaviVozila/" + idPutovanja,
		dataType : "json",
		success: function(data){
			vozila = data;
			prikaziRezVozila(vozila);			
		},
	});
}

function prikaziRezervisaneLetove(rezLetova) {
	
	let tabela = $("#prikazRezervisanihLetova");
	tabela.empty();
	
	$.each(rezLetova, function(i, rLet) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + rLet.aviokompanija.naziv + '</td>');
		noviRed.append('<td class="column1">' + rLet.let.brojLeta + '</td>');
		noviRed.append('<td class="column1">' + rLet.cijena.toFixed(2) + '</td>');		
		noviRed.append('<td class="column1">' + rLet.sjediste.red + '/' + rLet.sjediste.kolona +  '</td>');
		noviRed.append('<td class="column1">' + rLet.let.polaziste.nazivDestinacije + '</td>');
		noviRed.append('<td class="column1">' + rLet.let.odrediste.nazivDestinacije + '</td>');
		noviRed.append('<td class="column1">' + rLet.let.datumPoletanja + '</td>');
		noviRed.append('<td class="column1">' + rLet.let.datumSletanja + '</td>');
		
		tabela.append(noviRed);
	});
}

function prikaziRezervisaneSobe(rezSoba) {
	let tabela = $("#prikazRezervisanihSoba");
	tabela.empty();
	
	$.each(rezSoba, function(i, rSoba) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + rSoba.rezervisanaSoba.hotel.naziv + '</td>');
		noviRed.append('<td class="column1">' + rSoba.rezervisanaSoba.brojSobe + '</td>');
		noviRed.append('<td class="column1">' + rSoba.rezervisanaSoba.brojKreveta + '</td>');		
		noviRed.append('<td class="column1">' + rSoba.cijena + '</td>');
		noviRed.append('<td class="column1">' + rSoba.datumDolaska + '</td>');
		noviRed.append('<td class="column1">' + rSoba.datumOdlaska + '</td>');
		tabela.append(noviRed);
	});
}

function prikaziRezVozila(vozila){
	let tabela = $("#prikazRezervisanihVozila");
	tabela.empty();
	
	$.each(vozila, function(i, vozilo) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + vozilo.nazivServisa + '</td>');
		noviRed.append('<td class="column1">' + vozilo.rezervisanoVozilo.naziv + '</td>');
		noviRed.append('<td class="column1">' + vozilo.cijena + '</td>');		
		noviRed.append('<td class="column1">' + vozilo.mjestoPreuzimanja + '</td>');
		noviRed.append('<td class="column1">' + vozilo.mjestoVracanja + '</td>');
		noviRed.append('<td class="column1">' + vozilo.datumPreuzimanja + '</td>');
		noviRed.append('<td class="column1">' + vozilo.datumVracanja + '</td>');

		tabela.append(noviRed);
	});
}