let podaciAdmina = null;
let podaciHotela = null;
let pocetnaStrana = "../pocetnaStranica/index.html";
let defaultSlika = "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg";
let stavkeMenija = ["stavkaUredjivanjeHotela", "stavkaBrzeRezervacije", "stavkaIzvjestaji", "stavkaProfilKorisnika"];
let tabovi = ["tab-sobe", "tab-dodatne-usluge", "tab-info-stranica", "tab-brze-rezervacije", "tab-izvjestaji", "tab-profil-kor", "tab-profil-lozinka"];
let mapaHotela = null;
let zoomLevel = 17;

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
	
	//dodavanje reakcija na klikove na navigacionom meniju
	$("#sobe").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaUredjivanjeHotela");
		prikaziTab("tab-sobe");
	});
	
	$("#dodatneUsluge").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaUredjivanjeHotela");
		prikaziTab("tab-dodatne-usluge");
	});
	
	$("#infoStranica").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaUredjivanjeHotela");
		prikaziTab("tab-info-stranica");
	});
	
	$("#brzeRezervacije").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaBrzeRezervacije");
		prikaziTab("tab-brze-rezervacije");
	});
	
	$("#izvjestaji").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaIzvjestaji");
		prikaziTab("tab-izvjestaji");
	});
	
	$("#izmjeni_podatke").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaProfilKorisnika");
		prikaziTab("tab-profil-kor");
	});
	
	$("#promjeni_lozinku").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaProfilKorisnika");
		prikaziTab("tab-profil-lozinka");
	});
	
	//ucitavanje podataka profila administratora
	korisnikInfo();
	
	//ucitavanja podataka hotela za koji administrator radi
	ucitajPodatkeHotela();
	ymaps.ready(inicijalizujMapu);
	
	//dodavanje sobe
	$("#dodavanjeSobeForm").submit(function(e) {
		e.preventDefault();
		dodavanjeSobe();
		$("#dodavanjeSobeForm")[0].reset();
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
	
	//ponistavanje unijetih izmjena info stranice
	$("#ponistavanjeIzmjenaStraniceHotela").click(function(e) {
		e.preventDefault();
		$("#izmjenaInfoStraniceForm")[0].reset();
		prikaziPodatkeHotela();
		postaviMarker([podaciHotela.adresa.latituda, podaciHotela.adresa.longituda]);
		mapaHotela.setZoom(zoomLevel);
	});
	
	//izmjena podataka sobe
	$("#izmjenaSobeForm").submit(function(e) {
		e.preventDefault();
		izmjenaSobe();
	});
	
	//ponistavanje unijetih izmjena sobe
	$("#ponistavanjeIzmjenaSobe").click(function(e) {
		e.preventDefault();
		$("#tab-izmjena-sobe").toggleClass("active");
		$("#tab-sobe").toggleClass("active");
		$("#izmjenaSobeForm")[0].reset();
	});
	
	//izmjena profila
	$("#forma_profil_korisnika").submit(function(e){
		e.preventDefault();
		izmjenaProfila();
	});
	
	//ponistavanje izmjena profila
	$("#ponistavanjeIzmjenaProfila").click(function(e) {
		e.preventDefault();
		prikaziPodatkeAdmina();
	});
	
	//izmjena lozinke
	$("#forma_lozinka").submit(function(e) {
		e.preventDefault();
		promjenaLozinke();
	});
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
		
	
});

function prikaziTab(idTaba) {
	for(i in tabovi) {
		if(idTaba == tabovi[i]) {
			$("#" + tabovi[i]).addClass("active");
		} else {
			$("#" + tabovi[i]).removeClass("active");
		}
	}
}

function aktivirajStavkuMenija(idStavke) {
	for(i in stavkeMenija) {
		if(idStavke == stavkeMenija[i]) {
			$("#" + stavkeMenija[i]).addClass("active");
		} else {
			$("#" + stavkeMenija[i]).removeClass("active");
		}
	}
}

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
			podaciHotela.sobe.push(response)
			prikaziSobe(podaciHotela.sobe);
		},
	});
}

function izmjenaSobe() {
	let _id = $("#idSobeIzmjena").val();
	let _brSobe = $("#brojSobeIzmjena").val();
	let _sprat = $("#spratSobeIzmjena").val();
	let _red = $("#redSobeIzmjena").val();
	let _kolona = $("#kolonaSobeIzmjena").val();
	let _brKreveta = $("#brKrevetaSobeIzmjena").val();
	let _cijena = $("#cijenaNocenjaIzmjena").val();
	
	let noviPodaciSobe = {
		id: _id,
		brojSobe: _brSobe,
		sprat: _sprat,
		vrsta: _red,
		kolona: _kolona,
		brojKreveta: _brKreveta,
		cijena: _cijena,
	}
	
	$.ajax({
		type: "PUT",
		url: "../hotelskeSobe/izmjeni",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(noviPodaciSobe),
		success: function(response) {
			let updated = false;
			
			for(i in podaciHotela.sobe) {
				if(podaciHotela.sobe[i].id == _id) {
					podaciHotela.sobe[i] = response;
					updated = true;
					break;
				}
			}
			
			if(!updated) {
				podaciHotela.sobe.push(response)
			}
			
			prikaziSobe(podaciHotela.sobe);
			$("#tab-izmjena-sobe").toggleClass("active");
			$("#tab-sobe").toggleClass("active");
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
	let _lat = $("#latitudaHotela").val();
	let _long = $("#longitudaHotela").val();
	let _opis = $("#opisHotela").val();
	
	if(_naziv == "") {
		alert("Naziv aviokompanije mora biti zadat.");
		return;
	}
	
	if(_lat == "" || _long == "") {
		alert("Morate zadati lokaciju na mapi (kliknite na link u polju adresa)");
		return;
	}
	
	if(_adresa == "") {
		alert("Adresa aviokompanije mora biti zadata.");
		return;
	}
	
	let hotel = {
			naziv: _naziv, 
			adresa: { 
				punaAdresa: _adresa,
				latituda: _lat,
				longituda: _long
			}, 
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
				prikaziPodatkeAdmina();
				if(!podaciAdmina.lozinkaPromjenjena) {
					izmjenaInicijalneLozinke();
				}
			}
			else{
				alert("nepostojeci korisnik");
			}
		},
	});
}

function izmjenaInicijalneLozinke() {
	$("#meni").hide();
	$("#izmjenaInicijalneLozinkePoruka").show();
	prikaziTab("tab-profil-lozinka");
}

function ucitajPodatkeHotela() {
	$.ajax({
		type: "GET",
		url: "../hoteli/administriraniHotel",
		async: false,
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
	let sobeTabela = $("#prikazSoba");
	sobeTabela.empty();
	$.each(sobe, function(i, soba) {
		prikaziSobu(soba);
	});
	
	$(".brisanjeSobe").click(function(e) {
		e.preventDefault();
		var idSobe = e.target.id.substring(2); //id datog linka je: bs + id_sobe (brisanje sobe <id sobe>)
		idSobe = parseInt(idSobe);
		brisanjeSobe(idSobe);
	});
	
	$(".izmjenaSobe").click(function(e) {
		e.preventDefault();
		var idSobe = e.target.id.substring(2); //id datog linka je: is + id_sobe (izmjena sobe <id sobe>)
		idSobe = parseInt(idSobe);
		prikaziIzmjenuSobe(idSobe);
	});
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
	noviRed.append('<td class="column6"><a href="#" class="izmjenaSobe" id="is' + soba.id + '">Izmjeni</a>&nbsp&nbsp<a class="brisanjeSobe" href="#" id="bs' + soba.id + '">Obriši</a></td>');
	sobeTabela.append(noviRed);
}

function prikaziIzmjenuSobe(idSobe) {
	let indeksSobe = 0;
	let soba = null;
	for(indeksSobe in podaciHotela.sobe) {
		soba = podaciHotela.sobe[indeksSobe];
		if(soba.id == idSobe) {
			break;
		}
	}
	if(soba == null) {
		alert("Doslo je do greske pri izmjeni sobe.");
		return;
	}
	
	//podesi formu za izmjenu sobe
	$("#idSobeIzmjena").val(idSobe);
	$("#brojSobeIzmjena").val(soba.brojSobe);
	$("#spratSobeIzmjena").val(soba.sprat);
	$("#redSobeIzmjena").val(soba.vrsta);
	$("#kolonaSobeIzmjena").val(soba.kolona);
	$("#brKrevetaSobeIzmjena").val(soba.brojKreveta);
	$("#cijenaNocenjaIzmjena").val(soba.cijena);
	$("#tab-sobe").toggleClass("active");
	$("#tab-izmjena-sobe").toggleClass("active");
	
}

function brisanjeSobe(idSobe) {
	let indeksSobe = 0;
	let soba = null;
	for(indeksSobe in podaciHotela.sobe) {
		soba = podaciHotela.sobe[indeksSobe];
		if(soba.id == idSobe) {
			break;
		}
	}
	if(soba == null) {
		alert("Doslo je do greske pri brisanju sobe.");
		return;
	}
	let obrisi = confirm("Da li ste sigurni da želite obrisati sobu broj " + soba.brojSobe + "?");
	if(!obrisi) {
		return;
	}
	
	$.ajax({
		type: "DELETE",
		url: "../hotelskeSobe/obrisi/" + idSobe,
		success: function(response) {
			alert(response);
			//uklanjanje sobe iz podataka hotela i osvjezavanje prikaza soba
			for(i in podaciHotela.sobe) {
				if(podaciHotela.sobe[i].id == idSobe) {
					podaciHotela.sobe.pop(i);
				}
			}
			prikaziSobe(podaciHotela.sobe);
		},
	});
}

function izmjenaProfila(){
	
	var imeAdmina = $("#imeAdmina").val();
	if (imeAdmina == ''){
		alert("Polje za unos imena ne moze biti prazno.");
		return;
	}
	var prezimeAdmina = $("#prezimeAdmina").val();
	if (prezimeAdmina == ''){
		alert("Polje za unos prezimena ne moze biti prazno.");
		return;
	}
	var brTelefonaAdmina = $("#brTelefonaAdmina").val();
	if (brTelefonaAdmina == ''){
		alert("Polje za unos broja telefona ne moze biti prazno.");
		return;
	}
	var adresaAdmina = $("#adresaAdmina").val();
	if (adresaAdmina == ''){
		alert("Polje za unos adrese ne moze biti prazno.");
		return;
	}		

	let admin = {
			id: podaciAdmina.id,
			email: podaciAdmina.email,
			ime: imeAdmina,
			prezime: prezimeAdmina,
			brojTelefona: brTelefonaAdmina,
			adresa: { punaAdresa : adresaAdmina }
	};
	$.ajax({
		type:"PUT",
		url:"../korisnik/izmjeniProfil",
		contentType : "application/json; charset=utf-8",
		data:JSON.stringify(admin),
		success:function(response){
			alert("Uspjesno ste izmjenili profil.");
			podaciAdmina = response;
			prikaziPodatkeAdmina();
		},
	});
}

function promjenaLozinke() {
	var staraLozinka = $("#staraLozinka").val();
	var novaLozinka = $("#novaLozinka").val();
	var novaLozinka2 = $("#novaLozinka2").val();
	var lozinkaMijenjana = podaciAdmina.lozinkaPromjenjena;

	if (novaLozinka == ''){
		alert("Niste unijeli novu lozinku");
		return;
	}
	
	if (novaLozinka != novaLozinka2){
		alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
		return;
	}
	
	$.ajax({
		type : 'PUT',
		url : "../auth/changePassword/" + staraLozinka,
		data : novaLozinka,
		success : function(data) {
			if (data == ''){
				alert("Pogresna trenutna lozinka.");
				return;
			}
			else{
				setJwtToken(data.accessToken);
				alert("Uspjesno ste izmjenili lozinku");
				if(!lozinkaMijenjana) {
					$("#izmjenaInicijalneLozinkePoruka").hide();
					$("#meni").show();
					prikaziTab("tab-sobe");
					podaciAdmina.lozinkaPromjenjena = true;
				}
			}
			$("#forma_lozinka")[0].reset();
		}
	});
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
	$("#latitudaHotela").val(podaciHotela.adresa.latituda);
	$("#longitudaHotela").val(podaciHotela.adresa.longituda);
	$("#opisHotela").val(podaciHotela.promotivniOpis);
	$("#slikaHotela").attr("src", defaultSlika);
}

function prikaziPodatkeAdmina() {
	$("#emailAdmina").val(podaciAdmina.email);
	$("#imeAdmina").val(podaciAdmina.ime);
	$("#prezimeAdmina").val(podaciAdmina.prezime);
	$("#brTelefonaAdmina").val(podaciAdmina.brojTelefona);
	$("#adresaAdmina").val(podaciAdmina.adresa.punaAdresa);
}

function odjava() {
	removeJwtToken();
	window.location.replace(pocetnaStrana);
}

function postaviMarker(koordinate) {
	var placemark = new ymaps.Placemark(koordinate, {balloonContentBody: podaciHotela.naziv});
	mapaHotela.geoObjects.removeAll();
	mapaHotela.geoObjects.add(placemark);
	mapaHotela.setCenter(koordinate);
}

function inicijalizujMapu() {
	var coords = [podaciHotela.adresa.latituda, podaciHotela.adresa.longituda];
	mapaHotela = new ymaps.Map('mapa', {
        center: coords,
        zoom: zoomLevel,
        controls: []
    });
	mapaHotela.controls.add('geolocationControl');
	mapaHotela.controls.add('typeSelector');
	mapaHotela.controls.add('zoomControl');
	postaviMarker(coords);
	
	mapaHotela.events.add('click', function(e) {
		var coords = e.get('coords');
		postaviMarker(coords);
		$("#latitudaHotela").val(coords[0]);
		$("#longitudaHotela").val(coords[1]);
	});
}
