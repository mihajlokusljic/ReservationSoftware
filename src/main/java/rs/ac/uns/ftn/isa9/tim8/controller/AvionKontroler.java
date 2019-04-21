package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.AvionDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.SegmentDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.SjedisteDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.model.Segment;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;
import rs.ac.uns.ftn.isa9.tim8.service.AvionService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

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
	public ResponseEntity<Avion> dodajAvion(@RequestBody AvionDTO noviAvion) {
		return new ResponseEntity<Avion>(servis.dodajAvion(noviAvion), HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/dobaviSegmenteZaAvion/{idAviona}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Segment>> dobaviSegmenteZaAvion(@PathVariable("idAviona") Long idAviona) {
		return new ResponseEntity<Collection<Segment>>(servis.dobaviSegmenteZaAvion(idAviona), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajSegment", method = RequestMethod.POST)
	public ResponseEntity<Segment> dodajSegment(@RequestBody SegmentDTO segment) {
		return new ResponseEntity<Segment>(servis.dodajSegment(segment.getIdAviona(), segment.getNaziv()), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajSjediste", method = RequestMethod.POST)
	public ResponseEntity<?> dodajSjediste(@RequestBody SjedisteDTO sjediste) {
		try {
			return new ResponseEntity<Sjediste>(servis.dodajSjediste(sjediste), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
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