let stavkeMenija = ["stavkaPoslovnice", "stavkaAdministratori", "stavkaPopusti","stavkaProfilKorisnika"];
let tabovi = ["tab-aviokompanije", "tab-hoteli", "tab-rac", "tab-administartori",
	"tab-zadavanje-skale-popusta", "tab-prikaz-skale-popusta", "tab-profil-kor", "tab-lozinka"];
let poslovnicaAdminaInputs = ["aviokompanijaAdmina", "hotelAdmina", "racServisAdmina"];
let poslovnicaInputs = ["aviokompanijaAdminaInp", "hotelAdminaInp", "racAdminaInp"];
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
	    		swal({
	  			  title: XMLHttpRequest.responseText,
	  			  icon: "warning",
	  			  timer:2000
	  			});
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
	
	//ucitavanje skale popusta
	ucitajSkaluPopusta();

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
	
	//podesavanje vidljivosti za pomoc pri unosu skale popusta za bonus poene
	$("#skalaPopustaSakrijPomoc").click(function(e) {
		e.preventDefault();
		$("#stavkePopustaPomoc").hide();
		$("#skalaPopustaPrikaziPomoc").show();
	});
	
	$("#skalaPopustaPrikaziPomoc").click(function(e) {
		e.preventDefault();
		$("#skalaPopustaPrikaziPomoc").hide();
		$("#stavkePopustaPomoc").show();
	});
	
	//reakcije na klik na navigacionom meniju
	$("#aviokompanije").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaPoslovnice");
		prikaziTab("tab-aviokompanije");
	});
	
	$("#hoteli").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaPoslovnice");
		prikaziTab("tab-hoteli");
	});
	
	$("#racServisi").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaPoslovnice");
		prikaziTab("tab-rac");
	});
	
	$("#administrtori").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaAdministratori");
		prikaziTab("tab-administartori");
	});
	
	$("#zadavanjeSkalePopusta").click(function(e) {
		e.preventDefault();
		
		swal({
			  title: "Da li ste sigurni da zelite obrisati postojecu skalu popusta i zadati novu?",
			  icon: "warning",
			  buttons: true,
			  dangerMode: true,
			})
			.then((willDelete) => {
			  if (willDelete) {
				  brisanjeSkalePopusta();
					$("#zadateStavkePopustaPrikaz").empty();
					$("#skalaPopustaPrikaz").empty();
					aktivirajStavkuMenija("stavkaPopusti");
					prikaziTab("tab-zadavanje-skale-popusta");
			  } 
			});

	});
	
	$("#pregledSkalePopusta").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaPopusti");
		prikaziTab("tab-prikaz-skale-popusta");
	});
	
	$("#izmjeni_podatke").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaProfilKorisnika");
		prikaziTab("tab-profil-kor");
	});
	
	$("#promjeni_lozinku").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaProfilKorisnika");
		prikaziTab("tab-lozinka");
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
	
	//dodavanje nove stavke skale popusta za bonus poene
	$("#dodavanjeStavkeSkalePopustaForm").submit(function(e) {
		e.preventDefault();
		dodavanjeStavkeDodatnogPopusta();
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
	for(var i in stavkeMenija) {
		if(idStavke == stavkeMenija[i]) {
			$("#" + stavkeMenija[i]).addClass("active");
		} else {
			$("#" + stavkeMenija[i]).removeClass("active");
		}
	}
}

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
		swal({
			  title: "Naziv aviokompanije mora biti zadat.",
			  icon: "warning",
			  timer:2000
			});
		return;
	}
	
	if(_lat == "" || _long == "") {
		swal({
			  title: "Morate označiti lokaciju aviokompanije na mapi.",
			  icon: "warning",
			  timer:2000
			});
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
			swal({
				  title: "Aviokompanija je uspjesno dodata.",
				  icon: "success",
				  timer:2000
				});
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
		swal({
			  title: "Naziv aviokompanije mora biti zadat.",
			  icon: "warning",
			  timer:2000
			});
		return;
	}
	
	if(_lat == "" || _long == "") {
		swal({
			  title: "Morate označiti lokaciju hotela na mapi.",
			  icon: "warning",
			  timer:2000
			});
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
			swal({
				  title: "Hotel je uspješno dodat.",
				  icon: "success",
				  timer:2000
				});
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
		swal({
			  title: "Naziv rent-a-car servisa mora biti zadat.",
			  icon: "warning",
			  timer:2000
			});		
		return;
	}
	
	if(_lat == "" || _long == "") {
		swal({
			  title: "Morate označiti lokaciju rent-a-car servisa na mapi.",
			  icon: "warning",
			  timer:2000
			});	
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
			swal({
				  title: "Rent-a-car servis je uspješno dodat.",
				  icon: "success",
				  timer:2000
				});	
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
		swal({
			  title: "Greška. ",
			  text: "Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.",
			  icon: "warning",
			  timer: 2500
			});
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
			if (response == "Administrator je uspesno dodat.") {
				swal({
					  title: response,
					  icon: "success",
					  timer: 2500
					});
			}
			else{
				swal({
					  title: response,
					  icon: "warning",
					  timer: 2500
					});
			}
				
		},
	});
}

function korisnikInfo(){
	let token = getJwtToken("jwtToken");
	$.ajax({
		type : 'GET',
		url : "../korisnik/getInfo",
		async: false,
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
				swal({
					  title: "Nepostojeći korsinik",
					  icon: "error",
					  timer: 2500
					});
			}
		},
	});
}

function brisanjeSkalePopusta() {
	$.ajax({
		type : 'DELETE',
		url : "../bonusSkala/obrisiSkalu",
		async: false,
		dataType : "json",
		success: function(ok){
			if(!ok) {
				swal({
					  title: "Došlo je do greške pri resetovanju skale popusta.",
					  icon: "error",
					  timer: 2500
					});
			}
		},
	});
}

function dodavanjeStavkeDodatnogPopusta() {
	let _donjaGranicaPoeni = $("#donjaGranicaPoeni").val();
	_donjaGranicaPoeni = parseFloat(_donjaGranicaPoeni);
	let _gornjaGranicaPoeni = $("#gornjaGranicaPoeni").val();
	_gornjaGranicaPoeni = parseFloat(_gornjaGranicaPoeni);
	
	if(_gornjaGranicaPoeni < _donjaGranicaPoeni) {
		swal({
			  title: "Gornja granica za ostvarene bonus poene ne smije biti manja od donje granice.",
			  icon: "warning",
			  timer: 2500
			});
	}
	
	let _procenatPopusta = $("#procenatBonusPopusta").val();
	
	let novaStavka = {
			donjaGranicaBonusPoeni: _donjaGranicaPoeni,
			gornjaGranicaBonusPoeni: _gornjaGranicaPoeni,
			ostvarenProcenatPopusta: _procenatPopusta
	};
	
	$.ajax({
		type: "POST",
		url: "../bonusSkala/dodajStavku",
		data: JSON.stringify(novaStavka),
		success: function(response) {
			prikaziStavku(response, true);
			$("#dodavanjeStavkeSkalePopustaForm")[0].reset();
			if(response.gornjaGranicaBonusPoeni == null) {
				prikaziTab("tab-prikaz-skale-popusta");
				$("#donjaGranicaPoeni").val(0);
				$("#donjaGranicaPoeni").attr("min", 0);
			} else {
				$("#donjaGranicaPoeni").val(response.gornjaGranicaBonusPoeni);
				$("#donjaGranicaPoeni").attr("min", response.gornjaGranicaBonusPoeni);
			}
		},
	});
	
}

function prikaziStavku(stavka, dodavanje) {
	let zadateStavke = $("#zadateStavkePopustaPrikaz");
	let sveStavke = $("#skalaPopustaPrikaz");
	let noviRed = $('<tr></tr>');
	let gornjaGranica = stavka.gornjaGranicaBonusPoeni;
	if(gornjaGranica == null) {
		gornjaGranica = "∞";
	}
	noviRed.append('<td class="column1">[' + stavka.donjaGranicaBonusPoeni + ", " + gornjaGranica + ")</td>");
	noviRed.append('<td class="column1">' + stavka.ostvarenProcenatPopusta + "%</td>");
	noviRed.appendTo(sveStavke);
	if(dodavanje) {
		noviRed.clone().appendTo(zadateStavke);
	}
	
}

function izmjenaInicijalneLozinke() {
	$("#meni").hide();
	$("#izmjenaInicijalneLozinkePoruka").show();
	prikaziTab("tab-lozinka");
}

function prikaziPodatkeAdmina() {
	$("#emailKorisnika").val(podaciAdmina.email);
	$("#imeKorisnika").val(podaciAdmina.ime);
	$("#prezimeKorisnika").val(podaciAdmina.prezime);
	$("#brTelefonaKorisnika").val(podaciAdmina.brojTelefona);
	if(podaciAdmina.adresa == null) {
		$("#adresaKorisnika").val("");
	} else {
		$("#adresaKorisnika").val(podaciAdmina.adresa.punaAdresa);
	}
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

function ucitajSkaluPopusta() {
	$.ajax({
		type: "GET",
		url: "../bonusSkala/dobaviStavke",
		success: function(response) {
			$.each(response, function(i, stavka) {
				prikaziStavku(stavka, false);
			});
		},
	});
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

function izmjenaProfila(){
	
	var imeAdmina = $("#imeKorisnika").val();
	if (imeAdmina == ''){
		swal({
			  title: "Polje za unos imena ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			});
		return;
	}
	var prezimeAdmina = $("#prezimeKorisnika").val();
	if (prezimeAdmina == ''){
		swal({
			  title: "Polje za unos prezimena ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			});
		return;
	}
	var brTelefonaAdmina = $("#brTelefonaKorisnika").val();
	if (brTelefonaAdmina == ''){
		swal({
			  title: "Polje za unos broja telefona ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			});
		return;
	}
	var adresaAdmina = $("#adresaKorisnika").val();
	if (adresaAdmina == ''){
		swal({
			  title: "Polje za unos adrese ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			});
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
		data: JSON.stringify(admin),
		success:function(response){
			swal({
				  title: "Uspješno ste izmjenili profil.",
				  icon: "success",
				  timer: 2500
				});
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
		swal({
			  title: "Niste unijeli novu lozinku.",
			  icon: "warning",
			  timer: 2500
			})
		return;
	}
	
	if (novaLozinka != novaLozinka2){
		swal({
			  title: "Greška. ",
			  text: "Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.",
			  icon: "error",
			  timer: 2500
			});
		return;
	}
	
	$.ajax({
		type : 'PUT',
		url : "../auth/changePassword/" + staraLozinka,
		data : novaLozinka,
		success : function(data) {
			if (data == ''){
				swal({
					  title: "Pogrešna trenutna lozinka.",
					  icon: "error",
					  timer: 2000
					});	
				return;
			}
			else{
				setJwtToken(data.accessToken);
				swal({
					  title: "Uspješno ste izmjenili lozinku.",
					  icon: "success",
					  timer:2000
					});
				if(!lozinkaMijenjana) {
					$("#izmjenaInicijalneLozinkePoruka").hide();
					$("#meni").show();
					prikaziTab("tab-aviokompanije");
					podaciAdmina.lozinkaPromjenjena = true;
				}
			}
			$("#forma_lozinka")[0].reset();
		}
	});
}

ymaps.ready(inicijalizujMape);