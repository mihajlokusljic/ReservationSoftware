package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.repository.MemorijskoSkladiste;

@Service
public class AviokompanijaServisMemSkladiste implements AviokompanijaServisi {
	
	@Autowired
	protected MemorijskoSkladiste bpMenadzer;

	@Override
	public boolean dodajAviokompaniju(Aviokompanija novaAviokompanija) {
		return bpMenadzer.dodajAviokompaniju(novaAviokompanija);
	}

	@Override
	public boolean obrisiAviokompaniju(Aviokompanija aviokompanijaZaBrisanje) {
		return bpMenadzer.obrisiAviokompaniju(aviokompanijaZaBrisanje);
	}

	@Override
	public boolean izmjeniAviokompanju(Aviokompanija noviPodaci) {
		return bpMenadzer.izmjeniAviokompanju(noviPodaci);
	}

	@Override
	public Collection<Aviokompanija> dobaviAviokompanije() {
		return bpMenadzer.dobaviAviokompanije();
	}

	@Override
	public Aviokompanija pronadjiAviokompaniju(String nazivAviokompanije) {
		return bpMenadzer.pronadjiAviokompaniju(nazivAviokompanije);
	}


}
