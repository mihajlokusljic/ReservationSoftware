
var TOKEN_KEY = 'jwtToken';


$(document).ready(function() {	
	
	
	
	$("#prijava_btn").click(function(e) {
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
				} 
				else if(response == 'verifikacija'){
					alert("Niste verifikovali svoj nalog.");
					return;
				}
				else {
					setJwtToken(TOKEN_KEY, response.accessToken);			
					if (response.tipKorisnika == "RegistrovanKorisnik"){
						alert ("Prijavili ste se kao registrovani korisnik.");
					}
					else if (response.tipKorisnika == "AdministratorHotela"){
						alert ("Prijavili ste se kao administrator hotela.");
					}
					else if (response.tipKorisnika == "AdministratorRentACar"){
			//			alert ("Prijavili ste se kao administrator rent-a-car-a.");
					}
					else if (response.tipKorisnika == "AdministratorAviokompanije"){
						alert ("Prijavili ste se kao administrator aviokompanije.");
					}
					else{
						alert ("Prijavili ste se kao sistemski administrator.");
					}
					window.location.replace(response.redirectionUrl);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
});