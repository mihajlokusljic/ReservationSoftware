var tokenKey = "jwtToken";
var aviokompanije = [];
var hoteli = [];
var rentACarServisi = [];
var korisnik = null;
var prijatelji = [];
var broj_zahtjeva_za_prijateljstvo = 0;
//spring.datasource.initialization-mode=always

$(document).ready(function() {	
	
	//dodavanje zaglavlja sa JWT tokenom u svaki zahtjev upucen ajax pozivom i obrada gresaka
	$.ajaxSetup({
	    headers: createAuthorizationTokenHeader(),
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
	
	korisnikInfo();
	
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
	};
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg", "infoStranicaAviokompanije");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg", "infoStranicaHotela");

	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStranicaRac");
	
	
	ucitajRezervacije();
	
	//odjavljivanje
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
	});
	
	$("#aviokompanijeT").click(function(e){
		e.preventDefault();
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
	});
	
	$("#hoteliT").click(function(e){
		e.preventDefault();
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
		});
	
	$("#rentacarT").click(function(e){
		e.preventDefault();
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
		promjeniLozinku();
	});
	
	$("#racSearchForm").submit(function(e) {
		e.preventDefault();
	   
		let nazivLokacije = $("#racNaziv").val();
		let dolazak = $("#input-start-rac").val();
		let odlazak = $("#input-end-rac").val();
		
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

					prikazi(podatak, tbody, "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStraanicaRac");
				});
				if(response.length == 0) {
					alert("Ne postoji ni jedan rent-a-car servis koji zadovoljava kriterijume pretrage");
				}
				$('#racSearchForm')[0].reset();
			},
		});
		
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
	
});

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
				alert("Nepostojeći korisnik");
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
	$("#adresaAdmina").val(korisnik.adresa.punaAdresa);
	
	$("#forma_profil_korisnika").unbind().submit(function(e){
		e.preventDefault();
		var imeAdmina = $("#imeAdmina").val();
		if (imeAdmina == ''){
			alert("Polje za unos imena ne moze biti prazno.");
			return;
		}
		var prezimeAdmina = $("#prezimeAdmina").val();
		if (prezimeAdmina == ''){
			alert("Polje za unos prezimena ne moze biti prazno.");
			return;
		}
		var brTelefonaAdmina = $("#brTelefonaAdmina").val();
		if (brTelefonaAdmina == ''){
			alert("Polje za unos broja telefona ne moze biti prazno.");
			return;
		}
		var adresaAdmina = $("#adresaAdmina").val();
		if (adresaAdmina == ''){
			alert("Polje za unos adrese ne moze biti prazno.");
			return;
		}		

		let admin = {
				id: korisnik.id,
				ime: imeAdmina,
				prezime: korisnik.prezime,
				email: korisnik.email,
				lozinka: korisnik.lozinka,
				brojTelefona: brTelefonaAdmina,
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
					alert("Izmjena nije uspjela.");
				}
				else{
					alert("Uspješno ste izmjenili profil.");
					korisnik = response;
					profilKorisnika();
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
			alert("Niste unijeli novu lozinku");
			return;
		}
		
		if (novaLozinka != novaLozinka2){
			alert("Greška. Vrijednosti polja za lozinku i njenu potvrdu moraju biti iste.");
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
					alert("Pogrešna trenutna lozinka.");
					return;
				}
				else{
					setJwtToken("jwtToken", data.accessToken);
					alert("Uspješno ste izmjenili lozinku.");	
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
				alert("Uspješno uklonjen korisnik iz liste prijatelja.");	
				return;
				} else {
					alert("Došlo je do greške prilikom procesiranja zahtjeva. Molimo sačekajte i pokušajte ponovo.");
					return;
				}
			},
		});
		
	});
	
}

function prikaziPrijatelja(prijatelj) {
	let prijateljiTabela = $("#pregledPrijateljaRows");
	let noviRed = $("<tr></tr>");
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
					alert("Zahtjev je uspješno poslat.");
					$("#dpt" + idPotencijalnogPrijatelja).attr("disabled", "disabled");
					$("#dpt" + idPotencijalnogPrijatelja).empty();
					$("#dpt" + idPotencijalnogPrijatelja).append("Poslat zahtjev ✔️");
					return;
				} else {
					alert("Došlo je do greške prilikom slanja zahtjeva. Molimo pričekajte i pokušajte ponovo.");
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
				alert("Uspješno prihvaćen zahtjev za prijateljstvo.");
				
				$.ajax({
					type: "POST",
					url : "../korisnik/dobaviKorisnikeZaDodavanjePrijatelja",
					contentType : "application/json; charset=utf-8",
					data: JSON.stringify(korisnik.id),
					success: function(response) {
						prikaziKorisnikeZaPrijateljstvo(response);
					},
				});
				
				return;
				} else {
					alert("Došlo je do greške prilikom procesiranja zahtjeva. Molimo sačekajte i pokušajte ponovo.");
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
				alert("Uspješno odbijen zahtjev za prijateljstvo.");
				return;
				} else {
					alert("Došlo je do greške prilikom procesiranja zahtjeva. Molimo sačekajte i pokušajte ponovo.");
					return;
				}
			},
		});
		
	});
	
}

function prikaziKorisnikaKojiJeZatrazioPrijateljstvo(prijatelj) {
	let prijateljiTabela = $("#zahtjeviZaPrijateljstvoRows");
	let noviRed = $("<tr></tr>");
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
		},
		async: false,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + textStatus);
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
		},
		async: false,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + textStatus);
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
		},
		async: false,
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + textStatus);
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
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "otkaziRezervaciju" id = "' + vozilo.id + '">Otkaži rezervaciju</a></td></tr>')

		tabela.append(noviRed);
	});
}

function prikaziRezervisaneLetove(rezLetova){
	
	let tabela = $("#prikazRezervisanihLetova");
	tabela.empty();
	
	$.each(rezLetova, function(i, rLet) {
		let noviRed = $('<tr></tr>');
		noviRed.append('<td class="column1">' + rLet.nazivAviokompanije + '</td>');
		noviRed.append('<td class="column1">' + rLet.brojLeta + '</td>');
		noviRed.append('<td class="column1">' + rLet.cijena + '</td>');		
		noviRed.append('<td class="column1">' + rLet.sjediste.red + '/' + rLet.sjediste.kolona +  '</td>');
		noviRed.append('<td class="column1">' + rLet.destinacijaPolaska + '</td>');
		noviRed.append('<td class="column1">' + rLet.destinacijaDolaska + '</td>');
		noviRed.append('<td class="column1">' + rLet.datumPolaska + '</td>');
		noviRed.append('<td class="column1">' + rLet.datumDolaska + '</td>');
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "otkaziRezervaciju" id = "' + rLet.id + '">Otkaži rezervaciju</a></td></tr>')

		tabela.append(noviRed);
	});
}

function prikaziRezervisaneSobe(rezSoba){
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
		noviRed.append('</td><td class = "column1"><a href = "javascript:void(0)" class = "otkaziRezervaciju" id = "' + rSoba.id + '">Otkaži rezervaciju</a></td></tr>')

		tabela.append(noviRed);
	});
}

