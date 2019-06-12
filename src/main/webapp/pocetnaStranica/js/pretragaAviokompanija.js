var defaultSlika = "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg";

$(document).ready(function(e) {
	
	$.ajaxSetup({
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	let statusCode = XMLHttpRequest.status;
	    	if(statusCode == 400) {
	    		//u slucaju neispravnih podataka (Bad request - 400) prikazuje se
	    		//poruka o greski koju je server poslao
	    		alert(XMLHttpRequest.responseText, "error");
	    	}
	    	else {
	    		alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown, "error");
	    	}
		}
	});
	
	$("#aviokompanijeSearchForm").submit(function(e) {
		e.preventDefault();
	   
		let nazivAviokompanije = $("#aviokompanijaNazivSearch").val();
		
	
		let pretragaAviokompanije = {
				nazivAviokompanije: nazivAviokompanije
		}
		
		$.ajax({
			type: "POST",
			url: "../aviokompanije/pretrazi/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(pretragaAviokompanije),
			success: function(response) {
				refreshAviokompanije(response);
				if(response.length == 0) {
					swal({
						  title: "Ne postoji ni jedna aviokompanija koja zadovoljava navedeni kriterijum pretrage.",
						  icon: "info",
						  timer: 2500
						})	
				}
				$('#aviokompanijeSearchForm')[0].reset();
			}
		});
		
	});
	
});

function refreshAviokompanije(aviokompanije) {
	let aviokompanijeTable = $("#aviokompanijeRows");
	aviokompanijeTable.empty();
	let aviokompanija = null;
	for(i in aviokompanije) {
		aviokompanija = aviokompanije[i];
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
		noviRed.append('<td class="column1">' + aviokompanija.naziv + "</td>");
		noviRed.append('<td class="column1">' + aviokompanija.adresa.punaAdresa + "</td>");
		if(aviokompanija.brojOcjena > 0) {
			noviRed.append('<td class="column1">' + (aviokompanija.sumaOcjena / aviokompanija.brojOcjena) + "</td>");
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		noviRed.append('<td class="column1"><a href="../infoStranicaAviokompanije/index.html?id=' + aviokompanija.id + '">Pogledaj detalje</a></td>');
		aviokompanijeTable.append(noviRed);
	}
}