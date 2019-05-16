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

import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaPrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.service.KorisnikService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikKontroler {

	@Autowired
	protected KorisnikService korisnikService;

	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserData() {
		Osoba o = (Osoba) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(o.getId(), o.getIme(), o.getPrezime(), o.getEmail(),
				o.getLozinka(), o.getBrojTelefona(), o.getAdresa(), o.isLozinkaPromjenjena()), HttpStatus.OK);
	}

	@RequestMapping(value = "/izmjeniProfil", method = RequestMethod.PUT)
	public ResponseEntity<?> izmjeniProfilKorisnika(@RequestBody KorisnikDTO noviPodaci) {
		try {
			return new ResponseEntity<KorisnikDTO>(korisnikService.izmjeniPorfilKorisnika(noviPodaci), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/izmjeniProfilKorisnika", method = RequestMethod.POST)
	public ResponseEntity<?> izmjeniKorisnika(@RequestBody KorisnikDTO korisnik) {
		return new ResponseEntity<KorisnikDTO>(korisnikService.izmjeniProfilRegistrovanogKorisnika(korisnik), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dobaviSvePrijatelje", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> dobaviPrijatelje(Long korisnikId) {
		try {
		return new ResponseEntity<Collection<PretragaPrijateljaDTO>>(korisnikService.dobaviPrijatelje(korisnikId),HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

}
