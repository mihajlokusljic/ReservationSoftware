package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.service.HotelService;

@RestController
@RequestMapping(value = "/hoteli")
public class HoteliKontroler {
	
	@Autowired
	protected HotelService servis;
	
	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public ResponseEntity<Collection<Hotel>> dobaviHotele() {
		return new ResponseEntity<Collection<Hotel>>(servis.dobaviHotele(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public ResponseEntity<String> dodajHotel(@RequestBody Hotel noviHotel) {
		return new ResponseEntity<String>(servis.dodajHotel(noviHotel),HttpStatus.OK);
	}

}
