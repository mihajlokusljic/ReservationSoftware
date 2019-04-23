var rentACarServis = null;

$(document).ready(function() {
	
	korisnikInfo();
	ucitajPodatkeSistema();
	
	$("#vozila").click(function(e){
		$("#tab-filijala").hide();
		$("#tab-dodajf").hide();
		$("#tab-profil").hide();
		$("#tab-vozila").show();
		$("#tab-dodaj").show();
		dobaviSvaVozilaServisa(rentACarServis.naziv);
	});
	
	$("#filijale").click(function(e){
		$("#tab-vozila").hide();
		$("#tab-dodaj").hide();
		$("#tab-profil").hide();
		$("#tab-filijala").show();
		$("#tab-dodajf").show();
		dobaviSveFilijale();
	});
	
	$("#profil").click(function(e){
		$("#tab-vozila").hide();
		$("#tab-dodaj").hide();
		$("#tab-filijala").hide();
		$("#tab-dodajf").hide();
		$("#tab-profil").show();
		$.ajax({
			type: "GET",
			url: "../rentACar/prikazServisa/" + rentACarServis.naziv,
			dataType : "json",
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				prikazProfila(response);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
});

function dobaviSvaVozilaServisa(naziv_servisa){
	$.ajax({
		type: "GET",
		url: "../rentACar/svaVozilaServisa/" + naziv_servisa,
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
	$("div#tab-vozila").empty();
	if (vozila.length == 0){
		$("div#tab-vozila").append('<h3>Trenutno nema vozila u sistemu.</h3>');
		
	}
	
	var tabela = $("<table border=\"2\" id = \"vozilaPrikaz\"></table>");
	tabela.append("<tr>"+
				"<th>Naziv</th>"+
				"<th>Marka</th>"+
				"<th>Model</th>"+
				"<th>Tip</th>"+
				"<th>Godina proizvodnje</th>"+
				"<th>Broj sjedista</th>"+
				"<th>Broj vrata</th>"+
				"<th>Kilovati</th>"+
				"<th>Cijena po danu</th>"+
				"<th></th>" +
				"<th></th>" +
			"</tr>");
	$.each(vozila, function(i,vozilo){
		tabela.append("<tr><td>" + vozilo["naziv"] + "</td><td>" + vozilo["marka"] + "</td><td>" + vozilo["model"]
		 + "</td><td>" + vozilo["tip_vozila"]  + "</td><td>" + vozilo["godina_proizvodnje"] + "</td><td>" + vozilo["godina_proizvodnje"] +
		 "</td><td>" + vozilo["broj_vrata"] + "</td><td>" + vozilo["kilovati"] + "</td><td>" + vozilo["cijena_po_danu"] + "</td>+" +
		 '<td><a href = "javascript:void(0)" class = "ukloni" id="' + i + '">Ukloni</a></td>' +  '<td><a href = "javascript:void(0)" class = "izmjeni" id="' + i + '">Izmjeni</a></td>' + 		
		"</tr>" )
	});
	if (vozila.length != 0){
		$("div#tab-vozila").append(tabela);
	}
	$("div#tab-vozila").append('<br><a href = "javascript:void(0)" id = "dodajVozilo">Dodaj novo vozilo</a>');
	dodajVozilo();
	let naziv_servisa = rentACarServis.naziv;
	$(".ukloni").click(function(e){
		let vozilo = vozila[e.target.id];
		$.ajax({
			type : "GET",
			url : "../rentACar/ukloniVozilo/" + vozilo["id"],
			dataType : "json",
			headers: createAuthorizationTokenHeader("jwtToken"),
			success : function(response){
				dobaviSvaVozilaServisa(naziv_servisa)			
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
	$(".izmjeni").click(function(e){
		let vozilo = vozila[e.target.id];		
		let div_forma = $("#tab-dodaj");		
		div_forma.empty();
		div_forma.append('<h1>Izmjena vozila</h1>' + 
				'<form id = "forma_izmjeni_vozilo"><table id = "tabela_vozila"><tr>' +
					'<tr><td>Naziv:</td><td><input type = "text" id = "naziv" value = "'+vozilo.naziv +'"/></td></tr>' +
					'<tr><td>Marka:</td><td><input type = "text" id = "marka" value = "'+vozilo.marka +'"/></td></tr>'+
					'<tr><td>Model:</td><td><input type = "text" id = "model" value = "'+vozilo.model +'"/></td></tr>' +
					'<tr><td>Tip vozila:</td><td><select id = "tip_vozila"></select></td></tr>' +
					'<tr>	<td>Godina proizvodje:</td><td><input type = "text" id = "godina" value = "'+vozilo.godina_proizvodnje +'"/></td></tr>' + 
					'<tr><td>Broj sjedista:</td><td><input type = "text" id = "broj_sjedista" value = "'+vozilo.broj_sjedista +'"/></td></tr>' +
					'<tr><td>Broj vrata:</td><td><input type = "text" id = "broj_vrata" value = "'+vozilo.broj_vrata +'"/></td></tr>' +
					'<tr><td>Kilovati:</td><td><input type = "text" id = "kilovati" value = "'+vozilo.kilovati +'"/></td></tr>' +
					'<tr><td>Cijena po danu:</td><td><input type = "text" id = "cijena" value = "'+vozilo.cijena_po_danu +'"/></td></tr></table>' + 
				'<td><input type="submit" value="Izmjeni"/></td></form>');
		
		var select = document.getElementById("tip_vozila");
		var tipovi = [];
		if (vozilo.tip_vozila == "Automobil"){
			tipovi.push("Automobil");
			tipovi.push("Kombi");
		}
		else if (vozilo.tip_vozila == "Kombi"){
			tipovi.push("Kombi");
			tipovi.push("Automobil");
		}
		for (el in tipovi){
			select.options[select.options.length] = new Option(tipovi[el],tipovi[el]);
		}
		
		$("#forma_izmjeni_vozilo").submit(function(e) {
			e.preventDefault();
			let naziv_v = $("#naziv").val();
			if (naziv_v == ''){
				alert("Niste unijeli naziv vozila");
				return;
			}
			let marka_v = $("#marka").val();
			if (marka_v == ''){
				alert("Niste unijeli marku vozila");
				return;
			}
			let model_v = $("#model").val();
			if (model_v == ''){
				alert("Niste unijeli model_v vozila");
				return;
			}
			let tip_vozila_ = $("#tip_vozila").val();
			let godina_s = $("#godina").val();
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
			
			let broj_sjedista_s = $("#broj_sjedista").val();
			if (broj_sjedista_s == ''){
				alert("Niste unijeli broj sjedista vozila");
				return;
			}
			var broj_sjedista_v = parseInt(broj_sjedista_s);
			if (isNaN(broj_sjedista_v) || broj_sjedista_v<0){
				alert("Broj sjedista mora biti broj veći od 0.");
				return;
			}
			let broj_vrata_s = $("#broj_vrata").val();
			if (broj_vrata_s == ''){
				alert("Niste unijeli broj vrata vozila");
				return;
			}
			var broj_vrata_v = parseInt(broj_vrata_s);
			if (isNaN(broj_vrata_v) || broj_vrata_v<0){
				alert("Broj vrata mora biti broj veći od 0.");
				return;
			}
			let kilovati_s = $("#kilovati").val();
			if (kilovati_s == ''){
				alert("Niste unijeli broj vrata vozila");
				return;
			}
			var kilovati_v = parseInt(kilovati_s);
			if (isNaN(kilovati_v) || kilovati_v<0){
				alert("Kilovati moraju biti broj veći od 0.");
				return;
			}
			let cijena_s = $("#cijena").val();
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
					kilovati:kilovati_v,
					cijena_po_danu:cijena_v
			};
			$.ajax({
				type : "POST",
				url : "../rentACar/izmjeniVozilo/" + voz.Id,
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify(voz),
				headers: createAuthorizationTokenHeader("jwtToken"),
				success : function(response){
					dobaviSvaVozilaServisa(naziv_servisa);			
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("AJAX error: " + errorThrown);
				}
			});
			
		});
		
		
	});

}


function prikazFilijala(filijale){
	//e.preventDefault();
	let div_ = $("#tab-filijala");
	div_.empty();
	if (filijale.length == 0){
		div_.append('<h3>Trenutno nema dodatih filijala u sistemu.</h3>');
		
	}
	let tabela = $('<table border = "1"></table>');
	tabela.append('<tr><th>Puna adresa</th><th></th><th></th></tr>');
	$.each(filijale, function(i,filijala){

		tabela.append("<tr><td>" + '<input type = "text" id = "adresa_filijale_nova" value = "' + filijala.punaAdresa +  '" /></td>' +
		 '<td><a href = "javascript:void(0)" class = "uklonif" id="' + i + '">Ukloni</a></td>' +  '<td><a href = "javascript:void(0)" class = "izmjenif" id="' + i + '">Izmjeni lokaciju</a></td>' + 		
		"</tr>" )
	});
	if (filijale.length != 0){
		div_.append("<h3>Adrese filijala sistema</h3>");
		div_.append(tabela);
	}
	div_.append('<br><a href = "javascript:void(0)" id = "dodajFilijalu">Dodaj novu filijalu</a>');
	dodajFilijalu();
	$(".uklonif").click(function(e){
		let filijala = filijale[e.target.id];
		$.ajax({
			type : "GET",
			url : "../rentACar/ukloniFilijalu/" + filijala.id,
			dataType : "json",
			headers: createAuthorizationTokenHeader("jwtToken"),
			success : function(response){
				prikazFilijalaOdabranogServisa();			
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	$(".izmjenif").click(function(e){
		let filijala = filijale[e.target.id];
		let nova_lokacija = $("#adresa_filijale_nova").val();
		if (nova_lokacija == ''){
			alert("Ne mozete unijeti praznu lokaciju.");
			return;
		}
		$.ajax({
			type : "GET",
			url : "../rentACar/izmjeniFilijalu/" + filijala.id + "/" + nova_lokacija,
			dataType : "json",
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
}
function prikazFilijalaOdabranogServisa(){
		let naziv_servisa_ = rentACarServis.naziv;
		$.ajax({
			type: "GET",
			url: "../rentACar/dobaviFilijale/" + naziv_servisa_,
			dataType : "json",
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				prikazFilijala(response);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	
}

function korisnikInfo(){
	let token = getJwtToken("jwtToken");
	$.ajax({
		type : 'GET',
		url : "../korisnik/getInfo",
		dataType : "json",
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(data){
			if(data != null){
				alert("Ulogovani ste kao administrator rent-a-car servisa: " + data.ime + " " + data.prezime );
			}
			else{
				alert("nepostojeci korisnik");
			}
		},
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
				$('p.navbar-brand').append('<span>' + data.naziv + '</span>');

			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + textStatus);
		}
	});
}
function prikazProfila(servis){
	let div_ = $("div#tab-profil");
	div_.empty();
	let forma = $('<form id = "forma_izmjena"></form>');
	let tabela = $('<table border = "1"></table>');

	tabela.append('<tr><td>Naziv: </td><td><input type = "text" id = "naziv" value = "'+servis.naziv+ '" disabled/></td></tr>'
			+	'<tr><td>Adresa: </td><td><input type = "text" id = "adresa" value = "'+servis.adresa.punaAdresa + '" /></td></tr>'
			+	'<tr><td>Promotivni opis: </td><td><input type = "text" id = "opis" value = "'+servis.promotivniOpis + '" /></td></tr>'
	);
	forma.append(tabela);
	forma.append('<input type = "submit" value = "Izmjeni" />');
	div_.append(forma);
	
	$("#forma_izmjena").submit(function(e){
		var naziv_serv = $("#naziv").val();
		var adresa = $("#adresa").val();
		if (adresa == ''){
			alert("Polje za unos adrese servisa ne moze biti prazno.");
			return;
		}
		var opis = $("#opis").val();
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
					prikazProfila(racServis);
				}
				else{
					alert(response);

				}
				
			}
		});
		
	});
}

function dodajVozilo(){
	$('#dodajVozilo').click(function(){
		let naziv_servisa = rentACarServis.naziv;		
		let div_forma = $("#tab-dodaj");
		div_forma.empty();
		div_forma.append('<h3>Dodavanje novog vozila</h3>' + 
				'<form id = "forma_dodaj_vozilo"><table id = "tabela_vozila"><tr>' +
					'<tr><td>Naziv:</td><td><input type = "text" id = "naziv"/></td></tr>' +
					'<tr><td>Marka:</td><td><input type = "text" id = "marka"/></td></tr>'+
					'<tr><td>Model:</td><td><input type = "text" id = "model"/></td></tr>' +
					'<tr><td>Tip vozila:</td><td><select name = "tip_vozila" id = "tip_vozila"><option value = "Automobil">Automobil</option><option value = "Kombi">Kombi</option><option value = "Minibus">Minibus</option><option value = "Motocikl">Motocikl</option>'+
							'</select></td></tr>' +
					'<tr>	<td>Godina proizvodje:</td><td><input type = "number" id = "godina" min = "0"/></td></tr>' + 
					'<tr><td>Broj sjedista:</td><td><input type = "number" id = "broj_sjedista" min = "0" /></td></tr>' +
					'<tr><td>Broj vrata:</td><td><input type = "number" id = "broj_vrata" min = "0"/></td></tr>' +
					'<tr><td>Kilovati:</td><td><input type = "number" id = "kilovati" in = "0"/></td></tr>' +
					'<tr><td>Cijena po danu:</td><td><input type = "number" id = "cijena" min = "0"/></td></tr></table>' + 
				'<td><input type="submit" value="Dodaj"/></td></form>');
		
		$("#forma_dodaj_vozilo").submit(function(e) {
			e.preventDefault();
			let naziv_v = $("#naziv").val();
			if (naziv_v == ''){
				alert("Niste unijeli naziv vozila");
				return;
			}
			let marka_v = $("#marka").val();
			if (marka_v == ''){
				alert("Niste unijeli marku vozila");
				return;
			}
			let model_v = $("#model").val();
			if (model_v == ''){
				alert("Niste unijeli model_v vozila");
				return;
			}
			let tip_vozila_ = $("#tip_vozila").val();
			let godina_s = $("#godina").val();
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
			
			let broj_sjedista_s = $("#broj_sjedista").val();
			if (broj_sjedista_s == ''){
				alert("Niste unijeli broj sjedista vozila");
				return;
			}
			var broj_sjedista_v = parseInt(broj_sjedista_s);
			if (isNaN(broj_sjedista_v) || broj_sjedista_v<0){
				alert("Broj sjedista mora biti broj veći od 0.");
				return;
			}
			let broj_vrata_s = $("#broj_vrata").val();
			if (broj_vrata_s == ''){
				alert("Niste unijeli broj vrata vozila");
				return;
			}
			var broj_vrata_v = parseInt(broj_vrata_s);
			if (isNaN(broj_vrata_v) || broj_vrata_v<0){
				alert("Broj vrata mora biti broj veći od 0.");
				return;
			}
			let kilovati_s = $("#kilovati").val();
			if (kilovati_s == ''){
				alert("Niste unijeli broj vrata vozila");
				return;
			}
			var kilovati_v = parseInt(kilovati_s);
			if (isNaN(kilovati_v) || kilovati_v<0){
				alert("Kilovati moraju biti broj veći od 0.");
				return;
			}
			let cijena_s = $("#cijena").val();
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
					naziv:naziv_v,
					marka:marka_v,
					model:model_v,
					godina_proizvodnje:godina,
					broj_sjedista:broj_sjedista_v,
					tip_vozila:tip_vozila_,
					broj_vrata:broj_vrata_v,
					kilovati:kilovati_v,
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
						dobaviSvaVozilaServisa(naziv_servisa);
					} else {
						alert(response);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("AJAX error: " + errorThrown);
				}
			});
			
		});
		
	});
}

function dobaviSveFilijale(){
		let naziv_servisa_ = rentACarServis.naziv;
		$.ajax({
			type: "GET",
			url: "../rentACar/dobaviFilijale/" + naziv_servisa_,
			dataType : "json",
			headers: createAuthorizationTokenHeader("jwtToken"),
			success: function(response) {
				prikazFilijala(response);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
}

function dodajFilijalu(){
	$("#dodajFilijalu").click(function(e) {
		e.preventDefault();
		let div_ = $("div#tab-dodajf");
		div_.empty();
		div_.append('<h3>Dodavanje nove filijale</h3>' + 
				'<form id = "dodavanje_filijale">'+
				'Adresa:  <input type = "text" id = "adresa_filijale" />'+
				'<input type ="submit" value = "Dodaj" />'+
			'</form>');
		$("#dodavanje_filijale").submit(function(e) {
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
				dataType : "json",
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
		

		
	});
}
