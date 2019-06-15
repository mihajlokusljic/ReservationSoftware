var tokenKey = "jwtToken";
var idPutovanja = -1;
var inicijator = false;
var putovanje = null;

$(document).ready(function() {
	$.ajaxSetup({
		headers: createAuthorizationTokenHeader(tokenKey),
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
		}
	});
		
	$("#povratakNaPocetnuBtn").click(function(e) {
		e.preventDefault();
		window.location.replace("../registrovaniKorisnikPocetna/index.html");
	});
	
	//ocitavanje parametara putanje
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	idPutovanja = params_parser.get("idPutovanja");
	inicijator = params_parser.get("inicijator");
	
	ucitajPodatkePutovanja();
	ucitajPodatkeZaVozila();

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