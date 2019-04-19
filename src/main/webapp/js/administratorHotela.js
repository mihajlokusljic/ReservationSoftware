$(document).ready(function() {
	
	$("#dodavanjeSobeForm").submit(function(e) {
		e.preventDefault();
		
		let _idHotela = $("#hotelID").val();
		let _brojSobe = $("#brojSobe").val();
		let _sprat = $("#sprat").val();
		let _vrsta = $("#vrsta").val();
		let _kolona = $("#kolona").val();
		let _brojKreveta = $("#brojKreveta").val();
		let _cijenaNocenja = $("#cijenaNocenja").val();
		
		let novaSoba = {
			idHotela: _idHotela,
			brojSobe: _brojSobe,
			sprat: _sprat,
			vrsta: _vrsta,
			kolona: _kolona,
			brojKreveta: _brojKreveta,
			cijenaNocenja: _cijenaNocenja
		};
		
		$.ajax({
			type: "POST",
			url: "../hotelskeSobe/dodaj",
			contentType : "application/json; charset=utf-8",
			data: JSON.stringify(novaSoba),
			success: function(response) {
				alert(response);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("AJAX error: " + errorThrown);
			}
		});
		
	});
	
});