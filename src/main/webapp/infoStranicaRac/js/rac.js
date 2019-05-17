let podaciRac = null;
let defaultSlika = "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg";
let ukupno = 0;

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
	
	ucitajPodatkeRac();
	
	$("#pretragaVozilaForm").submit(function(e) {
		e.preventDefault();
		pretragaVozila();
	})
});


function ucitajPodatkeRac() {
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	var id = params_parser.get("id");
	
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
				$("#ocjenaRac").val(sumaOcjena / brOcjena);
			} else {
				$("#ocjenaRac").val("Nema ocjena");
			}
			$("#opisRac").val(response.promotivniOpis);
			$("#mjestoPreuzimanjaSelect").empty();
			$("#mjestoVracanjaSelect").empty();
			$.each(podaciRac.filijale, function(i,filijala){
				$("#mjestoPreuzimanjaSelect").append('<option value = "' + filijala.id + '">' + filijala.adresa.punaAdresa + '</option>');
				$("#mjestoVracanjaSelect").append('<option value = "' + filijala.id + '">' + filijala.adresa.punaAdresa + '</option>');
			});
		},
	});

}

function pretragaVozila(){
	let _datumPreuzimanja = $("#input-start").val();
	let _datumVracanja = $("#input-end").val();
	
	let _vrijemePreuzimanja = $("#input-start-time").val();
	let _vrijemeVracanja = $("#input-end-time").val();
	if (_vrijemePreuzimanja == '' || _vrijemeVracanja == ''){
		alert("Niste unijeli vrijeme preuzimanja/vracanja vozila");
	}
	
	let _mjestoPreuzimanja = $("#mjestoPreuzimanjaSelect").val();
	let _mjestoVracanja = $("#mjestoVracanjaSelect").val();
	
	let _brojPutnika = $("#brojPutnikaInput").val();
	let _tipVozila = $("#tipVozilaSelect").val();
	let pretragaVozila = {
			idRac : podaciRac.id,
			datumPreuzimanja : _datumPreuzimanja,
			datumVracanja : _datumVracanja,
			vrijemePreuzimanja: _vrijemePreuzimanja,
			vrijemeVracanja: _vrijemeVracanja,
			idMjestoPreuzimanja: _mjestoPreuzimanja,
			idMjestoVracanja: _mjestoVracanja,
			tipVozila: _tipVozila,
			brojPutnika: _brojPutnika
			
	}
	
	if (_datumPreuzimanja != '' && _datumVracanja != ''){
		ukupno = Math.abs(Date.parse(_datumPreuzimanja) - Date.parse(_datumVracanja)) / 36e5;
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
				alert("Ne postoji ni jedno slobodno vozilo za dati vremenski period.");
				return;
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
			noviRed.append('<td class="column1">' + sumaOcjena / brOcjena + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column1">' + ukupno*vozilo.cijena_po_danu + '</td>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "rezervacija" id = "' + i + '">Rezervisi vozilo</a></td></tr>');
		prikaz.append(noviRed);
	})
}
