package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.DodavanjeSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.repository.BrzeRezervacijeSobaRepository;
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
	
	@Autowired
	BrzeRezervacijeSobaRepository brzeRezervacijeRepository;

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
	
	public HotelskaSoba izmjeniSobu(HotelskaSoba noviPodaciSobe) throws NevalidniPodaciException {
		if(noviPodaciSobe.getId() == null) {
			throw new NevalidniPodaciException("Došlo je do greške. Id sobe mora biti zadat.");
		}
		if (noviPodaciSobe.getBrojSobe() < 0 || noviPodaciSobe.getBrojKreveta() < 0 || noviPodaciSobe.getSprat() < 0 || noviPodaciSobe.getKolona() < 0
				|| noviPodaciSobe.getVrsta() < 0 || noviPodaciSobe.getCijena() < 0) {
			throw new NevalidniPodaciException("Nisu dozvoljene negativne vrijednosti");
		}
		Optional<HotelskaSoba> sobaSearch = this.sobeRepository.findById(noviPodaciSobe.getId());
		if(!sobaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji soba sa datim id-em.");
		}
		HotelskaSoba target = sobaSearch.get();
		if(this.sobaJeTrenutnoRezervisana(target)) {
			throw new NevalidniPodaciException("Nije moguca izmjena sobe jer postoji rezervacija za datu sobu.");
		}
		Hotel hotel = target.getHotel();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if(!hotel.getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException("Nemate ovlascenja da obrišete datu sobu.");
		}
		
		for (HotelskaSoba s : hotel.getSobe()) {
			if (s.getBrojSobe() == noviPodaciSobe.getBrojSobe() && !s.getId().equals(target.getId())) {
				throw new NevalidniPodaciException("Proslijedjeni broj sobe je zauzet.");
			}

			if (s.getSprat() == noviPodaciSobe.getSprat() && s.getVrsta() == noviPodaciSobe.getVrsta()
					&& s.getKolona() == noviPodaciSobe.getKolona() && !s.getId().equals(target.getId())) {
				throw new NevalidniPodaciException("Vec postoji soba na istoj poziciji.");
			}
		}
		target.setBrojSobe(noviPodaciSobe.getBrojSobe());
		target.setCijena(noviPodaciSobe.getCijena());
		target.setBrojKreveta(noviPodaciSobe.getBrojKreveta());
		target.setSprat(noviPodaciSobe.getSprat());
		target.setVrsta(noviPodaciSobe.getVrsta());
		target.setKolona(noviPodaciSobe.getKolona());
		this.sobeRepository.save(target);
		return target;
	}
	
	public String obrisiSobu(Long idSobe) throws NevalidniPodaciException {
		Optional<HotelskaSoba> sobaSearch = this.sobeRepository.findById(idSobe);
		if(!sobaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji soba sa zadatim id-em.");
		}
		HotelskaSoba target = sobaSearch.get();
		if(this.sobaJeTrenutnoRezervisana(target)) {
			throw new NevalidniPodaciException("Brisanje sobe nije moguce jer je soba rezervisana.");
		}
		Hotel hotel = target.getHotel();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if(!hotel.getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException("Nemate ovlascenja da obrišete datu sobu.");
		}
		hotel.getSobe().remove(target);
		this.sobeRepository.delete(target);
		this.hoteliRepository.save(hotel);
		return "Soba je uspješno obrisana.";
	}
	
	public boolean sobaJeRezervisana(HotelskaSoba soba, Date pocetniDatum, Date krajnjiDatum) {
		if(pocetniDatum == null || krajnjiDatum == null) {
			return false;
		}
		for(RezervacijaSobe r : this.rezervacijeRepository.findAllByRezervisanaSoba(soba)) {
			if(!pocetniDatum.after(r.getDatumOdlaska()) && !r.getDatumDolaska().after(krajnjiDatum)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean sobaJeNaBrzojRezervaciji(HotelskaSoba soba, Date pocetniDatum, Date krajnjiDatum) {
		if(pocetniDatum == null || krajnjiDatum == null) {
			return false;
		}
		for(BrzaRezervacijaSoba r : this.brzeRezervacijeRepository.findAllBySobaZaRezervaciju(soba)) {
			if(!pocetniDatum.after(r.getDatumOdlaska()) && !r.getDatumDolaska().after(krajnjiDatum)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean sobaJeTrenutnoRezervisana(HotelskaSoba soba) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String trenutniDatumStr = df.format(new Date());
		Date trenutniDatum = null;
		try {
			trenutniDatum = df.parse(trenutniDatumStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(RezervacijaSobe r : this.rezervacijeRepository.findAllByRezervisanaSoba(soba)) {
			if(!trenutniDatum.after(r.getDatumOdlaska())) {
				return true;
			}
		}
		return false;
	}

	public Collection<HotelskaSoba> pretraziSobeHotela(PretragaSobaDTO kriterijumiPretrage) throws NevalidniPodaciException {
		Optional<Hotel> hotelSearch = this.hoteliRepository.findById(kriterijumiPretrage.getIdHotela());
		if(!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadati hotel.");
		}
		Hotel hotel = hotelSearch.get();
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			pocetniDatum = df.parse(kriterijumiPretrage.getDatumDolaska());
			krajnjiDatum = df.parse(kriterijumiPretrage.getDatumOdlaska());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		ArrayList<HotelskaSoba> rezultat = new ArrayList<HotelskaSoba>();
		for(HotelskaSoba s : hotel.getSobe()) {
			if(!this.sobaJeRezervisana(s, pocetniDatum, krajnjiDatum) && !this.sobaJeNaBrzojRezervaciji(s, pocetniDatum, krajnjiDatum)) {
				rezultat.add(s);
			}
		}
		return rezultat;
	}

	public BrzaRezervacijaSobeDTO dodajBrzuRezervaciju(BrzaRezervacijaSobeDTO novaRezervacija) throws NevalidniPodaciException {
		Optional<HotelskaSoba> sobaSearch = this.sobeRepository.findById(novaRezervacija.getIdSobe());
		if(!sobaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata soba.");
		}
		HotelskaSoba target = sobaSearch.get();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if(!target.getHotel().getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException("Niste ulogovani kao ovlascen administrator za dati hotel");
		}
		Date datumDolaska = null;
		Date datumOdlaska = null;
		Date danasnjiDatum = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			datumDolaska = df.parse(novaRezervacija.getDatumDolaska());
			datumOdlaska = df.parse(novaRezervacija.getDatumOdlaska());
			danasnjiDatum = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		if(datumDolaska.before(danasnjiDatum) || datumOdlaska.before(danasnjiDatum)) {
			throw new NevalidniPodaciException("Datumi ne smiju biti u proslosti.");
		}
		if(datumOdlaska.before(datumDolaska)) {
			throw new NevalidniPodaciException("Datum odlaska ne moze biti prije datuma dolaska");
		}
		if(sobaJeRezervisana(target, datumDolaska, datumOdlaska)) {
			throw new NevalidniPodaciException("Data soba je rezervisana u zadatom vremenskom periodu.");
		}
		if(sobaJeNaBrzojRezervaciji(target, datumDolaska, datumOdlaska)) {
			throw new NevalidniPodaciException("Data soba se vec nalazi na brzoj rezervaciji u zadatom vremenskom periodu");
		}
		BrzaRezervacijaSoba brs = new BrzaRezervacijaSoba(datumDolaska, datumOdlaska, 0, 0, new HashSet<Usluga>(), target);
		long diff = datumOdlaska.getTime() - datumDolaska.getTime(); //razlika u milisekundama
		long brojNocenja = diff / (24 * 60 * 60 * 1000);             //razlika u danima
		if(brojNocenja == 0) {
			brojNocenja = 1;
		}
		brs.setBaznaCijena(brojNocenja * target.getCijena());
		this.brzeRezervacijeRepository.save(brs);
		novaRezervacija.setId(brs.getId());
		novaRezervacija.setCijenaBoravka(brs.getBaznaCijena());
		return novaRezervacija;
	}

}
