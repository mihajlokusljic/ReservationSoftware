package rs.ac.uns.ftn.isa9.tim8.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

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
import rs.ac.uns.ftn.isa9.tim8.service.EmailService;
import rs.ac.uns.ftn.isa9.tim8.service.KorisnikService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private EmailService mailService;

	@Autowired
	private KorisnikService korisnikService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@Valid @RequestBody Osoba korisnik) {

		if (this.userDetailsService.emailZauzet(korisnik.getEmail())) {
			return new ResponseEntity<String>("Zauzet email", HttpStatus.OK);
		}

		RegistrovanKorisnik registrovaniKorisnik = new RegistrovanKorisnik();
		registrovaniKorisnik.setIme(korisnik.getIme());
		registrovaniKorisnik.setPrezime(korisnik.getPrezime());
		registrovaniKorisnik.setEmail(korisnik.getEmail());
		registrovaniKorisnik.setIme(korisnik.getIme());
		registrovaniKorisnik.setLozinka(userDetailsService.encodePassword(korisnik.getLozinka()));
		this.userDetailsService.podesiPrivilegije(registrovaniKorisnik, TipKorisnika.RegistrovanKorisnik);
		registrovaniKorisnik.setBonusPoeni(0);
		registrovaniKorisnik.setBrojTelefona(korisnik.getBrojTelefona());
		registrovaniKorisnik.setAdresa(korisnik.getAdresa());
		registrovaniKorisnik.setPrijatelji(new HashSet<RegistrovanKorisnik>());
		registrovaniKorisnik.setPrimljenePozivnice(new HashSet<Pozivnica>());
		registrovaniKorisnik.setEnabled(true);
		registrovaniKorisnik.setVerifikovanMail(false); // ceka se verifikacija
		registrovaniKorisnik.setBrojPasosa(korisnik.getBrojPasosa());

		String dodatKor = userDetailsService.dodajRegistrovanogKorisnika(registrovaniKorisnik);
		try {
			mailService.sendMailAsync(registrovaniKorisnik);
		} catch (MailException | InterruptedException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(dodatKor, HttpStatus.OK);
	}

	@RequestMapping(value = "/registerAvioAdmin", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> registrujAdministratoraAviokompanije(@RequestBody RegistracijaAdminaDTO adminReg) {
		try {
			this.userDetailsService.dodajAdminaAviokompanije(adminReg);
			return new ResponseEntity<String>("Administrator je uspesno dodat.", HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/registerHotelAdmin", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajAdminaHotela(@RequestBody RegistracijaAdminaDTO adminReg) {
		try {
			this.userDetailsService.dodajAdminaHotela(adminReg);
			return new ResponseEntity<String>("Administrator je uspesno dodat", HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/registerRacAdmin", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajAdminaRacServisa(@RequestBody RegistracijaAdminaDTO adminReg) {
		try {
			this.userDetailsService.dodajAdminaRacServisa(adminReg);
			return new ResponseEntity<String>("Administrtor je uspesno dodat.", HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	/*
	 * Napomena: postoji default-ni sistemski administrator: email - root@root.com
	 * lozinka: IsaMrs2019
	 */
	@RequestMapping(value = "/registerSysAdmin", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajSistemskogAdmina(@RequestBody RegistracijaAdminaDTO adminReg) {
		try {
			this.userDetailsService.dodajSistemAdmina(adminReg);
			return new ResponseEntity<String>("Administrtor je uspesno dodat.", HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException {

		final Authentication authentication;

		try {
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		Osoba user = (Osoba) authentication.getPrincipal();

		if (!user.isVerifikovanMail()) {
			return new ResponseEntity<String>("verifikacija", HttpStatus.OK);
		}
		String jwt = tokenUtils.generateToken(user.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		TipKorisnika tipKorisnika = null;
		String redirectionURL = "#";

		if (user instanceof RegistrovanKorisnik) {
			tipKorisnika = TipKorisnika.RegistrovanKorisnik;
			redirectionURL = "../registrovaniKorisnikPocetna/index.html";
		} else if (user instanceof AdministratorHotela) {
			tipKorisnika = TipKorisnika.AdministratorHotela;
			redirectionURL = "../administratorHotela/index.html";
		} else if (user instanceof AdministratorRentACar) {
			tipKorisnika = TipKorisnika.AdministratorRentACar;
			redirectionURL = "../administratorRentACarServisa/administratorRentACarServisa.html";
		} else if (user instanceof AdministratorAviokompanije) {
			tipKorisnika = TipKorisnika.AdministratorAviokompanije;
			redirectionURL = "../administratorAviokompanije/administratorAviokompanije.html";
		} else {
			tipKorisnika = TipKorisnika.AdministratorSistema;
			redirectionURL = "../sistemAdministrator/index.html";
		}

		// Vrati token kao odgovor na uspesno autentifikaciju
		return new ResponseEntity<UserTokenState>(new UserTokenState(jwt, expiresIn, tipKorisnika, redirectionURL),
				HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changePassword/{staraLozinka}", method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword(@PathVariable("staraLozinka") String staraLozinka,
			@RequestBody String novaLozinka) {

		Osoba o = (Osoba) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!userDetailsService.poklapanjeLozinki(staraLozinka, o.getLozinka())) {
			return new ResponseEntity<String>("", HttpStatus.OK);
		}

		userDetailsService.changePassword(staraLozinka, novaLozinka);
		String jwt = tokenUtils.generateToken(o.getUsername());
		int expiresIn = tokenUtils.getExpiredIn();
		Set<Authority> a = (Set<Authority>) o.getAuthorities();
		return new ResponseEntity<UserTokenState>(
				new UserTokenState(jwt, expiresIn, a.iterator().next().getTipKorisnika(), true), HttpStatus.OK);
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public RedirectView confirmRegistration(@RequestParam String token) {
		Osoba korisnik = korisnikService.vratiKorisnikaPoTokenu(token);
		if (korisnik != null) {
			korisnik.setVerifikovanMail(true);
			userDetailsService.dodajRegistrovanogKorisnika((RegistrovanKorisnik) korisnik);
			return new RedirectView("/registracija/verifikacija.html");
		}
		return null;
	}

}
