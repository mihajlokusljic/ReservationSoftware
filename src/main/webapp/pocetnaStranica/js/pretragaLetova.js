var scGlobal = null;

$(document).ready(function(e) {
	
	$("#letoviTabNK").click(function(e) {
		e.preventDefault();
		$("#tabelaPrikazLetovaNK").show();
		$("#formaPretagaLetovaNK").show();
		$("#pregledSjedistaLetaNK").hide();
	});
	
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
	
	$("#povratakNaPretraguLetovaNK").click(function(e) {
		e.preventDefault();
		
		//resetovanje zauzetih i selektovanih sjedista
		scGlobal.find('selected').status('available');
		scGlobal.find('unavailable').status('available');
		
		$("#tabelaPrikazLetovaNK").show();
		$("#formaPretagaLetovaNK").show();
		$("#pregledSjedistaLetaNK").hide();
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
		row.append('</td><td class = "column1"><a href = "#" class = "prikazZauzetosti" id = "pzz' + flight.id + 
		'">Pregledaj sjedišta</a></td></tr>');
		table.append(row);
	});
	
	$(".prikazZauzetosti").click(function(e) {
		e.preventDefault();
		let idLeta = e.target.id.substring(3);
		prikaziIzborSjedistaBrzeRezervacije(idLeta);
	});
}

function prikaziIzborSjedistaBrzeRezervacije(idLeta) {
	podaciOMapi = null;
	$("#tabelaPrikazLetovaNK").hide();
	$("#formaPretagaLetovaNK").hide();
	$("#pregledSjedistaLetaNK").show();
	
	$.ajax({
		type : 'GET',
		async : false,
		url : "../letovi/dobaviSjedistaZaPrikazNaMapi/" + idLeta,
		dataType : "json",
		success: function(data){
			podaciOMapi = data;
		},
	});
	
	var seatsData = {};
	for(var i in podaciOMapi.segmenti) {
		let tekuciSegment = podaciOMapi.segmenti[i];
		
		seatsData[tekuciSegment.oznakaSegmenta] = {
				price : tekuciSegment.cijenaSegmenta,
				category : tekuciSegment.nazivSegmenta
		};
		
	}
	
	firstSeatLabel = 1;
    var $cart = $('#selected-seats'),
    $counter = $('#counter'),
    $total = $('#total'),
    
    sc = $('#seat-map').seatCharts({
    map: podaciOMapi.sjedista,
    seats: seatsData,
    naming : {
      top : false,
      getLabel : function (character, row, column) {
    	scGlobal = sc;
        return firstSeatLabel++;
      },
    },
    legend : {
      node : $('#legend'),
        items : [
        [ '', 'available',   'Slobodno' ],
        [ '', 'unavailable', 'Zauzeto']
        ]         
    },
    click: function () {
        return this.style();

    }
  });
    scGlobal = sc;
    sc.get(podaciOMapi.zauzetaSjedistaIds).status('unavailable');
  
}
