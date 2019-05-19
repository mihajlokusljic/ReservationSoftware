package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.FiltriranjePrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaPrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.UklanjanjePrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.ZahtjevZaPrijateljstvoDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
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
		if (o instanceof RegistrovanKorisnik) {
			o = (RegistrovanKorisnik) o;
			return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(o.getId(), o.getIme(), o.getPrezime(), o.getEmail(),
					o.getLozinka(), o.getBrojTelefona(), o.getAdresa(), o.isLozinkaPromjenjena(),
					((RegistrovanKorisnik) o).getPrimljeniZahtjevi().size()), HttpStatus.OK);
		} else {
			return new ResponseEntity<KorisnikDTO>(new KorisnikDTO(o.getId(), o.getIme(), o.getPrezime(), o.getEmail(),
					o.getLozinka(), o.getBrojTelefona(), o.getAdresa(), o.isLozinkaPromjenjena()), HttpStatus.OK);
		}
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
		return new ResponseEntity<KorisnikDTO>(korisnikService.izmjeniProfilRegistrovanogKorisnika(korisnik),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/dobaviSvePrijatelje", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> dobaviPrijatelje(@RequestBody Long korisnikId) {
		try {
			return new ResponseEntity<Collection<PretragaPrijateljaDTO>>(korisnikService.dobaviPrijatelje(korisnikId),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/dobaviKorisnikeZaDodavanjePrijatelja", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> dobaviKorisnikeZaPrijateljstvo(@RequestBody Long korisnikId) {
		try {
			return new ResponseEntity<Collection<PretragaPrijateljaDTO>>(
					korisnikService.dobaviKorisnikeZaPrijateljstvo(korisnikId), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/dobaviZahtjeveZaPrijateljstvo", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> dobaviZahtjeveZaPrijateljstvo(@RequestBody Long korisnikId) {
		try {
			return new ResponseEntity<Collection<PretragaPrijateljaDTO>>(
					korisnikService.dobaviZahtjeveZaPrijateljstvo(korisnikId), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/dodajPrijatelja", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> dodajPrijatelja(@RequestBody ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvo) {
		try {
			return new ResponseEntity<Boolean>(korisnikService.dodajPrijatelja(zahtjevZaPrijateljstvo), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/prihvatiZahtjevZaPrijateljstvo", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> prihvatiZahtjevZaPrijateljstvo(
			@RequestBody ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvo) {
		try {
			return new ResponseEntity<Boolean>(korisnikService.prihvatiZahtjevZaPrijateljstvo(zahtjevZaPrijateljstvo),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/odbijZahtjevZaPrijateljstvo", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> odbijZahtjevZaPrijateljstvo(
			@RequestBody ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvo) {
		try {
			return new ResponseEntity<Boolean>(korisnikService.odbijZahtjevZaPrijateljstvo(zahtjevZaPrijateljstvo),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/ukloniPrijatelja", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> ukloniPrijatelja(
			@RequestBody UklanjanjePrijateljaDTO uklanjanjePrijateljaDTO) {
		try {
			return new ResponseEntity<Boolean>(korisnikService.uklanjanjePrijatelja(uklanjanjePrijateljaDTO),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/pretraziPrijatelje", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> pretraziPrijatelje(
			@RequestBody FiltriranjePrijateljaDTO filtriranjePrijateljaDTO) {
		try {
			return new ResponseEntity<Collection<PretragaPrijateljaDTO>>(korisnikService.pretragaPrijatelja(filtriranjePrijateljaDTO),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/daLiJeZahtjevVecPoslat", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> daLiJeZahtjevVecPoslat(@RequestBody ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvo) {
		try {
			return new ResponseEntity<Boolean>(korisnikService.daLiJeZahtjevVecPoslat(zahtjevZaPrijateljstvo),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/rezervisanaVozila", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> rezervisanaVozila() {
		RegistrovanKorisnik regKor = (RegistrovanKorisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<Collection<PrikazRezVozilaDTO>>(korisnikService.vratiRezervacijeVozila(regKor), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/rezervisaniLetovi", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> rezervisaniLetovi() {
		RegistrovanKorisnik regKor = (RegistrovanKorisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<Collection<PrikazRezSjedistaDTO>>(korisnikService.vratiRezervacijeLetova(regKor), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/rezervisaneSobe", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> rezervisaneSobe() {
		RegistrovanKorisnik regKor = (RegistrovanKorisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<Collection<PrikazRezSobeDTO>>(korisnikService.vratiRezervacijeSoba(regKor), HttpStatus.OK);
	}
}
