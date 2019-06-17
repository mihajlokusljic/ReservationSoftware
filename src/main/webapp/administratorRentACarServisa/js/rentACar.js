var rentACarServis = null;
var korisnik = null;
var filijale = null;
var defaultSlika = "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg";
var mapaRacServisa = null;
var mapaFilijaleDodavanje = null;
var mapaFilijaleIzmjena = null;
var zoomLevel = 17;
let stavkeMenija = ["stavka_vozila", "stavka_filijale", "stavka_brze_rezervacije", "stavka_profil_servisa", "stavka_profil_korisnika", "stavka_izvjestaj", "stavka_odjava", "stavka_izvjestaj"];

$(document).ready(function() {
	
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
		  			});	    	}
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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

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
		$("#izborVozilaBrzeRezervacije").show();
		$("#definisanjePopustaBrzeRezervacije").hide()
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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

	});
	
	$("#pregledanjeBrzihRezervacija").click(function(e) {
		e.preventDefault();
		vratiSveBrzeRezervacije();
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
		$("#tab-prihodi-servisa").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

	});
	
	$("#prikazi_prihode_tab").click(function(e){
		e.preventDefault();
		aktivirajStavkuMenija("stavka_izvjestaj");
		$("#tab-prihodi-servisa").show();
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
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		$("#grafik_rez_vozila").hide();
		$("#tab-slob-rez").hide();

		prihodiServisa();
	});
	
	$("#grafik_rez_vozila_tab").click(function(e){
		e.preventDefault();
		aktivirajStavkuMenija("stavka_izvjestaj");
		$("#grafik_rez_vozila").show();
		$("#tab-prihodi-servisa").hide();
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
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		$("#chartContainer").hide();
		$("#tab-slob-rez").hide();

		grafikRezervisanihVozila();
	});
	
	$("#slob_rez_vozila_tab").click(function(e){
		e.preventDefault();
		aktivirajStavkuMenija("stavka_izvjestaj");
		$("#grafik_rez_vozila").hide();
		$("#tab-prihodi-servisa").hide();
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
		$("#tab-brze-rezervacije-pregledanje").hide();
		$("#tab-brze-rezervacije-dodavanje").hide();
		$("#chartContainer").hide();
		$("#tab-slob-rez").show();
		slobodnaRezervisanaVozila();
		
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
				if(!korisnik.lozinkaPromjenjena) {
					izmjenaInicijalneLozinke();
				}
				else{
					dobaviSvaVozilaServisa();
					ponudiFilijale();
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
				swal({
					  title: response,
					  icon: "error",
					  timer: 2500
					})
			} else {
				prikaziVozila(response);
				
			}
		},
		async: false
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
		var prosjecnaOcjena = 0;
		if (vozilo.sumaOcjena != 0) {
			prosjecnaOcjena = (vozilo.sumaOcjena/vozilo.brojOcjena).toFixed(2);
		}
		tbody.append('<tr><td class = "column1">' + vozilo["naziv"] + '</td><td class = "column2">' + vozilo["marka"] + '</td><td class = "column3">' + vozilo["model"]
		 + '</td><td class = "column4">' + vozilo["tip_vozila"]  + '</td><td class = "column5">' + vozilo["godina_proizvodnje"] + '</td><td class = "column6">' + vozilo["cijena_po_danu"] +
		 '</td><td class = "column6">' +  prosjecnaOcjena + 
		 '</td><td class = "column7"><a href = "javascript:void(0)" class = "detaljan_prikaz" id = "' + i + '">Detaljan prikaz</a></td></tr>');
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
				swal({
					  title: "Niste unijeli naziv vozila",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			let marka_v = $("#marka_input1").val();
			if (marka_v == ''){
				swal({
					  title: "Niste unijeli marku vozila",
					  icon: "warning",
					  timer: 2500
					})
				
				return;
			}
			let model_v = $("#model_input1").val();
			if (model_v == ''){
				swal({
					  title: "Niste unijeli model vozila",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			let tip_vozila_ = $("#tip_vozila_input1").val();
			let godina_s = $("#godina_input1").val();
			if (godina_s == ''){
				swal({
					  title: "Niste unijeli godinu proizvodnje vozila",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			var dt = new Date();
	        var trenutna_godina = dt.getFullYear();
			var godina = parseInt(godina_s);
			if (isNaN(godina) || godina<0){
				swal({
					  title: "Niste unijeli validnu godinu proizvodnje.",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			if (godina>trenutna_godina) {
				swal({
					  title: "Godina proizvodnje mora biti manja od trenutne godine.",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			
			let broj_sjedista_s = $("#broj_sjedista_input1").val();
			if (broj_sjedista_s == ''){
				swal({
					  title: "Niste unijeli broj sjedišta vozila",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			var broj_sjedista_v = parseInt(broj_sjedista_s);
			if (isNaN(broj_sjedista_v) || broj_sjedista_v<0){
				swal({
					  title: "Broj sjedista mora biti broj veći od 0.",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			let broj_vrata_s = $("#broj_vrata_input1").val();
			if (broj_vrata_s == ''){
				swal({
					  title: "Niste unijeli broj vrata vozila",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			var broj_vrata_v = parseInt(broj_vrata_s);
			if (isNaN(broj_vrata_v) || broj_vrata_v<0){
				swal({
					  title: "Broj vrata mora biti broj veći od 0.",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			
			let cijena_s = $("#cijena_input1").val();
			if (cijena_s == ''){
				swal({
					  title: "Niste unijeli cijenu usluga vozila po danu",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			var cijena_v = parseInt(cijena_s);
			if (isNaN(cijena_v) || cijena_v<0){
				swal({
					  title: "Cijena usluge po danu mora biti decimalan broj veci od 0.",
					  icon: "warning",
					  timer: 2500
					})
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
			swal({
				  title: "Niste unijeli naziv vozila.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		let marka = $("#marka_input").val();
		if (marka == ''){
			swal({
				  title: "Niste unijeli marku vozila.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		let model = $("#model_input").val();
		if (model == ''){
			swal({
				  title: "Niste unijeli model vozila.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		let tip_vozila = $("#tip_vozila_input").val();
		
		let godina = $("#godina_proizvodnje_input").val();
		if (godina == ''){
			swal({
				  title: "Niste unijeli godinu proizvodnje vozila.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		var dt = new Date();
	    var trenutna_godina = dt.getFullYear();
		var godina_ = parseInt(godina);
		if (isNaN(godina_) || godina_<0){
			swal({
				  title: "Niste unijeli validnu godinu proizvodnje.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		if (godina_>trenutna_godina) {
			swal({
				  title: "Godina proizvodnje mora biti manja od trenutne godine.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		let broj_sjedista_s = $("#broj_sjedista_input").val();
		if (broj_sjedista_s == ''){
			swal({
				  title: "Niste unijeli broj sjedišta vozila.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		var broj_sjedista_v = parseInt(broj_sjedista_s);
		
		let broj_vrata_s = $("#broj_vrata_input").val();
		if (broj_vrata_s == ''){
			swal({
				  title: "Niste unijeli broj vrata vozila.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		var broj_vrata_v = parseInt(broj_vrata_s);
	
		
		let cijena_s = $("#cijena_input").val();
		if (cijena_s == ''){
			swal({
				  title: "Niste unijeli cijenu usluga vozila po danu.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		var cijena_v = parseInt(cijena_s);
		if (isNaN(cijena_v) || cijena_v<0){
			swal({
				  title: "Cijena usluge po danu mora biti decimalan broj veci od 0.",
				  icon: "warning",
				  timer: 2500
				})
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
					swal({
						  title: "Uspješno ste dodali novo vozilo.",
						  icon: "success",
						  timer: 2500
						})
					dobaviSvaVozilaServisa();
				} else {
					swal({
						  title: response,
						  icon: "warning",
						  timer: 2500
						})
				}
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
				swal({
					  title: "Ne mozete unijeti praznu lokaciju.",
					  icon: "warning",
					  timer: 2500
					})
				return;
			}
			let _lat = $("#latitudaFilijaleIzmjena").val();
			let _long = $("#longitudaFilijaleIzmjena").val();
			if(_lat == "" || _long == "") {
				swal({
					  title: "Morate označiti lokaciju filijale na mapi.",
					  icon: "warning",
					  timer: 2500
					})
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
						swal({
							  title: response,
							  icon: "warning",
							  timer: 2500
							})						
						prikazFilijalaOdabranogServisa();				

					}
					else{
						swal({
							  title: "Uspjesno ste izmjenili adresu filijale",
							  icon: "warning",
							  timer: 2500
							})	
						prikazFilijalaOdabranogServisa();				
					}
					
				}
			});
		});
		
	});
}

function dodajFilijalu(){
	
	$("#forma_dodaj_filijalu").unbind().submit(function(e) {
		e.preventDefault();
		let naziv_servisa = rentACarServis.naziv;

		let adresa = $("#adresa_filijale").val();
		if (adresa == ''){
			swal({
				  title: "Niste unijeli adresu.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		let _lat = $("#latitudaFilijaleDodavanje").val();
		let _long = $("#longitudaFilijaleDodavanje").val();
		
		if(_lat == "" || _long == "") {
			swal({
				  title: "Morate označiti lokaciju filijale na mapi.",
				  icon: "warning",
				  timer: 2500
				})	
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
	let sumaOcjena = rentACarServis.sumaOcjena;
	sumaOcjena = parseFloat(sumaOcjena);
	let brOcjena = rentACarServis.brojOcjena;
	brOcjena = parseInt(brOcjena);
	if(brOcjena > 0) {
		var prosjek = sumaOcjena/brOcjena;
		prosjek = prosjek.toFixed(2);

		$("#ocjenaRac").val(prosjek);
	} else {
		$("#ocjenaRac").val("Nema ocjena");
	}
}

function profilServisa(){
	prikaziPodatkeServisa();
	
	$("#profil_servisa_forma").unbind().submit(function(e){
		e.preventDefault();
		var naziv_serv = $("#profil_servisa_naziv").val();
		var adresa = $("#profil_servisa_adresa").val();
		if (adresa == ''){
			swal({
				  title: "Polje za unos adrese servisa ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})				
			return;
		}
		var _lat = $("#latitudaServisa").val();
		var _long = $("#longitudaServisa").val();
		
		if(_lat == "" || _long == "") {
			swal({
				  title: "Morate zadati lokaciju rent-a-car servisa na mapi.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		
		var opis = $("#profil_servisa_opis").val();
		if (opis == ''){
			swal({
				  title: "Polje za unos promotivnog opisa servisa ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})	
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
					swal({
						  title: "Uspješno ste izmjenili profil.",
						  icon: "success",
						  timer: 2500
						})	
					ucitajPodatkeSistema();
					profilServisa();
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


function odjava() {
	removeJwtToken();
	window.location.replace("../pocetnaStranica/index.html");
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
					swal({
						  title: "Izmjena nije uspjela.",
						  icon: "error",
						  timer: 2500
						})
				}
				else{
					swal({
						  title: "Uspješno ste izmjenili profil.",
						  icon: "success",
						  timer: 2500
						})
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
			headers : createAuthorizationTokenHeader("jwtToken"),
			contentType : "application/json",
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
					setJwtToken("jwtToken", data.accessToken);
					swal({
						  title: "Uspješno ste izmjenili lozinku.",
						  icon: "success",
						  timer:2000
						}).then(function(){
							if(!lozinkaMijenjana) {
								$("#izmjenaInicijalneLozinkePoruka").hide();
								$("#tab-profil-lozinka").hide();
								$("#meni").show();					
								korisnik.lozinkaPromjenjena = true;
								dobaviSvaVozilaServisa();
								ponudiFilijale();
							}
						})
					
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
		async: false
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
	for(var i in stavkeMenija) {
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
				swal({
					  title: "Nema slobodnih vozila u zadatom periodu.",
					  icon: "warning",
					  timer:2000
					});
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
				datumPreuzimanjaVozila: _datumPreuzimanja,
				datumVracanjaVozila: _datumVracanja,
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
					swal({
						  title: "Vozilo je uspješno dodato na brzu rezervaciju.",
						  icon: "success",
						  timer:2000
						});
					$("#ukupnaCijenaBezPopustaBrzeRezervacije").val(tekucaBrzaRezervacija.baznaCijena);
					$("#ukupnaCijenaSaPopustomBrzeRezervacije").val(tekucaBrzaRezervacija.baznaCijena);
					$("#izborVozilaBrzeRezervacije").hide();
					$("#definisanjePopustaBrzeRezervacije").show();
					$("#izborVozilaBrzeRezervacijeBtn")[0].checked = false;
					$("#definisanjePopustaBrzeRezervacijeBtn")[0].checked = true;
				}
			});
			return;
		}
	});
	if(!voziloIzabrano) {
		swal({
			  title: "Morate izabrati vozilo za brzu rezervaciju.",
			  icon: "warning",
			  timer:2000
			});
	}
}

function zadavanjePopustaBrzeRezervacije(){
	if(tekucaBrzaRezervacija == null) {
		swal({
			  title: "Morate izabrati vozilo.",
			  icon: "warning",
			  timer:2000
			});
	}
	let popustProc = $("#procenatPopustaBrzeRezervacije").val();
	tekucaBrzaRezervacija.procenatPopusta = popustProc;
	$.ajax({
		type : 'POST',
		url : "../rentACar/zadajPopustBrzojRezervaciji",
		data : JSON.stringify(tekucaBrzaRezervacija),
		headers: createAuthorizationTokenHeader("jwtToken"),
		success : function(responseBrzaRez) {
			tekucaBrzaRezervacija = null;
			swal({
				  title: "Usješno ste definisali popust za brzu rezervaciju.",
				  icon: "success",
				  timer:2000
				});
			resetBrzeRezervacijeView();	
		}
	});
}

function resetBrzeRezervacijeView() {
	$("#izborVozilaBrzeRezervacije").show();
	$("#definisanjePopustaBrzeRezervacije").hide();
	$("#izborVozilaBrzeRezervacijeBtn")[0].checked = true;
	$("#definisanjePopustaBrzeRezervacijeBtn")[0].checked = false;
	$("#vozilaBrzeRezervacije").hide();
	$("#prikazVozilaBrzeRezervacije").empty();
	$("#pretragaVozilaForm")[0].reset();
	let uslugeIzborBtns = $(".uslugaBrzaRezBtn");
	$.each(uslugeIzborBtns, function(i, btn) {
		btn.checked = false;
	});
	$("#ukupnaCijenaBezPopustaBrzeRezervacije").val(0);
	$("#procenatPopustaBrzeRezervacije").val(0);
	$("#ukupnaCijenaSaPopustomBrzeRezervacije").val(0);
	$("#tab-brze-rezervacije-pregledanje").show();
	$("#izborVozilaBrzeRezervacije").hide();
	$("#tab-brze-rezervacije-dodavanje").hide();
	vratiSveBrzeRezervacije();
}

function azurirajCijeneBrzeRezervacije(){
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
	let popust = tekucaBrzaRezervacija.baznaCijena * procenatPopusta / 100;
	let cijenaSaPopustom = tekucaBrzaRezervacija.baznaCijena - popust;
	$("#ukupnaCijenaSaPopustomBrzeRezervacije").val(cijenaSaPopustom);
}

function vratiSveBrzeRezervacije(){
	let idServisa = rentACarServis.id;
	$.ajax({
		type: "GET",
		url: "../rentACar/dobaviBrzeRezervacije/" + idServisa,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(response) {
			prikaziBrzeRez(response);
		}
	});
}



function prikaziBrzeRez(brzeRez){
	let prikaz = $("#prikazBrzeRezervacije");
	prikaz.empty();
	
	$.each(brzeRez, function(i, br) {
		
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1">' + br.nazivVozila + '</td>');
		noviRed.append('<td class="column1">' + br.nazivServisa + '</td>');
		noviRed.append('<td class="column1">' + br.pocetniDatum + '</td>');
		noviRed.append('<td class="column1">' + br.krajnjiDatum + '</td>');
		noviRed.append('<td class="column1">' + br.punaCijena + '</td>');
		noviRed.append('<td class="column1">' + br.cijenaSaPopustom + '</td>');
		
		prikaz.append(noviRed);
	});
}

function prihodiServisa(){
	$("#prihod_id").hide();
	$("#forma_prihodi").submit(function(e){
		e.preventDefault();
		let _datumPocetni = $("#input-start-2").val();
		let _datumKrajnji = $("#input-end-2").val();
		
		let datumiZaPrihod = {
				datumPocetni : _datumPocetni,
				datumKrajnji : _datumKrajnji
		};
		
		let idServisa = rentACarServis.id;
		$.ajax({
			type : 'POST',
			url : "../rentACar/prihodServisa/" + idServisa,
			data : JSON.stringify(datumiZaPrihod),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				$("#prihod_id").text("Ostvareni prihodi: " + response);
				$("#prihod_id").show();
			}
		});
	});
}

function grafikRezervisanihVozila(){
	
	$("#prikazGrafika").submit(function(e){
		e.preventDefault();
		let idServisa = rentACarServis.id;

		let _datumPocetni = $("#input-start-3").val();
		let _datumKrajnji = $("#input-end-3").val();
		
		if (_datumPocetni == '') {
			swal({
				  title: "Morate unijeti početni datum",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		if (_datumKrajnji == '') {
			swal({
				  title: "Morate unijeti krajnji datum",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		let tergetUrl = "../rentACar/dnevniIzvjestaj";
		let text = "Broj rezervisanih vozila po danu";
		let axisX = "Dani";
		
		if($("#grafikDnevniBtn").is(":checked")) {
			tergetUrl = "../rentACar/dnevniIzvjestaj";
			text = "Broj rezervisanih vozila na dnevnom nivou";
			axisX = "Dani";
		}
		
		if($("#grafikNedeljniBtn").is(":checked")) {
			tergetUrl = "../rentACar/nedeljniIzvjestaj";
			text = "Broj rezervisanih vozila na svakih 7 dana";
			axisX = "Nedelje";
		}
		else if ($("#grafikMjesecniBtn").is(":checked")) {
			tergetUrl = "../rentACar/mjesecniIzvjestaj";
			text = "Broj rezervisanih vozila na svakih mjesec dana";
			axisX = "Mjeseci";
		}
		
		
		let datumiZaIzvjestaj = {
				datumPocetni : _datumPocetni,
				datumKrajnji : _datumKrajnji
		}
		$.ajax({
			type : 'POST',
			url : tergetUrl+ "/" + idServisa,
			data : JSON.stringify(datumiZaIzvjestaj),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				prikaziGrafik(response,text,axisX);
			}
		});
		
	});
}

function prikaziGrafik (izvjestaj,text,axisX) {
	var dataP = [];
	
	for (var i = 0; i < izvjestaj.brojeviYOsa.length; i++) {
		dataP.push({"y": izvjestaj.brojeviYOsa[i], "label" : izvjestaj.vrijednostiXOse[i]});
	}
	
	var options = {
			title:{
				text: text  
			},
			axisY:{
				title:"Broj vozila"
			},
			axisX:{
				title:axisX
			},
			data: [{
				dataPoints: dataP
		    }]
		};
	$("#chartContainer").show();
	$("#chartContainer").CanvasJSChart(options);
}

function slobodnaRezervisanaVozila(){
	$("#prikazSlobodnihVozila").empty();
	$("#prikazRezervisanihVozila").empty();
	
	$("#forma_slob_rez").submit(function(e){
		e.preventDefault();
		let _datumPocetni = $("#input-start-4").val();
		let _datumKrajnji = $("#input-end-4").val();
		
		if (_datumPocetni == '') {
			swal({
				  title: "Morate unijeti početni datum",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		if (_datumKrajnji == '') {
			swal({
				  title: "Morate unijeti krajnji datum",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		let datumiZaIzvjestaj = {
				datumPocetni : _datumPocetni,
				datumKrajnji : _datumKrajnji
		}
		let idServisa = rentACarServis.id;
		$.ajax({
			type : 'POST',
			url : "../rentACar/slobodnaVozila/"+ idServisa,
			data : JSON.stringify(datumiZaIzvjestaj),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				prikaziSlobodnaVozila(response);
			}
		});
		$.ajax({
			type : 'POST',
			url : "../rentACar/rezervisanaVozila/"+ idServisa,
			data : JSON.stringify(datumiZaIzvjestaj),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				prikaziRezervisanaVozila(response);
			}
		});
	});
}

function prikaziSlobodnaVozila(vozila) {
	$("#prikazSlobodnihVozila").empty();
	$.each(vozila, function(i,vozilo){
		var prosjecnaOcjena = 0;
		if (vozilo.sumaOcjena != 0) {
			prosjecnaOcjena = (vozilo.sumaOcjena/vozilo.brojOcjena).toFixed(2);
		}
		$("#prikazSlobodnihVozila").append('<tr><td class = "column1">' + vozilo["naziv"] + '</td><td class = "column2">' + vozilo["marka"] + '</td><td class = "column3">' + vozilo["model"]
		 + '</td><td class = "column4">' + vozilo["tip_vozila"]  + '</td><td class = "column5">' + vozilo["godina_proizvodnje"] + '</td><td class = "column6">' + vozilo["cijena_po_danu"] +
		 '</td><td class = "column6">' +  prosjecnaOcjena);
	});
}

function prikaziRezervisanaVozila(vozila) {
	$("#prikazRezervisanihVozila").empty();
	$.each(vozila, function(i,vozilo){
		var prosjecnaOcjena = 0;
		if (vozilo.sumaOcjena != 0) {
			prosjecnaOcjena = (vozilo.sumaOcjena/vozilo.brojOcjena).toFixed(2);
		}
		$("#prikazRezervisanihVozila").append('<tr><td class = "column1">' + vozilo["naziv"] + '</td><td class = "column2">' + vozilo["marka"] + '</td><td class = "column3">' + vozilo["model"]
		 + '</td><td class = "column4">' + vozilo["tip_vozila"]  + '</td><td class = "column5">' + vozilo["godina_proizvodnje"] + '</td><td class = "column6">' + vozilo["cijena_po_danu"] +
		 '</td><td class = "column6">' +  prosjecnaOcjena);
	});

}