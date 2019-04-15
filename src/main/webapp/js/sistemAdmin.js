$(document).ready(function() {
	
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "aviokompanijePrikaz", "aviokompanijeSelect");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "hoteliPrikaz", "");
	
	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "racServisiPrikaz", "");
	
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
				if(response == '') {
					let tabelaHotela = $("#hoteliPrikaz");
					prikazi(hotel, tabelaHotela);
				} else {
					alert(response);
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
				if(response == '') {
					//alert("Rent-a-car servis je uspesno dodat.");
					prikaziRacServis(racServis);
				} else {
					alert(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
	//dodavanje admina aviokompanije
	$("#unosAdminaAviokompanijeForm").submit(function(e) {
		e.preventDefault();
		
		let idAviokompanije = $("#aviokompanijeSelect").val();
		if(idAviokompanije == undefined || idAviokompanije === "") {
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
		
		let noviAdmin = {
				adresaGrada: { punaAdresa: "Nije podrzano" },
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
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
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
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
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