package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.DodavanjeSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelskaSobaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijeSobaRepository;

@Service
public class HotelskeSobeService {

	@Autowired
	HotelskaSobaRepository sobeRepository;

	@Autowired
	HotelRepository hoteliRepository;
	
	@Autowired
	RezervacijeSobaRepository rezervacijeRepository;

	public HotelskaSoba dodajSobuHotelu(DodavanjeSobeDTO podaci) throws NevalidniPodaciException {
		Optional<Hotel> hotelSearch = this.hoteliRepository.findById(podaci.getIdHotela());
		if (!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadati hotel");
		}
		Hotel hotel = hotelSearch.get();

		if (podaci.getBrojSobe() < 0 || podaci.getBrojKreveta() < 0 || podaci.getSprat() < 0 || podaci.getKolona() < 0
				|| podaci.getVrsta() < 0 || podaci.getCijenaNocenja() < 0) {
			throw new NevalidniPodaciException("Nisu dozvoljene negativne vrijednosti");
		}

		for (HotelskaSoba s : hotel.getSobe()) {
			if (s.getBrojSobe() == podaci.getBrojSobe()) {
				throw new NevalidniPodaciException("Proslijedjeni broj sobe je zauzet.");
			}

			if (s.getSprat() == podaci.getSprat() && s.getVrsta() == podaci.getVrsta()
					&& s.getKolona() == podaci.getKolona()) {
				throw new NevalidniPodaciException("Vec postoji soba na istoj poziciji.");
			}
		}

		HotelskaSoba soba = new HotelskaSoba(podaci.getBrojSobe(), podaci.getBrojKreveta(), podaci.getSprat(),
				podaci.getVrsta(), podaci.getKolona(), podaci.getCijenaNocenja(), hotel);
		this.sobeRepository.save(soba);
		hotel.getSobe().add(soba);
		this.hoteliRepository.save(hotel);
		return soba;
	}
	
	public boolean sobaJeRezervisana(HotelskaSoba soba, Date pocetniDatum, Date krajnjiDatum) {
		for(RezervacijaSobe r : this.rezervacijeRepository.findAllByRezervisanaSoba(soba)) {
			if(!pocetniDatum.after(r.getDatumOdlaska()) && !r.getDatumDolaska().after(krajnjiDatum)) {
				return true;
			}
		}
		return false;
	}

}
