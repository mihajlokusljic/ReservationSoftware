var rentACarServis = null;
var korisnik = null;

$(document).ready(function() {
	korisnikInfo();
	ucitajPodatkeSistema();
	dobaviSvaVozilaServisa();
	
	$("#vozila").click(function(e){
		e.preventDefault();
		$("#tab-filijala").hide();
		$("tab-dodaj-vozilo").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").show();
		dobaviSvaVozilaServisa();
	
	});
	$("#dodaj_vozilo_dugme").click(function(e){
		e.preventDefault();
		$("#tab-filijala").hide();
		$("#tab-profil-servisa").hide();
		$("#tab-profil-kor").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-odjava").hide();
		$("#tab-vozila").hide();
		$("#tab-dodaj-vozilo").show();
		dodajVozilo();
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
	$.ajax({
		type: "GET",
		url: "../rentACar/svaVozilaServisa/" + rentACarServis.naziv,
		dataType : "json",
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(response) {
			if(response == "Servis koji ste unijeli ne postoji.") {
				alert(response);
			} else {
				prikaziVozila(response);
				
			}
		},
		 error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
			}
	});
}

function prikaziVozila(vozila){

	$("#tab-dodaj-vozilo").hide();
	$("#tab-vozila").show();
	$("#tab-prikaz-vozila").hide();


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
		$('#tip_vozila_input1').val(vozilo.tip_vozila);
		$('#tip_vozila_input1').trigger('change'); //obavjestavanje komponenti
		
		
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
					cijena_po_danu:cijena_v
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
		
		let vozilo = {
				naziv:naziv,
				marka:marka,
				model:model,
				godina_proizvodnje:godina_,
				broj_sjedista:broj_sjedista_v,
				tip_vozila:tip_vozila,
				broj_vrata:broj_vrata_v,
				kilovati:0,
				cijena_po_danu:cijena_v
		};
		
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