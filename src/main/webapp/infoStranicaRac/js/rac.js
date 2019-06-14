let podaciRac = null;
let defaultSlika = "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg";
let ukupno = 0;
let korisnikId = null;
let mapa = null;
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
	
	ucitajPodatkeRac();
	
	//pretraga vozila za obicnu rezervaciju
	$("#pretragaVozilaForm").submit(function(e) {
		e.preventDefault();
		pretragaVozila();
	});
	
	//pretraga vozila za brzu rezervaciju
	$("#pretragaVozilaSaPopustomForm").submit(function(e){
		e.preventDefault();
		pretragaVozilaSaPopustom();
	});
	
	//prikazivanje lokacije sjedista RAC servisa
	$("#lokacijaRacServisaPrikaz").click(function(e) {
		e.preventDefault();
		postaviMarker([podaciRac.adresa.latituda, podaciRac.adresa.longituda]);
	});
	
	$("#vratiNaPocetnu").click(function(e){
		e.preventDefault();
		if (korisnikId != null){
			window.location.replace("../registrovaniKorisnikPocetna/index.html");
		}
		else{
			window.location.replace("../pocetnaStranica/index.html");
		}
	});
});


function ucitajPodatkeRac() {
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	var id = params_parser.get("id");
	var kor =  params_parser.get("korisnik");
	korisnikId = kor;
	
	$.ajax({
		type: "GET",
		url: "../rentACar/dobavi/" + id,
		success: function(response) {
			podaciRac = response;
			$("#slikaRac").attr("src", defaultSlika);
			$("#title").html(response.naziv);
			$("#racNaslov").html(response.naziv);
			$("#nazivRac").val(response.naziv);
			$("#adresaRac").val(response.adresa.punaAdresa);
			let sumaOcjena = response.sumaOcjena;
			sumaOcjena = parseFloat(sumaOcjena);
			let brOcjena = response.brojOcjena;
			brOcjena = parseInt(brOcjena);
			if(brOcjena > 0) {
				var prosjek = sumaOcjena/brOcjena;
				prosjek = prosjek.toFixed(2);

				$("#ocjenaRac").val(prosjek);
			} else {
				$("#ocjenaRac").val("Nema ocjena");
			}
			$("#opisRac").val(response.promotivniOpis);
			$("#mjestoPreuzimanjaSelect").empty();
			$("#mjestoVracanjaSelect").empty();
			$("#mjestoPreuzimanjaPopustSelect").empty();
			$("#mjestoVracanjaPopustSelect").empty();
			$.each(podaciRac.filijale, function(i,filijala){
				$("#mjestoPreuzimanjaSelect").append('<option value = "' + filijala.id + '">' + filijala.adresa.punaAdresa + '</option>');
				$("#mjestoVracanjaSelect").append('<option value = "' + filijala.id + '">' + filijala.adresa.punaAdresa + '</option>');
				$("#mjestoPreuzimanjaPopustSelect").append('<option value = "' + filijala.id + '">' + filijala.adresa.punaAdresa + '</option>');
				$("#mjestoVracanjaPopustSelect").append('<option value = "' + filijala.id + '">' + filijala.adresa.punaAdresa + '</option>');
			});
			ymaps.ready(inicijalizujMape);
			prikaziFilijale();
		},
	});

}

function prikaziFilijale() {
	let tabela = $("#prikazFilijala");
	tabela.empty();
	
	$.each(podaciRac.filijale, function(i, filijala) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + filijala.adresa.punaAdresa + '</td>');
		noviRed.append('<td class="column1"><a class="trigger_popup_fricc prikazFil" id="fil' + i + '">Prikaži na mapi</a></td>');
		tabela.append(noviRed);
	});
	
	registerWindowHandlers(); //omogucuje otvaranje prozora (popup.js)
	
	//prikazivanje lokacije izabrane filijale na mapi
	$(".prikazFil").click(function(e) {
		e.preventDefault();
		let index = e.target.id.substring(3); //id je oblika "fil<indeks u kolekciji filijala>"
		index = parseInt(index);
		let adresa = podaciRac.filijale[index].adresa;
		postaviMarker([adresa.latituda, adresa.longituda]);
	});
	
}

function pretragaVozila(){
	let _datumPreuzimanja = $("#input-start").val();
	let _datumVracanja = $("#input-end").val();
	
	let _vrijemePreuzimanja = $("#input-start-time").val();
	let _vrijemeVracanja = $("#input-end-time").val();
	if (_vrijemePreuzimanja == '' || _vrijemeVracanja == ''){
		swal({
			  title: "Niste unijeli vrijeme preuzimanja/vraćanja vozila.",
			  icon: "warning",
			  timer: 2500
			});	
	}
	
	let _mjestoPreuzimanja = $("#mjestoPreuzimanjaSelect").val();
	let _mjestoVracanja = $("#mjestoVracanjaSelect").val();
	
	let _brojPutnika = $("#brojPutnikaInput").val();
	let _tipVozila = $("#tipVozilaSelect").val();
	
	let _minCijena = $("#minCijena").val();
	let _maxCijena = $("#maxCijena").val();
	
	
	let pretragaVozila = {
			idRac : podaciRac.id,
			datumPreuzimanja : _datumPreuzimanja,
			datumVracanja : _datumVracanja,
			vrijemePreuzimanja: _vrijemePreuzimanja,
			vrijemeVracanja: _vrijemeVracanja,
			idMjestoPreuzimanja: _mjestoPreuzimanja,
			idMjestoVracanja: _mjestoVracanja,
			tipVozila: _tipVozila,
			brojPutnika: _brojPutnika,
			
	}
	
	if (_minCijena != ""){
		pretragaVozila.minimalnaCijenaPoDanu = _minCijena;
	}
	if (_maxCijena != ""){
		pretragaVozila.maksimalnaCijenaPoDanu = _maxCijena;

	}
	if (_datumPreuzimanja != '' && _datumVracanja != ''){
		ukupno = Math.abs(Date.parse(_datumPreuzimanja) - Date.parse(_datumVracanja)) / (1000*60*60*24);
		/*var datetime1 = new Date('1970-01-01T' + _vrijemePreuzimanja + 'Z');
		var datetime2 = new Date('1970-01-01T' + _vrijemeVracanja + 'Z');
		ukupno = ukupno + Math.abs(Date.parse(datetime1) - Date.parse(datetime2)) / 36e5;*/
	}
	
	
	$.ajax({
		type: "POST",
		url: "../rentACar/pretraziVozila",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(pretragaVozila),
		success: function(response) {
			if(response.length == 0) {
				swal({
					  title: "Ne postoji ni jedno slobodno vozilo sa unijetim karakteristikama za dati vremenski period.",
					  icon: "info",
					  timer: 2500
					});	
			}
			prikaziVozila(response);
		},
	});
}

function prikaziVozila(vozila) {
	$("#tabelaVozila").show();
	let prikaz = $("#prikazVozila");
	prikaz.empty();
	
	$.each(vozila, function(i, vozilo) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + vozilo.naziv + '</td>');
		noviRed.append('<td class="column1">' + vozilo.marka + '</td>');
		noviRed.append('<td class="column1">' + vozilo.model + '</td>');
		noviRed.append('<td class="column1">' + vozilo.godina_proizvodnje + '</td>');
		noviRed.append('<td class="column1">' + vozilo.broj_sjedista + '</td>');
		noviRed.append('<td class="column1">' + vozilo.tip_vozila + '</td>');
		let sumaOcjena = vozilo.sumaOcjena;
		sumaOcjena = parseFloat(sumaOcjena);
		let brOcjena = vozilo.brojOcjena;
		brOcjena = parseInt(brOcjena);
		if(brOcjena > 0) {
			noviRed.append('<td class="column1">' + (sumaOcjena / brOcjena).toFixed(2) + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column1">' + ukupno*vozilo.cijena_po_danu + '</td>');
		if (korisnikId != null){
			noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "rezervacija" id = "' + i + '">Rezervisi vozilo</a></td></tr>');
		}
		
		prikaz.append(noviRed);
	})
	
	$(".rezervacija").click(function(e){
		e.preventDefault();
		let vozilo = vozila[e.target.id];
				
		let rezervacijaVozila = {
				rezervisanoVozilo : vozilo,
				mjestoPreuzimanjaVozila : $("#mjestoPreuzimanjaSelect").val(),
				datumPreuzimanjaVozila : Date.parse($("#input-start").val()),
				mjestoVracanjaVozila : $("#mjestoVracanjaSelect").val(),
			    datumVracanjaVozila : Date.parse($("#input-end").val()),
			    cijena : ukupno*vozilo.cijena_po_danu,
			    putnik : korisnikId ,
			    putovanje : null
		}
		
		$.ajax({
			type: "POST",
			url: "../rentACar/rezervisiVozilo/" + podaciRac.id,
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(rezervacijaVozila),
			success: function(response) {
				if(response == '') {
					swal({
						  title: "Uspješno ste rezervisali vozilo.",
						  icon: "success",
						  timer: 2500
						}).then(function(){
							location.reload(true);
						})	
					return;
				}
				else{
					swal({
						  title: response,
						  icon: "warning",
						  timer: 2500
						})
				}
			},
		});
		
	});
}


function postaviMarker(koordinate) {
	var placemark = new ymaps.Placemark(koordinate);
	mapa.setCenter(koordinate, zoomLevel);
	mapa.geoObjects.removeAll();
	mapa.geoObjects.add(placemark);
}

function inicijalizujMape() {
	var coords = [podaciRac.adresa.latituda, podaciRac.adresa.longituda];
	mapa = new ymaps.Map('mapa', {
        center: coords,
        zoom: zoomLevel,
        controls: []
    });
	mapa.controls.add('typeSelector');
	mapa.controls.add('zoomControl');
}

function pretragaVozilaSaPopustom(){
	let _datumPreuzimanja = $("#input-start-2").val();
	let _datumVracanja = $("#input-end-2").val();
	
	let _vrijemePreuzimanja = $("#input-start-time-popust").val();
	let _vrijemeVracanja = $("#input-end-time-popust").val();
	if (_vrijemePreuzimanja == '' || _vrijemeVracanja == ''){
		swal({
			  title: "Niste unijeli vrijeme preuzimanja/vracanja vozila.",
			  icon: "warning",
			  timer: 2500
			});
		return;
	}
	
	let _mjestoPreuzimanja = $("#mjestoPreuzimanjaPopustSelect").val();
	let _mjestoVracanja = $("#mjestoVracanjaPopustSelect").val();
	
	let _brojPutnika = $("#brojPutnikaPopustInput").val();
	let _tipVozila = $("#tipVozilaPopustSelect").val();
	
	
	let pretragaVozila = {
			idRac : podaciRac.id,
			datumPreuzimanja : _datumPreuzimanja,
			datumVracanja : _datumVracanja,
			vrijemePreuzimanja: _vrijemePreuzimanja,
			vrijemeVracanja: _vrijemeVracanja,
			idMjestoPreuzimanja: _mjestoPreuzimanja,
			idMjestoVracanja: _mjestoVracanja,
			tipVozila: _tipVozila,
			brojPutnika: _brojPutnika,
			
	}

	if (_datumPreuzimanja != '' && _datumVracanja != ''){
		ukupno = Math.abs(Date.parse(_datumPreuzimanja) - Date.parse(_datumVracanja)) / (1000*60*60*24);
		/*var datetime1 = new Date('1970-01-01T' + _vrijemePreuzimanja + 'Z');
		var datetime2 = new Date('1970-01-01T' + _vrijemeVracanja + 'Z');
		ukupno = ukupno + Math.abs(Date.parse(datetime1) - Date.parse(datetime2)) / 36e5;*/
	}
	
	
	$.ajax({
		type: "POST",
		url: "../rentACar/pretraziVozilaSaPopustom",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(pretragaVozila),
		success: function(response) {
			if(response.length == 0) {
				swal({
					  title: "Ne postoji ni jedno slobodno vozilo sa unijetim karakteristikama za dati vremenski period.",
					  icon: "info",
					  timer: 2500
					});
			}
			prikaziVozilaSaPopustom(response);
		},
	});
}

function prikaziVozilaSaPopustom(rezVozila) {
	$("#tabelaVozilaSaPopustom").show();
	let prikaz = $("#prikazVozilaSaPopustom");
	prikaz.empty();
	
	$.each(rezVozila, function(i, rez) {
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + rez.vozilo.naziv + '</td>');
		noviRed.append('<td class="column1">' + rez.vozilo.marka + '</td>');
		noviRed.append('<td class="column1">' + rez.vozilo.model + '</td>');
		noviRed.append('<td class="column1">' + rez.vozilo.godina_proizvodnje + '</td>');
		noviRed.append('<td class="column1">' + rez.vozilo.broj_sjedista + '</td>');
		noviRed.append('<td class="column1">' + rez.vozilo.tip_vozila + '</td>');
		let sumaOcjena = rez.vozilo.sumaOcjena;
		sumaOcjena = parseFloat(sumaOcjena);
		let brOcjena = rez.vozilo.brojOcjena;
		brOcjena = parseInt(brOcjena);
		if(brOcjena > 0) {
			noviRed.append('<td class="column1">' + (sumaOcjena / brOcjena).toFixed(2) + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column1">' + ukupno*rez.vozilo.cijena_po_danu + '</td>');
		let cijenaSaPopustom = 0;
		if (rez.procenatPopusta != 0){
			cijenaSaPopustom = ukupno*rez.vozilo.cijena_po_danu - ukupno*rez.vozilo.cijena_po_danu*rez.procenatPopusta/100 ;
		}
		noviRed.append('<td class="column1">' + cijenaSaPopustom + '</td>');
		if (korisnikId != null){
			noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "brzaRezervacija" id = "' + i + '">Rezervisi vozilo</a></td></tr>');
		}
		
		prikaz.append(noviRed);
	});
	$(".brzaRezervacija").click(function(e){
		e.preventDefault();
		let brzaRez = rezVozila[e.target.id];
		cijenaSaPopustom = ukupno*brzaRez.vozilo.cijena_po_danu - ukupno*brzaRez.vozilo.cijena_po_danu*brzaRez.procenatPopusta/100 ;		
		let rezervacijaVozila = {
				rezervisanoVozilo : brzaRez.vozilo,
				mjestoPreuzimanjaVozila : $("#mjestoPreuzimanjaPopustSelect").val(),
				datumPreuzimanjaVozila : Date.parse($("#input-start-2").val()),
				mjestoVracanjaVozila : $("#mjestoVracanjaPopustSelect").val(),
			    datumVracanjaVozila : Date.parse($("#input-end-2").val()),
			    cijena : cijenaSaPopustom,
			    putnik : korisnikId ,
			    putovanje : null
		};
		
		$.ajax({
			type: "POST",
			url: "../rentACar/rezervisiVozilo/" + podaciRac.id,
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(rezervacijaVozila),
			success: function(response) {
				if(response == '') {
					swal({
						  title: "Uspješno ste rezervisali vozilo.",
						  icon: "success",
						  timer: 2500
						}).then(function(){
							location.reload(true);
						});
					return;
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
		
	});
}
