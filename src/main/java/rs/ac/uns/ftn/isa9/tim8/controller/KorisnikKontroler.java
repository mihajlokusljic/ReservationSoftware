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

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikKontroler {
	
	@Autowired
	protected KorisnikService korisnikService;
	
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public ResponseEntity<KorisnikDTO> getUserData() {
		Osoba o = (Osoba) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<KorisnikDTO>(
				new KorisnikDTO(o.getIme(), o.getPrezime(), o.getEmail(), o.getLozinka(), o.getBrojTelefona(), o.getAdresa()),
				HttpStatus.OK);
	}
	
	
	

}
