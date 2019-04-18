package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.service.AvionService;

@RestController
@RequestMapping(value = "/avioni")
public class AvionKontroler {
	@Autowired
	protected AvionService servis;
	
	@RequestMapping(value = "dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Avion>> dobaviAvione() {
		return new ResponseEntity<Collection<Avion>>(servis.dobaviAvione(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<Avion> dodajAvion(@RequestBody Avion noviAvion) {
		return new ResponseEntity<Avion>(servis.dodajAvion(noviAvion), HttpStatus.OK);
	}	
}


/*	
	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Aviokompanija>> dobaviAviokompanije() {
		return new ResponseEntity<Collection<Aviokompanija>>(servis.dobaviAviokompanije(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<String> dodajAviokompaniju(@RequestBody Aviokompanija novaAviokompanija) {
		return new ResponseEntity<String>(servis.dodajAviokompaniju(novaAviokompanija),HttpStatus.OK);
	}

}

 */