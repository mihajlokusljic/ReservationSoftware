let podaciAdmina = null;
let podaciHotela = null;
let tekucaBrzaRezervacija = null;
let brzeRezervacije = [];
let pocetnaStrana = "../pocetnaStranica/index.html";
let defaultSlika = "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg";
let stavkeMenija = ["stavkaUredjivanjeHotela", "stavkaBrzeRezervacije", "stavkaIzvjestaji", "stavkaProfilKorisnika"];
let tabovi = ["tab-sobe", "tab-dodatne-usluge", "tab-info-stranica", "tab-brze-rezervacije-dodavanje",
	"tab-brze-rezervacije-pregledanje", "tab-izvjestaji", "tab-profil-kor", "tab-profil-lozinka"];
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
	
	$("#dodavanjeBrzeRezervacije").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaBrzeRezervacije");
		prikaziTab("tab-brze-rezervacije-dodavanje");
	});
	
	$("#pregledanjeBrzihRezervacija").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaBrzeRezervacije");
		prikaziTab("tab-brze-rezervacije-pregledanje");
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
	
	//prikaz koraka za dodavanje brze rezervacije
	$("#izborSobeBrzeRezervacijeBtn").click(function(e) {
		if ($("#izborSobeBrzeRezervacijeBtn").is(":checked")) {
			$("#izborSobeBrzeRezervacije").show();
			$("#izborDodatnihUslugaBrzeRezervacije").hide();
			$("#definisanjePopustaBrzeRezervacije").hide();
		}
	});
	
	$("#izborDodatnihUslugaBrzeRezervacijeBtn").click(function(e) {
		if ($("#izborDodatnihUslugaBrzeRezervacijeBtn").is(":checked")) {
			$("#izborDodatnihUslugaBrzeRezervacije").show();
			$("#izborSobeBrzeRezervacije").hide();
			$("#definisanjePopustaBrzeRezervacije").hide();
		}
	});
	
	$("#definisanjePopustaBrzeRezervacijeBtn").click(function(e) {
		if ($("#definisanjePopustaBrzeRezervacijeBtn").is(":checked")) {
			$("#definisanjePopustaBrzeRezervacije").show();
			$("#izborSobeBrzeRezervacije").hide();
			$("#izborDodatnihUslugaBrzeRezervacije").hide();
		}
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
	
	//pretraga soba za brzu rezervaciju
	$("#pretragaSobaForm").submit(function(e) {
		e.preventDefault();
		pretraziSobeZaSlobodnuRezervaciju();
	});
	
	//zadavanje sobe i prelazak na zadavanje usluga brze rezervacije
	$("#zadajSobuBrzeRezervacijeBtn").click(function (e) {
		e.preventDefault();
		zadajSobuBrzeRez();
	});
	
	//zadavanje dodatnih usluga i prelazak na zadavanje popusta
	$("#zadajDodatneUslugeBrzeRezervacijeBtn").click(function (e) {
		e.preventDefault();
		zadajDodatneUsluge();
	});
	
	//izmjena procenta popusta kod brzih rezervacija
	$("#procenatPopustaBrzeRezervacije").change(function(e) {
		e.preventDefault();
		azurirajCijeneBrzeRezervacije();
	});
	
	//zadavanje procenta popusta kod brzih rezervacija
	$("#zadavanjePopustaBrzeRezervacijeBtn").click(function(e) {
		e.preventDefault();
		zadavanjePopustaBrzeRezervacije();
	});
	
	//zatvaranje detaljnog prikaza brze rezervacije
	$("#zatvoriDetaljanPrikazBrzeRezBtn").click(function(e) {
		e.preventDefault();
		$("#detaljanPrikazBrzeRez").hide();
		$("#sveBrzeRezervacije").show();
		$("#prikazUslugaBrzeRezDetalji").empty();
	});
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
		
	
});

function prikaziTab(idTaba) {
	for(var i in tabovi) {
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
			
			for(var i in podaciHotela.sobe) {
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
		swal({
			  title: "Naziv aviokompanije mora biti zadat.",
			  icon: "warning",
			  timer: 2500
			})
		return;
	}
	
	if(_lat == "" || _long == "") {
		swal({
			  title: "Morate zadati lokaciju na mapi (kliknite na link u polju adresa).",
			  icon: "warning",
			  timer: 2500
			})
		return;
	}
	
	if(_adresa == "") {
		swal({
			  title: "Adresa aviokompanije mora biti zadata.",
			  icon: "warning",
			  timer: 2500
			})
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
			swal({
				  title: "Informacije hotela su uspjesno izmjenjene.",
				  icon: "success",
				  timer: 2500
				})
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
				swal({
					  title: "Nepostojeći korisnik.",
					  icon: "error",
					  timer: 2500
					})
			}
		},
	});
}

function izmjenaInicijalneLozinke() {
	$("#meni").hide();
	$("#izmjenaInicijalneLozinkePoruka").show();
	prikaziTab("tab-profil-lozinka");
}

function prikaziBrzuRezervaciju(tabela, rezervacija) {
	let noviRed = $('<tr></tr>');
	noviRed.append('<td class="column1">' + rezervacija.datumDolaska + '</td>');
	noviRed.append('<td class="column1">' + rezervacija.datumOdlaska + '</td>');
	noviRed.append('<td class="column1">' + rezervacija.baznaCijena + '</td>');
	noviRed.append('<td class="column1">' + rezervacija.procenatPopusta + '%</td>');
	let popust = rezervacija.baznaCijena * rezervacija.procenatPopusta / 100.0;
	let cijenaSaPopustom = rezervacija.baznaCijena - popust;
	noviRed.append('<td class="column1">' + cijenaSaPopustom + '</td>');
	let soba = rezervacija.sobaZaRezervaciju;
	noviRed.append('<td class="column1">' + soba.brojKreveta + '</td>');
	if(soba.brojOcjena > 0) {
		noviRed.append('<td class="column1">' + soba.sumaOcjena / soba.brojOcjena + '</td>');
	} else {
		noviRed.append('<td class="column1">Nema ocjena</td>');
	}
	noviRed.append('<td class="column6"><a href="#" class="brzaRezervacijaDetalji" id="br' + rezervacija.id + '">Detalji</a></td>');
	tabela.append(noviRed);
}

function nadjiBrzuRezervaciju(id) {
	let brzaRez = null;
	for(var i in brzeRezervacije) {
		brzaRez = brzeRezervacije[i];
		if(brzaRez.id == id) {
			return brzaRez;
		}
	}
	return null;
}

function detaljanPrikazBrzeRez(idRez) {
	let brzaRez = nadjiBrzuRezervaciju(idRez);
	if(brzaRez == null) {
		return;
	}
	$("#datumDolaskaBrzaRez").val(brzaRez.datumDolaska);
	$("#datumOdlaskaBrzaRez").val(brzaRez.datumOdlaska);
	let soba = brzaRez.sobaZaRezervaciju;
	$("#brojSobeBrzaRez").val(soba.brojSobe);
	if(soba.brojOcjena > 0) {
		let ocjena = soba.sumaOcjena / soba.brojOcjena;
		$("#ocjenaSobeBrzaRez").val(ocjena);
	} else {
		$("#ocjenaSobeBrzaRez").val("Nema ocjena");
	}
	$("#spratSobeBrzaRez").val(soba.sprat);
	$("#brKrevetaSobeBrzaRez").val(soba.brojKreveta);
	$("#pocetnaCijenaBrzaRez").val(brzaRez.baznaCijena);
	let popust = brzaRez.baznaCijena * brzaRez.procenatPopusta / 100.0;
	let cijenaSaPopustom = brzaRez.baznaCijena - popust;
	$("#cijenaSaPopsutomBrzaRez").val(cijenaSaPopustom);
	let tabelaDodatneUsluge = $("#prikazUslugaBrzeRezDetalji");
	let noviRed = null;
	$.each(brzaRez.dodatneUsluge, function(i, usluga) {
		noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
		noviRed.append('<td class="column6">' + usluga.cijena + '</td>');
		noviRed.append('<td class="column1">' + usluga.nacinPlacanja + '</td>');
		noviRed.append('<td class="column1">' + usluga.opis + '</td>');
		tabelaDodatneUsluge.append(noviRed);
	});
	
	$("#sveBrzeRezervacije").hide();
	$("#detaljanPrikazBrzeRez").show();
}

function prikaziBrzeRezervacije(rezervacije) {
	let tabela = $("#prikazBrzihRezervacija");
	tabela.empty();
	$.each(rezervacije, function(i, brzaRezervacija) {
		prikaziBrzuRezervaciju(tabela, brzaRezervacija);
	});
	
	$(".brzaRezervacijaDetalji").click(function(e) {
		e.preventDefault();
		let idRez = e.target.id.substring(2);
		detaljanPrikazBrzeRez(idRez);
	});
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
	
	//ucitavanje brzih rezervaicija hotela
	$.ajax({
		type: "GET",
		url: "../rezervacijeSoba/sveBrzeRezervacijeHotela/" + podaciHotela.id,
		async: false,
		success: function(response) {
			if(response != null) {
				brzeRezervacije = response;
				prikaziBrzeRezervacije(response);
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
	if(soba.brojOcjena > 0) {
		noviRed.append('<td class="column1">' + (soba.sumaOcjena / soba.brojOcjena).toFixed(2) + '</td>');
	} else {
		noviRed.append('<td class="column1">Nema ocjena</td>');
	}
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
		swal({
			  title: "Došlo je do greške pri izmjeni sobe.",
			  icon: "error",
			  timer: 2500
			})
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

function nadjiSobu(idSobe) {
	for(var indeksSobe in podaciHotela.sobe) {
		soba = podaciHotela.sobe[indeksSobe];
		if(soba.id == idSobe) {
			return soba;
		}
	}
	return null;
}

function brisanjeSobe(idSobe) {
	let indeksSobe = 0;
	let soba = nadjiSobu(idSobe);
	if(soba == null) {
		swal({
			  title: "Došlo je do greške pri brisanju sobe.",
			  icon: "error",
			  timer: 2500
			})		
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
			swal({
				  title: response,
				  icon: "success",
				  timer: 2500
				})	
			//uklanjanje sobe iz podataka hotela i osvjezavanje prikaza soba
			for(var i in podaciHotela.sobe) {
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
		swal({
			  title: "Polje za unos imena ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			})	
		return;
	}
	var prezimeAdmina = $("#prezimeAdmina").val();
	if (prezimeAdmina == ''){
		swal({
			  title: "Polje za unos prezimena ne može biti prazno.",
			  icon: "warning",
			  timer: 2500
			})
		return;
	}
	var brTelefonaAdmina = $("#brTelefonaAdmina").val();
	if (brTelefonaAdmina == ''){
		swal({
			  title: "Polje za unos broja telefona ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			})
		return;
	}
	var adresaAdmina = $("#adresaAdmina").val();
	if (adresaAdmina == ''){
		swal({
			  title: "Polje za unos adrese ne moze biti prazno.",
			  icon: "warning",
			  timer: 2500
			})
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
			swal({
				  title: "Uspješno ste izmjenili profil.",
				  icon: "success",
				  timer: 2500
				})
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
	
	let uslugeBrzeRezervacijeTabela = $("#prikazUslugaBrzeRezervacije");
	noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
	noviRed.append('<td class="column6">' + usluga.cijena + '</td>');
	noviRed.append('<td class="column1">' + usluga.nacinPlacanja + '</td>');
	noviRed.append('<td class="column5"><input type="checkbox" class="uslugaBrzaRezBtn" id="ubr' + usluga.id + '"/></td>');
	uslugeBrzeRezervacijeTabela.append(noviRed);

}

function prikaziPodatkeHotela() {
	$("#nazivHotela").val(podaciHotela.naziv);
	$("#adresaHotela").val(podaciHotela.adresa.punaAdresa);
	$("#latitudaHotela").val(podaciHotela.adresa.latituda);
	$("#longitudaHotela").val(podaciHotela.adresa.longituda);
	$("#opisHotela").val(podaciHotela.promotivniOpis);
	$("#slikaHotela").attr("src", defaultSlika);
	
	let sumaOcjena = podaciHotela.sumaOcjena;
	sumaOcjena = parseFloat(sumaOcjena);
	let brojOcjena = podaciHotela.brojOcjena;
	brojOcjena = parseInt(brojOcjena);
	if(brojOcjena > 0) {
		var prosjek = sumaOcjena/brojOcjena;
		prosjek = prosjek.toFixed(2);

		$("#ocjenaHotela").val(prosjek);
	} else {
		$("#ocjenaHotela").val("Nema ocjena");
	}
}

function prikaziPodatkeAdmina() {
	$("#emailAdmina").val(podaciAdmina.email);
	$("#imeAdmina").val(podaciAdmina.ime);
	$("#prezimeAdmina").val(podaciAdmina.prezime);
	$("#brTelefonaAdmina").val(podaciAdmina.brojTelefona);
	$("#adresaAdmina").val(podaciAdmina.adresa.punaAdresa);
}

function prikaziSobeZaBrzuRezervaciju(sobe) {
	let tabela = $("#prikazSobaBrzeRezervacije");
	let sumaOcjena = 0;
	let brojOcjena = 0;
	tabela.empty();
	
	$.each(sobe, function(i, soba) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + soba.brojSobe + '</td>');
		noviRed.append('<td class="column1">' + soba.brojKreveta + '</td>');
		noviRed.append('<td class="column1">' + soba.cijenaBoravka + '</td>');
		noviRed.append('<td class="column1">' + soba.sprat + '</td>');
		noviRed.append('<td class="column1">' + soba.vrsta + '</td>');
		noviRed.append('<td class="column1">' + soba.kolona + '</td>');
		if(soba.brojOcjena > 0) {
			noviRed.append('<td class="column1">' + soba.sumaOcjena / soba.brojOcjena + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column1"><input type="radio" name="sobaBrzaRez" class="sobaBrzaRez" id="sbr' + soba.id + '"></td>');
		tabela.append(noviRed);
	});
	
}

function pretraziSobeZaSlobodnuRezervaciju() {
	let _datumDolaska = $("#input-start").val();
	let _datumOdlaska = $("#input-end").val();
	
	let pretragaSoba = {
		datumDolaska: _datumDolaska,
		datumOdlaska: _datumOdlaska,
		idHotela: podaciHotela.id
	};
	
	$.ajax({
		type : 'POST',
		url : "../hotelskeSobe/pretrazi",
		data : JSON.stringify(pretragaSoba),
		success : function(sobe) {
			if(sobe.length > 0) {
				$("#sobeBrzeRezervacije").show();
				prikaziSobeZaBrzuRezervaciju(sobe);
			} else {
				swal({
					  title: "Nema slobodnih soba u zadatom periodu.",
					  icon: "info",
					  timer: 2000
					});	
			}
		}
	});
}

function zadajSobuBrzeRez() {
	let _datumDolaska = $("#input-start").val();
	let _datumOdlaska = $("#input-end").val();
	let _idSobe = -1;
	let sobaIzabrana = false;
	
	let brzeSobeRezBtns = $(".sobaBrzaRez");
	$.each(brzeSobeRezBtns, function(i, btn) {
		if(btn.checked) {
			sobaIzabrana = true;
			_idSobe = btn.id.substring(3); //ime dugmeta je tipa "sbr<id sobe>"
			let soba = nadjiSobu(_idSobe);
			let brzaRezervacija = {
				idSobe: _idSobe,
				datumDolaska: _datumDolaska,
				datumOdlaska: _datumOdlaska,
				cijenaBoravka: 0,
				procenatPopusta: 0,
				dodatneUslugeIds: []
			};
			
			$.ajax({
				type : 'POST',
				url : "../rezervacijeSoba/dodajBrzuRezervaciju",
				data : JSON.stringify(brzaRezervacija),
				success : function(responseBrzaRez) {
					tekucaBrzaRezervacija = responseBrzaRez;
					swal({
						  title:"Soba broj " + soba.brojSobe + " je uspješno dodata na brzu rezervaciju.",
						  icon: "success",
						  timer: 2000
						});	
					$("#izborDodatnihUslugaBrzeRezervacije").show();
					$("#izborSobeBrzeRezervacije").hide();
					$("#definisanjePopustaBrzeRezervacije").hide();
					$("#izborSobeBrzeRezervacijeBtn")[0].checked = false;
					$("#izborDodatnihUslugaBrzeRezervacijeBtn")[0].checked = true;
					$("#definisanjePopustaBrzeRezervacijeBtn")[0].checked = false;
				}
			});
			return;
		}
	});
	if(!sobaIzabrana) {
		swal({
			  title:"Morate izabrati sobu za brzu rezervaciju",
			  icon: "warning",
			  timer: 2000
			});			
	}
}

function zadajDodatneUsluge() {
	if(tekucaBrzaRezervacija == null) {
		swal({
			  title:"Morate zadati sobu za brzu rezervaciju u koraku 1",
			  icon: "warning",
			  timer: 2000
			});	
		return;
	}
	let uslugeIzborBtns = $(".uslugaBrzaRezBtn");
	let uslugaId = -1;
	tekucaBrzaRezervacija.dodatneUslugeIds = [];
	$.each(uslugeIzborBtns, function(i, btn) {
		if(btn.checked) {
			uslugaId = btn.id.substring(3);
			tekucaBrzaRezervacija.dodatneUslugeIds.push(uslugaId);
		}
	});
	//slanje zahtjeva za dodavanje dodatnih usluga
	$.ajax({
		type : 'POST',
		url : "../rezervacijeSoba/dodajUslugeBrzojRezervaciji",
		data : JSON.stringify(tekucaBrzaRezervacija),
		success : function(responseBrzaRez) {
			tekucaBrzaRezervacija = responseBrzaRez;
			swal({
				  title:"Usješno ste definisali dodatne usluge za brzu rezervaciju.",
				  icon: "success",
				  timer: 2000
				});	
			$("#ukupnaCijenaBezPopustaBrzeRezervacije").val(tekucaBrzaRezervacija.cijenaBoravka);
			$("#ukupnaCijenaSaPopustomBrzeRezervacije").val(tekucaBrzaRezervacija.cijenaBoravka);
			$("#definisanjePopustaBrzeRezervacije").show();
			$("#izborSobeBrzeRezervacije").hide();
			$("#izborDodatnihUslugaBrzeRezervacije").hide();
			$("#izborSobeBrzeRezervacijeBtn")[0].checked = false;
			$("#izborDodatnihUslugaBrzeRezervacijeBtn")[0].checked = false;
			$("#definisanjePopustaBrzeRezervacijeBtn")[0].checked = true;
		}
	});
}

function azurirajCijeneBrzeRezervacije() {
	if(tekucaBrzaRezervacija == null) {
		return;
	}
	let procenatPopusta = $("#procenatPopustaBrzeRezervacije").val();
	procenatPopusta = parseFloat(procenatPopusta);
	if(isNaN(procenatPopusta)) {
		return;
	}
	if(procenatPopusta < 0 || procenatPopusta > 100) {
		return;
	}
	let popust = tekucaBrzaRezervacija.cijenaBoravka * procenatPopusta / 100;
	let cijenaSaPopustom = tekucaBrzaRezervacija.cijenaBoravka - popust;
	$("#ukupnaCijenaSaPopustomBrzeRezervacije").val(cijenaSaPopustom);
}

function resetBrzeRezervacijeView() {
	$("#detaljanPrikazBrzeRez").hide();
	$("#sveBrzeRezervacije").show();
	$("#prikazUslugaBrzeRezDetalji").empty();
	$("#izborSobeBrzeRezervacije").show();
	$("#izborDodatnihUslugaBrzeRezervacije").hide();
	$("#definisanjePopustaBrzeRezervacije").hide();
	$("#izborSobeBrzeRezervacijeBtn")[0].checked = true;
	$("#izborDodatnihUslugaBrzeRezervacijeBtn")[0].checked = false;
	$("#definisanjePopustaBrzeRezervacijeBtn")[0].checked = false;
	$("#sobeBrzeRezervacije").hide();
	$("#prikazSobaBrzeRezervacije").empty();
	$("#pretragaSobaForm")[0].reset();
	let uslugeIzborBtns = $(".uslugaBrzaRezBtn");
	$.each(uslugeIzborBtns, function(i, btn) {
		btn.checked = false;
	});
	$("#ukupnaCijenaBezPopustaBrzeRezervacije").val(0);
	$("#procenatPopustaBrzeRezervacije").val(0);
	$("#ukupnaCijenaSaPopustomBrzeRezervacije").val(0);
	prikaziTab("tab-brze-rezervacije-pregledanje");
}

function zadavanjePopustaBrzeRezervacije() {
	if(tekucaBrzaRezervacija == null) {
		swal({
			  title:"Morate izabrati sobu i dodatne usluge.",
			  icon: "warning",
			  timer: 2000
			});	
	}
	let popustProc = $("#procenatPopustaBrzeRezervacije").val();
	tekucaBrzaRezervacija.procenatPopusta = popustProc;
	$.ajax({
		type : 'POST',
		url : "../rezervacijeSoba/zadajPopustBrzojRezervaciji",
		data : JSON.stringify(tekucaBrzaRezervacija),
		success : function(responseBrzaRez) {
			tekucaBrzaRezervacija = null;
			brzeRezervacije.push(responseBrzaRez);
			prikaziBrzeRezervacije(brzeRezervacije);
			swal({
				  title:"Usješno ste definisali popust za brzu rezervaciju.",
				  icon: "success",
				  timer: 2000
				}).then(function(){
					resetBrzeRezervacijeView();			
				})	
		}
	});
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
