var tokenKey = "jwtToken";
var aviokompanije = [];
var hoteli = [];
var rentACarServisi = [];
var korisnik = null;
var prijatelji = [];
var broj_zahtjeva_za_prijateljstvo = 0;
var rezimRezervacije = false;
var scGlobal = null;
//podaci o periodu u kojem je korisnik na putovanju
var datumDolaska = null;
var datumOdlaska = null;
var idPutovanja = null;
var rezervisanoSjedista = 0;
var pozvaniPrijateljiIds = [];
var idLetaZaRezervaciju = -1;
var podaciRezervacijeSjedista = null;
var _podaciNeregistrovanihPutnika = [];
var podaciBoravka = null;
var defaultSlika = "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg";
//  spring.datasource.initialization-mode=always

$(document).ready(function() {	
	
	//dodavanje zaglavlja sa JWT tokenom u svaki zahtjev upucen ajax pozivom i obrada gresaka
	$.ajaxSetup({
	    headers: createAuthorizationTokenHeader(),
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	    	let statusCode = XMLHttpRequest.status;
	    	if(statusCode == 400) {
	    		//u slucaju neispravnih podataka (Bad request - 400) prikazuje se
	    		//poruka o greski koju je server poslao
	    		swal({
	  			  title: XMLHttpRequest.responseText,
	  			  icon: "warning",
	  			  timer:2000
	  			});
	    	}
	    	else {
	    		alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
	    	}
		}
	})
	
	korisnikInfo();
	
	//  ocitavanje opcionih parametara putanje za rezervaciju
	var url = window.location.href;
	var parametri = url.substring(url.indexOf("?") + 1);
	var params_parser = new URLSearchParams(parametri);
	
	idPutovanja = params_parser.get("idPutovanja");
	idLetaZaRezervaciju = params_parser.get("idLetaRez");
	if(idPutovanja != null && idLetaZaRezervaciju != null) {
		//  korisnik dolazi sa rezervacije hotelskog smjestaja i treba da mu se ponudi rezervacija vozila
		rezimRezervacije = true;
		prikaziRacServiseZaRezervaciju();
		
	}
	
	broj_zahtjeva_za_prijateljstvo = korisnik.brojZahtjevaZaPrijateljstvo;
	switch (broj_zahtjeva_za_prijateljstvo) {
	case 0:
		$("#brojZahtjevaZaPrijateljstvo").append("0️⃣");
		break;
	case 1:
		$("#brojZahtjevaZaPrijateljstvo").append("1️⃣ ");
		break;
	case 2:
		$("#brojZahtjevaZaPrijateljstvo").append("2️⃣");
		break;
	case 3:
		$("#brojZahtjevaZaPrijateljstvo").append("3️⃣");
		break;
	case 4:
		$("#brojZahtjevaZaPrijateljstvo").append("4️⃣");
		break;
	case 5:
		$("#brojZahtjevaZaPrijateljstvo").append("5️⃣");
		break;
	case 6:
		$("#brojZahtjevaZaPrijateljstvo").append("6️⃣");
		break;
	case 7:
		$("#brojZahtjevaZaPrijateljstvo").append("7️⃣");
		break;
	case 8:
		$("#brojZahtjevaZaPrijateljstvo").append("8️⃣");
		break;
	case 9:
		$("#brojZahtjevaZaPrijateljstvo").append("9️⃣");
		break;
	case 10:
		$("#brojZahtjevaZaPrijateljstvo").append("🔟");
		break;
	default:
		$("#brojZahtjevaZaPrijateljstvo").append("🔟➕");
		break;
	}
	if(!rezimRezervacije) {
		//ucitavanje aviokompanija
		ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg", "infoStranicaAviokompanije");
		
		//ucitavanje hotela
		ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg", "infoStranicaHotela");
	
		//ucitavanje rent-a-car servisa
		ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStranicaRac");
	}
	
	ucitajRezervacije();
	ucitajPozivnice();
	
	//pretraga hotela
	$("#hotelSearchForm").submit(function(e) {
		e.preventDefault();
		//funkcija iz modula hotelSearch.js
		//paramteri su potrebni za pozivanje info stranice hotela
		if(!rezimRezervacije) {
			pretragaHotela(korisnik.id, null, null, null, null);
		}
		else 
		{
			pretragaHotela(korisnik.id, podaciBoravka.datumDolaska, podaciBoravka.datumPovratka, 
					idPutovanja, idLetaZaRezervaciju);
		}
		
	});
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
	
	$("#aviokompanijeT").click(function(e){
		e.preventDefault();
		ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg", "infoStranicaAviokompanije");
		$("#tab-aviokompanije").show();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();
		$("#tab-letovi").hide();

	});
	
	$("#letoviT").click(function(e){
		e.preventDefault();
		ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg", "infoStranicaAviokompanije");
		$("#tab-aviokompanije").hide();
		$("#tab-letovi").show();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();
	});	
	
	$("#hoteliT").click(function(e){
		e.preventDefault();
		ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg", "infoStranicaHotela");
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").show();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();	
		$("#tab-letovi").hide();

		});
	
	$("#rentacarT").click(function(e){
		e.preventDefault();
		ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStranicaRac");
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").show();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();	
		$("#tab-letovi").hide();

		});
	
	$("#rezervacijeT").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").show();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();
		$("#tab-letovi").hide();
		ucitajRezervacije();
	});
	
	$("#pregled_prijatelja_tab").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").show();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();
		$("#tab-letovi").hide();
		$("#pregledPrijateljaSearch").val("");
		
		$.ajax({
			type: "POST",
			url : "../korisnik/dobaviSvePrijatelje",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik.id),
			success: function(response) {
				prikaziPrijatelje(response);
			},
		});
				
		});

	$("#dodaj_prijatelje_tab").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").show();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();	
		$("#tab-letovi").hide();

		$("#dodavanjePrijateljaSearch").val("");

		$.ajax({
			type: "POST",
			url : "../korisnik/dobaviKorisnikeZaDodavanjePrijatelja",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik.id),
			async: false,
			success: function(response) {
				prikaziKorisnikeZaPrijateljstvo(response);
			},
		});
		
		$("#dodavanjePrijateljaRows").empty();

				
	});
	
	$("#zahtjevi_prijateljstva_tab").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").show();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();
		$("#tab-letovi").hide();

		$.ajax({
			type: "POST",
			url : "../korisnik/dobaviZahtjeveZaPrijateljstvo",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik.id),
			success: function(response) {
				prikaziKorisnikeKojiSuZatraziliPrijateljstvo(response);
			},
		});
		
		});
	
	$("#pozivniceT").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").show();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();	
		$("#tab-letovi").hide();

	});
	
	$("#izmjeni_podatke_tab").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").show();
		$("#tab-profil-lozinka").hide();
		$("#tab-odjava").hide();
		$("#tab-letovi").hide();
		profilKorisnika();
	});
	
	$("#promjeni_lozinku_tab").click(function(e){
		e.preventDefault();
		$("#tab-aviokompanije").hide();
		$("#tab-hoteli").hide();
		$("#tab-rac-servisi").hide();
		$("#tab-rezervacije").hide();
		$("#pregled-prijatelja-tab").hide();
		$("#dodaj-prijatelje-tab").hide();
		$("#zahtjevi-prijateljstva-tab").hide();
		$("#tab-pozivnice").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").show();
		$("#tab-odjava").hide();
		$("#tab-letovi").hide();
		promjeniLozinku();
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
							})	
			        }
			        updateLetovi(response);
			        $('#pretragaLetovaForm')[0].reset();
			      }
			    }
			  });
		
	});
	
	$("#zadavanjePodatakaPutnikaForm").submit(function(e) {
		e.preventDefault();

		let _ime = $("#imePutnikaRez").val();
		let _prezime = $("#prezimePutnikaRez").val();
		let _brojPasosa = $("#brPasosaPutnikaRez").val();
		
		let podaciPutnika = {
				brojPasosa : _brojPasosa,
				ime : _ime,
				prezime : _prezime
		}
		
		_podaciNeregistrovanihPutnika.push(podaciPutnika);
		$("#zadavanjePodatakaPutnikaForm")[0].reset();
		
		if (_podaciNeregistrovanihPutnika.length === rezervisanoSjedista - 1 - pozvaniPrijateljiIds.length) {
			// Izvrsava se sve dok se ne unesu podaci svih neregistrovanih putnika
			
			krajRezervacijeSjedista();
			
			$("#brPodatakaNeregistrovanihPutnika").html(1); // jer je zavrsen unos
			
		} else {
			$("#brPodatakaNeregistrovanihPutnika").html(parseInt(_podaciNeregistrovanihPutnika.length + 1));
		}
				
	});
	
	//  pretraga rent-a-car servisa
	$("#racSearchForm").submit(function(e) {
		e.preventDefault();
	    //  poziv funkcije iz modula pretragaRac.js
		if(!rezimRezervacije) {
			pretragaRacServisa(korisnik.id, null, null, null, null);
		}
		else
		{
			pretragaRacServisa(korisnik.id, podaciBoravka.datumDolaska, podaciBoravka.datumPovratka, 
					idPutovanja, idLetaZaRezervaciju);
		}
		
		
	});
	
	//  preskakanje rezervacije hotelskog smjestaja
	$("#prelazakNaRezervacijuVozilaBtn").click(function(e) {
		e.preventDefault();
		prikaziRacServiseZaRezervaciju();
	});
	
	$("#zavrsetakrezervacijeBtn").click(function(e) {
		e.preventDefault();
		prelazakNaPregledPutovanja();
		
	});
	
	$("#dodavanjePrijateljaForm").submit(function(e) {
		e.preventDefault();
		
		let imePrezimeInput = $("#dodavanjePrijateljaSearch").val();
		
		let filtriranjePrijateljaDTO = {
				idKorisnika : korisnik.id,
				imePrezime: imePrezimeInput,
				pregledPrijatelja: false
		};
		
		$.ajax({
			type: "POST",
			url: "../korisnik/pretraziPrijatelje",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(filtriranjePrijateljaDTO),
			success: function(response) {
				prikaziKorisnikeZaPrijateljstvo(response);
				return;
			},
		});
		
	});
	
	$("#pregledPrijateljaForm").submit(function(e) {
		e.preventDefault();
		
		let imePrezimeInput = $("#pregledPrijateljaSearch").val();
		
		let filtriranjePrijateljaDTO = {
				idKorisnika : korisnik.id,
				imePrezime: imePrezimeInput,
				pregledPrijatelja: true
		};
		
		$.ajax({
			type: "POST",
			url: "../korisnik/pretraziPrijatelje",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(filtriranjePrijateljaDTO),
			success: function(response) {
				prikaziPrijatelje(response);
				return;
			},
		});
		
	});
	
	$("#zadajSjedisteBrzeRezBtn").click(function(e) {
		e.preventDefault();
		rezervisanoSjedista = scGlobal.find("selected").length;
		if(rezervisanoSjedista == 0) {
			swal({
				  title: "Morate izabrati bar jedno sjedište.",
				  icon: "warning",
				  timer: 2500
			});
			return;
		}
		if(rezervisanoSjedista >= 1) {
			//slanje zahtjeva za rezervaciju sjedista
			let podaciRezervacije = {
					rezervisanaSjedistaIds: scGlobal.find("selected").seatIds,
					pozvaniPrijateljiIds: [],
					podaciNeregistrovanihPutnika: [],
					idLeta: idLetaZaRezervaciju,
					idKorisnika : korisnik.id,
					idPutovanja: null
			};
			
			$.ajax({
				type: "POST",
				async: false,
				url: "../aviokompanije/rezervisiSjedista",
				contentType : "application/json; charset=utf-8",
				data: JSON.stringify(podaciRezervacije),
				success: function(response) {
					podaciRezervacijeSjedista = response;
					idPutovanja = podaciRezervacijeSjedista.idPutovanja;
					swal({
						  title: "Uspješna rezervacija!",
						  text: "Uspješno ste rezervisali sjedišta.",
						  icon: "success",
					})
					return;
				},
			});
			if (rezervisanoSjedista > 1) {
			prikaziPozivanjePrijatelja();
			} else {
				krajRezervacijeSjedista(); //  kada idemo sami na putovanje
			}
		}
		else
		{
			prikaziHoteleZaRezervaciju();
		}
	});
	
	$("#pretragaPrijateljaZaPozivanje").submit(function(e) {
		e.preventDefault();
		
		let imePrezimeInput = $("#pretragaPrijateljaZaPozivanjeInp").val();
		
		let filtriranjePrijateljaDTO = {
				idKorisnika : korisnik.id,
				imePrezime: imePrezimeInput,
				pregledPrijatelja: true
		};
		
		$.ajax({
			type: "POST",
			url: "../korisnik/pretraziPrijatelje",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(filtriranjePrijateljaDTO),
			success: function(response) {
				prikaziPrijateljeZaPozivanje(response);
				return;
			},
		});
		
	});
	
	$("#zadajPozivePrijateljaZaRezervacijuBtn").click(function(e) {
		e.preventDefault();
		if(rezervisanoSjedista - 1 - pozvaniPrijateljiIds.length > 0) {
			prikaziUnosPodatakaZaNeregistrovanePutnike();
		}
		else
		{
			krajRezervacijeSjedista(); // zavrseno pozivanje prijatelja, a nema nereg. putnika
		}
	});
	
	$("#aviokompanijeSearchForm").submit(function(e) {
		e.preventDefault();
	   
		let nazivAviokompanije = $("#aviokompanijaNazivSearch").val();
		
	
		let pretragaAviokompanije = {
				nazivAviokompanije: nazivAviokompanije
		};
		
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
						});	
				}
				$('#aviokompanijeSearchForm')[0].reset();
			}
		});
		
	});
	
});

function refreshAviokompanije(aviokompanije) {
	let aviokompanijeTable = $("#prikazAviokompanija");
	aviokompanijeTable.empty();
	let aviokompanija = null;
	for(var i in aviokompanije) {
		aviokompanija = aviokompanije[i];
		let noviRed = $("<tr></tr>");
		noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
		noviRed.append('<td class="column1">' + aviokompanija.naziv + "</td>");
		noviRed.append('<td class="column1">' + aviokompanija.adresa.punaAdresa + "</td>");
		if(aviokompanija.brojOcjena > 0) {
			noviRed.append('<td class="column1">' + (aviokompanija.sumaOcjena / aviokompanija.brojOcjena) + "</td>");
		} else {
			noviRed.append('<td class="column1">0</td>');
		}
		noviRed.append('<td class="column1"><a href="../infoStranicaAviokompanije/index.html?id=' + 
				aviokompanija.id + "&korisnik=" + korisnik.id +  '">Pogledaj detalje</a></td>');
		aviokompanijeTable.append(noviRed);
	}
}

function ucitajPozivnice() {
	$.ajax({
		type: "GET",
		url: "../korisnik/prihvacenePozivnice",
		success: function(response) {
			prikaziPozivnice(response);
		}
	});
}

function prikaziPozivnice(pozivnice) {
	var tabela = $("#prihvacenePozivniceRows");
	tabela.empty();
	let noviRed = null;
	
	$.each(pozivnice, function(i, pozivnica) {
		noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + pozivnica.posiljalac + '</td>');
		noviRed.append('<td class="column1">' + pozivnica.polaziste + '</td>');
		noviRed.append('<td class="column1">' + pozivnica.odrediste + '</td>');
		noviRed.append('<td class="column1">' + pozivnica.datumPolaska + '</td>');
		noviRed.append('<td class="column1">' + pozivnica.datumPovratka + '</td>');
		tabela.append(noviRed);
	});
}

function updateLetovi(letovi) {
	let table = $("#prikazLetovaRezervacije");
	table.empty();
	
	$.each(letovi, function(i, flight) {
		let row = $("<tr></tr>");
		
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
		row.append('</td><td class = "column1"><a href = "#" class = "rezervacijaSjed" id = "rsl' + flight.id + 
		'">Rezerviši sjedišta</a></td></tr>');
		
		table.append(row);
	});
	
	$(".rezervacijaSjed").click(function(e) {
		e.preventDefault();
		let idLeta = e.target.id.substring(3);
		idLetaZaRezervaciju = idLeta;
		prikaziIzborSjedistaBrzeRezervacije(idLeta);
	});
}

function krajRezervacijeSjedista() {
	podaciRezervacijeSjedista.pozvaniPrijateljiIds = pozvaniPrijateljiIds;
	podaciRezervacijeSjedista.podaciNeregistrovanihPutnika = _podaciNeregistrovanihPutnika;
	
	$.ajax({
		type: "POST",
		async: false,
		url: "../aviokompanije/popuniPodatkeZaPutnike",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(podaciRezervacijeSjedista),
		success: function(response) {
			podaciRezervacijeSjedista = response;
			
			swal({
				  title: "Uspješno uneseni podaci!",
				  text: "Uspješno ste unijeli podatke za putnike.",
				  icon: "success",
			})
			
			$("#zadavanjePodatakaPutnikaForm")[0].reset();
			prikaziHoteleZaRezervaciju();
			return;
		},
	});
	
}

function recalculateTotal(sc) {
	  var total = 0;

	  //basically find every selected seat and sum its price
	  sc.find('selected').each(function () {
	    total += this.data().price;
	  });
	  
	  return total;
}

function prikaziIzborSjedistaBrzeRezervacije(idLeta) {
	rezimRezervacije = true;
	podaciOMapi = null;
	$("#izborLetaZaRezervaciju").hide();
	$("#pozivPrijateljaZaRezervaciju").hide();
	$("#unosPodatakaPutnika").hide();
	$("#izborSjedistaZaRezervaciju").show();
	
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
        [ '', 'unavailable', 'Zauzeto'],
        [ '', 'selected',   'Odabrano' ]
        ]         
    },
    click: function () {
    
      if (this.status() == 'available') {

    	  
        //let's create a new <li> which we'll add to the cart items
        $('<li>'+this.data().category+' Seat # '+this.settings.label+': <b>$'+this.data().price+'</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
          .attr('id', 'cart-item-'+this.settings.id)
          .data('seatId', this.settings.id)
          .appendTo($cart);

        
        /*
         * Lets up<a href="https://www.jqueryscript.net/time-clock/">date</a> the counter and total
         *
         * .find function will not find the current seat, because it will change its stauts only after return
         * 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
         */
        $counter.text(sc.find('selected').length+1);
        $total.text(recalculateTotal(sc)+this.data().price);
        
        return 'selected';
      } else if (this.status() == 'selected') {
        //update the counter
        $counter.text(sc.find('selected').length-1);
        //and total
        $total.text(recalculateTotal(sc)-this.data().price);
      
        //remove the item from our cart
        $('#cart-item-'+this.settings.id).remove();
              
        //seat has been vacated
        return 'available';
      } else if (this.status() == 'unavailable') {
        //seat has been already booked
        return 'unavailable';
      } else {
        return this.style();
      }
    }
  });
    scGlobal = sc;
    sc.get(podaciOMapi.zauzetaSjedistaIds).status('unavailable');
  
}

function prikaziPozivanjePrijatelja() {
	$("#izborLetaZaRezervaciju").hide();
	$("#izborSjedistaZaRezervaciju").hide();
	$("#unosPodatakaPutnika").hide();
	$("#pozivPrijateljaZaRezervaciju").show();
	
	//resetovanje izabranih i zauzetih sjedista
	scGlobal.find('selected').status('available');
	scGlobal.find('unavailable').status('available');
}

function prikaziUnosPodatakaZaNeregistrovanePutnike() {
	$("#izborLetaZaRezervaciju").hide();
	$("#izborSjedistaZaRezervaciju").hide();
	$("#pozivPrijateljaZaRezervaciju").hide();
	$("#unosPodatakaPutnika").show();
	$("#brNeregistrovanihPutnika").html(rezervisanoSjedista - 1 - pozvaniPrijateljiIds.length);
}

function prikaziPrijateljeZaPozivanje(prijatelji) {
	let tabela = $("#prijateljiZaPozivanjeRows");
	tabela.empty();
	let noviRed = null;
	let akcija = "";
	
	$.each(prijatelji, function(i, prijatelj) {
		noviRed = $('<tr id = "prijateljPoz' + prijatelj.id + '"></tr>');
		noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
		"</td>");
		noviRed.append('<td class="column1"> </td>');
		noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
		noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
		akcija = "Pozovi";
		for(var i in pozvaniPrijateljiIds) {
			if(prijatelj.id == pozvaniPrijateljiIds[i]) {
				akcija = "Otkaži poziv";
			}
		}
		noviRed.append('<td class="column1"><button class="btn-submit pozivanjePrijatelja" type="button" id="prpoz' + prijatelj.id + 
				'">' + akcija + '</button></td>');
		noviRed.append('<td class="column6"> </td>');
		tabela.append(noviRed);
	});
	
	$(".pozivanjePrijatelja").click(function(e) {
		e.preventDefault();
		var idPrijatelja = e.target.id.substring(5); //id dugmeta je oblika "prpoz<id prijatelja>"
		var index = pozvaniPrijateljiIds.indexOf(idPrijatelja);
		if(index !== -1) {
			pozvaniPrijateljiIds.splice(index, 1);
			$(this).html("Pozovi");
		}
		else
		{
			if(pozvaniPrijateljiIds.length == rezervisanoSjedista - 1) {
				swal({
					  title: "Broj pozvanih prijatelja ne smije biti veći od broja rezervisanih sjedišta. Napomena: jedno sjedište već je rezervisano za Vas.",
					  icon: "warning",
					})
				return;
			}
			pozvaniPrijateljiIds.push(idPrijatelja);
			$(this).html('Otkaži poziv');
		}
		$("#brPozvanihPrijatelja").html(pozvaniPrijateljiIds.length);
	});
	
	
	
}

function prikaziHoteleZaRezervaciju() {
	
	$.ajax({
		type : 'GET',
		url : "../letovi/podaciBoravkaZaLet/" + idLetaZaRezervaciju,
		dataType : "json",
		async : false,
		success: function(data){
			podaciBoravka = data;
		},
	});
	
	$("#izborSjedistaZaRezervaciju").hide();
	$("#pozivPrijateljaZaRezervaciju").hide();
	$("#izborLetaZaRezervaciju").show();
	
	//pretraga hotela i podesavanje izgleda stranice za rezervaciju
	$("#rezervacijaHotelaPoruka").show();
	$("#prelazakNaRezervacijuVozilaBtn").show();
	$("#nazivOdredistaPretragaHotela").val(podaciBoravka.nazivDestinacije);
	$("#nazivOdredistaPretragaHotela").attr("readonly", true);
	$("#input-start").val(podaciBoravka.datumDolaska);
	$("#input-end").val(podaciBoravka.datumPovratka);
	pretragaHotela(korisnik.id, podaciBoravka.datumDolaska, podaciBoravka.datumPovratka, podaciRezervacijeSjedista.idPutovanja, idLetaZaRezervaciju);
	
	$("#tab-aviokompanije").hide();
	$("#tab-hoteli").show();
	$("#tab-rac-servisi").hide();
	$("#tab-rezervacije").hide();
	$("#pregled-prijatelja-tab").hide();
	$("#dodaj-prijatelje-tab").hide();
	$("#zahtjevi-prijateljstva-tab").hide();
	$("#tab-pozivnice").hide();
	$("#tab-profilKorisnika").hide();
	$("#tab-profil-lozinka").hide();
	$("#tab-odjava").hide();	
	$("#tab-letovi").hide();
}

function prikaziRacServiseZaRezervaciju() {
	//  prikazivanje poruka i dugmeta za korake rezervacije
	$("#rezervacijaVozilaPoruka").show();
	$("#zavrsetakrezervacijeBtn").show();
	
	//  ucitavanje podataka o odredistu i periodu boravka
	$.ajax({
		type: "GET",
		async: false,
		url: "../letovi/podaciBoravkaZaLet/" + idLetaZaRezervaciju,
		contentType : "application/json; charset=utf-8",
		success: function(response) {
			podaciBoravka = response;
		},
	});
	datumDolaska = podaciBoravka.datumDolaska;
	datumOdlaska = podaciBoravka.datumPovratka;
	
	//  pretraga RAC servisa na odredistu leta
	$("#nazivOdredistaPretragaRacServisa").val(podaciBoravka.nazivDestinacije);
	$("#nazivOdredistaPretragaRacServisa").attr("readonly", true);
	$("#input-start-rac").val(podaciBoravka.datumDolaska);
	$("#input-end-rac").val(podaciBoravka.datumPovratka);

	pretragaRacServisa(korisnik.id, podaciBoravka.datumDolaska, podaciBoravka.datumPovratka, 
			idPutovanja, idLetaZaRezervaciju);
	
	
	//  prikaz taba za rezervaciju vozila
	$("#tab-aviokompanije").hide();
	$("#tab-hoteli").hide();
	$("#tab-rac-servisi").show();
	$("#tab-rezervacije").hide();
	$("#pregled-prijatelja-tab").hide();
	$("#dodaj-prijatelje-tab").hide();
	$("#zahtjevi-prijateljstva-tab").hide();
	$("#tab-pozivnice").hide();
	$("#tab-profilKorisnika").hide();
	$("#tab-profil-lozinka").hide();
	$("#tab-odjava").hide();	
	$("#tab-letovi").hide();
}

function korisnikInfo(){
	let token = getJwtToken("jwtToken");
	$.ajax({
		type : 'GET',
		url : "../korisnik/getInfo",
		dataType : "json",
		async : false,
		success: function(data){
			if(data != null){
				korisnik = data;
				$("#korisnik").append(data.ime + " " + data.prezime);
			}
			else{
				swal({
					  title: "Nepostojeći korisnik",
					  icon: "warning",
					  timer: 2500
					})
			}
		},
	});
}

function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz, defaultSlika, infoStranica) {
	let tabela = $("#" + idTabeleZaPrikaz);
	$.ajax({
		type: "GET",
		url: putanjaControlera,
		success: function(response) {
			tabela.empty();
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela, defaultSlika, infoStranica);
			});
		},
	});
}

function prikazi(podatak, tabelaZaPrikaz, defaultSlika, infoStranica) {
	let noviRed = $("<tr></tr>");
	var ocjena = null;
	if (podatak.sumaOcjena == 0){
		ocjena = 0;
	}
	else{
		ocjena = podatak.sumaOcjena/podatak.brojOcjena;
		ocjena = ocjena.toFixed(2);
	}
	noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
	noviRed.append('<td class="column1">' + podatak.naziv + '</td>');
	noviRed.append('<td class="column1">' + podatak.adresa.punaAdresa + '</td>');
	noviRed.append('<td class="column1">' + ocjena + '</td>');
	noviRed.append('<td class="column1"><a href="../' + infoStranica+'/index.html?id=' + podatak.id + '&korisnik='+ korisnik.id + '">Pogledaj detalje</a></td>');

	tabelaZaPrikaz.append(noviRed);
}

function profilKorisnika(){

	$("#emailAdmina").val(korisnik.email);
	$("#imeAdmina").val(korisnik.ime);
	$("#prezimeAdmina").val(korisnik.prezime);
	$("#brTelefonaAdmina").val(korisnik.brojTelefona);
	$("#brPasosa").val(korisnik.brojPasosa);
	
	$("#adresaAdmina").val(korisnik.adresa.punaAdresa);

	$("#forma_profil_korisnika").unbind().submit(function(e){
		e.preventDefault();
		

		var imeAdmina = $("#imeAdmina").val();
		if (imeAdmina == ''){
			swal({
				  title: "Polje za unos imena ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		var prezimeAdmina = $("#prezimeAdmina").val();
		if (prezimeAdmina == ''){
			swal({
				  title: "Polje za unos prezimena ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		var brTelefonaAdmina = $("#brTelefonaAdmina").val();
		if (brTelefonaAdmina == ''){
			swal({
				  title: "Polje za unos broja telefona ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		var brPasosa = $("#brPasosa").val();
		if (brPasosa == ''){
			swal({
				  title: "Polje za unos broja pasoša ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		var adresaAdmina = $("#adresaAdmina").val();
		if (adresaAdmina == ''){
			swal({
				  title: "Polje za unos adrese ne moze biti prazno.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}		

		let admin = {
				id: korisnik.id,
				ime: imeAdmina,
				prezime: prezimeAdmina,
				email: korisnik.email,
				lozinka: korisnik.lozinka,
				brojTelefona: brTelefonaAdmina,
				brojPasosa : brPasosa,
				adresa: { punaAdresa : adresaAdmina }
		};
		$.ajax({
			type:"POST",
			url:"../korisnik/izmjeniProfilKorisnika",
			contentType : "application/json; charset=utf-8",
			data:JSON.stringify(admin),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success:function(response){
				if (response == ''){
					swal({
						  title: "Izmjena nije uspjela.",
						  icon: "error",
						  timer: 2000
						}).then(function(){
							korisnik = response;
							profilKorisnika();
						});
				}
				else{
					swal({
						  title: "Uspješno ste izmjenili profil.",
						  icon: "success",
						  timer:2000
						}).then(function(){
							korisnik = response;
							profilKorisnika();
						});
					
				}
				
			},
		});
		
	});
}

function promjeniLozinku(){
	$("#forma_lozinka").unbind().submit(function(e){
		e.preventDefault();
		var staraLozinka = $("#staraLozinka").val();
		var novaLozinka = $("#novaLozinka").val();
		var novaLozinka2 = $("#novaLozinka2").val();

		if (novaLozinka == ''){
			swal({
				  title: "Niste unijeli novu lozinku.",
				  icon: "warning",
				  timer: 2500
				})
			return;
		}
		
		if (novaLozinka != novaLozinka2){
			swal({
				  title: "Greška. ",
				  text: "Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.",
				  icon: "error",
				  timer: 2500
				});
			return;
		}
		
		
		$.ajax({
			type : 'PUT',
			url : "../auth/changePassword/" + staraLozinka,
			headers : createAuthorizationTokenHeader("jwtToken"),
			contentType : "application/json",
			data : novaLozinka,
			success : function(data) {
				if (data == ''){
					swal({
						  title: "Pogrešna trenutna lozinka.",
						  icon: "error",
						  timer: 2000
						});
					return;
				}
				else{
					setJwtToken("jwtToken", data.accessToken);
					swal({
						  title: "Uspješno ste izmjenili lozinku.",
						  icon: "success",
						  timer:2000
						});
				}
				
			}
		});
	});	
}

function prikaziPrijatelje(prijatelji) {
	$("#pregledPrijateljaRows").empty();
	$.each(prijatelji, function(i, prijatelj) {
		prikaziPrijatelja(prijatelj);
	});
	
	$(".obrisiPrijateljaClass").click(function(e) {
		e.preventDefault();
		var prijateljZaBrisanjeId = e.target.id.substring(3);
		prijateljZaBrisanjeId = parseInt(prijateljZaBrisanjeId);
		
		var uklanjanjePrijateljaDTO = {
				korisnikId : korisnik.id,
				prijateljZaUklonitiId : prijateljZaBrisanjeId
		};
		
		$.ajax({
			type: "POST",
			url : "../korisnik/ukloniPrijatelja",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(uklanjanjePrijateljaDTO),
			async : false,
			success: function(response) {
				if (response == true) {
					swal({
						  title: "Uspješno uklonjen korisnik iz liste prijatelja.",
						  icon: "success",
						  timer:2000
						});
				$("#prijatelj" + prijateljZaBrisanjeId).remove();
				return;
				} else {
					swal({
						  title: "Došlo je do greške prilikom procesiranja zahtjeva. Molimo sačekajte i pokušajte ponovo.",
						  icon: "error",
						  timer:2500
						});
					return;
				}
			},
		});
		
	});
	
}

function prikaziPrijatelja(prijatelj) {
	let prijateljiTabela = $("#pregledPrijateljaRows");
	let noviRed = $('<tr id = "prijatelj' + prijatelj.id + '"></tr>');
	noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
	"</td>");
	noviRed.append('<td class="column6">' + '<input type="hidden" id="' + prijatelj.id +  '">' + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
	noviRed.append('<td class="column1"><button class="btn-submit obrisiPrijateljaClass" type="button" id="pgp' + prijatelj.id + 
			'">' + '❌ </button></td>');
	noviRed.append('<td class="column6"></td>');
	prijateljiTabela.append(noviRed);
}

function prikaziKorisnikeZaPrijateljstvo(prijatelji) {
	$("#dodavanjePrijateljaRows").empty();
	
	$.each(prijatelji, function(i, prijatelj) {
		prikaziKorisnikaZaPrijateljstvo(prijatelj);
	});
	
	$(".dodajPrijateljaClass").click(function(e) {
		e.preventDefault();
		var idPotencijalnogPrijatelja = e.target.id.substring(3);
		idPotencijalnogPrijatelja = parseInt(idPotencijalnogPrijatelja);
		
		var zahtjevZaPrijateljstvoDTO = {
				posiljalacId: korisnik.id,
				primalacId : idPotencijalnogPrijatelja
		};
		
		$.ajax({
			type: "POST",
			url : "../korisnik/dodajPrijatelja",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(zahtjevZaPrijateljstvoDTO),
			async: false,
			success: function(response) {
				if (response == true) {
					swal({
						  title: "Zahtjev je uspješno poslat.",
						  icon: "success",
						  timer:2000
						});
					$("#dpt" + idPotencijalnogPrijatelja).attr("disabled", "disabled");
					$("#dpt" + idPotencijalnogPrijatelja).empty();
					$("#dpt" + idPotencijalnogPrijatelja).append("Poslat zahtjev ✔️");
					return;
				} else {
					swal({
						  title: "Došlo je do greške prilikom slanja zahtjeva. Molimo pričekajte i pokušajte ponovo.",
						  icon: "error",
						  timer:2500
						});
					return;
				}
			},
		});
		
	});
}

function prikaziKorisnikaZaPrijateljstvo(prijatelj) {
	let prijateljiTabela = $("#dodavanjePrijateljaRows");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
	"</td>");
	noviRed.append('<td class="column6">' + '<input type="hidden" id="' + prijatelj.id +  '">' + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
	
	var poslatZahtjevBool = null;
	
	var zahtjevZaPrijateljstvoDTO = {
			posiljalacId: korisnik.id,
			primalacId : prijatelj.id
	};
	
	$.ajax({
		type: "POST",
		url : "../korisnik/daLiJeZahtjevVecPoslat",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(zahtjevZaPrijateljstvoDTO),
		async : false,
		success: function(response) {
			poslatZahtjevBool = response;
		},
	});
	
	if (poslatZahtjevBool) {
	noviRed.append('<td class="column1"><button class="btn-submit poslatZahtjev" type="button" disabled id="zjvp' 
			+ prijatelj.id + '">Poslat zahtjev ✔️</button></td>');
	} else {
		noviRed.append('<td class="column1"><button class="btn-submit dodajPrijateljaClass" type="button" id="dpt' 
				+ prijatelj.id + '">Dodaj prijatelja</button></td>');		
	}
	
	noviRed.append('<td class="column6"></td>');
	prijateljiTabela.append(noviRed);
	
}

function prikaziKorisnikeKojiSuZatraziliPrijateljstvo(prijatelji) {
	$("#zahtjeviZaPrijateljstvoRows").empty();
	$.each(prijatelji, function(i, prijatelj) {
		prikaziKorisnikaKojiJeZatrazioPrijateljstvo(prijatelj);
	});
	
	$(".prihvatiPrijateljstvoClass").click(function(e) {
		e.preventDefault();
		var idPrijatelja = e.target.id.substring(3);
		idPrijatelja = parseInt(idPrijatelja);
		
		var zahtjevZaPrijateljstvoDTO = {
				posiljalacId : korisnik.id,
				primalacId : idPrijatelja
		};
		
		$.ajax({
			type: "POST",
			url : "../korisnik/prihvatiZahtjevZaPrijateljstvo",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(zahtjevZaPrijateljstvoDTO),
			async : false,
			success: function(response) {
				if (response == true) {
					swal({
						  title: "Uspješno prihvaćen zahtjev za prijateljstvo.",
						  icon: "success",
						  timer:2000
						});
				
				$.ajax({
					type: "POST",
					url : "../korisnik/dobaviKorisnikeZaDodavanjePrijatelja",
					contentType : "application/json; charset=utf-8",
					data: JSON.stringify(korisnik.id),
					success: function(response) {
						prikaziKorisnikeZaPrijateljstvo(response);
						$("#adf" + idPrijatelja).remove();
					},
				});
				
				return;
				} else {
					swal({
						  title: "Došlo je do greške prilikom procesiranja zahtjeva. Molimo sačekajte i pokušajte ponovo.",
						  icon: "error",
						  timer:2500
						});
					return;
				}
			},
		});
		
	});
	
	$(".odbijPrijateljstvoClass").click(function(e) {
		e.preventDefault();
		var idPrijatelja = e.target.id.substring(3);
		idPrijatelja = parseInt(idPrijatelja);
		
		var zahtjevZaPrijateljstvoDTO = {
				posiljalacId : korisnik.id,
				primalacId : idPrijatelja
		};
		
		$.ajax({
			type: "POST",
			url : "../korisnik/odbijZahtjevZaPrijateljstvo",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(zahtjevZaPrijateljstvoDTO),
			async : false,
			success: function(response) {
				if (response == true) {
					swal({
						  title: "Uspješno odbijen zahtjev za prijateljstvo.",
						  icon: "success",
						  timer:2000
						});
				$("#adf" + idPrijatelja).remove();
				return;
				} else {
					swal({
						  title: "Došlo je do greške prilikom procesiranja zahtjeva. Molimo sačekajte i pokušajte ponovo.",
						  icon: "error",
						  timer:2500
						});
					return;
				}
			},
		});
		
	});
	
}

function prikaziKorisnikaKojiJeZatrazioPrijateljstvo(prijatelj) {
	let prijateljiTabela = $("#zahtjeviZaPrijateljstvoRows");
	let noviRed = $('<tr id="adf' + prijatelj.id + '"></tr>');
	noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
	"</td>");
	noviRed.append('<td class="column6">' + '<input type="hidden" id="' + prijatelj.id +  '">' + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
	noviRed.append('<td class="column1"><button class="btn-submit prihvatiPrijateljstvoClass" type="button" id="zpp' 
			+ prijatelj.id + '">✔️</button></td>');
	noviRed.append('<td class="column1"><button class="btn-submit odbijPrijateljstvoClass" type="button" id="zpo' 
			+ prijatelj.id + '">❌</button></td>');
	noviRed.append('<td class="column6"></td>');
	prijateljiTabela.append(noviRed);
}

function odjava() {
	removeJwtToken();
	window.location.replace("../pocetnaStranica/index.html");
}

function ucitajRezervacije(){
	ucitajRezervisaneLetove();
	ucitajRezervisaneSobe();
	ucitajRezervisanaVozila();
}

function ucitajRezervisaneLetove(){
	$.ajax({
		type : 'GET',
		url : "../korisnik/rezervisaniLetovi",
		dataType : "json",
		async: false,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(data){
			prikaziRezervisaneLetove(data);
		}
	});
}

function ucitajRezervisaneSobe(){
	$.ajax({
		type : 'GET',
		url : "../korisnik/rezervisaneSobe",
		dataType : "json",
		async: false,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(data){
			prikaziRezervisaneSobe(data);
		}
	});
}

function ucitajRezervisanaVozila(){
	$.ajax({
		type : 'GET',
		url : "../korisnik/rezervisanaVozila",
		dataType : "json",
		async: false,
		headers: createAuthorizationTokenHeader("jwtToken"),
		success: function(data){
			prikaziRezVozila(data);
		}
	});
}

function prikaziRezVozila(vozila){
	let tabela = $("#prikazRezervisanihVozila");
	tabela.empty();
	
	$.each(vozila, function(i, vozilo) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + vozilo.nazivServisa + '</td>');
		noviRed.append('<td class="column1">' + vozilo.rezervisanoVozilo.naziv + '</td>');
		noviRed.append('<td class="column1">' + vozilo.cijena + '</td>');		
		noviRed.append('<td class="column1">' + vozilo.mjestoPreuzimanja + '</td>');
		noviRed.append('<td class="column1">' + vozilo.mjestoVracanja + '</td>');
		noviRed.append('<td class="column1">' + vozilo.datumPreuzimanja + '</td>');
		noviRed.append('<td class="column1">' + vozilo.datumVracanja + '</td>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "otkaziRezervaciju" id = "' + i + '">Otkaži rezervaciju</a></td></tr>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "ocjeniVozilo" id = "' + i + '">Ocjeni vozilo</a></td></tr>');

		tabela.append(noviRed);
	});
	
	// okvir popup-a
	var modal = document.getElementById("myModal");

	// <span> za zatvaranje
	var span = document.getElementsByClassName("close")[0];
	
	
	
	$(".ocjeniVozilo").click(function(e){
		
		e.preventDefault();
		
		var ratingValue = 0;
		let rezervacija = vozila[e.target.id];
		
		var ocjenjeno = false;
		//provjera da li je vec ocjenjeno
		$.ajax({
			type : 'GET',
			url : "../rentACar/ocjenjenaRezervacija/" + rezervacija.idRezervacije,
			dataType : "json",
			async : false,
			success: function(response){
				ocjenjeno = response;
				if(response == true){
					swal({
						  text: "Vec ste ocjenili koristenu rezervaciju.",
						  icon: "warning",
						  timer: 2000
						})	
					return;
				}
			},
		});
		
		if (ocjenjeno == true){
			return;
		}
		
		var trenutniDatum = new Date();
		var datumVracanja = Date.parse(rezervacija.datumVracanja);
		
		if (datumVracanja>trenutniDatum) {
			swal({
				  text: "Ne mozete ocjeniti vozilo prije njegovog vracanja.",
				  icon: "warning",
				  timer: 2000
				})
			return;
		}
		
		modal.style.display = "block";
		// kad korisnik klikne na "x" zatvara se prozor
		span.onclick = function() {
		  modal.style.display = "none";
		}
		 var stars = $('#stars li').parent().children('li.star');
		    for (i = 0; i < stars.length; i++) {
		      $(stars[i]).removeClass('selected');
		    }
		// kad korisnik klikne bilo gdje izvan prozora prozor se zatvara
		window.onclick = function(event) {
		  if (event.target == modal) {
		    modal.style.display = "none";
		  }
		}
		
		/* 1. Prelazak preko zvjezdica */
		  $('#stars li').on('mouseover', function(){
		    var onStar = parseInt($(this).data('value'), 10); // Zvijezda na kojoj je trenutno kursor misa
		   
		    // highlight zvjezdica preko kojih je predjeno
		    $(this).parent().children('li.star').each(function(e){
		      if (e < onStar) {
		        $(this).addClass('hover');
		      }
		      else {
		        $(this).removeClass('hover');
		      }
		    });
		    
		  }).on('mouseout', function(){
		    $(this).parent().children('li.star').each(function(e){
		      $(this).removeClass('hover');
		    });
		  });
		  
		  
		  /* 2. Akcije za klik */
		  $('#stars li').click(function(e){
			  e.preventDefault();
		    var onStar = parseInt($(this).data('value'), 10); // Zvijezda koja je odabrana
		    var stars = $(this).parent().children('li.star');
		    
		    for (i = 0; i < stars.length; i++) {
		      $(stars[i]).removeClass('selected');
		    }
		    
		    for (i = 0; i < onStar; i++) {
		      $(stars[i]).addClass('selected');
		    }
		    
		    // Vrijednost odabrane zvjezdice
		     ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
		    var msg = "";
		    if (ratingValue > 1) {
		        msg = "Hvala! Glasali se sa " + ratingValue + " zvjezdica.";
		    }
		    else {
		        msg = "Pokušaćemo da poboljšamo svoje usluge. Glasali ste sa " + ratingValue + " zvjezdica.";
		    }
		    
		    $("#potvrdi_ocjenu").unbind().click(function(e){
		    	e.preventDefault();
			    modal.style.display = "none";
			    $.ajax({
					type: "POST",
					url : "../rentACar/ocjeniVozilo/" + ratingValue,
					contentType : "application/json; charset=utf-8",
					data: JSON.stringify(rezervacija.idRezervacije),
					async : false,
					success: function(response) {
						if (response == ''){
							swal({
								  title: "Uspješno ste ocjenili usluge vozila.",
								  icon: "success",
								  timer:2000
								})
						}
						else{
							swal({
								  title: response,
								  icon: "warning",
								  timer:2000
								})
						}
					},
				});

		    });
		  });
	})
	
	$(".otkaziRezervaciju").click(function(e){
		e.preventDefault();
		let rezervacija = vozila[e.target.id];
		var datumP = Date.parse(rezervacija.datumPreuzimanja);
		var sadasnjiDatum = new Date();
		var diff= (datumP - sadasnjiDatum ) / (1000*60*60*24);
		if (diff<2){
			swal({
				  title: "Zakasnili ste sa otkazivanjem rezervacije vozila.",
				  icon: "error",
				  timer: 2500
				});
			return;
		}
		
		$.ajax({
			type: "POST",
			url : "../rentACar/otkaziRezervaciju",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(rezervacija.idRezervacije),
			async : true,
			success: function(response) {
				swal({
					  title: response,
					  icon: "success",
					  timer:2000
					})
				ucitajRezervisanaVozila();
			},
		});
		
	});
}

function prikaziRezervisaneLetove(rezLetova) {
	
	let tabela = $("#prikazRezervisanihLetova");
	tabela.empty();
	
	$.each(rezLetova, function(i, rLet) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + rLet.nazivAviokompanije + '</td>');
		noviRed.append('<td class="column1">' + rLet.brojLeta + '</td>');
		noviRed.append('<td class="column1">' + rLet.originalnaCijena.toFixed(2) + '</td>');		
		noviRed.append('<td class="column1">' + rLet.sjediste.red + '/' + rLet.sjediste.kolona +  '</td>');
		noviRed.append('<td class="column1">' + rLet.nazivPolazista + '</td>');
		noviRed.append('<td class="column1">' + rLet.nazivOdredista + '</td>');
		noviRed.append('<td class="column1">' + rLet.datumPolaska + '</td>');
		noviRed.append('<td class="column1">' + rLet.datumDolaska + '</td>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "otkaziRezervacijuLeta" id = "' + i + '">Otkaži rezervaciju</a></td></tr>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "ocjeniLet" id = "' + i + '">Ocjeni let</a></td></tr>');

		tabela.append(noviRed);
	});
	
	// okvir popup-a
	var modal = document.getElementById("myModal");

	// <span> za zatvaranje
	var span = document.getElementsByClassName("close")[0];
	
	
	
	$(".ocjeniLet").click(function(e){
		
		e.preventDefault();
		
		var ratingValue = 0;
		let rezervacija = rezLetova[e.target.id];
		
		var ocjenjeno = false;
		//provjera da li je vec ocjenjeno
		$.ajax({
			type : 'GET',
			url : "../letovi/ocjenjenaRezervacija/" + rezervacija.idRezervacije,
			dataType : "json",
			async : false,
			success: function(response){
				ocjenjeno = response;
				if(response == true){
					swal({
						  title: "Vec ste ocjenili koristenu rezervaciju.",
						  icon: "info",
						  timer:2000
						})
					return;
				}
			},
		});
		
		if (ocjenjeno == true){
			return;
		}
		
		var trenutniDatum = new Date();
		var datumVracanja = Date.parse(rezervacija.datumDolaska);
		
		if (datumVracanja>trenutniDatum) {
			swal({
				  title: "Ne mozete ocjeniti let prije njegovog sletanja.",
				  icon: "warning",
				  timer:2000
				})
			return;
		}
		
		modal.style.display = "block";
		// kad korisnik klikne na "x" zatvara se prozor
		span.onclick = function() {
		  modal.style.display = "none";
		}
		 var stars = $('#stars li').parent().children('li.star');
		    for (i = 0; i < stars.length; i++) {
		      $(stars[i]).removeClass('selected');
		    }
		// kad korisnik klikne bilo gdje izvan prozora prozor se zatvara
		window.onclick = function(event) {
		  if (event.target == modal) {
		    modal.style.display = "none";
		  }
		}
		
		/* 1. Prelazak preko zvjezdica */
		  $('#stars li').on('mouseover', function(){
		    var onStar = parseInt($(this).data('value'), 10); // Zvijezda na kojoj je trenutno kursor misa
		   
		    // highlight zvjezdica preko kojih je predjeno
		    $(this).parent().children('li.star').each(function(e){
		      if (e < onStar) {
		        $(this).addClass('hover');
		      }
		      else {
		        $(this).removeClass('hover');
		      }
		    });
		    
		  }).on('mouseout', function(){
		    $(this).parent().children('li.star').each(function(e){
		      $(this).removeClass('hover');
		    });
		  });
		  
		  
		  /* 2. Akcije za klik */
		  $('#stars li').click(function(e){
			  e.preventDefault();
		    var onStar = parseInt($(this).data('value'), 10); // Zvijezda koja je odabrana
		    var stars = $(this).parent().children('li.star');
		    
		    for (i = 0; i < stars.length; i++) {
		      $(stars[i]).removeClass('selected');
		    }
		    
		    for (i = 0; i < onStar; i++) {
		      $(stars[i]).addClass('selected');
		    }
		    
		    // Vrijednost odabrane zvjezdice
		     ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
		    var msg = "";
		    if (ratingValue > 1) {
		        msg = "Hvala! Glasali se sa " + ratingValue + " zvjezdica.";
		    }
		    else {
		        msg = "Pokušaćemo da poboljšamo svoje usluge. Glasali ste sa " + ratingValue + " zvjezdica.";
		    }
		    
		    $("#potvrdi_ocjenu").unbind().click(function(e){
		    	e.preventDefault();
			    modal.style.display = "none";
			    $.ajax({
					type: "POST",
					url : "../letovi/ocjeniLet/" + ratingValue,
					contentType : "application/json; charset=utf-8",
					data: JSON.stringify(rezervacija.idRezervacije),
					async : false,
					success: function(response) {
						if (response == ''){
							swal({
								  title: "Uspješno ste ocjenili let.",
								  icon: "success",
								  timer:2000
								})
						}
						else{
							swal({
								  title: response,
								  icon: "warning",
								  timer:2000
								})
						}
					},
				});

		    });
		  });
	})
	
	
	$(".otkaziRezervacijuLeta").click(function(e){
		e.preventDefault();
		
		let rezervacija = rezLetova[e.target.id];
		var datumP = Date.parse(rezervacija.datumPolaska);
		var dt = new Date(rezervacija.datumPolaska);
		let sati = dt.getHours();
		let minuti = dt.getMinutes();
		var sadasnjiDatum = new Date();
		let trenutnoSati = sadasnjiDatum.getHours();
		let trenutnoMinuti = sadasnjiDatum.getMinutes();
		var diff= (datumP - sadasnjiDatum ) / (1000*60*60*24);
		if (diff<0){
			swal({
				  text: "Zakasnili ste sa otkazivanjem rezervacije leta.",
				  icon: "warning",
				  timer: 2000
				})	
			return;
		}
		else if (diff < 1) {
			var razlikaSati = sati - trenutnoSati;
			if (razlikaSati<3) {
				swal({
					  text: "Zakasnili ste sa otkazivanjem rezervacije leta.",
					  icon: "warning",
					  timer: 2000
					})	
				return;
			}
			else if (razlikaSati == 3) {
				var razlikaMinuta = minuti - trenutnoMinuti;
				if (razlikaMinuta<0) {
					swal({
						  text: "Zakasnili ste sa otkazivanjem rezervacije leta.",
						  icon: "warning",
						  timer: 2000
						})	
					return;
				}
			}
		}
		
		$.ajax({
			type: "POST",
			url : "../letovi/otkaziRezervaciju",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(rezervacija.idRezervacije),
			async : true,
			success: function(response) {
				swal({
					  title: response,
					  icon: "success",
					  timer:2000
					})
				ucitajRezervisaneLetove();
				ucitajRezervisaneSobe();
				ucitajRezervisanaVozila();
			},
		});
		
	});
}

function prikaziRezervisaneSobe(rezSoba) {
	let tabela = $("#prikazRezervisanihSoba");
	tabela.empty();
	
	$.each(rezSoba, function(i, rSoba) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + rSoba.nazivHotela + '</td>');
		noviRed.append('<td class="column1">' + rSoba.brojSobe + '</td>');
		noviRed.append('<td class="column1">' + rSoba.brojKreveta + '</td>');		
		noviRed.append('<td class="column1">' + rSoba.cijena + '</td>');
		noviRed.append('<td class="column1">' + rSoba.datumDolaksa + '</td>');
		noviRed.append('<td class="column1">' + rSoba.datumOdlaksa + '</td>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "otkaziRezervacijuSobe" id = "' + i + '">Otkaži rezervaciju</a></td></tr>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "ocjeniSobu" id = "' + i + '">Ocjeni sobu</a></td></tr>');

		tabela.append(noviRed);
	});
	
	// okvir popup-a
	var modal = document.getElementById("myModal");

	// <span> za zatvaranje
	var span = document.getElementsByClassName("close")[0];
	
	
	
	$(".ocjeniSobu").click(function(e){
		
		e.preventDefault();
		
		var ratingValue = 0;
		let rezervacija = rezSoba[e.target.id];
		
		var ocjenjeno = false;
		//provjera da li je vec ocjenjeno
		$.ajax({
			type : 'GET',
			url : "../rezervacijeSoba/ocjenjenaRezervacija/" + rezervacija.idRezervacije,
			dataType : "json",
			async : false,
			success: function(response){
				ocjenjeno = response;
				if(response == true){
					swal({
						  title: "Vec ste ocjenili koristenu rezervaciju.",
						  icon: "info",
						  timer:2000
						})
					return;
				}
			},
		});
		
		if (ocjenjeno == true){
			return;
		}
		
		var trenutniDatum = new Date();
		var datumVracanja = Date.parse(rezervacija.datumOdlaksa);
		
		if (datumVracanja>trenutniDatum) {
			swal({
				  title: "Ne mozete ocjeniti sobu prije njenog napustanja.",
				  icon: "warning",
				  timer:2000
				})
			return;
		}
		
		modal.style.display = "block";
		// kad korisnik klikne na "x" zatvara se prozor
		span.onclick = function() {
		  modal.style.display = "none";
		};
		 var stars = $('#stars li').parent().children('li.star');
		    for (i = 0; i < stars.length; i++) {
		      $(stars[i]).removeClass('selected');
		    }
		// kad korisnik klikne bilo gdje izvan prozora prozor se zatvara
		window.onclick = function(event) {
		  if (event.target == modal) {
		    modal.style.display = "none";
		  }
		};
		
		/* 1. Prelazak preko zvjezdica */
		  $('#stars li').on('mouseover', function(){
		    var onStar = parseInt($(this).data('value'), 10); // Zvijezda na kojoj je trenutno kursor misa
		   
		    // highlight zvjezdica preko kojih je predjeno
		    $(this).parent().children('li.star').each(function(e){
		      if (e < onStar) {
		        $(this).addClass('hover');
		      }
		      else {
		        $(this).removeClass('hover');
		      }
		    });
		    
		  }).on('mouseout', function(){
		    $(this).parent().children('li.star').each(function(e){
		      $(this).removeClass('hover');
		    });
		  });
		  
		  
		  /* 2. Akcije za klik */
		  $('#stars li').click(function(e){
			  e.preventDefault();
		    var onStar = parseInt($(this).data('value'), 10); // Zvijezda koja je odabrana
		    var stars = $(this).parent().children('li.star');
		    
		    for (i = 0; i < stars.length; i++) {
		      $(stars[i]).removeClass('selected');
		    }
		    
		    for (i = 0; i < onStar; i++) {
		      $(stars[i]).addClass('selected');
		    }
		    
		    // Vrijednost odabrane zvjezdice
		     ratingValue = parseInt($('#stars li.selected').last().data('value'), 10);
		    var msg = "";
		    if (ratingValue > 1) {
		        msg = "Hvala! Glasali se sa " + ratingValue + " zvjezdica.";
		    }
		    else {
		        msg = "Pokušaćemo da poboljšamo svoje usluge. Glasali ste sa " + ratingValue + " zvjezdica.";
		    }
		    
		    $("#potvrdi_ocjenu").unbind().click(function(e){
		    	e.preventDefault();
			    modal.style.display = "none";
			    $.ajax({
					type: "POST",
					url : "../rezervacijeSoba/ocjeniSobu/" + ratingValue,
					contentType : "application/json; charset=utf-8",
					data: JSON.stringify(rezervacija.idRezervacije),
					async : false,
					success: function(response) {
						if (response == ''){
							swal({
								  title: "Uspješno ste ocjenili usluge sobe.",
								  icon: "success",
								  timer:2000
								})
						}
						else{
							swal({
								  title: response,
								  icon: "warning",
								  timer:2000
								})
						}
					},
				});

		    });
		  });
	})
	
	
	$(".otkaziRezervacijuSobe").click(function(e){
		e.preventDefault();
		let rezervacija = rezSoba[e.target.id];
		var datumP = Date.parse(rezervacija.datumDolaksa);
		var sadasnjiDatum = new Date();
		var diff= (datumP - sadasnjiDatum ) / (1000*60*60*24);
		if (diff<2){
			swal({
				  text: "Zakasnili ste sa otkazivanjem rezervacije sobe..",
				  icon: "warning",
				  timer: 2000
				});	
			return;
		}
		
		$.ajax({
			type: "POST",
			url : "../rezervacijeSoba/otkaziRezervaciju",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(rezervacija.idRezervacije),
			async : true,
			success: function(response) {
				swal({
					  title: response,
					  icon: "success",
					  timer:2000
					});
				ucitajRezervisaneSobe();
			},
		});
		
	});
}

function prelazakNaPregledPutovanja() {
	window.location.replace("../pregledPutovanja/index.html?idPutovanja=" + idPutovanja + "&inicijator=true");
}
