package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.DestinacijaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorAviokompanije;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.service.DestinacijaService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/destinacije")
public class DestinacijeKontroler {

	@Autowired
	protected DestinacijaService servis;

	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Destinacija>> dobaviDestinacije() {
		return new ResponseEntity<Collection<Destinacija>>(servis.dobaviDestinacije(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dobaviSveDestinacijeZaAviokompaniju/{idAviokompanije}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Destinacija>> dobaviDestinacije(@PathVariable("idAviokompanije") Long idAviokompanije) {
		return new ResponseEntity<Collection<Destinacija>>(servis.dobaviDestinacije(idAviokompanije), HttpStatus.OK);
	}

	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<?> dodajDestinaciju(@RequestBody DestinacijaDTO novaDestinacija) {
		try {
			return new ResponseEntity<Destinacija>(servis.dodajDestinaciju(novaDestinacija), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/podaciOServisu", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorAviokompanije')")
	public ResponseEntity<Poslovnica> podaciOServisu() {
		AdministratorAviokompanije admin = (AdministratorAviokompanije) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return new ResponseEntity<Poslovnica>(admin.getAviokompanija(), HttpStatus.OK);
	}
}
