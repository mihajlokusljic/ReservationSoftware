package rs.ac.uns.ftn.isa9.tim8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
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
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NullPointerException e) {
			return new ResponseEntity<String>("Sve vrijednosti moraju biti zadate.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/izmjeni", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> izmjeniSobu(@RequestBody HotelskaSoba noviPodaciSobe) {
		try {
			return new ResponseEntity<HotelskaSoba>(sobeService.izmjeniSobu(noviPodaciSobe), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/obrisi/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> obrisiHotelskuSobu(@PathVariable("id") Long idSobe) {
		try {
			return new ResponseEntity<String>(this.sobeService.obrisiSobu(idSobe), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
