package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.BonusPopust;
import rs.ac.uns.ftn.isa9.tim8.service.BonusSkalaService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;

@RestController
@RequestMapping(value = "/bonusSkala")
public class BonusSkalaKontroler {
	
	@Autowired
	protected BonusSkalaService bonusSkalaServis;
	
	@RequestMapping(value = "/dodajStavku", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajStavku(@RequestBody BonusPopust novaStavka) {
		try {
			return new ResponseEntity<BonusPopust>(this.bonusSkalaServis.dodajStavku(novaStavka), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/dobaviStavke", method = RequestMethod.GET)
	public ResponseEntity<Collection<BonusPopust>> dobaviStavke() {
		return new ResponseEntity<Collection<BonusPopust>>(this.bonusSkalaServis.dobaviStavke(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/obrisiSkalu", method = RequestMethod.DELETE)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public boolean obrisiSkalu() {
		return this.bonusSkalaServis.obrisiSkalu();
	}

}
