$(document).ready(function() {
	
	$("#registracijaForma").submit(function(e) {
		e.preventDefault();
		var ime = $("#ime").val();
		if (ime == ""){
			swal({
				  title: "Niste unijeli ime.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var prezime = $("#prezime").val();
		if (prezime == ""){
			swal({
				  title: "Niste unijeli prezime.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var email = $("#email").val();
		if (email == ""){
			swal({
				  title: "Niste unijeli email.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var brojTelefona = $("#brojTelefona").val();
		if (brojTelefona == ""){
			swal({
				  title: "Niste unijeli broj telefona.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var _brojPasosa = $("#brojPasosa").val();
		if (_brojPasosa == ""){
			swal({
				  title: "Niste unijeli broj pasoša.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var adresa = $("#adresa").val();
		if (adresa == ""){
			swal({
				  title: "Niste unijeli adresu stanovanja.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var lozinka = $("#lozinka").val();
		if (lozinka== ""){
			swal({
				  title: "Niste unijeli lozinku.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}
		var lozinka2 = $("#lozinka2").val();
		if (lozinka2 != lozinka){
			swal({
				  title: "Ne poklapaju se lozinke.",
				  icon: "warning",
				  timer: 2500
				})	
			return;
		}

		let korisnik = {
				ime:ime,
				prezime:prezime,
				email:email,
				brojTelefona:brojTelefona,
				brojPasosa : _brojPasosa,
				lozinka:lozinka,
				adresa: { punaAdresa : adresa }
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/register",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik),
			success: function(response) {
				if(response == '') {
					swal({
						  title: "Uspješno ste se registrovali.",
						  icon: "success",
						  timer: 2500
						}).then(function(){
							window.location.replace("../login/login.html");
						});	
					
				} else {
					swal({
						  title: response,
						  icon: "warning",
						  timer: 2500
						});
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
});