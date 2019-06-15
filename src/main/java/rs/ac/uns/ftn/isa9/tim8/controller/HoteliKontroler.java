package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;
import java.util.HashSet;

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

import rs.ac.uns.ftn.isa9.tim8.dto.DatumiZaPrihodDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.IzvjestajDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaHotelaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.UslugaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.service.HotelService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/hoteli")
public class HoteliKontroler {
	
	@Autowired
	protected HotelService servis;
	
	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Hotel>> dobaviHotele() {
		return new ResponseEntity<Collection<Hotel>>(servis.dobaviHotele(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/dobavi/{idHotela}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviHotel(@PathVariable("idHotela") Long idHotela) {
		try {
			return new ResponseEntity<Hotel>(servis.dobaviHotel(idHotela), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value = "/sobeHotela/{idHotela}", method = RequestMethod.GET)
	public ResponseEntity<?> sobeHotela(@PathVariable("idHotela") Long idHotela) {
		try {
			return new ResponseEntity<Collection<HotelskaSoba> >(servis.sobehotela(idHotela), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajHotel(@RequestBody Hotel noviHotel) {
		noviHotel.setSobe(new HashSet<HotelskaSoba>());
		try {
			return new ResponseEntity<Hotel>(servis.dodajHotel(noviHotel),HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/izmjeni", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> izmjeniHotel(@RequestBody Hotel noviPodaciHotela) {
		try {
			return new ResponseEntity<Hotel>(servis.izmjeniHotel(noviPodaciHotela), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/dodajUslugu", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> dodajUsluguHotela(@RequestBody UslugaDTO novaUsluga) {
		try {
			return new ResponseEntity<Usluga>(servis.dodajUsluguHotela(novaUsluga), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/pretrazi", method = RequestMethod.POST)
	public ResponseEntity<?> pretragaHotela(@RequestBody PretragaHotelaDTO kriterijumiPretrage) {
		try {
			return new ResponseEntity<Collection<Hotel> >(this.servis.pretraziHotele(kriterijumiPretrage), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/administriraniHotel", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<Hotel> podaciOServisu() {
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<Hotel>(admin.getHotel(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prihodHotela/{idHotela}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
	public ResponseEntity<?> prihodServisa(@RequestBody DatumiZaPrihodDTO datumiDto, @PathVariable("idHotela") Long idHotela ) {
		try {
			return new ResponseEntity<String >(this.servis.izracunajPrihode(datumiDto, idHotela), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@RequestMapping(value = "/dnevniIzvjestaj/{idHotela}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
 	public ResponseEntity<?> dnevniIzvjestaj(@RequestBody DatumiZaPrihodDTO datumiDto, @PathVariable("idHotela") Long idHotela){
		try {
			return new ResponseEntity<IzvjestajDTO>(this.servis.dnevniIzvjestaj(idHotela, datumiDto), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/nedeljniIzvjestaj/{idHotela}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
 	public ResponseEntity<?> nedeljniIzvjestaj(@RequestBody DatumiZaPrihodDTO datumiDto, @PathVariable("idHotela") Long idHotela){
		try {
			return new ResponseEntity<IzvjestajDTO>(this.servis.nedeljniIzvjestaj(idHotela, datumiDto), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/mjesecniIzvjestaj/{idHotela}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
 	public ResponseEntity<?> mjesecniIzvjestaj(@RequestBody DatumiZaPrihodDTO datumiDto, @PathVariable("idHotela") Long idHotela){
		try {
			return new ResponseEntity<IzvjestajDTO>(this.servis.mjesecniIzvjestaj(idHotela, datumiDto), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/slobodneSobe/{idHotela}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
 	public ResponseEntity<?> slobodneSobe(@RequestBody DatumiZaPrihodDTO datumiDto, @PathVariable("idHotela") Long idHotela){
		try {
			return new ResponseEntity<Collection<HotelskaSoba>>(servis.slobodneSobe(idHotela, datumiDto), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/rezervisaneSobe/{idHotela}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorHotela')")
 	public ResponseEntity<?> rezervisaneSobe(@RequestBody DatumiZaPrihodDTO datumiDto, @PathVariable("idHotela") Long idHotela){
		try {
			return new ResponseEntity<Collection<HotelskaSoba>>(servis.rezervisaneSobe(idHotela, datumiDto), HttpStatus.OK);
		}catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}

}
