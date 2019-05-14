var aviokompanije = [];
var hoteli = [];
var rentACarServisi = [];
var korisnik = null;
//spring.datasource.initialization-mode=always

$(document).ready(function() {	
	
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
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg", "infoStranicaAviokompanije");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg", "infoStranicaHotela");

	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStraanicaRac");
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
	
	$("#racSearchForm").submit(function(e) {
		e.preventDefault();
	   
		let nazivLokacije = $("#racNaziv").val();
		let dolazak = $("#input-start-rac").val();
		let odlazak = $("#input-end-rac").val();
		
		let pretragaRac = {
				nazivRacIliDestinacije: nazivLokacije,
				datumDolaska: dolazak,
				datumOdlaska: odlazak,
		}
		
		$.ajax({
			type: "POST",
			url: "../rentACar/pretrazi",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(pretragaRac),
			success: function(response) {
				let tbody = $("#prikazRacServisa");
				tbody.empty();
				$.each(response, function(i, podatak) {

					prikazi(podatak, tbody, "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStraanicaRac");
				});
				if(response.length == 0) {
					alert("Ne postoji ni jedan rent-a-car servis koji zadovoljava kriterijume pretrage");
				}
				$('#racSearchForm')[0].reset();
			},
		});
		
	});

});


function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz, defaultSlika, infoStranica) {
	let tabela = $("#" + idTabeleZaPrikaz);
	$.ajax({
		type: "GET",
		url: putanjaControlera,
		success: function(response) {
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela, defaultSlika, infoStranica);
			});
		},
	});
}

function prikazi(podatak, tabelaZaPrikaz, defaultSlika, infoStranica) {
	let noviRed = $("<tr></tr>");
	var ocjena = null;
	if (podatak.sumaOcjena == 0){
		ocjena = 0;
	}
	else{
		ocjena = podatak.sumaOcjena/podatak.brojOcjena;
	}
	noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
	noviRed.append('<td class="column1">' + podatak.naziv + '</td>');
	noviRed.append('<td class="column1">' + podatak.adresa.punaAdresa + '</td>');
	noviRed.append('<td class="column1">' + ocjena + '</td>');
	noviRed.append('<td class="column1"><a href="../' + infoStranica+'/index.html?id=' + podatak.id + '">Pogledaj detalje</a></td>');

	tabelaZaPrikaz.append(noviRed);
}

function odjava() {
	removeJwtToken();
	window.location.replace("../pocetnaStranica/index.html");
}