package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.isa9.tim8.dto.DestinacijaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.service.DestinacijaService;

@RestController
@RequestMapping(value = "/destinacije")
public class DestinacijeKontroler {
	
	@Autowired
	protected DestinacijaService servis;

	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Destinacija>> dobaviDestinacije() {
		return new ResponseEntity<Collection<Destinacija>>(servis.dobaviDestinacije(), HttpStatus.OK);
	}

	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<Destinacija> dodajDestinaciju(@RequestBody DestinacijaDTO novaDestinacija) {
		return new ResponseEntity<Destinacija>(servis.dodajDestinaciju(novaDestinacija), HttpStatus.OK);
	}
}