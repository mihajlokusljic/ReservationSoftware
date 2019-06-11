$(document).ready(function() {	
	
	//dodavanje zaglavlja sa JWT tokenom u svaki zahtjev upucen ajax pozivom i obrada gresaka
	$.ajaxSetup({
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
	
	
	$("#racSearchForm").submit(function(e) {
		
		e.preventDefault();
	   
		let nazivLokacije = $("#racNaziv").val();
		let dolazak = $("#input-start-2").val();
		let odlazak = $("#input-end-2").val();
		
		let pretragaRac = {
				nazivRacIliDestinacije: nazivLokacije,
				datumDolaska: dolazak,
				datumOdlaska: odlazak,
		}
		
		$.ajax({
			type: "POST",
			url: "../rentACar/pretrazi",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(pretragaRac),
			success: function(response) {
				let tbody = $("#prikazRacServisa");
				tbody.empty();
				$.each(response, function(i, podatak) {
					prikazi(podatak, tbody, "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStranicaRac");
				});
				if(response.length == 0) {
					swal("Ne postoji ni jedan rent-a-car servis koji zadovoljava kriterijume pretrage");
				}
				$('#racSearchForm')[0].reset();
			},
		});
		
	});
});

function prikazi(podatak, tabelaZaPrikaz, defaultSlika, infoStranica) {
	let noviRed = $("<tr></tr>");
	var ocjena = null;
	if (podatak.sumaOcjena == 0){
		ocjena = 0;
	}
	else{
		ocjena = (podatak.sumaOcjena/podatak.brojOcjena).toFixed(2);
	}
	noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
	noviRed.append('<td class="column1">' + podatak.naziv + '</td>');
	noviRed.append('<td class="column1">' + podatak.adresa.punaAdresa + '</td>');
	noviRed.append('<td class="column1">' + ocjena + '</td>');
	noviRed.append('<td class="column1"><a href="../' + infoStranica+'/index.html?id=' + podatak.id + '">Pogledaj detalje</a></td>');

	tabelaZaPrikaz.append(noviRed);
}