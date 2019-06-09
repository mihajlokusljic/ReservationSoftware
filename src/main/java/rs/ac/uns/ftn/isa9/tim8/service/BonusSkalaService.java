package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.common.StavkePopustaComparator;
import rs.ac.uns.ftn.isa9.tim8.model.BonusPopust;
import rs.ac.uns.ftn.isa9.tim8.repository.BonusPopustRepository;

@Service
public class BonusSkalaService {
	
	@Autowired
	protected BonusPopustRepository bonusPopustRepository;
	
	private boolean preklapanjeStavki(BonusPopust novaStavka) {
		//uslov preklapanja: novaDonja < staraGornja && staraDonja <= novaGornja
		boolean novaDonjaPreklapanje = false;
		boolean novaGornjaPreklapanje = false;
		for(BonusPopust stavka : this.bonusPopustRepository.findAll()) {
			if(stavka.getGornjaGranicaBonusPoeni() != null) {
				novaDonjaPreklapanje = novaStavka.getDonjaGranicaBonusPoeni() < stavka.getGornjaGranicaBonusPoeni();
			} else {
				novaDonjaPreklapanje = true;
			}
			
			if(novaStavka.getGornjaGranicaBonusPoeni() != null) {
				novaGornjaPreklapanje = stavka.getDonjaGranicaBonusPoeni() <= novaStavka.getGornjaGranicaBonusPoeni();
			} else {
				novaGornjaPreklapanje = true;
			}
			
			if(novaDonjaPreklapanje && novaGornjaPreklapanje) {
				return true;
			}
		}
		return false;
	}

	public BonusPopust dodajStavku(BonusPopust novaStavka) throws NevalidniPodaciException {
		// TODO Auto-generated method stub
		if(novaStavka.getDonjaGranicaBonusPoeni() == null) {
			throw new NevalidniPodaciException("Donja granica za bonus poene mora biti zadata.");
		}
		if(novaStavka.getDonjaGranicaBonusPoeni() < 0) {
			throw new NevalidniPodaciException("Donja granica za bonus poene ne smije biti negativna.");
		}
		if(novaStavka.getGornjaGranicaBonusPoeni() != null) {
			if(novaStavka.getGornjaGranicaBonusPoeni() < novaStavka.getDonjaGranicaBonusPoeni()) {
				throw new NevalidniPodaciException("Gornja granica za bonus poene ne smije biti manja od donje granice");
			}
		}
		if(novaStavka.getOstvarenProcenatPopusta() < 0 || novaStavka.getOstvarenProcenatPopusta() > 100) {
			throw new NevalidniPodaciException("Procenat ostvarenog popusta mora biti u intervalu od 0 do 100.");
		}
		if(this.preklapanjeStavki(novaStavka)) {
			throw new NevalidniPodaciException("Preklapanje intervala za bonus poene nije dozvoljeno.");
		}
		this.bonusPopustRepository.save(novaStavka);
		return novaStavka;
	}

	public boolean obrisiSkalu() {
		try {
			this.bonusPopustRepository.deleteAll();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Collection<BonusPopust> dobaviStavke() {
		List<BonusPopust> stavke = this.bonusPopustRepository.findAll();
		Collections.sort(stavke, new StavkePopustaComparator());
		return stavke;
	}

}
