var rentACarServis = null;
var korisnik = null;

$(document).ready(function() {
	korisnikInfo();
	ucitajPodatkeSistema();
	dobaviSvaVozilaServisa();
	
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
		$("#tab-vozila").show();
		dobaviSvaVozilaServisa();
	
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

		profilServisa();
	});

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
		 )
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
		
		$("#forma_izmjeni_filijalu").unbind().submit(function(e){
			e.preventDefault();
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
		
		$.ajax({
			type: "GET",
			url: "../rentACar/dodajFilijalu/" + naziv_servisa + "/" + adresa,
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				if(response != '') {
					alert(response);
				} else {
					prikazFilijalaOdabranogServisa();
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
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

function profilServisa(){
	$("#profil_servisa_naziv").val(rentACarServis.naziv);
	$("#profil_servisa_adresa").val(rentACarServis.adresa.punaAdresa);
	$("#profil_servisa_opis").val(rentACarServis.promotivniOpis);
	
	$("#profil_servisa_forma").unbind().submit(function(e){
		e.preventDefault();
		var naziv_serv = $("#profil_servisa_naziv").val();
		var adresa = $("#profil_servisa_adresa").val();
		if (adresa == ''){
			alert("Polje za unos adrese servisa ne moze biti prazno.");
			return;
		}
		var opis = $("#profil_servisa_opis").val();
		if (opis == ''){
			alert("Polje za unos promotivnog opisa servisa ne moze biti prazno.");
			return;
		}
		let racServis = {
				naziv: naziv_serv, 
				adresa: { punaAdresa : adresa }, 
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
