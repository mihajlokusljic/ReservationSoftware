let podaciAviokompanije = null;
let defaultSlika = "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg";
let defaultSlikaDestinacije = "https://static1.squarespace.com/static/57b9b98a29687f1ef5c622df/t/5b78746dc2241b4f57afadf6/1534620788911/zurich+best+view?format=1500w";
let defaultSlikaAviona = "https://upload.wikimedia.org/wikipedia/commons/4/40/Pan_Am_Boeing_747-121_N732PA_Bidini.jpg";
let stavkeMenija = ["info", "pregledaj", "brzeRezervacije"];
let tabovi = ["tab-info", "tab-destinacije", "tab-avioni", "tab-letovi", "tab-prtljag", "tab-brze-rezervacije"];


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
	
	$("#info").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("info");
		prikaziTab("tab-info");
	});
	
	$("#destinacije").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("pregledaj");
		prikaziTab("tab-destinacije");
	});
	
	$("#avioni").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("pregledaj");
		prikaziTab("tab-avioni");
	});
	
	$("#letovi").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("pregledaj");
		prikaziTab("tab-letovi");
	});
	
	$("#prtljag").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("pregledaj");
		prikaziTab("tab-prtljag");
	});
	
	$("#brzeRezervacije").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("brzeRezervacije");
		prikaziTab("tab-brze-rezervacije");
	});
	
	$("#pretragaLetovaForm").submit(function(e) {
		e.preventDefault();
		pretragaLetova();
	});
	
	ucitajPodatkeAviokompanije();
});

function pretragaLetova() {
	let cijenaK = $("#cijenaKarte").val();
	if (cijenaK == "") {
		cijenaK = 0;
	}
	
	let parametriPretrage = {
			brojLeta : $("#brojLeta").val(),
			nazivAviokompanije : podaciAviokompanije.naziv,
			nazivPolazista : $("#nazivPolazista").val(),
			nazivOdredista : $("#nazivOdredista").val(),
			datumPoletanja : $("#input-start").val(),
			datumSletanja : $("#input-end").val(),
			duzinaPutovanja : "",
			cijenaKarte : cijenaK
	};
	
	  $.ajax({
		    type : "POST",
		    url : "../letovi/pretraziLetove",
		    contentType: "application/json; charset=utf-8",
		    data : JSON.stringify(parametriPretrage),
		    success : function(response) {
		      if (response == undefined) {
		        alert("Došlo je do greške.");
		      } else {
		        if (response.length == 0) {
		          alert("Ne postoji ni jedan let koji zadovoljava kriterijume pretrage.");
		        }
		        prikaziLetove(response);
		      }
		    }	
		  });
}

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

function ucitajPodatkeAviokompanije() {
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	var id = params_parser.get("id");
	
	$.ajax({
		type: "GET",
		url: "../aviokompanije/dobavi/" + id,
		success: function(response) {
			podaciAviokompanije = response;
			prikaziPodatkeAviokompanije();
			prikaziDestinacije();
			prikaziAvione();
			prikaziCjenovnikPrtljaga();
			ymaps.ready(inicijalizujMape);
		},
	});

}

function prikaziPodatkeAviokompanije() {
	$("#slikaAviokompanije").attr("src", defaultSlika);
	$("#title").html(podaciAviokompanije.naziv);
	$("#aviokompanijaNaslov").html(podaciAviokompanije.naziv);
	$("#nazivAviokompanije").val(podaciAviokompanije.naziv);
	$("#adresaAviokompanije").val(podaciAviokompanije.adresa.punaAdresa);
	let sumaOcjena = podaciAviokompanije.sumaOcjena;
	sumaOcjena = parseFloat(sumaOcjena);
	let brOcjena = podaciAviokompanije.brojOcjena;
	brOcjena = parseInt(brOcjena);
	if(brOcjena > 0) {
		$("#ocjenaAviokompanije").val(sumaOcjena / brOcjena);
	} else {
		$("#ocjenaAviokompanije").val("Nema ocjena");
	}
	$("#opisAviokompanije").val(podaciAviokompanije.promotivniOpis);
}

function prikaziDestinacije() {
	let tabela = $("#prikazDestinacija");
	tabela.empty();
	
	$.each(podaciAviokompanije.destinacije, function(i, destinacija) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column3"><img src="' + defaultSlikaDestinacije + '" alt="Greska pri učitavanju slike"/></td>');
		noviRed.append('<td class="column3">' + destinacija.nazivDestinacije + '</td>');
		noviRed.append('<td class="column3">' + destinacija.adresa.punaAdresa + '</td>');
		tabela.append(noviRed);
	});
}

function prikaziAvione() {
	let tabela = $("#prikazAviona");
	tabela.empty();
	
	$.each(podaciAviokompanije.avioni, function(i, avion) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column3"><img src="' + defaultSlikaAviona + '" alt="Greska pri učitavanju slike"/></td>');
		noviRed.append('<td class="column3">' + avion.naziv + '</td>');
		noviRed.append('<td class="column5">' + avion.sjedista.length + '</td>');
		noviRed.append('<td class="column5">' + avion.segmenti.length + '</td>');
		tabela.append(noviRed);
	});
}

function ucitajLetove() {
	
	$.ajax({
		type: "GET",
		url: "../letovi/letoviAviokompanije/" + podaciAviokompanije.id,
		success: function(response) {
			prikaziLetove(response);
		},
	});
	
}

function prikaziLetove(letovi) {
	let tabela = $("#prikazLetova");
	tabela.empty();
	
	$.each(letovi, function(i, tekuciLet) {
		let noviRed = $("<tr></tr>");
		
		let datum_poletanja = new Date(tekuciLet.datumPoletanja);
		let datum_sletanja = new Date(tekuciLet.datumSletanja);
		
		noviRed.append('<td class="column2">' + tekuciLet.brojLeta + "</td>");
		noviRed.append('<td class="column3">' + tekuciLet.polaziste.nazivDestinacije + "</td>");
		noviRed.append('<td class="column3">' + tekuciLet.odrediste.nazivDestinacije + "</td>");
		noviRed.append('<td class="column1">' + datum_poletanja.getFullYear() + "-" + datum_poletanja.getMonth() + 
				"-" + datum_poletanja.getDate() + "</td>");
		noviRed.append('<td class="column1">' + datum_sletanja.getFullYear() + "-" + datum_sletanja.getMonth() + 
				"-" + datum_sletanja.getDate() + "</td>");
		noviRed.append('<td class="column5">' + tekuciLet.presjedanja.length + "</td>");
		noviRed.append('<td class="column6">' + tekuciLet.cijenaKarte + "</td>");
		if (tekuciLet.brojOcjena > 0) {
			noviRed.append('<td class="column6">' + (tekuciLet.sumaOcjena / tekuciLet.brojOcjena) + "</td>");
		} else {
			noviRed.append('<td class="column6">Nema ocjena</td>');
		}
		tabela.append(noviRed);
	});
}

function prikaziCjenovnikPrtljaga() {
	let tabela = $("#prikazPrtljaga");
	tabela.empty();
	
	$.each(podaciAviokompanije.cjenovnikDodatnihUsluga, function(i, prtljag) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + prtljag.naziv + '</td>');
		noviRed.append('<td class="column1">' + prtljag.opis + '</td>');
		noviRed.append('<td class="column1">' + prtljag.cijena + '</td>');
		tabela.append(noviRed);
	});
}

function inicijalizujMape() {
	var coords = [61.79, 34.36];
	var myGeocoder = ymaps.geocode(podaciAviokompanije.adresa.punaAdresa);
	var myPlacemark = null;
	myGeocoder.then(
		    function (res) {
		        //alert('Object coordinates:' + res.geoObjects.get(0).geometry.getCoordinates());
				coords = res.geoObjects.get(0).geometry.getCoordinates();
				var myPlacemark = new ymaps.Placemark(coords, {balloonContentBody: podaciAviokompanije.naziv});
				var myMap = new ymaps.Map('mapa', {
			        center: coords,
			        zoom: 16,
			        controls: []
			    });
				myMap.geoObjects.add(myPlacemark);
				myMap.controls.add('typeSelector');
		    },
		    function (err) {
		        console.log('Error');
		    }
	);
}
