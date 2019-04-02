package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.service.LetoviServisi;

@RestController
@RequestMapping(value = "/letovi")
public class LetoviKontroler {

	@Autowired
	@Qualifier("letoviServisMemSkladiste")
	protected LetoviServisi servis;

	@RequestMapping(value = "/dobaviSve", method = RequestMethod.GET)
	public Collection<Let> dobaviLetove() {
		return servis.dobaviLetove();
	}

	@RequestMapping(value = "/dodaj")
	public boolean dodajLet(@RequestBody Let noviLet) {
		return servis.dodajLet(noviLet);
	}

}
