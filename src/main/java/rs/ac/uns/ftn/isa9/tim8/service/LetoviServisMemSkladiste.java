package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.repository.MemorijskoSkladiste;

@Service
public class LetoviServisMemSkladiste implements LetoviServisi {

	@Autowired
	protected MemorijskoSkladiste bpMenadzer;
	
	
	@Override
	public boolean dodajLet(Let noviLet) {
		return bpMenadzer.dodajLet(noviLet);
	}

	@Override
	public boolean obrisiLet(Let letZaBrisanje) {
		return bpMenadzer.obrisiLet(letZaBrisanje);
	}

	@Override
	public boolean izmjeniLet(Let azuriranLet) {
		return bpMenadzer.izmjeniLet(azuriranLet);
	}

	@Override
	public Collection<Let> dobaviLetove() {
		return bpMenadzer.dobaviLetove();
	}

	@Override
	public Let pronadjiLet(String brojLeta) {
		return bpMenadzer.pronadjiLet(brojLeta);
	}
	
}
