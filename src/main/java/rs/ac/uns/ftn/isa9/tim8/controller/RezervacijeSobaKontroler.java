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

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.IzvrsavanjeBrzeRezervacijeSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.ZahtjevRezervacijaSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.service.HotelskeSobeService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;
import rs.ac.uns.ftn.isa9.tim8.service.RezervacijeSobaService;

@RestController
@RequestMapping(value = "/rezervacijeSoba")
public class RezervacijeSobaKontroler {

	@Autowired
	protected RezervacijeSobaService rezervacijeServis;

	@Autowired
	protected HotelskeSobeService sobeServis;

	@RequestMapping(value = "/dodajBrzuRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> dodajBrzuRezervaciju(@RequestBody BrzaRezervacijaSobeDTO novaRezervacija) {
		try {
			return new ResponseEntity<BrzaRezervacijaSobeDTO>(sobeServis.dodajBrzuRezervaciju(novaRezervacija),
					HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/dodajUslugeBrzojRezervaciji", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> dodajUslugeBrzeRezervacije(@RequestBody BrzaRezervacijaSobeDTO brzaRezervacija) {
		try {
			return new ResponseEntity<BrzaRezervacijaSobeDTO>(
					rezervacijeServis.dodajUslugeBrzeRezervacije(brzaRezervacija), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/zadajPopustBrzojRezervaciji", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> zadajPopustBrzeRezervacije(@RequestBody BrzaRezervacijaSobeDTO brzaRezervacija) {
		try {
			return new ResponseEntity<BrzaRezervacijaSoba>(
					rezervacijeServis.zadajPopustBrzeRezervacije(brzaRezervacija), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/sveBrzeRezervacijeHotela/{idHotela}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviSveBrzeRezervacijeZaHotel(@PathVariable("idHotela") Long idHotela) {
		try {
			return new ResponseEntity<Collection<BrzaRezervacijaSoba>>(
					rezervacijeServis.dobaviSveBrzeRezervacijeZaHotel(idHotela), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/pretragaBrzihRezervacijaSoba", method = RequestMethod.POST)
	public ResponseEntity<?> pretraziBrzeRezervacijeSoba(@RequestBody PretragaSobaDTO kriterijumiPretrage) {
		try {
			return new ResponseEntity<Collection<BrzaRezervacijaSoba>>(
					rezervacijeServis.pretraziBrzeRezervacijeSoba(kriterijumiPretrage), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/izvrsiBrzuRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> izvrsiBrzuRezervaciju(@RequestBody IzvrsavanjeBrzeRezervacijeSobeDTO podaciRezervacije) {
		try {
			return new ResponseEntity<String>(rezervacijeServis.izvrsiBrzuRezervaciju(
					podaciRezervacije.getIdBrzeRezervacije(), podaciRezervacije.getIdPutovanja()), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/izvrsiRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<String> izvrsiRezervacijuSoba(@RequestBody ZahtjevRezervacijaSobaDTO rezervacijaPodaci) {
		try {
			return new ResponseEntity<String>(rezervacijeServis.izvrsiRezervacijuSoba(rezervacijaPodaci), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/otkaziRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> otkaziRezervaciju(@RequestBody Long id) {
		try {
			return new ResponseEntity<String >(rezervacijeServis.otkaziRezervaciju(id), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value = "/ocjenjenaRezervacija/{idRezervacije}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
 	public ResponseEntity<?> ocjenjenaRezervacija(@PathVariable("idRezervacije") Long idRezervacije){
		try {
			return new ResponseEntity<Boolean>(rezervacijeServis.rezervacijaSobeOcjenjena(idRezervacije), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/ocjeniSobu/{ratingValue}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('RegistrovanKorisnik')")
	public ResponseEntity<?> ocjeniVozilo(@RequestBody Long id, @PathVariable("ratingValue") int ratingValue){
		try {
			return new ResponseEntity<String >(this.rezervacijeServis.ocjeniSobu(id, ratingValue), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
