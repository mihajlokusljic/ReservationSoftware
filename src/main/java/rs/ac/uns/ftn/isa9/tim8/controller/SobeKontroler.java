package rs.ac.uns.ftn.isa9.tim8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.DodavanjeSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.service.HotelskeSobeService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/hotelskeSobe")
public class SobeKontroler {
	
	@Autowired
	HotelskeSobeService sobeService;
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> dodajHotelskuSobu(@RequestBody DodavanjeSobeDTO novaSobaPodaci) {
		try {
			return new ResponseEntity<HotelskaSoba>(this.sobeService.dodajSobuHotelu(novaSobaPodaci), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Sve vrijednosti moraju biti zadate.", HttpStatus.OK);
		}
	}

}
