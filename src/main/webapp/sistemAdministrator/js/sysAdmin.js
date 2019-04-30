let poslovnicaAdminaInputs = ["aviokompanijaAdmina", "hotelAdmina", "racServisAdmina"]
let poslovnicaInputs = ["aviokompanijaAdminaInp", "hotelAdminaInp", "racAdminaInp"]
let podaciAdmina = null;

function prikaziIzborPoslovnice(idPoslovnice) {
	$.each(poslovnicaAdminaInputs, function(i, tekucaPoslovnicaId) {
		if(idPoslovnice === tekucaPoslovnicaId) {
			$("#" + tekucaPoslovnicaId).show();
			$("#" + poslovnicaInputs[i]).attr("required", "required");
		}
		else
		{
			$("#" + tekucaPoslovnicaId).hide();
			$("#" + poslovnicaInputs[i]).removeAttr("required");
		}
	})
}

$(document).ready(function(e) {
	
	//dodavanje zaglavlja sa JWT tokenom u svaki zahtjev upucen ajax pozivom
	$.ajaxSetup({
	    headers: createAuthorizationTokenHeader(),
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error - " + XMLHttpRequest.status + " " + XMLHttpRequest.statusText + ": " + errorThrown);
		}
	});
	
	//ucitavanje podataka profila administratora
	korisnikInfo();
	
	//ucitavanje aviokompanija
	ucitajPodatke("../aviokompanije/dobaviSve", "prikazAviokompanija", "aviokompanije-list", "https://cdn.logojoy.com/wp-content/uploads/2018/05/30142202/1_big-768x591.jpg");
	
	//ucitavanje hotela
	ucitajPodatke("../hoteli/dobaviSve", "prikazHotela", "hoteli-list", "https://s-ec.bstatic.com/images/hotel/max1024x768/147/147997361.jpg");

	//ucitavanje rent-a-car servisa
	ucitajPodatke("../rentACar/sviServisi", "prikazRacServisa", "rac-list", "https://previews.123rf.com/images/helloweenn/helloweenn1612/helloweenn161200021/67973090-car-rent-logo-design-template-eps-10.jpg");

	//podesavanje vidljivosti polja za poslovnicu kod dodavanja novog administratora
	$("#adminAviokompanijeBtn").click(function() {
		if($("#adminAviokompanijeBtn").is(":checked")) { prikaziIzborPoslovnice("aviokompanijaAdmina"); }
	});
	
	$("#adminHotelaBtn").click(function() {
		if($("#adminHotelaBtn").is(":checked")) { prikaziIzborPoslovnice("hotelAdmina"); }
	});
	
	$("#racAdminBtn").click(function() {
		if($("#racAdminBtn").is(":checked")) { prikaziIzborPoslovnice("racServisAdmina"); }
	});
	
	$("#sysAdminBtn").click(function() {
		if($("#sysAdminBtn").is(":checked")) { prikaziIzborPoslovnice(""); }
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
				alert("nepostojeci korisnik");
			}
		},
	});
}

function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz, idSelekcionogMenija, defaultSlika) {
	let tabela = $("#" + idTabeleZaPrikaz);
	if(idSelekcionogMenija == "") {
		return;
	}
	let selekcioniMeni = $("#" + idSelekcionogMenija);
	$.ajax({
		type: "GET",
		url: putanjaControlera,
		success: function(response) {
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela, selekcioniMeni, defaultSlika);
			});
		},
	});
}

function prikazi(podatak, tabelaZaPrikaz, selekcioniMeni, defaultSlika) {
	let noviRed = $("<tr></tr>");
	noviRed.append('<td class="column1"><img src="' + defaultSlika + '"/></td>');
	noviRed.append('<td class="column1">' + podatak.naziv + '</td>');
	noviRed.append('<td class="column1">' + podatak.adresa.punaAdresa + '</td>');
	noviRed.append('<td class="column6">' + podatak.promotivniOpis + '</td>');
	tabelaZaPrikaz.append(noviRed);
	if(selekcioniMeni != undefined) {
		selekcioniMeni.append('<option data-value="' + podatak.id + '" value="' + podatak.naziv + ', ' + podatak.adresa.punaAdresa + '"></option>');
	}
}