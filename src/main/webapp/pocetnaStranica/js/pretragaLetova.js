$(document).ready(function(e) {
	
	$("#duzinaPutovanja").val("");
	
	$("#pretragaLetovaForm").submit(function(e) {
		e.preventDefault();
		
		let cijenaK = $("#cijenaKarte").val();
		if (cijenaK == "") {
			cijenaK = 0;
		}
		
		let parametriPretrage = {
				brojLeta : $("#brojLeta").val(),
				nazivAviokompanije : $("#nazivAviokompanije").val(),
				nazivPolazista : $("#nazivPolazista").val(),
				nazivOdredista : $("#nazivOdredista").val(),
				datumPoletanja : $("#input-start-1").val(),
				datumSletanja : $("#input-end-1").val(),
				duzinaPutovanja : $("#duzinaPutovanja").val(),
				cijenaKarte : cijenaK
		};
		
		  $.ajax({
			    type : "POST",
			    url : "../letovi/pretraziLetove",
			    contentType: "application/json; charset=utf-8",
			    data : JSON.stringify(parametriPretrage),
			    success : function(response) {
			      if (response == undefined) {
			    		swal({
							  title: "Došlo je do greške.",
							  icon: "error",
							  timer: 2500
							})	
			      } else {
			        if (response.length == 0) {
			        	swal({
							  title: "Ne postoji ni jedan let koji zadovoljava navedeni kriterijum pretrage.",
							  icon: "info",
							  timer: 2500
							});	
			        }
			        updateLetovi(response);
			        $('#pretragaLetovaForm')[0].reset();
			      }
			    },
			    error : function(XMLHttpRequest, textStatus, errorThrown) {
			      alert("AJAX ERROR: " + errorThrown);
			    }	
			  });
		
	});
	
});

function updateLetovi(letovi) {
	let table = $("#letoviRows");
	table.empty();
	
	$.each(letovi, function(i, flight) {
		let row = $("<tr></tr>");
		
		row.append("<td>" + (i + 1) + "</td>");
		row.append("<td>" + flight.brojLeta + "</td>");
		row.append("<td>" + flight.polaziste.nazivDestinacije + "</td>");
		row.append("<td>" + flight.odrediste.nazivDestinacije + "</td>");
		row.append("<td>" + flight.datumPoletanja + "</td>");
		row.append("<td>" + flight.datumSletanja + "</td>");
		row.append("<td>" + flight.presjedanja.length + "</td>");
		row.append("<td>" + flight.cijenaKarte + "</td>");
		if (flight.brojOcjena > 0) {
			row.append("<td>" + (flight.sumaOcjena / flight.brojOcjena) + "</td>");
		} else {
			row.append("<td>Nema ocjena</td>");
		}
		table.append(row);
	});
}