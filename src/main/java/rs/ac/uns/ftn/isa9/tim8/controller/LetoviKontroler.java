package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
import rs.ac.uns.ftn.isa9.tim8.service.AviokompanijaService;

@RestController
@RequestMapping(value = "/letovi")
public class LetoviKontroler {

	@Autowired
	protected AviokompanijaService servis;

	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<LetDTO>>  dobaviLetove() {
		return new ResponseEntity<Collection<LetDTO>>(servis.vratiLetoveOsnovno(servis.dobaviLetove()),HttpStatus.OK);
	}

	@RequestMapping(value = "/dodaj")
	public ResponseEntity<String> dodajLet(@RequestBody LetDTO noviLet) {
		return new ResponseEntity<String>(servis.dodajLet(noviLet),HttpStatus.OK);
	}

}
