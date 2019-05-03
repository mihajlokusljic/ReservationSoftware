let podaciAdmina = null;
let podaciHotela = null;
let pocetnaStrana = "../pocetnaStranica/index.html";

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

function odjava() {
	removeJwtToken();
	window.location.replace(pocetnaStrana);
}