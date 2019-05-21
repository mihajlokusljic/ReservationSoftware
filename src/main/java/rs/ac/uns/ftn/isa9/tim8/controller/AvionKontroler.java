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

	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Avion>> dobaviAvione() {
		return new ResponseEntity<Collection<Avion>>(servis.dobaviAvione(), HttpStatus.OK);
	}

	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<?> dodajAvion(@RequestBody AvionDTO noviAvion) {
		try {
			return new ResponseEntity<Avion>(servis.dodajAvion(noviAvion), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/dobaviSegmenteZaAvion/{idAviona}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Segment>> dobaviSegmenteZaAvion(@PathVariable("idAviona") Long idAviona) {
		return new ResponseEntity<Collection<Segment>>(servis.dobaviSegmenteZaAvion(idAviona), HttpStatus.OK);
	}

	@RequestMapping(value = "/dodajSegment", method = RequestMethod.POST)
	public ResponseEntity<?> dodajSegment(@RequestBody SegmentDTO segment) {
		try {
			return new ResponseEntity<Segment>(servis.dodajSegment(segment.getIdAviona(), segment.getNaziv(),
					segment.getPocetniRed(), segment.getKrajnjiRed(), segment.getDodatnaCijenaZaSegment()),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
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

	@RequestMapping(value = "/ukloniSjediste/{idSjedista}", method = RequestMethod.DELETE)
	public ResponseEntity<String> ukloniSjediste(@PathVariable("idSjedista") Long idSjedista) {
		return new ResponseEntity<String>(servis.ukloniSjediste(idSjedista), HttpStatus.OK);
	}

	// azurirajSjediste(Long idSjedista, SjedisteDTO sjediste)
	@RequestMapping(value = "/azurirajSjediste/{idSjedista}", method = RequestMethod.PUT)
	public ResponseEntity<?> azurirajSjediste(@PathVariable("idSjedista") Long idSjedista,
			@RequestBody SjedisteDTO sjediste) {
		try {
			return new ResponseEntity<Sjediste>(servis.azurirajSjediste(idSjedista, sjediste), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/dobaviBrojRedovaAviona/{idAviona}", method = RequestMethod.GET)
	public ResponseEntity<Integer> brojRedovaAviona(@PathVariable("idAviona") Long idAviona) {
		return new ResponseEntity<Integer>(servis.dobaviBrojRedovaAviona(idAviona), HttpStatus.OK);
	}

}