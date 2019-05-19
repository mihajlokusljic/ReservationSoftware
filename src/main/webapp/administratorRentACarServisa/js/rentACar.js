var rentACarServis = null;
var korisnik = null;
var filijale = null;
var defaultSlika = "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg";
var mapaRacServisa = null;
var mapaFilijaleDodavanje = null;
var mapaFilijaleIzmjena = null;
var zoomLevel = 17;
let stavkeMenija = ["stavka_vozila", "stavka_filijale", "stavka_brze_rezervacije", "stavka_profil_servisa", "stavka_profil_korisnika", "stavka_izvjestaj", "stavka_odjava"];

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
	
	ucitajPodatkeSistema();
	ymaps.ready(inicijalizujMape);
	korisnikInfo();
	
	$("#vozila").click(function(e){
		e.preventDefault();
		$("#tab-filijala").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-vozila").show();
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		dobaviSvaVozilaServisa();
		ponudiFilijale();

	
	});
	
	$("#filijale").click(function(e){
		e.preventDefault();
		$("#tab-filijala").show();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		dobaviSveFilijale();
	
	});
	
	$("#profil_servisa").click(function(e){
		e.preventDefault();
		$("#tab-profil-servisa").show();

		$("#tab-filijala").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		profilServisa();
	});
	
	$("#ponistavanjeIzmjenaRacServisa").click(function(e) {
		e.preventDefault();
		prikaziPodatkeServisa();
		postaviMarker(mapaRacServisa, [rentACarServis.adresa.latituda, rentACarServis.adresa.longituda]);
	});
	
	$("#izmjeni_podatke_tab").click(function(e){
		e.preventDefault();
		aktivirajStavkuMenija("stavka_profil_korisnika");
		$("#tab-profil-kor").show();
		$("#tab-profil-lozinka").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-filijala").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		profilKorisnika();
	})
	
	$("#promjeni_lozinku_tab").click(function(e){
		e.preventDefault();
		aktivirajStavkuMenija("stavka_profil_korisnika");
		$("#tab-profil-lozinka").show();
		$("#tab-profil-kor").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-filijala").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		promjeniLozinku();
	})
	$("#dodaj_vozilo_dugme").click(function(e){
		e.preventDefault();
		$("#tab-filijala").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-dodaj-vozilo").show();
		//ponudiFilijale();
		dodajVozilo();
	});
	
	$("#dodaj_filijalu_dugme").click(function(e){
		e.preventDefault();
		$("#tab-filijala").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").show();
		dodajFilijalu();
	});
	
	$("#ponistavanjeIzmjenaFilijale").click(function(e) {
		e.preventDefault();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
		$("#tab-filijala").show();
	});
	
	$("#dodavanjeBrzeRezervacije").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavka_brze_rezervacije");
		$("#tab-brze-rezervacije-dodavanje").show();
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-profil-kor").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-filijala").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();
	});
	
	$("#pregledanjeBrzihRezervacija").click(function(e) {
		e.preventDefault();
		aktivirajStavkuMenija("stavkaBrzeRezervacije");
		$("#tab-brze-rezervacije-pregledanje").show();
		$("#tab-brze-rezervacije-dodavanje").hide();
		$("#tab-profil-kor").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-filijala").hide();
		$("#tab-dodaj-vozilo").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").hide();
		$("#tab-dodaj-filijalu").hide();
		$("#tab-izmjeni-filijalu").hide();

	});
	
	//prikaz koraka za dodavanje brze rezervacije
	$("#izborVozilaBrzeRezervacijeBtn").click(function(e) {
		if ($("#izborVozilaBrzeRezervacijeBtn").is(":checked")) {
			$("#izborVozilaBrzeRezervacije").show();
			$("#definisanjePopustaBrzeRezervacije").hide();
		}
	});
	
	$("#definisanjePopustaBrzeRezervacijeBtn").click(function(e) {
		if ($("#definisanjePopustaBrzeRezervacijeBtn").is(":checked")) {
			$("#definisanjePopustaBrzeRezervacije").show();
			$("#izborVozilaBrzeRezervacije").hide();
		}
	});
	
	//pretraga vozila za brzu rezervaciju
	$("#pretragaVozilaForm").submit(function(e) {
		e.preventDefault();
		pretraziSlobodnaVozila();
	});
	
	//zadavanje vozila za brze rezervacije
	$("#zadajVoziloBrzeRezervacijeBtn").click(function (e) {
		e.preventDefault();
		zadajVoziloBrzeRez();
	});
	
	$("#odjava").click(function(e){
		e.preventDefault();
		odjava();
	});
	
});

function korisnikInfo(){
	let token = getJwtToken("jwtToken");
	$.ajax({
		type : 'GET',
		url : "../korisnik/getInfo",
		dataType : "json",
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(data){
			if(data != null){
				korisnik = data;
				alert("Ulogovani ste kao administrator rent-a-car servisa: " + data.ime + " " + data.prezime );
				if(!korisnik.lozinkaPromjenjena) {
					izmjenaInicijalneLozinke();
				}
				else{
					dobaviSvaVozilaServisa();
					ponudiFilijale();
				}
			}
			else{
				alert("nepostojeci korisnik");
			}
		},
		async: false,
		error : function(XMLHttpRequest) {
			alert("AJAX ERROR: ");
		}
	});
}

function ucitajPodatkeSistema(){

	$.ajax({
		type : 'GET',
		url : "../rentACar/podaciOServisu",
		dataType : "json",
		async: false,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(data){
			if(data != null){
				rentACarServis = data;
			}
		},
		async: false,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + textStatus);
		}
	});
}

function dobaviSvaVozilaServisa(){
	nazivServisa = rentACarServis.naziv;
	$.ajax({
		type: "GET",
		url: "../rentACar/svaVozilaServisa/" + nazivServisa,
		dataType : "json",
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(response) {
			if(response == "Servis koji ste unijeli ne postoji.") {
				alert(response);
			} else {
				prikaziVozila(response);
				
			}
		},
		async: false,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
			}
	});
}

function prikaziVozila(vozila){

	$("#tab-dodaj-vozilo").hide();
	$("#tab-vozila").show();
	$("#tab-prikaz-vozila").hide();
	$("#tab-dodaj-filijalu").hide();


	let tbody = $("#tbody_vozila");
	tbody.empty();
	$.each(vozila, function(i,vozilo){
		tbody.append('<tr><td class = "column1">' + vozilo["naziv"] + '</td><td class = "column2">' + vozilo["marka"] + '</td><td class = "column3">' + vozilo["model"]
		 + '</td><td class = "column4">' + vozilo["tip_vozila"]  + '</td><td class = "column5">' + vozilo["godina_proizvodnje"] + '</td><td class = "column6">' + vozilo["cijena_po_danu"] +
		 '</td><td class = "column7"><a href = "javascript:void(0)" class = "detaljan_prikaz" id = "' + i + '">Detaljan prikaz</a></td></tr>')
	});
	
	$(".detaljan_prikaz").click(function(e){
		$("#tab-vozila").hide();
		$("#tab-prikaz-vozila").show();
		let vozilo = vozila[e.target.id];
		$("#naziv_input1").val(vozilo.naziv);
		$("#marka_input1").val(vozilo.marka);
		$("#model_input1").val(vozilo.model);
		$("#godina_input1").val(vozilo.godina_proizvodnje);
		$("#broj_sjedista_input1").val(vozilo.broj_sjedista);
		$("#broj_vrata_input1").val(vozilo.broj_vrata);
		$("#cijena_input1").val(vozilo.cijena_po_danu);
		$("#filijalaSelectDetaljanPrikaz").val(vozilo.filijala).change();
		$('#tip_vozila_input1').val(vozilo.tip_vozila).change();
		//$('#tip_vozila_input1').trigger('change'); //obavjestavanje komponenti
		
		$("#izmjeni_vozilo_dugme").unbind().click(function(e){
			e.preventDefault();
			let naziv_v = $("#naziv_input1").val();
			if (naziv_v == ''){
				alert("Niste unijeli naziv vozila");
				return;
			}
			let marka_v = $("#marka_input1").val();
			if (marka_v == ''){
				alert("Niste unijeli marku vozila");
				return;
			}
			let model_v = $("#model_input1").val();
			if (model_v == ''){
				alert("Niste unijeli model vozila");
				return;
			}
			let tip_vozila_ = $("#tip_vozila_input1").val();
			let godina_s = $("#godina_input1").val();
			if (godina_s == ''){
				alert("Niste unijeli godinu proizvodnje vozila");
				return;
			}
			var dt = new Date();
	        var trenutna_godina = dt.getFullYear();
			var godina = parseInt(godina_s);
			if (isNaN(godina) || godina<0){
				alert("Niste unijeli validnu godinu proizvodnje.");
				return;
			}
			if (godina>trenutna_godina) {
				alert("Godina proizvodnje mora biti manja od trenutne godine.");
				return;
			}
			
			let broj_sjedista_s = $("#broj_sjedista_input1").val();
			if (broj_sjedista_s == ''){
				alert("Niste unijeli broj sjedista vozila");
				return;
			}
			var broj_sjedista_v = parseInt(broj_sjedista_s);
			if (isNaN(broj_sjedista_v) || broj_sjedista_v<0){
				alert("Broj sjedista mora biti broj veći od 0.");
				return;
			}
			let broj_vrata_s = $("#broj_vrata_input1").val();
			if (broj_vrata_s == ''){
				alert("Niste unijeli broj vrata vozila");
				return;
			}
			var broj_vrata_v = parseInt(broj_vrata_s);
			if (isNaN(broj_vrata_v) || broj_vrata_v<0){
				alert("Broj vrata mora biti broj veći od 0.");
				return;
			}
			
			let cijena_s = $("#cijena_input1").val();
			if (cijena_s == ''){
				alert("Niste unijeli cijenu usluga vozila po danu.");
				return;
			}
			var cijena_v = parseInt(cijena_s);
			if (isNaN(cijena_v) || cijena_v<0){
				alert("Cijena usluge po danu mora biti decimalan broj veci od 0.");
				return;
			}
			
			let filijala = $("#filijalaSelectDetaljanPrikaz option:selected").text();
			
			let voz= {
					Id: vozilo.id,
					naziv:naziv_v,
					marka:marka_v,
					model:model_v,
					godina_proizvodnje:godina,
					broj_sjedista:broj_sjedista_v,
					tip_vozila:tip_vozila_,
					broj_vrata:broj_vrata_v,
					kilovati:0,
					cijena_po_danu:cijena_v,
					filijala:filijala
			};
			$.ajax({
				type : "POST",
				url : "../rentACar/izmjeniVozilo/" + voz.Id,
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify(voz),
				headers: createAuthorizationTokenHeader("jwtToken"),
				success : function(response){
					dobaviSvaVozilaServisa();			
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("AJAX error: " + errorThrown);
				}
			});
		});
		
		$("#ukloni_vozilo_dugme").unbind().click(function(e){
			e.preventDefault();
			$.ajax({
				type : "GET",
				url : "../rentACar/ukloniVozilo/" + vozilo["id"],
				headers: createAuthorizationTokenHeader("jwtToken"),
				success : function(response){
					dobaviSvaVozilaServisa();			
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("AJAX error: " + errorThrown);
				}
			});
		});


	});
}


function dodajVozilo(){
	$("#forma_dodaj_vozilo").unbind().submit(function(e) {
		e.preventDefault();
		dobaviSvaVozilaServisa();
		let naziv_servisa = rentACarServis.naziv;
		let naziv = $('#naziv_input').val();
		if (naziv == ''){
			alert("Niste unijeli naziv vozila");
			return;
		}
		let marka = $("#marka_input").val();
		if (marka == ''){
			alert("Niste unijeli marku vozila");
			return;
		}
		let model = $("#model_input").val();
		if (model == ''){
			alert("Niste unijeli model vozila");
			return;
		}
		let tip_vozila = $("#tip_vozila_input").val();
		
		let godina = $("#godina_proizvodnje_input").val();
		if (godina == ''){
			alert("Niste unijeli godinu proizvodnje vozila");
			return;
		}
		
		var dt = new Date();
	    var trenutna_godina = dt.getFullYear();
		var godina_ = parseInt(godina);
		if (isNaN(godina_) || godina_<0){
			alert("Niste unijeli validnu godinu proizvodnje.");
			return;
		}
		if (godina_>trenutna_godina) {
			alert("Godina proizvodnje mora biti manja od trenutne godine.");
			return;
		}
		
		let broj_sjedista_s = $("#broj_sjedista_input").val();
		if (broj_sjedista_s == ''){
			alert("Niste unijeli broj sjedista vozila");
			return;
		}
		var broj_sjedista_v = parseInt(broj_sjedista_s);
		
		let broj_vrata_s = $("#broj_vrata_input").val();
		if (broj_vrata_s == ''){
			alert("Niste unijeli broj vrata vozila");
			return;
		}
		var broj_vrata_v = parseInt(broj_vrata_s);
	
		
		let cijena_s = $("#cijena_input").val();
		if (cijena_s == ''){
			alert("Niste unijeli cijenu usluga vozila po danu.");
			return;
		}
		
		var cijena_v = parseInt(cijena_s);
		if (isNaN(cijena_v) || cijena_v<0){
			alert("Cijena usluge po danu mora biti decimalan broj veci od 0.");
			return;
		}
		
		let filijala = $("#filijalaSelect").val();
		
		
		let vozilo = {
				naziv:naziv,
				marka:marka,
				model:model,
				godina_proizvodnje:godina_,
				broj_sjedista:broj_sjedista_v,
				tip_vozila:tip_vozila,
				broj_vrata:broj_vrata_v,
				kilovati:0,
				cijena_po_danu:cijena_v,
//				filijala: {id : filijala}
		};
		
		if (filijala != null) {
			let f = { id: filijala};
			vozilo.filijala = f;
		}
		
		$.ajax({
			type: "POST",
			url: "../rentACar/dodajVozilo/" + naziv_servisa,
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(vozilo),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				if(response == '') {
					alert("Uspjesno ste dodali novo vozilo");
					dobaviSvaVozilaServisa();
				} else {
					alert(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
}

function dobaviSveFilijale(){
	let naziv_servisa_ = rentACarServis.naziv;
	$.ajax({
		type: "GET",
		url: "../rentACar/dobaviFilijale/" + naziv_servisa_,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(response) {
			prikazFilijala(response);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});
}

function prikazFilijala(filijale){
	$("#tab-dodaj-filijalu").hide();
	$("#tab-izmjeni-filijalu").hide();
	$("#tab-filijala").show();

	let tbody = $("#tbody_filijale");
	tbody.empty();
	$.each(filijale, function(i,filijala){
		tbody.append('<tr><td class = "column1">' + filijala["punaAdresa"] + '</td><td class = "column2">' + filijala["brojVozila"] + '</td>'+
		 '<td class = "column3"><a href = "javascript:void(0)" class = "izmjeni_lokaciju" id = "' + i + '">Izmjeni lokaciju</a></td>' + 
		 '<td class = "column4"><a href = "javascript:void(0)" class = "ukloni_filijalu" id = "' + i + '">Ukloni filijalu</a></td></tr>'
		 );
		
	});
	
	$(".ukloni_filijalu").click(function(e){
		let filijala = filijale[e.target.id];
		$.ajax({
			type : "GET",
			url : "../rentACar/ukloniFilijalu/" + filijala.id,
			headers: createAuthorizationTokenHeader("jwtToken"),
			success : function(response){
				prikazFilijalaOdabranogServisa();			
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
	$(".izmjeni_lokaciju").click(function(e){
		e.preventDefault();
		$("#tab-filijala").hide();
		$("#tab-izmjeni-filijalu").show();
		let filijala = filijale[e.target.id];

		$("#adresa_filijale_nova").val(filijala.punaAdresa);
		$.ajax({
			type : "GET",
			url : "../rentACar/adresaFilijale/" + filijala.id,
			headers: createAuthorizationTokenHeader("jwtToken"),
			success : function(adresa){
				$("#latitudaFilijaleIzmjena").val(adresa.latituda);
				$("#longitudaFilijaleIzmjena").val(adresa.longituda);
				postaviMarker(mapaFilijaleIzmjena, [adresa.latituda, adresa.longituda]);
				mapaFilijaleIzmjena.setZoom(zoomLevel);
			},
		});
		
		$("#forma_izmjeni_filijalu").unbind().submit(function(e){
			e.preventDefault();
			let nova_lokacija = $("#adresa_filijale_nova").val();
			if (nova_lokacija == ''){
				alert("Ne mozete unijeti praznu lokaciju.");
				return;
			}
			let _lat = $("#latitudaFilijaleIzmjena").val();
			let _long = $("#longitudaFilijaleIzmjena").val();
			if(_lat == "" || _long == "") {
				alert("Morate označiti lokaciju filijale na mapi.");
				return;
			}
			
			let novaAdresa = {
					punaAdresa: nova_lokacija,
					latituda: _lat,
					longituda: _long
			};
			
			$.ajax({
				type : "PUT",
				url : "../rentACar/izmjeniFilijalu/" + filijala.id + "/" + nova_lokacija,
				data: JSON.stringify(novaAdresa),
				headers: createAuthorizationTokenHeader("jwtToken"),
				success : function(response){
					if (response != ''){
						alert(response);
						prikazFilijalaOdabranogServisa();				

					}
					else{
						alert("Uspjesno ste izmjenili adresu filijale");
						prikazFilijalaOdabranogServisa();				
					}
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("AJAX error: " + errorThrown);
				}
			});
		});
		
	});
	
	
	
	$("#forma_izmjeni_filijalu").unbind().submit(function(e){
		e.preventDefault();
		let filijala = filijale[e.target.id];
		let nova_lokacija = $("#adresa_filijale_nova").val();
		if (nova_lokacija == ''){
			alert("Ne mozete unijeti praznu lokaciju.");
			return;
		}
		$.ajax({
			type : "GET",
			url : "../rentACar/izmjeniFilijalu/" + filijala.id + "/" + nova_lokacija,
			headers: createAuthorizationTokenHeader("jwtToken"),
			success : function(response){
				if (response != ''){
					alert(response);		
				}
				else{
					alert("Uspjesno ste izmjenili adresu filijale");
					prikazFilijalaOdabranogServisa();				
				}
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
}

function dodajFilijalu(){
	
	$("#forma_dodaj_filijalu").unbind().submit(function(e) {
		e.preventDefault();
		let naziv_servisa = rentACarServis.naziv;

		let adresa = $("#adresa_filijale").val();
		if (adresa == ''){
			alert ("Niste unijeli adresu.");
			return;
		}
		let _lat = $("#latitudaFilijaleDodavanje").val();
		let _long = $("#longitudaFilijaleDodavanje").val();
		
		if(_lat == "" || _long == "") {
			alert("Morate označiti lokaciju filijale na mapi.");
			return;
		}
		
		let adresaFilijale = {
				punaAdresa: adresa,
				latituda: _lat,
				longituda: _long
		};
		
		$.ajax({
			type: "POST",
			url: "../rentACar/dodajFilijalu",
			data: JSON.stringify(adresaFilijale),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				$("#forma_dodaj_filijalu")[0].reset();
				mapaFilijaleDodavanje.geoObjects.removeAll();
				prikazFilijalaOdabranogServisa();
			},
		});			
	});
}

function prikazFilijalaOdabranogServisa(){
	let naziv_servisa_ = rentACarServis.naziv;
	$.ajax({
		type: "GET",
		url: "../rentACar/dobaviFilijale/" + naziv_servisa_,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(response) {
			prikazFilijala(response);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});
	

}

function prikaziPodatkeServisa() {
	$("#profil_servisa_naziv").val(rentACarServis.naziv);
	$("#profil_servisa_adresa").val(rentACarServis.adresa.punaAdresa);
	$("#profil_servisa_opis").val(rentACarServis.promotivniOpis);
	$("#slikaRacServisa").attr("src", defaultSlika);
	$("#latitudaServisa").val(rentACarServis.adresa.latituda);
	$("#longitudaServisa").val(rentACarServis.adresa.longituda);
}

function profilServisa(){
	prikaziPodatkeServisa();
	
	$("#profil_servisa_forma").unbind().submit(function(e){
		e.preventDefault();
		var naziv_serv = $("#profil_servisa_naziv").val();
		var adresa = $("#profil_servisa_adresa").val();
		if (adresa == ''){
			alert("Polje za unos adrese servisa ne moze biti prazno.");
			return;
		}
		var _lat = $("#latitudaServisa").val();
		var _long = $("#longitudaServisa").val();
		
		if(_lat == "" || _long == "") {
			alert("Morate zadati lokaciju rent-a-car servisa na mapi.");
			return;
		}
		
		var opis = $("#profil_servisa_opis").val();
		if (opis == ''){
			alert("Polje za unos promotivnog opisa servisa ne moze biti prazno.");
			return;
		}
		
		let racServis = {
				naziv: naziv_serv, 
				adresa: { 
					punaAdresa: adresa,
					latituda: _lat,
					longituda: _long
				}, 
				promotivniOpis: opis,
				sumaOcjena: 0,
				brojOcjena: 0,
				vozila: [],
				filijale: []
		};
		$.ajax({
			type:"POST",
			url:"../rentACar/izmjeniProfil",
			contentType : "application/json; charset=utf-8",
			data:JSON.stringify(racServis),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success:function(response){
				if (response == ''){
					alert("Uspjesno ste izmjenili profil");
					ucitajPodatkeSistema();
					profilServisa();
				}
				else{
					alert(response);

				}
				
			},
		});
		
	});
}


function odjava() {
	removeJwtToken();
	window.location.replace("../login/login.html");
}

function profilKorisnika(){
	$("#emailAdmina").val(korisnik.email);
	$("#imeAdmina").val(korisnik.ime);
	$("#prezimeAdmina").val(korisnik.prezime);
	$("#brTelefonaAdmina").val(korisnik.brojTelefona);
	$("#adresaAdmina").val(korisnik.adresa.punaAdresa);
	
	$("#forma_profil_korisnika").unbind().submit(function(e){
		e.preventDefault();
		var imeAdmina = $("#imeAdmina").val();
		if (imeAdmina == ''){
			alert("Polje za unos imena ne moze biti prazno.");
			return;
		}
		var prezimeAdmina = $("#prezimeAdmina").val();
		if (prezimeAdmina == ''){
			alert("Polje za unos prezimena ne moze biti prazno.");
			return;
		}
		var brTelefonaAdmina = $("#brTelefonaAdmina").val();
		if (brTelefonaAdmina == ''){
			alert("Polje za unos broja telefona ne moze biti prazno.");
			return;
		}
		var adresaAdmina = $("#adresaAdmina").val();
		if (adresaAdmina == ''){
			alert("Polje za unos adrese ne moze biti prazno.");
			return;
		}		

		let admin = {
				id: korisnik.id,
				ime: imeAdmina,
				prezime: korisnik.prezime,
				email: korisnik.email,
				lozinka: korisnik.lozinka,
				brojTelefona: brTelefonaAdmina,
				adresa: { punaAdresa : adresaAdmina }
		};
		$.ajax({
			type:"POST",
			url:"../rentACar/izmjeniProfilKorisnika",
			contentType : "application/json; charset=utf-8",
			data:JSON.stringify(admin),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success:function(response){
				if (response == ''){
					alert("Izmjena nije uspjela");
				}
				else{
					alert("Uspjesno ste izmjenili profil.");
					korisnik = response;
					profilKorisnika();
				}
				
			},
		});
		
	});
}

function promjeniLozinku(){
	$("#forma_lozinka").unbind().submit(function(e){
		e.preventDefault();
		var staraLozinka = $("#staraLozinka").val();
		var novaLozinka = $("#novaLozinka").val();
		var novaLozinka2 = $("#novaLozinka2").val();
		var lozinkaMijenjana = korisnik.lozinkaPromjenjena;
		
		if (novaLozinka == ''){
			alert("Niste unijeli novu lozinku");
			return;
		}
		
		if (novaLozinka != novaLozinka2){
			alert("Greska. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
			return;
		}
		
		
		$.ajax({
			type : 'PUT',
			url : "../auth/changePassword/" + staraLozinka,
			headers : createAuthorizationTokenHeader("jwtToken"),
			contentType : "application/json",
			data : novaLozinka,
			success : function(data) {
				if (data == ''){
					alert("Pogresna trenutna lozinka.");
					return;
				}
				else{
					setJwtToken("jwtToken", data.accessToken);
					alert("Uspjesno ste izmjenili lozinku");
					if(!lozinkaMijenjana) {
						$("#izmjenaInicijalneLozinkePoruka").hide();
						$("#tab-profil-lozinka").hide();
						$("#meni").show();					
						korisnik.lozinkaPromjenjena = true;
						dobaviSvaVozilaServisa();
						ponudiFilijale();
					}
				}
				
			}
		});
	});	
}

function ponudiFilijale(){
	
	let naziv_servisa_ = rentACarServis.naziv;
	let filijale_select = $("#filijalaSelect");
	filijale_select.empty();
	let filijala_select2 = $("#filijalaSelectDetaljanPrikaz");
	filijala_select2.empty();
	$.ajax({
		type: "GET",
		url: "../rentACar/dobaviFilijale/" + naziv_servisa_,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(response) {
			$.each(response, function(i,filijala){
				filijale_select.append('<option value = "' + filijala.id + '">' + filijala.punaAdresa + '</option>');
				filijala_select2.append('<option value = "' + filijala.punaAdresa + '">' + filijala.punaAdresa + '</option>');
			});
		},
		async: false,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});

}

function izmjenaInicijalneLozinke() {
	$("#tab-vozila").hide();
	$("#meni").hide();
	$("#izmjenaInicijalneLozinkePoruka").show();
	$("#tab-profil-lozinka").show();
	promjeniLozinku();
}

function postaviMarker(mapa, koordinate) {
	var placemark = new ymaps.Placemark(koordinate);
	mapa.geoObjects.removeAll();
	mapa.geoObjects.add(placemark);
	mapa.setCenter(koordinate);
}

function inicijalizujMape() {
	var coords = [rentACarServis.adresa.latituda, rentACarServis.adresa.longituda];
	mapaRacServisa = new ymaps.Map('mapa', {
        center: coords,
        zoom: zoomLevel,
        controls: []
    });
	
	mapaRacServisa.controls.add('geolocationControl');
	mapaRacServisa.controls.add('typeSelector');
	mapaRacServisa.controls.add('zoomControl');
	postaviMarker(mapaRacServisa, coords);
	
	mapaRacServisa.events.add('click', function(e) {
		var coords = e.get('coords');
		postaviMarker(mapaRacServisa, coords);
		$("#latitudaServisa").val(coords[0]);
		$("#longitudaServisa").val(coords[1]);
	});
	
	
	coords = [44.7866, 20.4489];
	mapaFilijaleDodavanje = new ymaps.Map('dodavanjeFilijaleMapa', {
        center: coords,
        zoom: zoomLevel,
        controls: []
    });
	
	mapaFilijaleDodavanje.controls.add('geolocationControl');
	mapaFilijaleDodavanje.controls.add('typeSelector');
	mapaFilijaleDodavanje.controls.add('zoomControl');
	
	mapaFilijaleDodavanje.events.add('click', function(e) {
		var coords = e.get('coords');
		postaviMarker(mapaFilijaleDodavanje, coords);
		$("#latitudaFilijaleDodavanje").val(coords[0]);
		$("#longitudaFilijaleDodavanje").val(coords[1]);
	});
	
	mapaFilijaleIzmjena = new ymaps.Map('izmjenaFilijaleMapa', {
        center: coords,
        zoom: zoomLevel,
        controls: []
    });
	
	mapaFilijaleIzmjena.controls.add('geolocationControl');
	mapaFilijaleIzmjena.controls.add('typeSelector');
	mapaFilijaleIzmjena.controls.add('zoomControl');
	
	mapaFilijaleIzmjena.events.add('click', function(e) {
		var coords = e.get('coords');
		postaviMarker(mapaFilijaleIzmjena, coords);
		$("#latitudaFilijaleIzmjena").val(coords[0]);
		$("#longitudaFilijaleIzmjena").val(coords[1]);
	});
	
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

function pretraziSlobodnaVozila(){
	let _datumDolaska = $("#input-start").val();
	let _datumOdlaska = $("#input-end").val();
	
	let pretragaVozila = {
		idRac: rentACarServis.id,
		datumPreuzimanja: _datumDolaska,
		datumVracanja: _datumOdlaska
	};
	
	$.ajax({
		type : 'POST',
		url : "../rentACar/pretraziZaBrzuRezervaciju",
		data : JSON.stringify(pretragaVozila),
		headers: createAuthorizationTokenHeader("jwtToken"),
		success : function(data) {
			if(data.length > 0) {
				$("#vozilaBrzeRezervacije").show();
				prikaziVozilaZaBrzuRezervaciju(data);
			} else {
				alert("Nema slobodnih vozila u zadatom periodu.");
			}
		}
	});
}

function prikaziVozilaZaBrzuRezervaciju(vozila){	
	
	let prikaz = $("#prikazVozilaBrzeRezervacije");
	prikaz.empty();
	
	
	$.each(vozila, function(i, vozilo) {
		
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + vozilo.naziv + '</td>');
		noviRed.append('<td class="column1">' + vozilo.marka + '</td>');
		noviRed.append('<td class="column1">' + vozilo.model + '</td>');
		noviRed.append('<td class="column1">' + vozilo.tip_vozila + '</td>');
		noviRed.append('<td class="column1">' + vozilo.godina_proizvodnje + '</td>');
		noviRed.append('<td class="column1">' + vozilo.cijena_po_danu + '</td>');
		
		let sumaOcjena = vozilo.sumaOcjena;
		sumaOcjena = parseFloat(sumaOcjena);
		let brOcjena = vozilo.brojOcjena;
		brOcjena = parseInt(brOcjena);
		if(brOcjena > 0) {
			noviRed.append('<td class="column1">' + sumaOcjena / brOcjena + '</td>');
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column1"><input type="radio" name="voziloBrzaRez" class="voziloBrzaRez" id="vbr' + vozilo.id + '"></td></tr>');
		
		
		prikaz.append(noviRed);
	})
}

function zadajVoziloBrzeRez(){
	let _datumPreuzimanja = $("#input-start").val();
	let _datumVracanja = $("#input-end").val();
	let _idVozila = -1;
	let voziloIzabrano = false;
	
	let brzeVozilaRezBtns = $(".voziloBrzaRez");
	$.each(brzeVozilaRezBtns, function(i, btn) {
		if(btn.checked) {
			voziloIzabrano = true;
			_idVozila = btn.id.substring(3); //ime dugmeta je tipa "sbr<id sobe>"
			
			let brzaRezervacija = {
				idVozila: _idVozila,
				datumPreuzimanjaVozila: Date.parse(_datumPreuzimanja),
				datumVracanjaVozila: Date.parse(_datumVracanja),
				baznaCijena: 0,
				procenatPopusta: 0
			};
			
			$.ajax({
				type : 'POST',
				url : "../rentACar/dodajBrzuRezervaciju",
				data : JSON.stringify(brzaRezervacija),
				headers: createAuthorizationTokenHeader("jwtToken"),
				success : function(responseBrzaRez) {
					tekucaBrzaRezervacija = responseBrzaRez;
					alert("Vozilo je uspješno dodato na brzu rezervaciju.");
					$("#izborVozilaBrzeRezervacije").hide();
					$("#definisanjePopustaBrzeRezervacije").show();
					$("#izborSobeBrzeRezervacijeBtn")[0].checked = false;
					$("#definisanjePopustaBrzeRezervacijeBtn")[0].checked = false;
				}
			});
			return;
		}
	});
	if(!voziloIzabrano) {
		alert("Morate izabrati vozilo za brzu rezervaciju.");
	}
}

