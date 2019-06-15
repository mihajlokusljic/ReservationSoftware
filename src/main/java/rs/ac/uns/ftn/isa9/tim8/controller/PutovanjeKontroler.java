package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;
import rs.ac.uns.ftn.isa9.tim8.service.PutovanjeService;

@RestController
@RequestMapping(value = "/putovanja")
public class PutovanjeKontroler {

	@Autowired
	protected PutovanjeService servis;

	@RequestMapping(value = "/dobaviSve/{idPutovanja}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviPutovanje(@PathVariable("idPutovanja") Long idPutovanja) {
		try {
			return new ResponseEntity<Putovanje>(servis.dobaviPutovanje(idPutovanja), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/dobaviVozila/{idPutovanja}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviVozila(@PathVariable("idPutovanja") Long idPutovanja) {
		try {
			return new ResponseEntity<Collection<PrikazRezVozilaDTO>>(servis.dobaviVozila(idPutovanja), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
