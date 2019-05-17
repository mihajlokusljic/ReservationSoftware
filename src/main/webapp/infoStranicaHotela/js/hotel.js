let podaciHotela = null;
let defaultSlika = "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg";
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
	
	
	ucitajPodatkeHotela();
	ymaps.ready(inicijalizujMapu);
	
	$("#pretragaSobaForm").submit(function(e) {
		e.preventDefault();
		pretragaSoba();
	})
});

function ucitajPodatkeHotela() {
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	var id = params_parser.get("id");
	
	$.ajax({
		type: "GET",
		url: "../hoteli/dobavi/" + id,
		async: false,
		success: function(response) {
			podaciHotela = response;
			prikaziPodatkeHotela();
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
	
	let pretragaSobaHotela = {
			idHotela: podaciHotela.id,
			datumDolaska: _datumDolaska,
			datumOdlaska: _datumOdlaska,
	}
	
	$.ajax({
		type: "POST",
		url: "../hotelskeSobe/pretrazi",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(pretragaSobaHotela),
		success: function(response) {
			if(response.length == 0) {
				alert("Ne postoji ni jedna slobodna soba za dati vremenski period.");
				return;
			}
			prikaziSobe(response);
		},
	});
	
}

function prikaziSobe(sobe) {
	$("#tabelaSobe").show();
	let prikaz = $("#prikazSoba");
	prikaz.empty();
	
	$.each(sobe, function(i, soba) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + soba.brojSobe + '</td>');
		noviRed.append('<td class="column1">' + soba.brojKreveta + '</td>');
		noviRed.append('<td class="column1">' + soba.cijena + '</td>');
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

