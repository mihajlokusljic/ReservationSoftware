let poslovnicaAdminaInputs = ["aviokompanijaAdmina", "hotelAdmina", "racServisAdmina"]
let poslovnicaInputs = ["aviokompanijaAdminaInp", "hotelAdminaInp", "racAdminaInp"]
let podaciAdmina = null;

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
		data: JSON.stringify(aviokompanija),
		success: function(response) {
			let tabelaAviokompanija = $("#prikazAviokompanija");
			let selekcioniMeni = $("#aviokompanijaAdminaSelect");
			prikazi(response, tabelaAviokompanija, selekcioniMeni, "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg");
			alert("Aviokompanija je uspjesno dodata.");
		},
	});
}

function dodavanjeHotela() {
	let _naziv = $("#nazivHotela").val();
	let _adresa = $("#adresaHotela").val();
	let _opis = $("#promotivniOpisHotela").val();
	
	if(_naziv == "") {
		alert("Naziv aviokompanije mora biti zadat.");
		return;
	}
	
	let hotel = {
			naziv: _naziv, 
			adresa: { punaAdresa : _adresa }, 
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
		},
	});
}

function dodavanjeRacServisa() {
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