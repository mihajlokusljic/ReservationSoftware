package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaKarteDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaAviokompanijaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.UslugaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.service.AviokompanijaService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;;

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
	
	@RequestMapping(value = "/dobavi/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviAviokompaniju(@PathVariable("id") Long aviokompanijaId) {
		try {
			return new ResponseEntity<Aviokompanija>(servis.dobaviAviokompaniju(aviokompanijaId), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajAviokompaniju(@RequestBody Aviokompanija novaAviokompanija) {
		try {
			return new ResponseEntity<Aviokompanija>(servis.dodajAviokompaniju(novaAviokompanija),HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/izmjeni", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('AdministratorAviokompanije')")
	public ResponseEntity<?> izmjeniAviokompaniju(@RequestBody Aviokompanija noviPodaciZaAviokompaniju) {
		try {
			return new ResponseEntity<Aviokompanija>(servis.izmjeniAviokompaniju(noviPodaciZaAviokompaniju),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/izmjeniProfilKorisnika", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorAviokompanije')")
	public ResponseEntity<?> izmjeniKorisnika(@RequestBody KorisnikDTO korisnik) {
		return new ResponseEntity<KorisnikDTO>(servis.izmjeniProfilAdminaAviokompanije(korisnik), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pretrazi", method = RequestMethod.POST)
	public ResponseEntity<?> pretragaAviokompanija(@RequestBody PretragaAviokompanijaDTO kriterijumPretrage) {
		try {
			return new ResponseEntity<Collection<Aviokompanija> >(servis.pretraziAviokompanije(kriterijumPretrage), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/dodajUslugu", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorAviokompanije')")
	public ResponseEntity<?> dodajUsluguAviokompanije(@RequestBody UslugaDTO novaUsluga) {
		try {
			return new ResponseEntity<Usluga>(servis.dodajUsluguAviokompanije(novaUsluga), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/dodajBrzuRezervacijuKarte", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorAviokompanije')")
	public ResponseEntity<?> dodajBrzuRezervacijuKarte(@RequestBody BrzaRezervacijaKarteDTO novaRezervacija) {
		try {
			return new ResponseEntity<BrzaRezervacijaKarteDTO>(servis.dodajBrzuRezervaciju(novaRezervacija), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/zadajPopustBrzojRezervaciji", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorAviokompanije')")
	public ResponseEntity<?> zadajPopustBrzeRezervacije(@RequestBody BrzaRezervacijaKarteDTO brzaRezervacija) {
		try {
			return new ResponseEntity<BrzaRezervacijaKarteDTO>(servis.zadajPopustBrzeRezervacije(brzaRezervacija), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	/*
	 SecurityContextHolder - omogucuje koriscenje statickih metoda koje delegiraju izvrsenje instanci SecurityContextHolderStrategy. Svrha ove klase
	 je u nalazenju nacina da specificiramo strategiju koja ce se koristiti za dati JVM.
	 Pozivanje nad njima .getContext().getAuthentication().getPrincipal() omogucuje prikupljanje podataka o trenutno ulogovanom korisniku u SS.
	 */

}
