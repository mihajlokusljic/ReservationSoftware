
var TOKEN_KEY = 'jwtToken';


$(document).ready(function() {	
	
	
	
	$("#formaPrijava").submit(function(e) {
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
					swal({
						  title: "Neuspješna prijava!",
						  text: "Pogrešan email ili lozinka",
						  icon: "error",
						});
					return;
				} 
				else if(response == 'verifikacija'){
					swal({
						  title: "Neuspješna prijava!",
						  text: "Niste verifikovali svoj nalog",
						  icon: "error",
						});
					return;
				}
				else {
					
					setJwtToken(TOKEN_KEY, response.accessToken);			
					if (response.tipKorisnika == "RegistrovanKorisnik"){
						
						swal({
							  title: "Uspješna prijava!",
							  text: "Prijavili ste se kao registrovani korisnik",
							  icon: "success",
							}).then(function(){
							window.location.replace(response.redirectionUrl);
						})
					}
					else if (response.tipKorisnika == "AdministratorHotela"){
						swal({
							  title: "Uspješna prijava!",
							  text: "Prijavili ste se kao administrator hotela",
							  icon: "success",
							}).then(function(){
							window.location.replace(response.redirectionUrl);
						})
					}
					else if (response.tipKorisnika == "AdministratorRentACar"){
						
						swal({
							  title: "Uspješna prijava!",
							  text: "Prijavili ste se kao administrator rent-a-car servisa",
							  icon: "success",
							}).then(function(){
							window.location.replace(response.redirectionUrl);
						})
					}
					else if (response.tipKorisnika == "AdministratorAviokompanije"){
						swal({
							  title: "Uspješna prijava!",
							  text: "Prijavili ste se kao administrator aviokompanije",
							  icon: "success",
							}).then(function(){
							window.location.replace(response.redirectionUrl);
						})
					}
					else{
						swal({
							  title: "Uspješna prijava!",
							  text: "Prijavili ste se kao sistemski administrator",
							  icon: "success",
							}).then(function(){
							window.location.replace(response.redirectionUrl);
						});
					}
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
	});
	
});