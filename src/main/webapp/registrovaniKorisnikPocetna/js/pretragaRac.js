var defaultSlika = "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg";

$(document).ready(function(e) {
	
	$.ajaxSetup({
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	let statusCode = XMLHttpRequest.status;
	    	if(statusCode == 400) {
	    		//  u slucaju neispravnih podataka (Bad request - 400) prikazuje se
	    		//  poruka o greski koju je server poslao
	    		swal({
					  title: "Došlo je do greške",
					  text: XMLHttpRequest.responseText,
					  icon: "warning",
					  timer: 2500
					})
	    	}
	    	else {
	    		swal({
					  title: "Došlo je do greške",
					  icon: "warning",
					  timer: 2500
					});	
	    	}
		}
	});
	
});

function pretragaRacServisa(idKorisnika, datumDolaska, datumOdlaska, idPutovanja, idLetaRez) {
	let _nazivRacServisa= $("#racNaziv").val();
	let _nazivDestinacije = $("#nazivOdredistaPretragaRacServisa").val();
	let dolazak = $("#input-start-rac").val();
	let odlazak = $("#input-end-rac").val();
	
	let pretragaRac = {
			nazivRacServisa: _nazivRacServisa,
			nazivDestinacije: _nazivDestinacije,
			datumDolaska: dolazak,
			datumOdlaska: odlazak,
	}
	
	$.ajax({
		type: "POST",
		url: "../rentACar/pretrazi",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(pretragaRac),
		success: function(response) {
			refreshRac(response, idKorisnika, datumDolaska, datumOdlaska, idPutovanja, idLetaRez);
			
			if(response.length == 0) {
				swal({
					  title: "Ne postoji ni jedan rent-a-car servis koji zadovoljava kriterijume pretrage",
					  icon: "warning",
					  timer: 2500
					})	
			}
			if(datumDolaska == null || datumOdlaska == null || idPutovanja == null) {
				//  ako nije rezim rezervacije, forma se resetuje
				$('#racSearchForm')[0].reset();
			}
		},
	});
	
}

/*
 * Prikazuje rezeultate pretrage rent-a-car servisa.
 * U rezimu rezervacije proslijedjuje potrebne parametre info stranici rent-a-car servisa kroz putanju
 */
function refreshRac(racServisi, idKorisnika, datumDolaska, datumOdlaska, idPutovanja, idLetaRez) {
	let tbody = $("#prikazRacServisa");
	tbody.empty();
	let noviRed = null;
	let ocjena = 0;
	let poruka = "Pogledaj detalje";
	let putanja = "";
	
	if(datumDolaska != null && datumOdlaska != null && idPutovanja != null) {
		//rezim rezervacije
		poruka = "Rezerviši vozila";
	}
	
	
	$.each(racServisi, function(i, rac) {
		noviRed = $("<tr></tr>");
		if (rac.sumaOcjena == 0){
			ocjena = 0;
		}
		else{
			ocjena = rac.sumaOcjena/rac.brojOcjena;
			ocjena = ocjena.toFixed(2);
		}
		noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
		noviRed.append('<td class="column1">' + rac.naziv + '</td>');
		noviRed.append('<td class="column1">' + rac.adresa.punaAdresa + '</td>');
		noviRed.append('<td class="column1">' + ocjena + '</td>');
		putanja = '../infoStranicaRac/index.html?id=' + rac.id + '&korisnik=' + idKorisnika;
		if(datumDolaska != null && datumOdlaska != null && idPutovanja != null) {
			putanja += '&datumDolaska=' + datumDolaska + '&datumOdlaska=' + datumOdlaska + 
			'&idPutovanja=' + idPutovanja + "&idLetaRez=" + idLetaRez;
		}
		noviRed.append('<td class="column1"><a href="' + putanja + '">' + poruka + '</a></td>');
		tbody.append(noviRed);
	});
	
}