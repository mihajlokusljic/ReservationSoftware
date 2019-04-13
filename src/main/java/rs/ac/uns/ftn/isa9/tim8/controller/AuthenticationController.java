package rs.ac.uns.ftn.isa9.tim8.controller;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Authority;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.Pozivnica;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.TipKorisnika;
import rs.ac.uns.ftn.isa9.tim8.security.TokenUtils;
import rs.ac.uns.ftn.isa9.tim8.service.CustomUserDetailsService;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
	
	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@Valid @RequestBody Osoba korisnik) {
		if (this.userDetailsService.korisnickoImeZauzeto(korisnik.getKorisnickoIme()) == true) {
			return new ResponseEntity<String> ("Zauzeto korisnicko ime", HttpStatus.OK);
		}
		
		else if (this.userDetailsService.emailZauzet(korisnik.getEmail()) == true) {
			return new ResponseEntity<String> ("Zauzet email", HttpStatus.OK);
		}
		
		RegistrovanKorisnik registrovaniKorisnik = new RegistrovanKorisnik();
		registrovaniKorisnik.setIme(korisnik.getIme());
		registrovaniKorisnik.setPrezime(korisnik.getPrezime());
		registrovaniKorisnik.setEmail(korisnik.getEmail());
		registrovaniKorisnik.setIme(korisnik.getIme());
		registrovaniKorisnik.setKorisnickoIme(korisnik.getKorisnickoIme());
		registrovaniKorisnik.setLozinka(userDetailsService.encodePassword(korisnik.getLozinka()));
		Set<Authority> autoriteti = new HashSet<Authority>();
		Authority a = new Authority();
		a.setTipKorisnika(TipKorisnika.RegistrovanKorisnik);
		autoriteti.add(a);
		registrovaniKorisnik.setAuthorities(autoriteti);
		registrovaniKorisnik.setBonusPoeni(0);
		registrovaniKorisnik.setBrojTelefona(korisnik.getBrojTelefona());
		registrovaniKorisnik.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		registrovaniKorisnik.setPrijatelji(new HashSet<RegistrovanKorisnik>());
		registrovaniKorisnik.setPrimljenePozivnice(new HashSet<Pozivnica>());
		registrovaniKorisnik.setEnabled(false);
		
		return new ResponseEntity<String> (userDetailsService.dodajRegistrovanogKorisnika(registrovaniKorisnik),HttpStatus.OK);
	}

}
