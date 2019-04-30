var tokenKey = "jwtToken";
var aviokompanija = null;
var podaciAdmina = null;


$(document).ready(function() {
	
	$.ajaxSetup({
		headers: createAuthorizationTokenHeader(tokenKey),
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown)
		}
	});
	
	korisnikInfo();
	ucitajPodatkeSistema();
	ucitajDestinacije();
	
	$("#administratorAviokompanijeDestinacijeForm").submit(function(e) {
		e.preventDefault();
				
		let naziv_destinacije = $("#destinacijeNazivDestinacije").val();		
		let puna_adresa = $("#destinacijePunaAdresa").val();
		
		let novaDestinacija = {
			naziv : naziv_destinacije,
			punaAdresa : puna_adresa,
			idAviokompanije : aviokompanija.id
		};
		
		$.ajax({
			type: "POST",
			url: "../destinacije/dodaj/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(novaDestinacija),
			success: function(response) {
				if(response == "Takva destinacija vec postoji.") {
					alert("Već postoji destinacija sa takvom punom adresom.");
					return;
				}
				else if (response == "Ne postoji aviokompanija sa datim id-jem.") {
					alert("Ne postoji aviokompanija sa datim id-jem.");
					return;
				}
				else {
					alert("Destinacija je uspješno dodata.");
					prikaziDestinaciju(response, $("#administratorAviokompanijeDestinacijeTabela"));
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});

	
});

function korisnikInfo(){
	let token = getJwtToken("jwtToken");
	$.ajax({
		type : 'GET',
		url : "../korisnik/getInfo",
		dataType : "json",
		success: function(data){
			if(data != null){
				podaciAdmina = data;
				$("#podaciAdmina").append(data.ime + " " + data.prezime);
			}
			else{
				alert("Nepostojeći korisnik");
			}
		},
	});
}

function ucitajPodatkeSistema() {

	$.ajax({
		type : 'GET',
		url : "../destinacije/podaciOServisu",
		dataType : "json",
		success: function(data){
			if(data != null){
				aviokompanija = data;
			}
		}
	});
}

function ucitajDestinacije() {
	$.ajax({
		type : "GET",
		url : "../destinacije/dobaviSve",
		dataType : "json",
		success : function(response) {
			prikaziDestinacije(response);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function prikaziDestinaciju(destinacija, tbody) {
	let row = $("<tr></tr>");
	
	row.append('<td class="column3">' + '<img src="https://static1.squarespace.com/static/57b9b98a29687f1ef5c622df/t/5b78746dc2241b4f57afadf6/1534620788911/zurich+best+view?format=1500w">' + 
			"</td>");
	row.append('<td class="column3">' + destinacija.nazivDestinacije + "</td>");
	row.append('<td class="column3">' + destinacija.adresa.punaAdresa + "</td>");
	
	tbody.append(row);
}

function prikaziDestinacije(destinacije){

	let tbody = $("#administratorAviokompanijeDestinacijeTabela");
	tbody.empty();
	
	$.each(destinacije, function(i,destinacija){
		prikaziDestinaciju(destinacija, tbody);
	});

}