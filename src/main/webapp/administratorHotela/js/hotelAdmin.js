let podaciAdmina = null;
let podaciHotela = null;
let pocetnaStrana = "../pocetnaStranica/index.html";
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
	
	//ucitavanje podataka profila administratora
	korisnikInfo();
	
	//ucitavanja podataka hotela za koji administrator radi
	ucitajPodatkeHotela();
	
	
	//dodavanje sobe
	$("#dodavanjeSobeForm").submit(function(e) {
		e.preventDefault();
		dodavanjeSobe();
	});
	
	//dodavanje usluge hotela
	$("#dodavanjeDodatneUslugeForm").submit(function(e) {
		e.preventDefault();
		dodavanjeUsluge();
	})
	
	//izmjena podataka info stranice
	$("#izmjenaInfoStraniceForm").submit(function(e) {
		e.preventDefault();
		izmjenaInfoStranice();
	})
	
	//ponistavanje unijetih uzmjena
	$("#ponistavanjeIzmjenaStraniceHotela").click(function(e) {
		e.preventDefault();
		$("#izmjenaInfoStraniceForm")[0].reset();
		prikaziPodatkeHotela();
	});
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
		
	
});

function dodavanjeSobe() {
	let _idHotela = podaciHotela.id;
	let _brojSobe = $("#brojSobeUnos").val();
	let _sprat = $("#spratSobeUnos").val();
	let _vrsta = $("#redSobeUnos").val();
	let _kolona = $("#kolonaSobeUnos").val();
	let _brojKreveta = $("#brKrevetaSobeUnos").val();
	let _cijenaNocenja = $("#cijenaNocenjaUnos").val();
	
	let novaSoba = {
		idHotela: _idHotela,
		brojSobe: _brojSobe,
		sprat: _sprat,
		vrsta: _vrsta,
		kolona: _kolona,
		brojKreveta: _brojKreveta,
		cijenaNocenja: _cijenaNocenja
	};
	
	$.ajax({
		type: "POST",
		url: "../hotelskeSobe/dodaj",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(novaSoba),
		success: function(response) {
			prikaziSobu(response);
		},
	});
}

function dodavanjeUsluge() {
	let idHotela = podaciHotela.id;
	let nazivUsluge = $("#nazivUslugeUnos").val();
	let cijenaUsluge = $("#cijenaUslugeUnos").val();
	let nacinPlacanjaUslugeId = $("#nacinPlacanjaUslugeInput").val();
	let popustUsluge = $("#popustUslugeUnos").val();
	let opisUsluge = $("#opisUslugeUnos").val();
	if(opisUsluge == undefined) {
		opisUsluge = "";
	}
	
	let novaUsluga = {
		idPoslovnice: idHotela,
		naziv: nazivUsluge,
		cijena: cijenaUsluge,
		procenatPopusta: popustUsluge,
		nacinPlacanjaId: nacinPlacanjaUslugeId,
		opis: opisUsluge
	}
	
	$.ajax({
		type: "POST",
		url: "../hoteli/dodajUslugu",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(novaUsluga),
		success: function(response) {
			podaciHotela.cjenovnikDodatnihUsluga.push(response);
			prikaziUslugu(response);
			 $('#dodavanjeDodatneUslugeForm')[0].reset();
		},
	});
}

function izmjenaInfoStranice() {
	let _naziv = $("#nazivHotela").val();
	let _adresa = $("#adresaHotela").val();
	let _opis = $("#opisHotela").val();
	
	if(_naziv == "") {
		alert("Naziv aviokompanije mora biti zadat.");
		return;
	}
	
	if(_adresa == "") {
		alert("Adresa aviokompanije mora biti zadata.");
		return;
	}
	
	let hotel = {
			naziv: _naziv, 
			adresa: { punaAdresa : _adresa }, 
			promotivniOpis: _opis
	};
	
	$.ajax({
		type: "PUT",
		url: "../hoteli/izmjeni",
		data: JSON.stringify(hotel),
		success: function(response) {
			podaciHotela = response;
			alert("Informacije hotela su uspjesno izmjenjene.");
		},
	});
}

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
				alert("nepostojeci korisnik");
			}
		},
	});
}

function ucitajPodatkeHotela() {
	$.ajax({
		type: "GET",
		url: "../hoteli/administriraniHotel",
		success: function(response) {
			if(response != null) {
				podaciHotela = response;
				ucitajSobehotela();
				prikaziUsluge(podaciHotela.cjenovnikDodatnihUsluga);
				prikaziPodatkeHotela();
			}
		},
	});
}

function ucitajSobehotela() {
	$.ajax({
		type: "GET",
		url: "../hoteli/sobeHotela/" + podaciHotela.id,
		success: function(response) {
			if(response != null) {
				podaciHotela.sobe = response;
				prikaziSobe(response);
			}
		},
	});
}

function prikaziSobe(sobe) {
	$.each(sobe, function(i, soba) {
		prikaziSobu(soba);
	})
}

function prikaziSobu(soba) {
	let sobeTabela = $("#prikazSoba");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + soba.brojSobe + '</td>');
	noviRed.append('<td class="column1">' + soba.brojKreveta + '</td>');
	noviRed.append('<td class="column1">' + soba.cijena + '</td>');
	noviRed.append('<td class="column1">' + soba.sprat + '</td>');
	noviRed.append('<td class="column1">' + soba.vrsta + '</td>');
	noviRed.append('<td class="column1">' + soba.kolona + '</td>');
	noviRed.append('<td class="column6"><a href="#">Izmjeni</a>&nbsp&nbsp<a href="#">Obriši</a></td>');
	sobeTabela.append(noviRed);
}

function prikaziUsluge(usluge) {
	$.each(usluge, function(i, usluga) {
		prikaziUslugu(usluga);
	})
}

function prikaziUslugu(usluga) {
	let uslugeTabela = $("#prikazUsluga");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
	noviRed.append('<td class="column6">' + usluga.cijena + '</td>');
	noviRed.append('<td class="column1">' + usluga.nacinPlacanja + '</td>');
	noviRed.append('<td class="column5"><a href="#">Izmjeni</a>&nbsp&nbsp<a href="#">Obriši</a></td>');
	uslugeTabela.append(noviRed);
}

function prikaziPodatkeHotela() {
	$("#nazivHotela").val(podaciHotela.naziv);
	$("#adresaHotela").val(podaciHotela.adresa.punaAdresa);
	$("#opisHotela").val(podaciHotela.promotivniOpis);
	$("#slikaHotela").attr("src", defaultSlika);
}

function odjava() {
	removeJwtToken();
	window.location.replace(pocetnaStrana);
}