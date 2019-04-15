package rs.ac.uns.ftn.isa9.tim8.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.RegistracijaAdminaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorAviokompanije;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorRentACar;
import rs.ac.uns.ftn.isa9.tim8.model.Authority;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.Pozivnica;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.TipKorisnika;
import rs.ac.uns.ftn.isa9.tim8.model.UserTokenState;
import rs.ac.uns.ftn.isa9.tim8.security.TokenUtils;
import rs.ac.uns.ftn.isa9.tim8.security.auth.JwtAuthenticationRequest;
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
		
		if (this.userDetailsService.emailZauzet(korisnik.getEmail()) == true) {
			return new ResponseEntity<String> ("Zauzet email", HttpStatus.OK);
		}
		
		RegistrovanKorisnik registrovaniKorisnik = new RegistrovanKorisnik();
		registrovaniKorisnik.setIme(korisnik.getIme());
		registrovaniKorisnik.setPrezime(korisnik.getPrezime());
		registrovaniKorisnik.setEmail(korisnik.getEmail());
		registrovaniKorisnik.setIme(korisnik.getIme());
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
		registrovaniKorisnik.setEnabled(true); //dok ne uvedemo verifikaciju mailom
		
		return new ResponseEntity<String> (userDetailsService.dodajRegistrovanogKorisnika(registrovaniKorisnik),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/registerAvioAdmin", method = RequestMethod.POST)
	public ResponseEntity<?> registrujAdministratoraAviokompanije(@RequestBody RegistracijaAdminaDTO adminReg) {
		if (this.userDetailsService.dodajAdminaAviokompanije(adminReg)) {
			return new ResponseEntity<String>("Administrator aviokompanije je uspesno dodat.", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Greska. Zadati email vec postoji ili ne postoji zadata aviokompanija.", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {
		
		final Authentication authentication;
		
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<String>("",HttpStatus.OK);
		}
		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		Osoba user = (Osoba) authentication.getPrincipal();
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		TipKorisnika tipKorisnika = null;
		
		if (user instanceof RegistrovanKorisnik) {
			tipKorisnika = TipKorisnika.RegistrovanKorisnik;
		} else if (user instanceof AdministratorHotela) {
			tipKorisnika = TipKorisnika.AdministratorHotela;
		} else if (user instanceof AdministratorRentACar) {
			tipKorisnika = TipKorisnika.AdministratorRentACar;
		} else if (user instanceof AdministratorAviokompanije) {
			tipKorisnika = TipKorisnika.AdministratorAviokompanije;
		} else {
			tipKorisnika = TipKorisnika.AdministratorSistema;
		}

		// Vrati token kao odgovor na uspesno autentifikaciju
		return new ResponseEntity<UserTokenState>(new UserTokenState(jwt, expiresIn, tipKorisnika), HttpStatus.OK);
	}


}
