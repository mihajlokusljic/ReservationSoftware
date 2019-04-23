$(document).ready(function() {
	
	$("#registracijaForma").submit(function(e) {
		e.preventDefault();
		var ime = $("#ime").val();
		if (ime == ""){
			alert("Niste unijeli ime.");
			return;
		}
		var prezime = $("#prezime").val();
		if (prezime == ""){
			alert("Niste unijeli prezime");
			return;
		}
		var email = $("#email").val();
		if (email == ""){
			alert("Niste unijeli email");
			return;
		}
		var brojTelefona = $("#brojTelefona").val();
		if (brojTelefona == ""){
			alert("Niste unijeli broj telefona");
			return;
		}
		var lozinka = $("#lozinka").val();
		if (lozinka== ""){
			alert("Niste unijeli lozinku");
			return;
		}
		var lozinka2 = $("#lozinka2").val();
		if (lozinka2 != lozinka){
			alert("Ne poklapaju se lozinke");
			return;
		}

		let korisnik = {
				ime:ime,
				prezime:prezime,
				email:email,
				brojTelefona:brojTelefona,
				lozinka:lozinka,
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/register",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik),
			success: function(response) {
				if(response == '') {
					alert("Uspjesno ste se registrovali");
					window.location.replace("../login/login.html");
				} else {
					alert(response);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
});