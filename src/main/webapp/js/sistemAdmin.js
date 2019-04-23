var tokenKey = "jwtToken";

$(document).ready(function() {
	
	$.ajaxSetup({
	    headers: createAuthorizationTokenHeader(tokenKey),
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
		}
	});
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "aviokompanijePrikaz", "aviokompanijeSelect");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "hoteliPrikaz", "hoteliSelect");
	
	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "racServisiPrikaz", "racServisiSelect");
	
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
				if(response == '') {
					let tabelaAviokompanija = $("#aviokompanijePrikaz");
					let selekcioniMeni = $("#aviokompanijeSelect");
					prikazi(aviokompanija, tabelaAviokompanija, selekcioniMeni);
					//alert("Proslo");
				} else {
					alert(response);
				}
			},
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
				if(response == '') {
					let tabelaHotela = $("#hoteliPrikaz");
					let selekcioniMeni = $("#hoteliSelect");
					prikazi(hotel, tabelaHotela, selekcioniMeni);
				} else {
					alert(response);
				}
			},
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
				if(response == '') {
					let tabelaRacServisa = $("#racServisiPrikaz");
					let selekcioniMeni = $("#racServisiSelect");
					prikaziRacServis(racServis, tabelaRacServisa, selekcioniMeni);
				} else {
					alert(response);
				}
			},
		});
	});
	
	//dodavanje admina aviokompanije
	$("#unosAdminaAviokompanijeForm").submit(function(e) {
		e.preventDefault();
		
		let idAviokompanije = $("#aviokompanijeSelect").val();
		if(idAviokompanije == "undefined" || idAviokompanije === "") {
			alert("Greska. Aviokompanija mora biti zadata.");
			return;
		}
		let _email = $("#emailAdminaAviokompanije").val();
		let _lozinka = $("#lozinkaAdminaAviokompanije").val();
		let lozinkaPotvrda = $("#potvrdaLozinkeAdminaAviokompanije").val();
		if (_lozinka != lozinkaPotvrda){
			alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
			return;
		}
		let _ime = $("#imeAdminaAviokompanije").val();
		let _prezime = $("#prezimeAdminaAviokompanije").val();
		let _brojTelefona = $("#brTelefonaAdminaAviokompanije").val();
		let _adresa = $("#adresaAdminaAviokompanije").val();
		
		let noviAdmin = {
				punaAdresa: _adresa,
				brojTelefona: _brojTelefona,
				email: _email,
				idPoslovnice: idAviokompanije,
				ime: _ime,
				prezime: _prezime,
				lozinka: _lozinka
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/registerAvioAdmin",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviAdmin),
			success: function(response) {
				alert(response);
			},
		});
	});

	//dodavanje admina hotela
	$("#unosAdminaHotelaForm").submit(function(e) {
		e.preventDefault();
		
		let idHotela = $("#hoteliSelect").val();
		if(idHotela == "undefined" || idHotela === null || idHotela === "") {
			alert("Greska. Hotel mora biti zadat.");
			return;
		}
		let _email = $("#emailAdminaHotela").val();
		let _lozinka = $("#lozinkaAdminaHotela").val();
		let lozinkaPotvrda = $("#potvrdaLozinkeAdminaHotela").val();
		if (_lozinka != lozinkaPotvrda){
			alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
			return;
		}
		let _ime = $("#imeAdminaHotela").val();
		let _prezime = $("#prezimeAdminaHotela").val();
		let _brojTelefona = $("#brTelefonaAdminaHotela").val();
		let _adresa = $("#adresaAdminaHotela").val();
		
		let noviAdmin = {
				punaAdresa: _adresa,
				brojTelefona: _brojTelefona,
				email: _email,
				idPoslovnice: idHotela,
				ime: _ime,
				prezime: _prezime,
				lozinka: _lozinka
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/registerHotelAdmin",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviAdmin),
			success: function(response) {
				alert(response);
			},
		});
	});
	
	//dodavanje admina rent-a-car servisa
	$("#unosRacAdminaForm").submit(function(e) {
		e.preventDefault();
		
		let idRacServisa = $("#racServisiSelect").val();
		if(idRacServisa == "undefined" || idRacServisa === null || idRacServisa === "") {
			alert("Greska. Rent-a-car servis mora biti zadat.");
			return;
		}
		let _email = $("#emaiRaclAdmina").val();
		let _lozinka = $("#lozinkaRacAdmina").val();
		let lozinkaPotvrda = $("#potvrdaLozinkeRacAdmina").val();
		if (_lozinka != lozinkaPotvrda){
			alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
			return;
		}
		let _ime = $("#imeRacAdmina").val();
		let _prezime = $("#prezimeRacAdmina").val();
		let _brojTelefona = $("#brTelefonaRacAdmina").val();
		let _adresa = $("#adresaRacAdmina").val();
		
		let noviAdmin = {
				punaAdresa: _adresa,
				brojTelefona: _brojTelefona,
				email: _email,
				idPoslovnice: idRacServisa,
				ime: _ime,
				prezime: _prezime,
				lozinka: _lozinka
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/registerRacAdmin",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviAdmin),
			success: function(response) {
				alert(response);
			},
		});
	});

	
	//dodavanje sistemskog admina
	$("#unosSysAdminaForm").submit(function(e) {
		e.preventDefault();
		
		let _email = $("#emaiSysAdmina").val();
		let _lozinka = $("#lozinkaSysAdmina").val();
		let lozinkaPotvrda = $("#potvrdaLozinkeSysAdmina").val();
		if (_lozinka != lozinkaPotvrda){
			alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
			return;
		}
		let _ime = $("#imeSysAdmina").val();
		let _prezime = $("#prezimeSysAdmina").val();
		let _brojTelefona = $("#brTelefonaSysAdmina").val();
		let _adresa = $("#adresaSysAdmina").val();
		
		let noviAdmin = {
				punaAdresa: _adresa,
				brojTelefona: _brojTelefona,
				email: _email,
				idPoslovnice: -1,
				ime: _ime,
				prezime: _prezime,
				lozinka: _lozinka
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/registerSysAdmin",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviAdmin),
			success: function(response) {
				alert(response);
			},
		});
	});

	
});

function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz, idSelekcionogMenija) {
	let tabela = $("#" + idTabeleZaPrikaz);
	if(idSelekcionogMenija == "") {
		return;
	}
	let selekcioniMeni = $("#" + idSelekcionogMenija);
	$.ajax({
		type: "GET",
		url: putanjaControlera,
		success: function(response) {
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela, selekcioniMeni);
			});
		},
	});
}

function prikazi(podatak, tabelaZaPrikaz, selekcioniMeni) {
	let noviRed = $("<tr></tr>");
	noviRed.append("<td>" + podatak.naziv + "</td>");
	noviRed.append("<td>" + podatak.adresa.punaAdresa + "</td>");
	noviRed.append("<td>" + podatak.promotivniOpis + "</td>");
	tabelaZaPrikaz.append(noviRed);
	if(selekcioniMeni != undefined) {
		selekcioniMeni.append('<option value="' + podatak.id + '">' + podatak.naziv + '</option>');
	}
}

function prikaziRacServis(racServis) {
	let racServisi = $("#racServisiPrikaz");
	let noviRed = $("<tr></tr>");
	noviRed.append("<td>" + racServis.naziv + "</td>");
	noviRed.append("<td>" + racServis.adresa.punaAdresa + "</td>");
	noviRed.append("<td>" + racServis.promotivniOpis + "</td>");
	racServisi.append(noviRed);
}