package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.service.AviokompanijaService;;

@RestController
@RequestMapping(value = "/aviokompanije")
public class AviokompanijeKontroler {
//	
//	@Autowired
//	//@Qualifier("aviokompanijaServisMemSkladiste")
//	protected AviokompanijaServisi servis;
//	
	@Autowired
	protected AviokompanijaService servis;
	
	
	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Aviokompanija>> dobaviAviokompanije() {
		return new ResponseEntity<Collection<Aviokompanija>>(servis.dobaviAviokompanije(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<String> dodajAviokompaniju(@RequestBody Aviokompanija novaAviokompanija) {
		return new ResponseEntity<String>(servis.dodajAviokompaniju(novaAviokompanija),HttpStatus.OK);
	}
	
	/*
	 SecurityContextHolder - omogucuje koriscenje statickih metoda koje delegiraju izvrsenje instanci SecurityContextHolderStrategy. Svrha ove klase
	 je u nalazenju nacina da specificiramo strategiju koja ce se koristiti za dati JVM.
	 Pozivanje nad njima .getContext().getAuthentication().getPrincipal() omogucuje prikupljanje podataka o trenutno ulogovanom korisniku u SS.
	 */
	
	
}
