var tokenKey = "jwtToken";
var aviokompanija = null;
var podaciAdmina = null;
let pocetnaStrana = "../pocetnaStranica/index.html";
let podrazumjevana_slika = "https://assets.nst.com.my/images/articles/PELANCARAN_SARAWAK_AIRCRAFT_LIVERsdjvsdjfjdfjfbgbfjdggfhY_1549092511.jpg";

$(document).ready(function() {
	
	$.ajaxSetup({
		headers: createAuthorizationTokenHeader(tokenKey),
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown)
		}
	});
	
	korisnikInfo();
	
	ucitajPodatkeSistema();
	ucitajDestinacije();
	ucitajAvione();
	ucitajLetove();
	
	$("#destinacije").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").show();
		$("#tab-avioni").hide();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();	
	});
	
	$("#avioni").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").show();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();	
	});
	
	$("#letovi").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").hide();
		$("#tab-letovi").show();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();	
	});

	$("#izvjestaj").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").hide();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").show();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();	
	});

	$("#izmjeni_podatke_tab").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").hide();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").show();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();
		profilKorisnika();
	});
	
	$("#promjeni_lozinku_tab").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").hide();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").show();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();
		promjeniLozinku();
	});
	
	$("#profilAviokomp").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").hide();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").show();
		$("#tab-dodatne-usluge").hide();
		$("#tab-odjava").hide();	
	});
	
	$("#dodatneUsluge").click(function(e){
		e.preventDefault();
		$("#tab-destinacije").hide();
		$("#tab-avioni").hide();
		$("#tab-letovi").hide();
		$("#tab-izvjestaj").hide();
		$("#tab-profilKorisnika").hide();
		$("#tab-profil-lozinka").hide();
		$("#tab-profilAviokompanije").hide();
		$("#tab-dodatne-usluge").show();
		$("#tab-odjava").hide();	
	});
	
	$("#dodavanjeDodatneUslugeForm").submit(function(e) {
		e.preventDefault();
		dodavanjeUsluge();
	})
	
	$("#administratorAviokompanijeDestinacijeForm").submit(function(e) {
		e.preventDefault();
				
		let naziv_destinacije = $("#destinacijeNazivDestinacije").val();		
		let puna_adresa = $("#destinacijePunaAdresa").val();
		
		let novaDestinacija = {
			naziv : naziv_destinacije,
			punaAdresa : puna_adresa,
			idAviokompanije : aviokompanija.id
		};
		
		$.ajax({
			type: "POST",
			url: "../destinacije/dodaj/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(novaDestinacija),
			success: function(response) {
				if(response == "Takva destinacija vec postoji.") {
					alert("Već postoji destinacija sa takvom punom adresom.");
					return;
				}
				else if (response == "Ne postoji aviokompanija sa datim id-jem.") {
					alert("Ne postoji aviokompanija sa datim id-jem.");
					return;
				}
				else if (response == "Ne mogu postojati dvije destinacije na istoj adresi.") {
					alert("Adresa je već zauzeta.");
					return;
				}
				else {
					alert("Destinacija je uspješno dodata.");
					prikaziDestinaciju(response, $("#administratorAviokompanijeDestinacijeTabela"));
					popuniListuZaDestinaciju(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	$("#osnovniPodaciAvionBtn").click(function() {
		if ($("#osnovniPodaciAvionBtn").is(":checked")) {
			$("#prvaFormaDodajAvion").show();
			$("#drugaFormaNaziviSegmenata").hide();
		}
	});

	$("#definisanjeSegmenataBtn").click(function() {
		if ($("#definisanjeSegmenataBtn").is(":checked")) {
			$("#prvaFormaDodajAvion").hide();
			$("#drugaFormaNaziviSegmenata").show();
			
			let id_aviona = $("#avionZaDodavanjeNekogSegmentaSelect").val();
			
			$.ajax({
				type: "GET",
				url: "../avioni/dobaviBrojRedovaAviona/" + id_aviona,
				contentType : "application/json; charset=utf-8",
				success: function(response) {
					$("#krajnjaVrstaZaSegment").attr("max", response);
					$("#krajnjaVrstaZaSegment").val(response);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("AJAX error: " + errorThrown);
				}
			});
			
		}
	});
	
	$("#FORMprvaFormaDodajAvion").submit(function(e) {
		e.preventDefault();
		
		let naziv_aviona = $("#avioniNazivAviona").val();
		let broj_vrsta = $("#avioniBrojVrsta").val();
		let broj_kolona = $("#avioniBrojKolona").val();
		let broj_segmenata = $("#avioniBrojSegmenata").val();
		
		if (parseInt(broj_segmenata) > parseInt(broj_vrsta)) {
			alert("Broj segmenata ne moze biti veći od broja vrsta (redova) aviona.");
			return;
		}
		
		let novi_avion = {
				naziv : naziv_aviona,
				idAviokompanije : aviokompanija.id,
				brojVrsta : broj_vrsta,
				brojKolona : broj_kolona,
				brojSegmenata : broj_segmenata
		};

		$.ajax({
			type: "POST",
			url: "../avioni/dodaj/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(novi_avion),
			success: function(response) {
				if(response == "Vec postoji avion sa zadatim nazivom.") {
					alert("Već postoji avion sa zadatim nazivom.");
					return;
				}
				else {
					alert("Avion je uspješno dodat.");
					prikaziAvion(response, $("#OsnovniAvioniRows"));
					popuniListuZaAvion(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	$("#avionZaDodavanjeNekogSegmentaSelect").change(function() {
		let id_aviona = $("#avionZaDodavanjeNekogSegmentaSelect").val();
		
		$.ajax({
			type: "GET",
			url: "../avioni/dobaviBrojRedovaAviona/" + id_aviona,
			contentType : "application/json; charset=utf-8",
			success: function(response) {
				$("#krajnjaVrstaZaSegment").attr("max", response);
				$("#krajnjaVrstaZaSegment").val(response);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	$("#FORMdrugaFormaNaziviSegmenata").submit(function(e) {
		e.preventDefault();
		
		let id_aviona = $("#avionZaDodavanjeNekogSegmentaSelect").val();
		let naziv_segmenta = $("#avioniNazivSegmentaZaDodavanje").val();
		let pocetni_red = $("#pocetnaVrstaZaSegment").val();
		let krajnji_red = $("#krajnjaVrstaZaSegment").val();
		
		let noviSegment = {
				naziv : naziv_segmenta,
				idAviona : id_aviona,
				pocetniRed : parseInt(pocetni_red),
				krajnjiRed : parseInt(krajnji_red)
		};
		
		$.ajax({
			type: "POST",
			url: "../avioni/dodajSegment/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviSegment),
			success: function(response) {
				if(response == "Svi segmenti zauzeti.") {
					alert("Svi segmenti su zauzeti, tj. već su im zadati nazivi.");
					return;
				}
				else {
					alert("Segment je uspješno dodat.");
					prikaziSegment(response, $("#osnovniSegmentiRows"));
					$("#pocetnaVrstaZaSegment").attr("min", parseInt(krajnji_red) + 1);
					$("#pocetnaVrstaZaSegment").val(parseInt(krajnji_red) + 1);
					$("#pocetnaVrstaZaSegment").attr("readonly", "readonly");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
	$("#administratorAviokompanijeLetoviForm").submit(function(e) {
		e.preventDefault();
				
		let broj_leta = $("#administratorAviokompanijeBrojLeta").val();		
		let polaziste = $("#administratorAviokompanijePolaziste").val();
		let odrediste = $("#administratorAviokompanijeOdrediste").val();
		let datum_poletanja = $("#input-start-2").val() + " " + $("#vrijemePolaskaTime").val();
		let datum_sletanja = $("#input-end-2").val() + " " + $("#vrijemeDolaskaTime").val();
		let datum_povratka = $("#duzinaPutovanja").val() + " " + $("#duzinaPutovanjaKadSeVracas").val();
		let cijena_karte = $("#baznaCijenaKarte").val();
		let id_aviona = $("#avionZaLetSelect").val();
		let id_odrediste = $("#administratorAviokompanijeOdrediste").val();
		let id_polaziste = $("#administratorAviokompanijePolaziste").val();
		let sva_presjedanja = $("#svaPresjedanja").val();
			
		let noviLet = {
			brojLeta : broj_leta,
			cijenaKarte : parseInt(cijena_karte),
			datumPoletanja : datum_poletanja,
			datumSletanja : datum_sletanja,
			duzinaPutovanja : datum_povratka,
			idAviokompanije : aviokompanija.id,
			idAviona : id_aviona,
			idDestinacijePresjedanja : sva_presjedanja,
			idOdrediste : id_odrediste,
			idPolaziste : id_polaziste
		};
		
		$.ajax({
			type: "POST",
			url: "../letovi/dodaj/",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(noviLet),
			success: function(response) {
				if(response == "Vec postoji let sa datim brojem leta.") {
					alert("Već postoji let sa datim brojem leta.");
					return;
				}
				else if (response == "Ne postoje makar dvije destinacije definisane za datu aviokompaniju.") {
					alert("Ne postoje makar dvije destinacije definisane za datu aviokompaniju.");
					return;
				}
				else if (response == "Datum poletanja mora biti prije datuma sletanja") {
					alert("Datum poletanja mora biti prije datuma sletanja");
					return;
				}
				else if (response == "Datum povratka mora biti nakon datuma sletanja.") {
					alert("Datum povratka mora biti nakon datuma sletanja.");
					return;
				}
				else if (response == "Nevalidan format datuma.") {
					alert("Nevalidan format datuma. Pokušajte ponovo.");
					return;
				}
				else if (response == "Cijena karte ne moze biti negativna.") {
					alert("Cijena karte ne moze biti negativna.");
					return;
				}
				else {
					alert("Let je uspješno dodat.");
					updateLet(response, $("#letoviRows"));
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});

	$("#izmjenaInfoStraniceAviokompanijeForm").submit(function(e) {
		e.preventDefault();
		izmjenaInfoStraniceAviokompanije();
	});
	
	$("#ponistiIzmjeneInfoStranicaAviokompanije").click(function(e) {
		e.preventDefault();
		$("#izmjenaInfoStraniceAviokompanijeForm")[0].reset();
		prikaziPodatkeAviokompanije();
	});
	
	$("#odjava").click(function(e) {
		e.preventDefault();
		odjava();
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
				podaciAdmina = data;
				$("#podaciAdmina").append(data.ime + " " + data.prezime);
			}
			else{
				alert("Nepostojeći korisnik");
			}
		},
	});
}

function ucitajPodatkeSistema() {

	$.ajax({
		type : 'GET',
		url : "../destinacije/podaciOServisu",
		dataType : "json",
		success: function(data){
			if(data != null){
				aviokompanija = data;
				prikaziPodatkeAviokompanije();
			}
		}
	});
}

function ucitajDestinacije() {
	$.ajax({
		type : "GET",
		url : "../destinacije/dobaviSve",
		dataType : "json",
		success : function(response) {
			prikaziDestinacije(response);
			popuniListuZaDestinacije(response);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function ucitajAvione() {
	$.ajax({
		type : "GET",
		url : "../avioni/dobaviSve",
		dataType : "json",
		success : function(response) {
			popuniListuZaAvione(response);
			prikaziAvione(response);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function ucitajLetove() {
	$.ajax({
		type : "GET",
		url : "../letovi/dobaviSve",
		dataType : "json",
		success : function(response) {
			updateLetovi(response);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX ERROR: " + errorThrown);
		}
	});
}

function izmjenaInfoStraniceAviokompanije() {
	let _naziv = $("#nazivAviokompanijeInfoStranica").val();
	let _adresa = $("#adresaAviokompanijeInfoStranica").val();
	let _opis = $("#promotivniOpisAviokompanijeInfoStranica").val();
	
	if (_naziv == "") {
		alert("Naziv aviokompanije mora biti zadat.");
		return;
	}
	
	if (_adresa == "") {
		alert("Adresa aviokompanije mora biti zadata.");
		return;
	}
	
	let izmjena_aviokompanija = {
			adresa : {punaAdresa : _adresa},
			naziv : _naziv,
			promotivniOpis : _opis
	};
	
	$.ajax({
		type: "PUT",
		url: "../aviokompanije/izmjeni",
		data: JSON.stringify(izmjena_aviokompanija),
		success: function(response) {
			aviokompanija = response;
			alert("Informacije o aviokompaniji su uspješno izmjenjene.");
		},
	});
	
}

function prikaziPodatkeAviokompanije() {
	$("#nazivAviokompanijeInfoStranica").val(aviokompanija.naziv);
	$("#adresaAviokompanijeInfoStranica").val(aviokompanija.adresa.punaAdresa);
	$("#promotivniOpisAviokompanijeInfoStranica").val(aviokompanija.promotivniOpis);
	$("#slikaAviokompanije").attr("src", podrazumjevana_slika);
	
}

function prikaziAvion(avion, tbody) {
	let row = $("<tr></tr>");
	
	row.append('<td class="column3">' + '<img src="https://upload.wikimedia.org/wikipedia/commons/4/40/Pan_Am_Boeing_747-121_N732PA_Bidini.jpg"' + 
			'</td>');
	row.append('<td class="column3">' + avion.naziv + "</td>");
	row.append('<td class="column5">' + avion.sjedista.length + "</td>");
	row.append('<td class="column5">' + avion.segmenti.length + "</td>");
	tbody.append(row);
}

function prikaziAvione(avioni) {
	let tbody = $("#OsnovniAvioniRows");
	tbody.empty();
	
	$.each(avioni, function(i, avion){
		prikaziAvion(avion, tbody);
	});
}

function prikaziDestinaciju(destinacija, tbody) {
	let row = $("<tr></tr>");
	
	row.append('<td class="column3">' + '<img src="https://static1.squarespace.com/static/57b9b98a29687f1ef5c622df/t/5b78746dc2241b4f57afadf6/1534620788911/zurich+best+view?format=1500w">' + 
			"</td>");
	row.append('<td class="column3">' + destinacija.nazivDestinacije + "</td>");
	row.append('<td class="column3">' + destinacija.adresa.punaAdresa + "</td>");
	
	tbody.append(row);
}

function prikaziDestinacije(destinacije){

	let tbody = $("#administratorAviokompanijeDestinacijeTabela");
	tbody.empty();
	
	$.each(destinacije, function(i,destinacija){
		prikaziDestinaciju(destinacija, tbody);
	});

}

function prikaziSegment(segment, tbody) {
	let row = $("<tr></tr>");
	
	row.append('<td class="column3">' + segment.naziv + "</td>");

	tbody.append(row);
}

function prikaziSegmente(segmenti) {
	let tbody = $("#osnovniSegmentiRows");
	tbody.empty();
	
	$.each(segmenti, function(i, segment){
		prikaziSegment(segment, tbody);
	});
}

function updateLet(flight, tbody) {	
	let row = $("<tr></tr>");
	
	// 2019-05-16	
	let datum_poletanja = new Date(flight.datumPoletanja);
	let datum_sletanja = new Date(flight.datumSletanja);
	
	row.append('<td class="column2">' + flight.brojLeta + "</td>");
	row.append('<td class="column3">' + flight.polaziste.nazivDestinacije + "</td>");
	row.append('<td class="column3">' + flight.odrediste.nazivDestinacije + "</td>");
	row.append('<td class="column1">' + datum_poletanja.getFullYear() + "-" + datum_poletanja.getMonth() + 
			"-" + datum_poletanja.getDate() + "</td>");
	row.append('<td class="column1">' + datum_sletanja.getFullYear() + "-" + datum_sletanja.getMonth() + 
			"-" + datum_sletanja.getDate() + "</td>");
	row.append('<td class="column5">' + flight.presjedanja.length + "</td>");
	row.append('<td class="column6">' + flight.cijenaKarte + "</td>");
	if (flight.brojOcjena > 0) {
		row.append('<td class="column6">' + (flight.sumaOcjena / flight.brojOcjena) + "</td>");
	} else {
		row.append('<td class="column6">Nema ocjena</td>');
	}
	tbody.append(row);

}

function updateLetovi(letovi) {
	let table = $("#letoviRows");
	table.empty();
	
	$.each(letovi, function(i, flight) {
		updateLet(flight, table);
	});
}

function popuniListuZaDestinaciju(destinacija) {
	let polazisteLista = $("#administratorAviokompanijePolaziste");
	let odredisteLista = $("#administratorAviokompanijeOdrediste");
	let svaPresjedanjaLista = $("#svaPresjedanja");
	
	polazisteLista.append('<option value="' + destinacija.id + '">' + destinacija.nazivDestinacije
			+ '</option>');
	odredisteLista.append('<option value="' + destinacija.id + '">' + destinacija.nazivDestinacije
			+ '</option>');
	svaPresjedanjaLista.append('<option value="' + destinacija.id + '">' + destinacija.nazivDestinacije
			+ '</option>');
}

function popuniListuZaDestinacije(destinacije) {
	let polazisteLista = $("#administratorAviokompanijePolaziste");
	let odredisteLista = $("#administratorAviokompanijeOdrediste");
	let svaPresjedanjaLista = $("#svaPresjedanja");
	
	polazisteLista.empty();
	odredisteLista.empty();
	svaPresjedanjaLista.empty();
	
	$.each(destinacije, function(i, destinacija) {
		popuniListuZaDestinaciju(destinacija);
	});
	
}

function popuniListuZaAvion(avion) {
	let avioniLista = $("#avionZaLetSelect");
	let avioniKodSegmenata = $("#avionZaDodavanjeNekogSegmentaSelect");
	
	avioniLista.append('<option value="' + avion.id + '">' + avion.naziv
			+ '</option>');
	avioniKodSegmenata.append('<option value="' + avion.id + '">' + avion.naziv
			+ '</option>');
}

function popuniListuZaAvione(avioni) {
	let avioniLista = $("#avionZaLetSelect");
	let avioniKodSegmenata = $("#avionZaDodavanjeNekogSegmentaSelect");

	avioniLista.empty();
	avioniKodSegmenata.empty();
	
	$.each(avioni, function(i, avion) {
		// <option value="1">A</option>
		avioniLista.append('<option value="' + avion.id + '">' + avion.naziv
				+ '</option>');
		avioniKodSegmenata.append('<option value="' + avion.id + '">' + avion.naziv
				+ '</option>');
	});
	
}

function profilKorisnika(){
	$("#emailAdmina").val(podaciAdmina.email);
	$("#imeAdmina").val(podaciAdmina.ime);
	$("#prezimeAdmina").val(podaciAdmina.prezime);
	$("#brTelefonaAdmina").val(podaciAdmina.brojTelefona);
	$("#adresaAdmina").val(podaciAdmina.adresa.punaAdresa);
	
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
				id: podaciAdmina.id,
				ime: imeAdmina,
				prezime: podaciAdmina.prezime,
				email: podaciAdmina.email,
				lozinka: podaciAdmina.lozinka,
				brojTelefona: brTelefonaAdmina,
				adresa: { punaAdresa : adresaAdmina }
		};
		$.ajax({
			type:"POST",
			url:"../aviokompanije/izmjeniProfilKorisnika",
			contentType : "application/json; charset=utf-8",
			data:JSON.stringify(admin),
			headers: createAuthorizationTokenHeader("jwtToken"),
			success:function(response){
				if (response == ''){
					alert("Izmjena nije uspjela.");
				}
				else{
					alert("Uspješno ste izmjenili profil.");
					podaciAdmina = response;
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
					alert("Uspješno ste izmjenili lozinku");	
				}
				
			}
		});
	});	
}

function dodavanjeUsluge() {
	let idAviokompanije = aviokompanija.id;
	let nazivUsluge = $("#nazivUslugeUnos").val();
	let cijenaUsluge = $("#cijenaUslugeUnos").val();
	let popustUsluge = $("#popustUslugeUnos").val();
	let opisUsluge = $("#opisUslugeUnos").val();
	if(opisUsluge == undefined) {
		opisUsluge = "";
	}
	
	let novaUsluga = {
		idPoslovnice: idAviokompanije,
		naziv: nazivUsluge,
		cijena: cijenaUsluge,
		procenatPopusta: popustUsluge,
		opis: opisUsluge
	}
	
	$.ajax({
		type: "POST",
		url: "../aviokompanije/dodajUslugu",
		contentType : "application/json; charset=utf-8",
		data: JSON.stringify(novaUsluga),
		success: function(response) {
			aviokompanija.cjenovnikDodatnihUsluga.push(response);
			prikaziUslugu(response);
			 $('#dodavanjeDodatneUslugeForm')[0].reset();
		},
	});
}

function prikaziUsluge(usluge) {
	$.each(usluge, function(i, usluga) {
		prikaziUslugu(usluga);
	})
}

function prikaziUslugu(usluga) {
	let uslugeTabela = $("#prikazUsluga");
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1">' + usluga.naziv + '</td>');
	noviRed.append('<td class="column6">' + usluga.cijena + '</td>');
	noviRed.append('<td class="column1">' + usluga.opis + '</td>');
	uslugeTabela.append(noviRed);
}

function odjava() {
	removeJwtToken();
	window.location.replace(pocetnaStrana);
}
