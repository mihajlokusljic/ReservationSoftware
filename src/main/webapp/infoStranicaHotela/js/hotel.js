let podaciHotela = null;
let defaultSlika = "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg";
let pocetnaStranicaNeregistrovanogKor = "../pocetnaStranica/index.html";
let pocetnaStranicaRegistrovanogKor = "../registrovaniKorisnikPocetna/index.html";
let brzeRezervacije = [];
let zoomLevel = 17;
let idHotela = null;
let idKorisnika = null;
let rezimRezervacije = false;
let datumDolaskaPutovanje = null;
let datumOdlaskaPutovanje = null;
let idPutovanja = null;


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
		  			  timer: 2500
		  			});	    	
	    		}
	    	else {
	    		alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
	    	}
		}
	});
	
	//ocitavanje parametara putanje
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	idHotela = params_parser.get("id");
	
	//inicijalizacijaPodataka
	ucitajPodatkeHotela();
	ymaps.ready(inicijalizujMapu);
	
	//ocitavanje opcionih parametara za rezervaciju
	idKorisnika = params_parser.get("korisnik");
	datumDolaskaPutovanje = params_parser.get("datumDolaska");
	datumOdlaskaPutovanje = params_parser.get("datumOdlaska");
	idPutovanja = params_parser.get("idPutovanja");
	
	if(idHotela == null) {
		swal({
			  title: "Došlo je do greške.",
			  icon: "error",
			  timer: 2500
			}).then(function(){
				redirectNaPocetnu();
			});
		
	}
	
	if(idKorisnika != null && datumDolaskaPutovanje != null && datumOdlaskaPutovanje != null) {
		rezimRezervacije = true;
		prikaziBrzeRezervacijeZaPutovanje();
		$("#rezervacijaSobaKoraci").show();
		$("#izborSobeCol").show();
		//odvodi korisnika na pretragu soba za rezervaciju
		$("#tab-info").removeClass("active");
		$("#stavka-info").removeClass("active");
		$("#tab-sobe").addClass("active");
		$("#stavka-sobe").addClass("active");
	}
	
	//pretraga soba
	$("#pretragaSobaForm").submit(function(e) {
		e.preventDefault();
		pretragaSoba();
	});
	
	//pretraga brzih rezervacija
	$("#pretragaBrzihRezervacijaForm").submit(function(e) {
		e.preventDefault();
		pretragaBrzihRezervacija();
	});
	
	//zatvaranje detaljnog prikaza brze rezervacije
	$("#zatvoriDetaljanPrikazBrzeRezBtn").click(function(e) {
		e.preventDefault();
		$("#detaljanPrikazBrzeRez").hide();
		$("#sveBrzeRezervacije").show();
		$("#prikazUslugaBrzeRezDetalji").empty();
	});
	
	//odjava
	$("#pocetna").click(function(e) {
		e.preventDefault();
		redirectNaPocetnu();
	});
	
	//prikaz koraka za rezervaciju sobe
	$("#izborSobeBtn").click(function(e) {
		if ($("#izborSobeBtn").is(":checked")) {
			$("#pretragaIzborSoba").show();
			$("#izborDodatnihUsluga").hide();
		}
	});
	
	$("#izborDodatnihUslugaBtn").click(function(e) {
		if ($("#izborDodatnihUslugaBtn").is(":checked")) {
			$("#izborDodatnihUsluga").show();
			$("#pretragaIzborSoba").hide();
		}
	});
	
	//izbor soba za rezevaciju i prelazak na izbor dodatnih usluga
	$("#zadajSobeRezervacijeBtn").click(function(e) {
		e.preventDefault();
		$("#izborDodatnihUsluga").show();
		$("#pretragaIzborSoba").hide();
		$("#izborSobeBtn")[0].checked = false;
		$("#izborDodatnihUslugaBtn")[0].checked = true;
	});
	
	//izbor dodatnih usluga i rezervacija smjestaja
	$("#uslugeIRezervacijaBtn").click(function(e) {
		e.preventDefault();
		izvrsiRezervaciju();
	});
});

function izvrsiRezervaciju() {
	let sobeIds = [];
	let uslugeIds = [];
	let idSobe = null;
	let idUsluge = null;
	
	let sobeBtns = $(".rezervacijaSobe");
	$.each(sobeBtns, function(i, btn) {
		if(btn.checked) {
			idSobe = btn.id.substring(2); //id dugmeta je tipa "rs<id sobe>"
			sobeIds.push(idSobe);
		}
	});
	if(sobeIds.length == 0) {
		swal({
			  title: "Morate izabrati bar jednu sobu.",
			  icon: "warning",
			  timer: 2500
			});
		return;
	}
	
	let uslugeBtns = $(".rezervacijaUsluge");
	$.each(uslugeBtns, function(i, btn) {
		if(btn.checked) {
			idUsluge = btn.id.substring(2); //id dugmeta je tipa "ru<id usluge>"
			uslugeIds.push(idUsluge);
		}
	});
	
	let _datumDolaska = $("#input-start").val();
	let _datumOdlaska = $("#input-end").val();
	
	if(_datumDolaska == "" || _datumOdlaska == "") {
		swal({
			  title: "Period boravka mora biti zadat.",
			  icon: "warning",
			  timer: 2500
			});
		return;
	}
	
	let rezervacija = {
			sobeZaRezervacijuIds: sobeIds,
			dodatneUslugeIds: uslugeIds,
			putovanjeId: idPutovanja,
			datumDolaska: _datumDolaska,
			datumOdlaksa: _datumOdlaska
	};
	
	$.ajax({
		type: "POST",
		url: "../rezervacijeSoba/izvrsiRezervaciju",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(rezervacija),
		success: function(response) {
			if (response == ''){
				
				swal({
					  title: "Uspješno ste rezervisali hotelski smještaj.",
					  icon: "success",
					  timer: 2500
					}).then(function(){
						redirectNaPocetnu();
					});
			}
			
			else{
				swal({
					  title: response,
					  icon: "error",
					  timer: 2500
					}).then(function(){
						redirectNaPocetnu();
					});
			}
		},
	});
	
	
}

function ucitajPodatkeHotela() {
	$.ajax({
		type: "GET",
		url: "../hoteli/dobavi/" + idHotela,
		async: false,
		success: function(response) {
			podaciHotela = response;
			prikaziPodatkeHotela();
		},
	});

}

function prikaziBrzeRezervacijeZaPutovanje() {
	$("#input-start-1").val(datumDolaskaPutovanje);
	$("#input-end-1").val(datumOdlaskaPutovanje);
	
	let kriterijumiPretrage = {
			datumDolaska: datumDolaskaPutovanje,
			datumOdlaska: datumOdlaskaPutovanje,
			idHotela: podaciHotela.id
	};
	
	$.ajax({
		type: "POST",
		url: "../rezervacijeSoba/pretragaBrzihRezervacijaSoba",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(kriterijumiPretrage),
		success: function(response) {
			brzeRezervacije = response;
			prikaziBrzeRezervacije(response);
		},
	});
	
}

function prikaziPodatkeHotela() {
	$("#slikaHotela").attr("src", defaultSlika);
	$("#title").html(podaciHotela.naziv);
	$("#hotelNaslov").html(podaciHotela.naziv);
	$("#nazivHotela").val(podaciHotela.naziv);
	$("#adresaHotela").val(podaciHotela.adresa.punaAdresa);
	let sumaOcjena = podaciHotela.sumaOcjena;
	sumaOcjena = parseFloat(sumaOcjena);
	let brOcjena = podaciHotela.brojOcjena;
	brOcjena = parseInt(brOcjena);
	if(brOcjena > 0) {
		$("#ocjenaHotela").val((sumaOcjena / brOcjena).toFixed(2));
	} else {
		$("#ocjenaHotela").val("Nema ocjena");
	}
	$("#opisHotela").val(podaciHotela.promotivniOpis);
	
	let tabelaUsluga = $("#prikazUsluga");
	$.each(podaciHotela.cjenovnikDodatnihUsluga, function(i, usluga) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
		noviRed.append('<td class="column1">' + usluga.opis + '</td>');
		noviRed.append('<td class="column1">' + usluga.cijena + '</td>');
		noviRed.append('<td class="column1">' + usluga.nacinPlacanja + '</td>');
		tabelaUsluga.append(noviRed);
	});
	
	let tabelaIzborUsluga = $("#izborUslugaRezervacija");
	$.each(podaciHotela.cjenovnikDodatnihUsluga, function(i, usluga) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
		noviRed.append('<td class="column1">' + usluga.cijena + '</td>');
		noviRed.append('<td class="column1">' + usluga.procenatPopusta + ' %</td>');
		noviRed.append('<td class="column1">' + usluga.nacinPlacanja + '</td>');
		noviRed.append('<td class="column6"><input type="checkbox" class="rezervacijaUsluge" id="ru' + usluga.id + '"/></td>');
		tabelaIzborUsluga.append(noviRed);
	});
}

function pretragaSoba() {
	let _datumDolaska = $("#input-start").val();
	let _datumOdlaska = $("#input-end").val();
	let _minCijena = $("#minCijenaBoravka").val();
	let _maxCijena = $("#maxCijenaBoravka").val();
	
	if(_minCijena == "") {
		_minCijena = null;
	}
	if(_maxCijena == "") {
		_maxCijena = null;
	}
	
	if(_minCijena != null && _maxCijena != null) {
		if(_minCijena > _maxCijena) {
			swal({
				  title: "Minimalna cijena ne smije biti veća od maksimalne cijene.",
				  icon: "warning",
				  timer: 2500
				});
			return;
		}
	}
	
	
	let pretragaSobaHotela = {
			idHotela: podaciHotela.id,
			datumDolaska: _datumDolaska,
			datumOdlaska: _datumOdlaska,
			minCijenaBoravka: _minCijena,
			maxCijenaBoravka: _maxCijena
	};
	
	$.ajax({
		type: "POST",
		url: "../hotelskeSobe/pretrazi",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(pretragaSobaHotela),
		success: function(response) {
			prikaziSobe(response);
			if(response.length == 0) {
				swal({
					  title: "Ne postoji ni jedna slobodna soba za dati vremenski period.",
					  icon: "info",
					  timer: 2500
					});
			}
		},
	});
	
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

function pretragaBrzihRezervacija() {
	let _datumDolaska = $("#input-start-1").val();
	let _datumOdlaska = $("#input-end-1").val();
	
	let kriterijumiPretrage = {
			datumDolaska: _datumDolaska,
			datumOdlaska: _datumOdlaska,
			idHotela: podaciHotela.id
	};
	
	$.ajax({
		type: "POST",
		url: "../rezervacijeSoba/pretragaBrzihRezervacijaSoba",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(kriterijumiPretrage),
		success: function(response) {
			brzeRezervacije = response;
			prikaziBrzeRezervacije(response);
			if(response.length == 0) {
				swal({
					  title: "Ne postoji ni jedna slobodna soba za dati vremenski period.",
					  icon: "info",
					  timer: 2500
					});
			}
		},
	});
}

function prikaziBrzeRezervacije(brzeRezervacije) {
	let brzeRezRows = $("#prikazBrzihRezervacija");
	brzeRezRows.empty();
	$.each(brzeRezervacije, function(i, rezervacija) {
		let noviRed = $('<tr id="brzaRez' + rezervacija.id + '"></tr>');
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
			noviRed.append('<td class="column1">' + (soba.sumaOcjena / soba.brojOcjena).toFixed(2) + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column6"><a href="#" class="brzaRezervacijaDetalji" id="brd' + rezervacija.id + '">Detalji</a></td>');
		noviRed.append('<td class="column6"></td>');
		if(rezimRezervacije) {
			noviRed.append('<td class="column6"><a href="#" class="brzaRezervacija" id="br' + rezervacija.id + '">Rezerviši</a></td>');
		} else {
			noviRed.append('<td class="column6"> </td>');
		}
		brzeRezRows.append(noviRed);
	});
	$("#tabelaBrzeRezervacije").show();
	
	$(".brzaRezervacijaDetalji").click(function(e) {
		e.preventDefault();
		let idRez = e.target.id.substring(3); //id je oblika "brd<id brze rezervacije>"
		detaljanPrikazBrzeRez(idRez);
	});
	
	$(".brzaRezervacija").click(function(e) {
		e.preventDefault();
		let idRez = e.target.id.substring(2); //id je oblika "brd<id brze rezervacije>"
		rezervisanjeBrzeRezervacije(idRez);
	});
}

function rezervisanjeBrzeRezervacije(idRez) {
	let podaciRez = {
			idBrzeRezervacije: idRez,
			idPutovanja: idPutovanja
	};
	$.ajax({
		type: "POST",
		url: "../rezervacijeSoba/izvrsiBrzuRezervaciju",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(podaciRez),
		success: function(response) {
			$("#brzaRez" + idRez).remove();
		if (response == ''){
				
				swal({
					  title: "Uspješno ste rezervisali hotelski smještaj.",
					  icon: "success",
					  timer: 2500
					});
			}
			
			else{
				swal({
					  title: response,
					  icon: "error",
					  timer: 2500
					});
			}
		},
	});
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
		let ocjena = (soba.sumaOcjena / soba.brojOcjena).toFixed(2);
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

function prikaziSobe(sobe) {
	$("#tabelaSobe").show();
	let prikaz = $("#prikazSoba");
	prikaz.empty();
	
	$.each(sobe, function(i, soba) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + soba.brojSobe + '</td>');
		noviRed.append('<td class="column1">' + soba.brojKreveta + '</td>');
		noviRed.append('<td class="column1">' + soba.cijenaBoravka + '</td>');
		noviRed.append('<td class="column1">' + soba.sprat + '</td>');
		noviRed.append('<td class="column1">' + soba.vrsta + '</td>');
		noviRed.append('<td class="column1">' + soba.kolona + '</td>');
		let sumaOcjena = soba.sumaOcjena;
		sumaOcjena = parseFloat(sumaOcjena);
		let brOcjena = soba.brojOcjena;
		brOcjena = parseInt(brOcjena);
		if(brOcjena > 0) {
			noviRed.append('<td class="column1">' + (sumaOcjena / brOcjena).toFixed(2) + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		if(rezimRezervacije) {
			noviRed.append('<td class="column6"><input type="checkbox" class="rezervacijaSobe" id="rs' + soba.id + '"/></td>');
		}
		prikaz.append(noviRed);
	});
	
	if(rezimRezervacije) {
		$("#zadajSobeRezervacijeBtn").show();
	}
}

function redirectNaPocetnu() {
	if(idKorisnika == null) {
		window.location.replace(pocetnaStranicaNeregistrovanogKor);
	} else {
		window.location.replace(pocetnaStranicaRegistrovanogKor);
	}
}

function inicijalizujMapu() {
	var coords = [podaciHotela.adresa.latituda, podaciHotela.adresa.longituda];
	var myPlacemark = new ymaps.Placemark(coords, {balloonContentBody: podaciHotela.naziv});
	var myMap = new ymaps.Map('mapa', {
        center: coords,
        zoom: zoomLevel,
        controls: []
    });
	myMap.geoObjects.add(myPlacemark);
	myMap.controls.add('typeSelector');
	myMap.controls.add('zoomControl');
}

