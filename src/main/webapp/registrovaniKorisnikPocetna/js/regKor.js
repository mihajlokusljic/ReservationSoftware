var tokenKey = "jwtToken";
var aviokompanije = [];
var hoteli = [];
var rentACarServisi = [];
var korisnik = null;
var prijatelji = [];
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
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg", "infoStranicaAviokompanije");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg", "infoStranicaHotela");

	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg", "infoStranicaRac");
	
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

		$.ajax({
			type: "POST",
			url : "../korisnik/dobaviKorisnikeZaDodavanjePrijatelja",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik.id),
			success: function(response) {
				prikaziKorisnikeZaPrijateljstvo(response);
			},
		});
		
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
	
});

function korisnikInfo(){
	let token = getJwtToken("jwtToken");
	$.ajax({
		type : 'GET',
		url : "../korisnik/getInfo",
		dataType : "json",
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
	noviRed.append('<td class="column1"><a href="../' + infoStranica+'/index.html?id=' + podatak.id + '">Pogledaj detalje</a></td>');

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
	$.each(prijatelji, function(i, prijatelj) {
		prikaziPrijatelja(prijatelj);
	})
}

function prikaziPrijatelja(prijatelj) {
	let prijateljiTabela = $("#pregledPrijateljaRows");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
	"</td>");	
	noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
	prijateljiTabela.append(noviRed);
}

function prikaziKorisnikeZaPrijateljstvo(prijatelji) {
	$("#dodavanjePrijateljaRows").empty();
	$.each(prijatelji, function(i, prijatelj) {
		prikaziKorisnikaZaPrijateljstvo(prijatelj);
	})
}

function prikaziKorisnikaZaPrijateljstvo(prijatelj) {
	let prijateljiTabela = $("#dodavanjePrijateljaRows");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
	"</td>");
	noviRed.append('<td class="column6">' + '<input type="hidden" id="' + prijatelj.id +  '">' + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
	noviRed.append('<td class="column1"><button class="btn-submit dodajPrijateljaClass" type="button" id="' 
			+ prijatelj.id + '">Dodaj prijatelja</button></td>');
	noviRed.append('<td class="column6"></td>');
	prijateljiTabela.append(noviRed);
}

function prikaziKorisnikeKojiSuZatraziliPrijateljstvo(prijatelji) {
	$("#zahtjeviZaPrijateljstvoRows").empty();
	$.each(prijatelji, function(i, prijatelj) {
		prikaziKorisnikaKojiJeZatrazioPrijateljstvo(prijatelj);
	})
}

function prikaziKorisnikaKojiJeZatrazioPrijateljstvo(prijatelj) {
	let prijateljiTabela = $("#zahtjeviZaPrijateljstvoRows");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + '<img src="http://www.logospng.com/images/64/user-pro-avatar-login-account-svg-png-icon-free-64755.png">' + 
	"</td>");
	noviRed.append('<td class="column6">' + '<input type="hidden" id="' + prijatelj.id +  '">' + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.ime + '</td>');
	noviRed.append('<td class="column1">' + prijatelj.prezime + '</td>');
	noviRed.append('<td class="column1"><button class="btn-submit prihvatiPrijateljstvoClass" type="button" id="' 
			+ prijatelj.id + '">✔️</button></td>');
	noviRed.append('<td class="column1"><button class="btn-submit odbijPrijateljstvoClass" type="button" id="' 
			+ prijatelj.id + '">❌</button></td>');
	noviRed.append('<td class="column6"></td>');
	prijateljiTabela.append(noviRed);
}

function odjava() {
	removeJwtToken();
	window.location.replace("../pocetnaStranica/index.html");
}