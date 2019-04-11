$(document).ready(function() {
	
	$("#forma_dodaj_vozilo").submit(function(e) {
		e.preventDefault();
		
		let naziv_servisa = $("#naziv_servisa").val();
		if (naziv_servisa == ''){
			alert("Niste unijeli naziv servisa");
			return;
		}
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
	
	$("#forma_za_izmjenu_servisa").submit(function(e) {
		e.preventDefault();		
		let naziv_servisa = $("#naziv_servisa").val();
		if (naziv_servisa == ''){
			alert ("Niste unijeli naziv servisa.");
			return;
		}
		$.ajax({
			type: "POST",
			url: "../rentACar/prikazServisa",
			data: {"nazivServisa" : naziv_servisa},
			success: function(response) {
				if(response == null) {
					alert("U sistemu ne postoji servis sa unijetim nazivom.");
				} else {
					prikazProfila(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	
});

function dobaviSvaVozilaServisa(naziv_servisa){
	$.ajax({
		type: "POST",
		url: "../rentACar/svaVozilaServisa",
		data: {"nazivServisa" : naziv_servisa},
		success: function(response) {
			if(response == null) {
				//alert("Unijeli ste naziv servisa koji ne postoji.");
			} else {
				prikaziVozila(response);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});
}

function prikaziVozila(vozila){
	let tabela = $("#vozilaPrikaz");
	tabela.empty();
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
			"</tr>");
	$.each(vozila, function(i,vozilo){
		tabela.append("<tr><td>" + vozilo["naziv"] + "</td><td>" + vozilo["marka"] + "</td><td>" + vozilo["model"]
		 + "</td><td>" + vozilo["tip_vozila"]  + "</td><td>" + vozilo["godina_proizvodnje"] + "</td><td>" + vozilo["broj_sjedista"] +
		 "</td><td>" + vozilo["broj_vrata"] + "</td><td>" + vozilo["kilovati"] + "</td><td>" + vozilo["cijena_po_danu"] + "</td></tr>" )
	});
}

function prikazProfila(servis){
	let div_ = $(".prikaz_izmjene");
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