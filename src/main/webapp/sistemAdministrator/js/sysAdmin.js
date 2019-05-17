let poslovnicaAdminaInputs = ["aviokompanijaAdmina", "hotelAdmina", "racServisAdmina"]
let poslovnicaInputs = ["aviokompanijaAdminaInp", "hotelAdminaInp", "racAdminaInp"]
let podaciAdmina = null;
let pocetnaStrana = "../pocetnaStranica/index.html";
let mapaAviokompanije = null;
let mapaHotela = null;
let mapaRacServisa = null;
let zoomLevel = 14;

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
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "aviokompanijaAdminaSelect", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "hotelAdminaSelect", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg");

	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "racServisAdminaSelect", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg");

	//podesavanje vidljivosti polja za poslovnicu kod dodavanja novog administratora
	$("#adminAviokompanijeBtn").click(function() {
		if($("#adminAviokompanijeBtn").is(":checked")) { prikaziIzborPoslovnice("aviokompanijaAdmina"); }
	});
	
	$("#adminHotelaBtn").click(function() {
		if($("#adminHotelaBtn").is(":checked")) { prikaziIzborPoslovnice("hotelAdmina"); }
	});
	
	$("#racAdminBtn").click(function() {
		if($("#racAdminBtn").is(":checked")) { prikaziIzborPoslovnice("racServisAdmina"); }
	});
	
	$("#sysAdminBtn").click(function() {
		if($("#sysAdminBtn").is(":checked")) { prikaziIzborPoslovnice(""); }
	});
	
	//dodavanje aviokompanije
	$("#dodavanjeAviokompanijeForm").submit(function(e) {
		e.preventDefault();
		dodavanjeAviokompanije();
	});
	
	//dodavanje hotela
	$("#dodavanjeHotelaForm").submit(function(e) {
		e.preventDefault();
		dodavanjeHotela();
	});
	
	//dodavanje rent-a-car servisa
	$("#dodavanjeRacServisaForm").submit(function(e) {
		e.preventDefault();
		dodavanjeRacServisa();
	});
	
	//dodavanjeAdministratora
	$("#dodavanjeAdminaForm").submit(function(e) {
		e.preventDefault();
		dodavanjeAdmina();
	});
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
		
	
});

function prikaziIzborPoslovnice(idPoslovnice) {
	$.each(poslovnicaAdminaInputs, function(i, tekucaPoslovnicaId) {
		if(idPoslovnice === tekucaPoslovnicaId) {
			$("#" + tekucaPoslovnicaId).show();
			$("#" + poslovnicaInputs[i]).attr("required", "required");
		}
		else
		{
			$("#" + tekucaPoslovnicaId).hide();
			$("#" + poslovnicaInputs[i]).removeAttr("required");
		}
	})
}

function dodavanjeAviokompanije() {
	let _naziv = $("#nazivAviokompanije").val();
	let _adresa = $("#adresaAviokompanije").val();
	let _lat = $("#latitudaAviokompanije").val();
	let _long = $("#longitudaAviokompanije").val();
	let _opis = $("#opisAviokompanije").val();
	
	if(_naziv == "") {
		alert("Naziv aviokompanije mora biti zadat.");
		return;
	}
	
	if(_lat == "" || _long == "") {
		alert("Morate označiti lokaciju aviokompanije na mapi.");
		return;
	}
	
	let aviokompanija = {
			naziv: _naziv, 
			adresa: { 
				punaAdresa: _adresa,
				latituda: _lat,
				longituda: _long
			}, 
			promotivniOpis: _opis,
			destinacije: [],
			letovi: [],
			brzeRezervacije: [],
	};
	
	$.ajax({
		type: "POST",
		url: "../aviokompanije/dodaj",
		data: JSON.stringify(aviokompanija),
		success: function(response) {
			let tabelaAviokompanija = $("#prikazAviokompanija");
			let selekcioniMeni = $("#aviokompanijaAdminaSelect");
			prikazi(response, tabelaAviokompanija, selekcioniMeni, "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg");
			alert("Aviokompanija je uspjesno dodata.");
			$("#dodavanjeAviokompanijeForm")[0].reset();
			mapaAviokompanije.geoObjects.removeAll();
		},
	});
}

function dodavanjeHotela() {
	let _naziv = $("#nazivHotela").val();
	let _adresa = $("#adresaHotela").val();
	let _lat = $("#latitudaHotela").val();
	let _long = $("#longitudaHotela").val();
	let _opis = $("#promotivniOpisHotela").val();
	
	if(_naziv == "") {
		alert("Naziv aviokompanije mora biti zadat.");
		return;
	}
	
	if(_lat == "" || _long == "") {
		alert("Morate označiti lokaciju hotela na mapi.");
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
		type: "POST",
		url: "../hoteli/dodaj",
		data: JSON.stringify(hotel),
		success: function(response) {
			let tabelaHotela = $("#prikazHotela");
			let selekcioniMeni = $("#hotelAdminaSelect");
			prikazi(response, tabelaHotela, selekcioniMeni, "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg");
			alert("Hotel je uspjesno dodat.");
			$("#dodavanjeHotelaForm")[0].reset();
			mapaHotela.geoObjects.removeAll();
		},
	});
}

function dodavanjeRacServisa() {
	let _naziv = $("#nazivRacServisa").val();
	let _adresa = $("#adresaRacServisa").val();
	let _opis = $("#opisRacServisa").val();
	let _lat = $("#latitudaRacServisa").val();
	let _long = $("#longitudaRacServisa").val();
	
	if(_naziv == "") {
		alert("Naziv rent-a-car servisa mora biti zadat.");
		return;
	}
	
	if(_lat == "" || _long == "") {
		alert("Morate označiti lokaciju rent-a-car servisa na mapi.");
		return;
	}
	
	let racServis = {
			naziv: _naziv, 
			adresa: { 
				punaAdresa: _adresa,
				latituda: _lat,
				longituda: _long
			}, 
			promotivniOpis: _opis
	};
	
	$.ajax({
		type: "POST",
		url: "../rentACar/dodajServis",
		data: JSON.stringify(racServis),
		success: function(response) {
			let tabelaRacServisa = $("#prikazRacServisa");
			let selekcioniMeni = $("#racServisAdminaSelect");
			prikazi(response, tabelaRacServisa, selekcioniMeni, "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg");
			alert("Rent-a-car servis je uspjesno dodat.");
			$("#dodavanjeRacServisaForm")[0].reset();
			mapaRacServisa.geoObjects.removeAll();
		},
	});
}

function dodavanjeAdmina() {
	let _email = $("#emailAdmina").val();
	let _lozinka = $("#lozinkaAdmina").val();
	let lozinkaPotvrda = $("#potvrdaLozinkeAdmina").val();
	if (_lozinka != lozinkaPotvrda){
		alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
		return;
	}
	let _ime = $("#imeAdmina").val();
	let _prezime = $("#prezimeAdmina").val();
	let _brojTelefona = $("#brTelefonaAdmina").val();
	let _adresa = $("#adresaAdmina").val();
	
	let _idPoslovnice = 0;
	let tergetUrl = "../auth/registerSysAdmin";
	
	if($("#adminAviokompanijeBtn").is(":checked")) {
		_idPoslovnice = $("#aviokompanijaAdminaSelect").val();
		tergetUrl = "../auth/registerAvioAdmin";
	}
	else if($("#adminHotelaBtn").is(":checked")) {
		_idPoslovnice = $("#hotelAdminaSelect").val();
		tergetUrl = "../auth/registerHotelAdmin";
	}
	else if($("#racAdminBtn").is(":checked")) {
		_idPoslovnice = $("#racServisAdminaSelect").val();
		tergetUrl = "../auth/registerRacAdmin";
	}
	
	let noviAdmin = {
			punaAdresa: _adresa,
			brojTelefona: _brojTelefona,
			email: _email,
			idPoslovnice: _idPoslovnice,
			ime: _ime,
			prezime: _prezime,
			lozinka: _lozinka
	};
	
	$.ajax({
		type: "POST",
		url: tergetUrl,
		data: JSON.stringify(noviAdmin),
		success: function(response) {
			alert(response);
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

function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz, idSelekcionogMenija, defaultSlika) {
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
				prikazi(podatak, tabela, selekcioniMeni, defaultSlika);
			});
		},
	});
}

function prikazi(podatak, tabelaZaPrikaz, selekcioniMeni, defaultSlika) {
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
	noviRed.append('<td class="column1">' + podatak.naziv + '</td>');
	noviRed.append('<td class="column1">' + podatak.adresa.punaAdresa + '</td>');
	noviRed.append('<td class="column6">' + podatak.promotivniOpis + '</td>');
	tabelaZaPrikaz.append(noviRed);
	if(selekcioniMeni != undefined) {
		selekcioniMeni.append('<option value="' + podatak.id + '">' + podatak.naziv + ', ' + podatak.adresa.punaAdresa + '</option>');
	}
}

function odjava() {
	removeJwtToken();
	window.location.replace(pocetnaStrana);
}

function inicijalizujMape() {
	let belgradeCoords = [44.7866, 20.4489];
	mapaHotela = new ymaps.Map('hotelMapa', {
        center: belgradeCoords,
        zoom: zoomLevel,
        controls: []
    });
	
	mapaHotela.events.add('click', function(e) {
		var coords = e.get('coords');
		var placemark = new ymaps.Placemark(coords);
		mapaHotela.geoObjects.removeAll();
		mapaHotela.geoObjects.add(placemark);
		$("#latitudaHotela").val(coords[0]);
		$("#longitudaHotela").val(coords[1]);
	});
	
	mapaHotela.controls.add('geolocationControl');
	mapaHotela.controls.add('typeSelector');
	mapaHotela.controls.add('zoomControl');
	
	mapaAviokompanije = new ymaps.Map('aviokompanijaMapa', {
        center: belgradeCoords,
        zoom: zoomLevel,
        controls: []
    });
	
	mapaAviokompanije.events.add('click', function(e) {
		var coords = e.get('coords');
		var placemark = new ymaps.Placemark(coords);
		mapaAviokompanije.geoObjects.removeAll();
		mapaAviokompanije.geoObjects.add(placemark);
		$("#latitudaAviokompanije").val(coords[0]);
		$("#longitudaAviokompanije").val(coords[1]);
	});
	
	mapaAviokompanije.controls.add('geolocationControl');
	mapaAviokompanije.controls.add('typeSelector');
	mapaAviokompanije.controls.add('zoomControl');
	
	mapaRacServisa = new ymaps.Map('racMapa', {
        center: belgradeCoords,
        zoom: zoomLevel,
        controls: []
    });
	
	mapaRacServisa.events.add('click', function(e) {
		var coords = e.get('coords');
		var placemark = new ymaps.Placemark(coords);
		mapaRacServisa.geoObjects.removeAll();
		mapaRacServisa.geoObjects.add(placemark);
		$("#latitudaRacServisa").val(coords[0]);
		$("#longitudaRacServisa").val(coords[1]);
	});
	
	mapaRacServisa.controls.add('geolocationControl');
	mapaRacServisa.controls.add('typeSelector');
	mapaRacServisa.controls.add('zoomControl');
}

ymaps.ready(inicijalizujMape);