package rs.ac.uns.ftn.isa9.tim8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.service.HotelskeSobeService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;
import rs.ac.uns.ftn.isa9.tim8.service.RezervacijeSobaService;

@RestController
@RequestMapping(value = "/rezervacijeSoba")
public class RezervacijeSobaKontroler {
	
	@Autowired
	protected RezervacijeSobaService servis;
	
	@Autowired
	protected HotelskeSobeService sobeServis;
	
	@RequestMapping(value = "/dodajBrzuRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> dodajBrzuRezervaciju(@RequestBody BrzaRezervacijaSobeDTO novaRezervacija) {
		try {
			return new ResponseEntity<BrzaRezervacijaSobeDTO>(sobeServis.dodajBrzuRezervaciju(novaRezervacija), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
