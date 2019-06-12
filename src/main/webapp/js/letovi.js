$(document).ready(function() {
	
	// ucitavanje podataka o letovima
	ucitajPodatke("../letovi/dobaviSve", "letoviPrikaz");
	
	
	// dodavanje leta
	$("#dodavanjeLetaForm").submit(function(e) {
		e.preventDefault();
		
		let brojLeta = $("#brojLeta").val();
		let polaziste = $("#polaziste").val();
		let odrediste = $("#odrediste").val();
		let datumPoletanja = $("#datumPoletanja").val();
		let datumSletanja = $("#datumSletanja").val();
		let duzinaPutovanja = $("#duzinaPutovanja").val();
		let brojPresjedanja = $("#brojPresjedanja").val();
		let kapacitetPrva = $("#kapacitetPrvaKlasa").val();
		let kapacitetBiznis = $("#kapacitetBiznis").val();
		let kapacitetEkonomska = $("#kapacitetEkonomska").val();
		let cijenaKarte = $("#cijena").val();
		
		
		if (brojLeta == "" || polaziste == "" || odrediste == "" || datumPoletanja == "" || datumSletanja == "" ||
				duzinaPutovanja == "" || brojPresjedanja == "" || kapacitetPrva == "" || kapacitetBiznis == "" ||
				kapacitetEkonomska == "") {
			swal({
				  title: "Polja ne smiju biti prazna. Pokušajte ponovo.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		let flight = {
				brojLeta : brojLeta,
				brojPresjedanja: brojPresjedanja,
				cijenaKarte : cijenaKarte,
				datumPoletanja: datumPoletanja,
				datumSletanja: datumSletanja,
				duzinaPutovanja: duzinaPutovanja,
				kapacitetBiznisKlasa: kapacitetBiznis,
				kapacitetEkonomskaKlasa: kapacitetEkonomska,
				kapacitetPrvaKlasa: kapacitetPrva,
				polaziste: {nazivDestinacije: polaziste},
				odrediste: {nazivDestinacije: odrediste},
		};
		
		$.ajax({
			type: "POST",
			url: "../letovi/dodaj",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(flight),
			success: function(response) {
				if(response == '') {
					let tabelaLetova = $("#letoviPrikaz");
					prikazi(flight, tabelaLetova);
				} else {
					swal(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
})


function ucitajPodatke(putanjaController, idTabeleZaPrikaz) {
	let tabela = $("#" + idTabeleZaPrikaz);
	$.ajax({
		type: "GET",
		url: putanjaController,
		success: function(response) {
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela);
			});
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});
}

function prikazi(podatak, tabelaZaPrikaz) {
	let noviRed = $("<tr></tr>");
	noviRed.append("<td>" + podatak.brojLeta + "</td>");
	noviRed.append("<td>" + podatak.polaziste.nazivDestinacije + "</td>");
	noviRed.append("<td>" + podatak.odrediste.nazivDestinacije + "</td>");
	noviRed.append("<td>" + podatak.datumPoletanja + "</td>");
	noviRed.append("<td>" + podatak.datumSletanja + "</td>");
	noviRed.append("<td>" + podatak.duzinaPutovanja + "</td>");
	noviRed.append("<td>" + podatak.brojPresjedanja + "</td>");
	noviRed.append("<td>" + podatak.kapacitetPrvaKlasa + "</td>");
	noviRed.append("<td>" + podatak.kapacitetBiznisKlasa + "</td>");
	noviRed.append("<td>" + podatak.kapacitetEkonomskaKlasa + "</td>");
	noviRed.append("<td>" + podatak.cijenaKarte + "</td>");

	tabelaZaPrikaz.append(noviRed);
}