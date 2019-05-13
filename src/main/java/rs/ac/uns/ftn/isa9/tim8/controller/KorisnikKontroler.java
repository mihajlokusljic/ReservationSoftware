package rs.ac.uns.ftn.isa9.tim8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
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
		return new ResponseEntity<KorisnikDTO>(
				new KorisnikDTO(o.getId(),o.getIme(), o.getPrezime(), o.getEmail(), o.getLozinka(), o.getBrojTelefona(), o.getAdresa()),
				HttpStatus.OK);
	}
	
	@RequestMapping(value = "/izmjeniProfil", method = RequestMethod.PUT)
	public ResponseEntity<?> izmjeniProfilKorisnika(KorisnikDTO noviPodaci) {
		try {
			return new ResponseEntity<KorisnikDTO>(korisnikService.izmjeniPorfilKorisnika(noviPodaci), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	

}
