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
	    		alert(XMLHttpRequest.responseText);
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
		alert("Doslo je do greske.");
		redirectNaPocetnu();
	}
	
	if(idKorisnika != null) {
		rezimRezervacije = true;
	}
	
	if(datumDolaskaPutovanje != null & datumOdlaskaPutovanje != null) {
		rezimRezervacije = true;
		prikaziBrzeRezervacijeZaPutovanje();
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
});

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
		$("#ocjenaHotela").val(sumaOcjena / brOcjena);
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
	
	
	let pretragaSobaHotela = {
			idHotela: podaciHotela.id,
			datumDolaska: _datumDolaska,
			datumOdlaska: _datumOdlaska,
			minCijenaBoravka: _minCijena,
			maxCijenaBoravka: _maxCijena
	}
	
	$.ajax({
		type: "POST",
		url: "../hotelskeSobe/pretrazi",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(pretragaSobaHotela),
		success: function(response) {
			prikaziSobe(response);
			if(response.length == 0) {
				alert("Ne postoji ni jedna slobodna soba za dati vremenski period.");
			}
		},
	});
	
}

function nadjiBrzuRezervaciju(id) {
	let brzaRez = null;
	for(i in brzeRezervacije) {
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
				alert("Ne postoji ni jedna soba na popustu za dati vremenski period.");
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
			noviRed.append('<td class="column1">' + soba.sumaOcjena / soba.brojOcjena + '</td>');
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
			alert(response);
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
			noviRed.append('<td class="column1">' + sumaOcjena / brOcjena + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		prikaz.append(noviRed);
	})
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

