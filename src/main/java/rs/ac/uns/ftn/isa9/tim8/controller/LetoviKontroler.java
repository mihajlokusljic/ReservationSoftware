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

import rs.ac.uns.ftn.isa9.tim8.dto.BoravakDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaLetaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.service.AviokompanijaService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/letovi")
public class LetoviKontroler {

	@Autowired
	protected AviokompanijaService servis;

	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Let>> dobaviLetove() {
		return new ResponseEntity<Collection<Let>>(servis.dobaviLetove(), HttpStatus.OK);
	}

	@RequestMapping(value = "/dobaviSveLetoveZaAviokompaniju/{idAviokompanije}", method = RequestMethod.GET)
	public ResponseEntity<Collection<Let>> dobaviLetove(@PathVariable("idAviokompanije") Long idAviokompanije) {
		return new ResponseEntity<Collection<Let>>(servis.dobaviLetove(idAviokompanije), HttpStatus.OK);
	}

	@RequestMapping(value = "/letoviAviokompanije/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviLetoveAviokompanije(@PathVariable("id") Long aviokompanijaId) {
		try {
			return new ResponseEntity<Collection<Let>>(servis.dobaviLetoveAviokompanije(aviokompanijaId),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<?> dodajLet(@RequestBody LetDTO noviLet) {
		try {
			return new ResponseEntity<Let>(servis.dodajLet(noviLet), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/pretraziLetove", method = RequestMethod.POST)
	public ResponseEntity<?> pretraziLetove(@RequestBody PretragaLetaDTO kriterijumiPretrage) {
		try {
			return new ResponseEntity<Collection<Let>>(servis.pretraziLetove(kriterijumiPretrage), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/dobaviSjedistaZaPrikazNaMapi/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviSjedistaZaPrikazNaMapi(@PathVariable("id") Long idLeta) {
		try {
			return new ResponseEntity<PrikazSjedistaDTO>(servis.dobaviSjedistaZaPrikazNaMapi(idLeta),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/otkaziRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> otkaziRezervaciju(@RequestBody Long id) {
		try {
			return new ResponseEntity<String >(servis.otkaziRezervaciju(id), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/podaciBoravkaZaLet/{idLeta}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviPodatkeBoravka(@PathVariable("idLeta") Long idLeta) {
		try {
			return new ResponseEntity<BoravakDTO>(servis.dobaviPodatkeBoravka(idLeta), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/ocjenjenaRezervacija/{idRezervacije}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
 	public ResponseEntity<?> ocjenjenaRezervacija(@PathVariable("idRezervacije") Long idRezervacije){
		try {
			return new ResponseEntity<Boolean>(servis.rezervacijaLetaOcjenjena(idRezervacije), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/ocjeniLet/{ratingValue}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> ocjeniVozilo(@RequestBody Long id, @PathVariable("ratingValue") int ratingValue){
		try {
			return new ResponseEntity<String >(servis.ocjeniLet(id, ratingValue), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
