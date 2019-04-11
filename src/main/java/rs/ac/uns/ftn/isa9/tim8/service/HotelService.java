package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;

@Service
public class HotelService {
	@Autowired
	protected HotelRepository hotelRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;
	
	public String dodajHotel(Hotel noviHotel) {
		
		Hotel hotel = hotelRepository.findOneByNaziv(noviHotel.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(noviHotel.getAdresa().getPunaAdresa());
		if (hotel != null) {
			
			return "Zauzet naziv hotela";
		}
		if (adresa != null) {
			
			return "Zauzeta adresa";
		}
		hotelRepository.save(noviHotel);
		return null;
	}
	
	public Collection<Hotel> dobaviHotele() {
		return hotelRepository.findAll();
	}
}
