package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.service.HotelServisi;

@RestController
@RequestMapping(value = "/hoteli")
public class HoteliKontroler {
	
	@Autowired
	@Qualifier("hoteliServisMemSkladiste")
	protected HotelServisi servis;
	
	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public Collection<Hotel> dobaviHotele() {
		return servis.dobaviHotele();
	}
	
	@RequestMapping(value = "/dodaj", method = RequestMethod.POST)
	public boolean dodajHotel(@RequestBody Hotel noviHotel) {
		return servis.dodajHotel(noviHotel);
	}

}
