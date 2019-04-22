
var TOKEN_KEY = 'jwtToken';


$(document).ready(function() {	
	
	
	
	$("#loginForma").submit(function(e) {
		e.preventDefault();
		var username_email = $("#username").val();
		var password = $("#lozinka").val();
		
		let korisnik = {
				username: username_email,
				password: password,
		};
		
		$.ajax({
			type: "POST",
			url: "../auth/login",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(korisnik),
			success: function(response) {
				if(response == '') {
					alert("Pogrešan email/korisničko ime ili lozinka");
					return;
				} else {
					setJwtToken(TOKEN_KEY, response.accessToken);			
					if (response.tipKorisnika == "RegistrovanKorisnik"){
						alert ("Prijavili ste se kao registrovani korisnik.");
						return;
					}
					else if (response.tipKorisnika == "AdministratorHotela"){
						alert ("Prijavili ste se kao administrator hotela.");
						return;
					}
					else if (response.tipKorisnika == "AdministratorRentACar"){
						alert ("Prijavili ste se kao administrator rent-a-car-a.");
						return;
					}
					else if (response.tipKorisnika == "AdministratorAviokompanije"){
						alert ("Prijavili ste se kao administrator aviokompanije.");
						return;
					}
					else{
						alert ("Prijavili ste se kao sistemski administrator.");
						return;
					}
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
});