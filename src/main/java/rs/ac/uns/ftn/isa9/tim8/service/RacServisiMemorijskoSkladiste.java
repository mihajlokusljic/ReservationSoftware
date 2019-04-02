package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.repository.MemorijskoSkladiste;

public class RacServisiMemorijskoSkladiste implements RentACarServisi {
	
	@Autowired
	protected MemorijskoSkladiste bpMenadzer;

	@Override
	public boolean dodajRentACarServis(RentACarServis noviServis) {
		return bpMenadzer.dodajRentACarServis(noviServis);
	}

	@Override
	public boolean obrisiRentACarServis(RentACarServis servisZaBrisanje) {
		return bpMenadzer.obrisiRentACarServis(servisZaBrisanje);
	}

	@Override
	public RentACarServis izmjeniRentACarServis(RentACarServis noviPodaci) {
		return bpMenadzer.izmjeniRentACarServis(noviPodaci);
	}

	@Override
	public Collection<RentACarServis> dobaviRentACarServise() {
		return bpMenadzer.dobaviRentACarServise();
	}

	@Override
	public RentACarServis pronadjiRentACarServis(String nazivServisa) {
		return bpMenadzer.pronadjiRentACarServis(nazivServisa);
	}

	@Override
	public boolean dodajVozilo(String nazivServisa, Vozilo vozilo) {
		return bpMenadzer.dodajVozilo(nazivServisa, vozilo);
	}

	@Override
	public Collection<Vozilo> prikaziVozilaServisa(String nazivServisa) {
		return bpMenadzer.prikaziVozilaServisa(nazivServisa);
	}

}
