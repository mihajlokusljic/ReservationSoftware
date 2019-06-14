var defaultSlika = "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg";

$(document).ready(function(e) {
	
	$.ajaxSetup({
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	let statusCode = XMLHttpRequest.status;
	    	if(statusCode == 400) {
	    		//u slucaju neispravnih podataka (Bad request - 400) prikazuje se
	    		//poruka o greski koju je server poslao
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
					})	
	    	}
		}
	});
	
});

function pretragaHotela(idKorisnika, datumDolaska, datumOdlaska, idPutovanja) {
	let _nazivOdredista = $("#nazivOdredistaPretragaHotela").val();
	let _nazivHotela = $("#hotelNaziv").val();
	let dolazak = $("#input-start").val();
	let odlazak = $("#input-end").val();
	let sobeZahtjevi = prebrojPotrebneSobe();
	let _potrebneSobe = [];
	
	for(var brKrevetaSobe in sobeZahtjevi) {
		_potrebneSobe.push({
			brKrevetaPoSobi : brKrevetaSobe,
			brSoba : sobeZahtjevi[brKrevetaSobe]
		});
	}
	
	let pretragaHotela = {
			nazivHotela: _nazivHotela,
			nazivDestinacije: _nazivOdredista,
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
			refreshHotels(response, idKorisnika, datumDolaska, datumOdlaska, idPutovanja);
			if(response.length == 0) {
				swal({
					  title: "Ne postoji ni jedan hotel koji zadovoljava kriterijume pretrage",
					  icon: "info",
					  timer: 2500
					});	
			}
			if(datumDolaska == null || datumOdlaska == null || datumOdlaska == null) {
				//ako nije rezim rezervacije, forma se resetuje
				$('#hotelSearchForm')[0].reset();
				resetujZahtjeveSoba();
			}
		},
	});
}

/*
 * Prikazuje rezeultate pretrage hotela.
 * U rezimu rezervacije proslijedjuje potrebne parametre info stranici hotela kroz putanju
 */
function refreshHotels(hotels, idKorisnika, datumDolaska, datumOdlaska, idPutovanja) {
	let hotelsTableBody = $("#prikazHotela");
	hotelsTableBody.empty();
	let hotel, noviRed = null;
	let putanja, poruka = "";
	for(var i in hotels) {
		hotel = hotels[i];
		noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
		noviRed.append('<td class="column1">' + hotel.naziv + "</td>");
		noviRed.append('<td class="column1">' + hotel.adresa.punaAdresa + "</td>");
		if(hotel.brojOcjena > 0) {
			noviRed.append('<td class="column1">' + (hotel.sumaOcjena / hotel.brojOcjena) + "</td>");
		} else {
			noviRed.append('<td class="column1">Nema ocjena</td>');
		}
		putanja = '../infoStranicaHotela/index.html?id=' + hotel.id + '&korisnik=' + idKorisnika;
		poruka = 'Pogledaj detalje';
		if(datumDolaska != null && datumOdlaska != null && datumOdlaska != null) {
			putanja += '&datumDolaska=' + datumDolaska + '&datumOdlaska=' + datumOdlaska + '&idPutovanja=' + idPutovanja;
			poruka = 'Rezerviši sobe';
		}
		noviRed.append('<td class="column1"><a href="' + putanja + '">' + poruka + '</a></td>');
		hotelsTableBody.append(noviRed);
	}
}

function resetujZahtjeveSoba() {
	var selectSpecial = $('#js-select-special');
    var info = selectSpecial.find('#info');
    var dropdownSelect = selectSpecial.parent().find('.dropdown-select');
    var listRoom = dropdownSelect.find('.list-room');
    
    listRoom.empty();
    listRoom.append('<li class="list-room__item">' +
            '                                        <span class="list-room__name"> Zahtjev 1</span>' +
            '                                        <ul class="list-person">' +
            '                                            <li class="list-person__item">' +
            '                                                <span class="name">' +
            '													Broj kreveta po sobi' +
            '                                                </span>' +
            '                                                <div class="quantity quantity1">' +
            '                                                    <span class="minus">' +
            '                                                        -' +
            '                                                    </span>' +
            '                                                    <input type="number" min="0" value="0" class="inputQty">' +
            '                                                    <span class="plus">' +
            '                                                        +' +
            '                                                    </span>' +
            '                                                </div>' +
            '                                            </li>' +
            '                                            <li class="list-person__item">' +
            '                                                <span class="name">' +
            '                                                    Broj soba' +
            '                                                </span>' +
            '                                                <div class="quantity quantity2">' +
            '                                                    <span class="minus">' +
            '                                                        -' +
            '                                                    </span>' +
            '                                                    <input type="number" min="0" value="0" class="inputQty">' +
            '                                                    <span class="plus">' +
            '                                                        +' +
            '                                                    </span>' +
            '                                                </div>' +
            '                                            </li>' +
            '                                        </ul>');
    info.val("Nema zahtjeva za sobe");
}

function azurirajSobeZahtjev() {
	var selectSpecial = $('#js-select-special');
    var info = selectSpecial.find('#info');
    var dropdownSelect = selectSpecial.parent().find('.dropdown-select');
    var listRoom = dropdownSelect.find('.list-room');
    var listRoomItem = listRoom.find('.list-room__item');
    var infoText = "";
    var potrebneSobe = prebrojPotrebneSobe();
    
    var tipoviSoba = [];
    for(var brKreveta in potrebneSobe) {
    	tipoviSoba.push(brKreveta);
    }
    
    for(var i in tipoviSoba) {
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