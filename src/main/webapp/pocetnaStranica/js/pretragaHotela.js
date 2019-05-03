$(document).ready(function(e) {
	
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
	
	$("#hotelSearchForm").submit(function(e) {
		e.preventDefault();
	   
		let nazivOdredista = $("#hotelNaziv").val();
		let dolazak = $("#input-start").val();
		let odlazak = $("#input-end").val();
		let sobeZahtjevi = prebrojPotrebneSobe();
		let _potrebneSobe = [];
		
		for(brKrevetaSobe in sobeZahtjevi) {
			_potrebneSobe.push({
				brKrevetaPoSobi : brKrevetaSobe,
				brSoba : sobeZahtjevi[brKrevetaSobe]
			});
		}
		
		let pretragaHotela = {
				nazivHotelaIliDestinacije: nazivOdredista,
				datumDolaska: dolazak,
				datumOdlaska: odlazak,
				potrebneSobe: _potrebneSobe
		}
		
		$.ajax({
			type: "POST",
			url: "../hoteli/pretrazi",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(pretragaHotela),
			success: function(response) {
				refreshHotels(response);
				if(response.length == 0) {
					alert("Ne postoji ni jedan hotel koji zadovoljava kriterijume pretrage");
				}
			},
		});
		
	});
	
});

function refreshHotels(hotels) {
	let hotelsTableBody = $("#hotelsView");
	hotelsTableBody.empty();
	let hotel = null;
	for(i in hotels) {
		hotel = hotels[i];
		let noviRed = $("<tr></tr>");
		noviRed.append("<td>" + (i + 1) + "</td>");
		noviRed.append("<td>" + hotel.naziv + "</td>");
		noviRed.append("<td>" + hotel.adresa.punaAdresa + "</td>");
		if(hotel.brojOcjena > 0) {
			noviRed.append("<td>" + (hotel.sumaOcjena / hotel.brojOcjena) + "</td>");
		} else {
			noviRed.append("<td>Nema ocjena</td>");
		}
		noviRed.append('<td><a href="#">Pogledaj</a></td>');
		hotelsTableBody.append(noviRed);
	}
}

function azurirajSobeZahtjev() {
	var selectSpecial = $('#js-select-special');
    var info = selectSpecial.find('#info');
    var dropdownSelect = selectSpecial.parent().find('.dropdown-select');
    var listRoom = dropdownSelect.find('.list-room');
    var listRoomItem = listRoom.find('.list-room__item');
	var info = selectSpecial.find('#info');
    var infoText = "";
    var potrebneSobe = prebrojPotrebneSobe();
    
    var tipoviSoba = []
    for(brKreveta in potrebneSobe) {
    	tipoviSoba.push(brKreveta);
    }
    
    for(i in tipoviSoba) {
    	infoText += potrebneSobe[tipoviSoba[i]] + " x " + tipoviSoba[i] + "-krevetnih soba";
    	if(i != tipoviSoba.length - 1) {
    		infoText += ", ";
    	}
    }
    if(infoText == "") {
    	infoText = "Nema zahtjeva za sobe";
    }

    info.val(infoText);
}

/*
 * Vraca "rijecnik" koji za svaki tip sobe (broj kreveta) odredjuje broj potrebnih takvih soba
 */
function prebrojPotrebneSobe() {
	var selectSpecial = $('#js-select-special');
    var info = selectSpecial.find('#info');
    var dropdownSelect = selectSpecial.parent().find('.dropdown-select');
    var listRoom = dropdownSelect.find('.list-room');
    var listRoomItem = listRoom.find('.list-room__item');
    var brojKreveta = 0;
    var brojSoba = 0;
    
    var potrebneSobe = {} //rijecnik koji za za svaki tip sobe (broj kreveta) cuva broj potrebnih takvih soba
    listRoomItem.each(function () {
        var that = $(this);
        brojKreveta = parseInt(that.find('.quantity1 > input').val());
        brojSoba = parseInt(that.find('.quantity2 > input').val());
        if(brojKreveta > 0 && brojSoba > 0) {
        if(potrebneSobe[brojKreveta] == undefined) {
        	potrebneSobe[brojKreveta] = brojSoba;
        }
        else 
        {
        	potrebneSobe[brojKreveta] += brojSoba;
        }
        }
    });
    return potrebneSobe;
}