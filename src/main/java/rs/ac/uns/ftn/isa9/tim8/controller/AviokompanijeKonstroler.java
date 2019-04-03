package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.service.AviokompanijaServisi;

@RestController
@RequestMapping(value = "/aviokompanije")
public class AviokompanijeKonstroler {
	
	@Autowired
	//@Qualifier("aviokompanijaServisMemSkladiste")
	protected AviokompanijaServisi servis;
	
	
	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public Collection<Aviokompanija> dobaviAviokompanije() {
		return servis.dobaviAviokompanije();
	}
	
	@RequestMapping(value = "/dodaj")
	public boolean dodajAviokompaniju(@RequestBody Aviokompanija novaAviokompanija) {
		return servis.dodajAviokompaniju(novaAviokompanija);
	}

}
