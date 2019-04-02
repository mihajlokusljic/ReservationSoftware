$(document).ready(function() {
	
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "aviokompanijePrikaz");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "hoteliPrikaz");
	
	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "racServisiPrikaz");
	
	//dodavanje aviokompanije
	$("#unosAviokompanijeForm").submit(function(e) {
		e.preventDefault();
		
		let _naziv = $("#nazivAviokompanije").val();
		let _adresa = $("#adresaAviokompanije").val();
		let _opis = $("#opisAviokompanije").val();
		
		if(_naziv == "") {
			alert("Naziv aviokompanije mora biti zadat.");
			return;
		}
		
		let aviokompanija = {
				naziv: _naziv, 
				adresa: { punaAdresa : _adresa }, 
				promotivniOpis: _opis,
				destinacije: [],
				letovi: [],
				brzeRezervacije: [],
		};
		
		$.ajax({
			type: "POST",
			url: "../aviokompanije/dodaj",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(aviokompanija),
			success: function(response) {
				if(response == true) {
					let tabelaAviokompanija = $("#aviokompanijePrikaz");
					prikazi(aviokompanija, tabelaAviokompanija);
				} else {
					alert("Vec postoji aviokompanija sa zadatim nazivom. Pokusajte ponovo.");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
	//dodavanje hotela
	$("#unosHotelaForm").submit(function(e) {
		e.preventDefault();
		
		let _naziv = $("#nazivHotela").val();
		let _adresa = $("#adresaHotela").val();
		let _opis = $("#opisHotela").val();
		
		if(_naziv == "") {
			alert("Naziv hotela mora biti zadat.");
			return;
		}
		
		let hotel = {
				naziv: _naziv, 
				adresa: { punaAdresa : _adresa }, 
				promotivniOpis: _opis,
				dodatneUsluge: [],
				sobe: []
		};
		
		$.ajax({
			type: "POST",
			url: "../hoteli/dodaj",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(hotel),
			success: function(response) {
				if(response == true) {
					let tabelaHotela = $("#hoteliPrikaz");
					prikazi(hotel, tabelaHotela);
				} else {
					alert("Vec postoji hotel sa zadatim nazivom. Pokusajte ponovo.");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
	//dodavanje rent-a-car servisa
	$("#unosRacServisaForm").submit(function(e) {
		e.preventDefault();
		
		let _naziv = $("#nazivRacServisa").val();
		let _adresa = $("#adresaRacServisa").val();
		let _opis = $("#opisRacServisa").val();
		
		if(_naziv == "") {
			alert("Naziv rent-a-car servisa mora biti zadat.");
			return;
		}
		
		let racServis = {
				naziv: _naziv, 
				adresa: { punaAdresa : _adresa }, 
				promotivniOpis: _opis,
				sumaOcjena: 0,
				brojOcjena: 0,
				vozila: [],
				filijale: []
		};
		
		$.ajax({
			type: "POST",
			url: "../rentACar/dodajServis",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(racServis),
			success: function(response) {
				if(response == true) {
					//alert("Rent-a-car servis je uspesno dodat.");
					prikaziRacServis(racServis);
				} else {
					alert("Vec postoji rent-a-car servis sa zadatim nazivom. Pokusajte ponovo.");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
});

function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz) {
	let tabela = $("#" + idTabeleZaPrikaz);
	$.ajax({
		type: "GET",
		url: putanjaControlera,
		success: function(response) {
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela);
			});
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});
}

function prikazi(podatak, tabelaZaPrikaz) {
	let noviRed = $("<tr></tr>");
	noviRed.append("<td>" + podatak.naziv + "</td>");
	noviRed.append("<td>" + podatak.adresa.punaAdresa + "</td>");
	noviRed.append("<td>" + podatak.promotivniOpis + "</td>");
	tabelaZaPrikaz.append(noviRed);
}

function prikaziRacServis(racServis) {
	let racServisi = $("#racServisiPrikaz");
	let noviRed = $("<tr></tr>");
	noviRed.append("<td>" + racServis.naziv + "</td>");
	noviRed.append("<td>" + racServis.adresa.punaAdresa + "</td>");
	noviRed.append("<td>" + racServis.promotivniOpis + "</td>");
	racServisi.append(noviRed);
}