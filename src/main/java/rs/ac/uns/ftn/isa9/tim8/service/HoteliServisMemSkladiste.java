package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.repository.MemorijskoSkladiste;
import rs.ac.uns.ftn.isa9.tim8.repository.MenadzerBazePodataka;

@Service
public class HoteliServisMemSkladiste implements HotelServisi {
	
	@Autowired
	protected MemorijskoSkladiste bpMenadzer;

	@Override
	public boolean dodajHotel(Hotel noviHotel) {
		return bpMenadzer.dodajHotel(noviHotel);
	}

	@Override
	public boolean obrisiHotel(Hotel hotelZaBrisanje) {
		return bpMenadzer.obrisiHotel(hotelZaBrisanje);
	}

	@Override
	public boolean izmjeniHotel(Hotel azuriranHotel) {
		return bpMenadzer.izmjeniHotel(azuriranHotel);
	}

	@Override
	public Collection<Hotel> dobaviHotele() {
		return bpMenadzer.dobaviHotele();
	}

	@Override
	public Hotel pronadjiHotel(String nazivHotela) {
		return bpMenadzer.pronadjiHotel(nazivHotela);
	}

}
