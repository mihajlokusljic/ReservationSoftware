var tokenKey = "jwtToken";
var aviokompanija = null;
var podaciAdmina = null;
let pocetnaStrana = "../pocetnaStranica/index.html";


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
	ucitajAvione();
	ucitajLetove();
	
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
				else if (response == "Ne mogu postojati dvije destinacije na istoj adresi.") {
					alert("Adresa je već zauzeta.");
					return;
				}
				else {
					alert("Destinacija je uspješno dodata.");
					prikaziDestinaciju(response, $("#administratorAviokompanijeDestinacijeTabela"));
					popuniListuZaDestinaciju(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	$("#administratorAviokompanijeLetoviForm").submit(function(e) {
		e.preventDefault();
				
		let broj_leta = $("#administratorAviokompanijeBrojLeta").val();		
		let polaziste = $("#administratorAviokompanijePolaziste").val();
		let odrediste = $("#administratorAviokompanijeOdrediste").val();
		

		let noviLet = {
			naziv : naziv_destinacije,
			punaAdresa : puna_adresa,
			idAviokompanije : aviokompanija.id
		};
		
		$.ajax({
			type: "POST",
			url: "../letovi/dodaj/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviLet),
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
					//prikaziDestinaciju(response, $("#administratorAviokompanijeDestinacijeTabela"));
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});

	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
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
			popuniListuZaDestinacije(response);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function ucitajAvione() {
	$.ajax({
		type : "GET",
		url : "../avioni/dobaviSve",
		dataType : "json",
		success : function(response) {
			popuniListuZaAvione(response);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function ucitajLetove() {
	$.ajax({
		type : "GET",
		url : "../letovi/dobaviSve",
		dataType : "json",
		success : function(response) {
			updateLetovi(response);
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

function updateLetovi(letovi) {
	let table = $("#letoviRows");
	table.empty();
	
	$.each(letovi, function(i, flight) {
		let row = $("<tr></tr>");
		
		row.append('<td class="column2">' + flight.brojLeta + "</td>");
		row.append('<td class="column3">' + flight.polaziste.nazivDestinacije + "</td>");
		row.append('<td class="column3">' + flight.odrediste.nazivDestinacije + "</td>");
		row.append('<td class="column1">' + flight.datumPoletanja + "</td>");
		row.append('<td class="column1">' + flight.datumSletanja + "</td>");
		row.append('<td class="column5">' + flight.presjedanja.length + "</td>");
		row.append('<td class="column6">' + flight.cijenaKarte + "</td>");
		if (flight.brojOcjena > 0) {
			row.append('<td class="column6">' + (flight.sumaOcjena / flight.brojOcjena) + "</td>");
		} else {
			row.append('<td class="column6">Nema ocjena</td>');
		}
		table.append(row);
	});
}

function popuniListuZaDestinaciju(destinacija) {
	let polazisteLista = $("#administratorAviokompanijePolaziste");
	let odredisteLista = $("#administratorAviokompanijeOdrediste");
	let svaPresjedanjaLista = $("#svaPresjedanja");
	
	polazisteLista.append('<option value="' + destinacija.id + '">' + destinacija.nazivDestinacije
			+ '</option>');
	odredisteLista.append('<option value="' + destinacija.id + '">' + destinacija.nazivDestinacije
			+ '</option>');
	svaPresjedanjaLista.append('<option value="' + destinacija.id + '">' + destinacija.nazivDestinacije
			+ '</option>');
}

function popuniListuZaDestinacije(destinacije) {
	let polazisteLista = $("#administratorAviokompanijePolaziste");
	let odredisteLista = $("#administratorAviokompanijeOdrediste");
	let svaPresjedanjaLista = $("#svaPresjedanja");
	
	polazisteLista.empty();
	odredisteLista.empty();
	svaPresjedanjaLista.empty();
	
	$.each(destinacije, function(i, destinacija) {
		popuniListuZaDestinaciju(destinacija);
	});
	
}

function popuniListuZaAvione(avioni) {
	let avioniLista = $("#avionZaLetSelect");
	
	avioniLista.empty();
	$.each(avioni, function(i, avion) {
		// <option value="1">A</option>
		avioniLista.append('<option value="' + avion.id + '">' + avion.naziv
				+ '</option>');
	});
	
}

function odjava() {
	removeJwtToken();
	window.location.replace(pocetnaStrana);
}
