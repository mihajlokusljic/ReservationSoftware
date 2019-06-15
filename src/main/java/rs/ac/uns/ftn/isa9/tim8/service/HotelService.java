package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.DatumiZaPrihodDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.IzvjestajDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PotrebnoSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaHotelaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.UslugaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.NacinPlacanjaUsluge;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelskaSobaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijaSobeRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.UslugeRepository;

@Service
public class HotelService {
	@Autowired
	protected HotelRepository hotelRepository;

	@Autowired
	protected UslugeRepository uslugeRepository;

	@Autowired
	protected AdresaRepository adresaRepository;
	
	@Autowired
	protected HotelskaSobaRepository sobaRepository;
	
	@Autowired
	protected RezervacijaSobeRepository rezervacijaRepository;
	
	@Autowired
	protected HotelskeSobeService sobeService;

	public void validirajAdresu(Adresa adresa) throws NevalidniPodaciException {
		if (adresa == null) {
			throw new NevalidniPodaciException("Adresa hotela mora biti zadata.");
		}
		if (adresa.getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa hotela mora biti zadata.");
		}
		Adresa zauzimaAdresu = adresaRepository.findOneByPunaAdresa(adresa.getPunaAdresa());
		if (zauzimaAdresu != null) {
			throw new NevalidniPodaciException("Vec postoji poslovnica na zadatoj adresi");
		}
	}

	public void validirajHotel(Hotel hotel) throws NevalidniPodaciException {
		if (hotel.getNaziv().equals("") || hotel.getNaziv() == null) {
			throw new NevalidniPodaciException("Naziv hotela mora biti zadat.");
		}
		Hotel zauzimaNaziv = hotelRepository.findOneByNaziv(hotel.getNaziv());
		if (zauzimaNaziv != null) {
			throw new NevalidniPodaciException("Vec postoji hotel sa zadatim nazivom.");
		}
	}

	public Hotel dodajHotel(Hotel noviHotel) throws NevalidniPodaciException {
		if (noviHotel.getNaziv() != null) {
			this.validirajHotel(noviHotel);
		}
		this.validirajAdresu(noviHotel.getAdresa());
		if (noviHotel.getPromotivniOpis() == null) {
			noviHotel.setPromotivniOpis("");
		}
		Hotel h = new Hotel(noviHotel.getNaziv(), noviHotel.getPromotivniOpis(), noviHotel.getAdresa());
		hotelRepository.save(h);
		return h;
	}

	public Hotel izmjeniHotel(Hotel noviPodaciHotela) throws NevalidniPodaciException {
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel target = admin.getHotel();
		if(target == null) {
			throw new NevalidniPodaciException("Niste ulogovani kao ovlasceni administrator hotela.");
		}
		
		if (noviPodaciHotela.getNaziv() == null) {
			throw new NevalidniPodaciException("Naziv hotela mora biti zadat.");
		}

		if (!noviPodaciHotela.getNaziv().equals(target.getNaziv())) {
			this.validirajHotel(noviPodaciHotela);
			target.setNaziv(noviPodaciHotela.getNaziv());
		}
		Adresa staraAdresa = null;
		Adresa novaAdresa = noviPodaciHotela.getAdresa();
		if (novaAdresa == null) {
			throw new NevalidniPodaciException("Adresa hotela mora biti zadata.");
		}
		target.getAdresa().setLatituda(novaAdresa.getLatituda());
		target.getAdresa().setLongituda(novaAdresa.getLongituda());
		if (!novaAdresa.getPunaAdresa().equals(target.getAdresa().getPunaAdresa())) {
			validirajAdresu(novaAdresa);
			staraAdresa = target.getAdresa();
			target.setAdresa(novaAdresa);
		}
		target.setPromotivniOpis(noviPodaciHotela.getPromotivniOpis());
		this.hotelRepository.save(target);
		if(staraAdresa != null) {
			//oslobadjamo staru adresu kako bi postala raspoloziva drugim poslovnicama
			this.adresaRepository.delete(staraAdresa);
		}
		return target;
	}

	public Collection<Hotel> dobaviHotele() {
		return hotelRepository.findAll();
	}

	/**
	 * Daje broj slobodnih n-krevetnih soba u datom hotelu za dati vremenski period.
	 * 
	 * Parametri: hotel - hotel cije se sobe pretrazuju pocetniDatum - pocetak
	 * vremenskog intervala u kom se provjerava dostupnost soba krajnjiDatum -
	 * pocetak vremenskog intervala u kom se provjerava dostupnost soba Rezultat:
	 * HashMap<Integer, Integer> - mapa koja odredjuje broj slobodnih soba za dati
	 * broj kreveta. Kljuc je broj kreveta u sobi a vrijednost je broj slobodnih
	 * soba sa tim brojem kreveta.
	 */
	public HashMap<Integer, Integer> slobodneSobe(Hotel hotel, Date pocetniDatum, Date krajnjiDatum) {
		HashMap<Integer, Integer> rezultat = new HashMap<Integer, Integer>();
		int slobodnoSoba = 0;
		for (HotelskaSoba soba : hotel.getSobe()) {
			if (!this.sobeService.sobaJeRezervisana(soba, pocetniDatum, krajnjiDatum)) {
				slobodnoSoba = rezultat.getOrDefault(soba.getBrojKreveta(), 0);
				slobodnoSoba++;
				rezultat.put(soba.getBrojKreveta(), slobodnoSoba);
			}
		}
		return rezultat;
	}

	/**
	 * Metoda koja pretrazuje letove po zadatim kriterijumima: naziv hotela ili
	 * destinacije, datumi dolaska i odlaska, vrsta i broj potrebnih soba
	 * (jednokrevetnih, dvokrevetnih...). Funkcionise tako sto od svih hotela
	 * odstranjuje one koji ne zadovoljavaju kriterijume pretrage.
	 * 
	 * Parametri: PretragaHotelaDTO kriterijumiPretrage - sadrzi vrijednosti
	 * kriterijuma po kojima se vrsi pretraga hotela Rezultat: Collection<Hotel> -
	 * lista hotela koji zadovoljavaju kriterijume pretrage
	 * 
	 * @throws NevalidniPodaciException - u slucaju nevalidnog formata datuma
	 */
	public Collection<Hotel> pretraziHotele(PretragaHotelaDTO kriterijumiPretrage) throws NevalidniPodaciException {
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if (!kriterijumiPretrage.getDatumDolaska().equals("") && kriterijumiPretrage.getDatumDolaska() != null) {
				pocetniDatum = df.parse(kriterijumiPretrage.getDatumDolaska());
			}

			if (!kriterijumiPretrage.getDatumOdlaska().equals("") && kriterijumiPretrage.getDatumOdlaska() != null) {
				krajnjiDatum = df.parse(kriterijumiPretrage.getDatumOdlaska());
			}
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		if (pocetniDatum != null && krajnjiDatum != null) {
			if (krajnjiDatum.before(pocetniDatum)) {
				throw new NevalidniPodaciException("Datum odlaska ne smije biti prije datuma dolaska.");
			}
			if (pocetniDatum.after(krajnjiDatum)) {
				throw new NevalidniPodaciException("Datum dolaska ne smije biti nakon datuma odlaska.");
			} 
		}
		// inicijalno su svi hoteli u rezultatu
		Collection<Hotel> rezultat = this.hotelRepository.findAll();

		// odbacujemo hotele koji ne zadovoljavaju naziv hotela ili destinacije
		Iterator<Hotel> it = rezultat.iterator();
		Hotel tekuciHotel;

		while (it.hasNext()) {
			tekuciHotel = it.next();
			
			if (!tekuciHotel.getAdresa().getPunaAdresa().toUpperCase().contains(kriterijumiPretrage.getNazivDestinacije().toUpperCase())) {
				it.remove();
			} else if(!tekuciHotel.getNaziv().toUpperCase().contains(kriterijumiPretrage.getNazivHotela().toUpperCase())) {
				it.remove();
			}
			
		}

		// odbacujemo hotele koji ne sadrze potreban broj slobodnih soba
		HashMap<Integer, Integer> raspoloziveSobe;
		it = rezultat.iterator();
		boolean ukloniHotel;

		// provjeriti svaki hotel
		while (it.hasNext()) {
			tekuciHotel = it.next();
			ukloniHotel = false;
			raspoloziveSobe = this.slobodneSobe(tekuciHotel, pocetniDatum, krajnjiDatum);

			// za svaki hotel provjeriti svaki zahtjev za broj slobodnih n-krevetnih soba
			for (PotrebnoSobaDTO zahtjev : kriterijumiPretrage.getPotrebneSobe()) {
				if (!raspoloziveSobe.containsKey(zahtjev.getBrKrevetaPoSobi())) {
					ukloniHotel = true;
					break;
				}
				if (zahtjev.getBrSoba() > raspoloziveSobe.get(zahtjev.getBrKrevetaPoSobi())) {
					ukloniHotel = true;
					break;
				}
			}

			if (ukloniHotel) {
				it.remove();
			}
		}

		return rezultat;
	}

	public Collection<HotelskaSoba> sobehotela(Long idHotela) throws NevalidniPodaciException {
		Optional<Hotel> hotelSearch = hotelRepository.findById(idHotela);
		if (!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji hotel sa zadatim id-em");
		}
		Hotel h = hotelSearch.get();
		return h.getSobe();
	}

	public void validirajUslugu(UslugaDTO novaUsluga) throws NevalidniPodaciException {
		if (novaUsluga.getNaziv() == null || novaUsluga.getNaziv().equals("")) {
			throw new NevalidniPodaciException("Naziv usluge mora biti zadat.");
		}
		if (novaUsluga.getCijena() < 0) {
			throw new NevalidniPodaciException("Cijena usluge ne smije biti negativna.");
		}
		if (novaUsluga.getProcenatPopusta() < 0) {
			throw new NevalidniPodaciException("Popust koji se ostvaruje uslugom ne smije biti negativan.");
		}
		if (NacinPlacanjaUsluge.getValue(novaUsluga.getNacinPlacanjaId()) == null) {
			throw new NevalidniPodaciException("Nevalidan nacin placanja usluge.");
		}
	}

	public Usluga dodajUsluguHotela(UslugaDTO novaUsluga) throws NevalidniPodaciException {
		validirajUslugu(novaUsluga);

		Optional<Hotel> hotelSearch = this.hotelRepository.findById(novaUsluga.getIdPoslovnice());
		if (!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji hotel sa zadatim id-em.");
		}
		Hotel hotel = hotelSearch.get();
		for (Usluga usluga : hotel.getCjenovnikDodatnihUsluga()) {
			if (usluga.getNaziv().equals(novaUsluga.getNaziv())) {
				throw new NevalidniPodaciException("Vec postoji usluga sa zadatim nazivom");
			}
		}
		NacinPlacanjaUsluge nacinPlacanja = NacinPlacanjaUsluge.getValue(novaUsluga.getNacinPlacanjaId());
		Usluga usluga = new Usluga(novaUsluga.getNaziv(), novaUsluga.getCijena(), novaUsluga.getProcenatPopusta(),
				nacinPlacanja, novaUsluga.getOpis(), hotel);
		hotel.getCjenovnikDodatnihUsluga().add(usluga);
		this.uslugeRepository.save(usluga);
		this.hotelRepository.save(hotel);
		return usluga;
	}

	public Hotel dobaviHotel(Long idHotela) throws NevalidniPodaciException {
		Optional<Hotel> hotelSearch = this.hotelRepository.findById(idHotela);
		if(!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji hotel sa zadatim id-em");
		}
		return hotelSearch.get();
	}

	public String izracunajPrihode(DatumiZaPrihodDTO datumiDto, Long idHotela) throws NevalidniPodaciException {
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		Optional<Hotel> pretragaHotel = hotelRepository.findById(idHotela);
		
		if (!pretragaHotel.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		Hotel hotel = pretragaHotel.get();
		double prihodi = 0;
		
		Collection<HotelskaSoba> sobe = sobaRepository.findByHotel(hotel);
		
		for (HotelskaSoba hSoba : sobe) {
			Collection<RezervacijaSobe> sveRezervacije = rezervacijaRepository.findAllByRezervisanaSoba(hSoba);
			for (RezervacijaSobe rSobe : sveRezervacije) {
				if (pocetniDatum != null && krajnjiDatum != null) {
					if (pocetniDatum.compareTo(rSobe.getDatumDolaska()) <= 0 && krajnjiDatum.compareTo(rSobe.getDatumOdlaska()) >= 0) {
						prihodi = prihodi + rSobe.getCijena();
					}
				}
				else if (pocetniDatum != null) {
					if (pocetniDatum.compareTo(rSobe.getDatumDolaska()) <= 0) {
						prihodi = prihodi + rSobe.getCijena();
					}
				}
				else if ( krajnjiDatum != null) {
					if (krajnjiDatum.compareTo(rSobe.getDatumDolaska()) >= 0) {
						prihodi = prihodi + rSobe.getCijena();
					}
				}
				else {
					prihodi = prihodi + rSobe.getCijena();
				}
			}
		}
		
		return Double.toString(prihodi);
	}

	public IzvjestajDTO dnevniIzvjestaj(Long idHotela, DatumiZaPrihodDTO datumiDto) throws NevalidniPodaciException{
		
		IzvjestajDTO izvjestajDTO = new IzvjestajDTO(new ArrayList<Integer>(), new ArrayList<String>());
		
		Optional<Hotel> pretragaHotel = hotelRepository.findById(idHotela);
		if (!pretragaHotel.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		Hotel hotel = pretragaHotel.get();
		
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		Collection<HotelskaSoba> sobe = sobaRepository.findByHotel(hotel);
		Collection<RezervacijaSobe> rezervacijeUOkviruDatuma = new ArrayList<>();

		for (HotelskaSoba hSoba : sobe) {
			Collection<RezervacijaSobe> sveRezervacije = rezervacijaRepository.findAllByRezervisanaSoba(hSoba);
			for (RezervacijaSobe rs : sveRezervacije) {
				
				if (sobeService.sobaJeRezervisana(rs.getRezervisanaSoba(), pocetniDatum, krajnjiDatum)) {
					rezervacijeUOkviruDatuma.add(rs);
				}
			}
		
		}			
		
		
		while (!pocetniDatum.after(krajnjiDatum)) {		
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
			izvjestajDTO.getVrijednostiXOse().add(sdf.format(pocetniDatum));
			
			int broj = 0;
			for (RezervacijaSobe rs : rezervacijeUOkviruDatuma) {
				
				if (!pocetniDatum.before(rs.getDatumDolaska()) && !pocetniDatum.after(rs.getDatumOdlaska()) ) {
					broj++;
				}
				
			}
			izvjestajDTO.getBrojeviYOsa().add(broj);
			Calendar c = Calendar.getInstance(); 
			c.setTime(pocetniDatum); 
			c.add(Calendar.DATE, 1);
			pocetniDatum = c.getTime();
		}
				
		return izvjestajDTO;
	}

	public IzvjestajDTO nedeljniIzvjestaj(Long idHotela, DatumiZaPrihodDTO datumiDto) throws NevalidniPodaciException {
		
		IzvjestajDTO izvjestajDTO = new IzvjestajDTO(new ArrayList<Integer>(), new ArrayList<String>());
		
		Optional<Hotel> pretragaHotel = hotelRepository.findById(idHotela);
		if (!pretragaHotel.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		Hotel hotel = pretragaHotel.get();
		
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		Collection<HotelskaSoba> sobe = sobaRepository.findByHotel(hotel);
		Collection<RezervacijaSobe> rezervacijeUOkviruDatuma = new ArrayList<>();

		for (HotelskaSoba hSoba : sobe) {
			Collection<RezervacijaSobe> sveRezervacije = rezervacijaRepository.findAllByRezervisanaSoba(hSoba);
			for (RezervacijaSobe rs : sveRezervacije) {
				
				if (sobeService.sobaJeRezervisana(rs.getRezervisanaSoba(), pocetniDatum, krajnjiDatum)) {
					rezervacijeUOkviruDatuma.add(rs);
				}
			}
		
		}	
		
		while(!pocetniDatum.after(krajnjiDatum)) {
			
			Object zaSedam_ = pocetniDatum.clone();
			
			Date zaSedam = (Date) zaSedam_;
			Calendar c = Calendar.getInstance(); 
			c.setTime(pocetniDatum); 
			c.add(Calendar.DATE, 7);
			zaSedam = c.getTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
			
			String xOsa =  sdf.format(pocetniDatum);
			
			int broj = 0;
			for (RezervacijaSobe rv : rezervacijeUOkviruDatuma) {
				if (!zaSedam.after(krajnjiDatum)) {
					
					if (pocetniDatum.compareTo(rv.getDatumOdlaska()) <= 0 && zaSedam.compareTo(rv.getDatumDolaska()) >= 0) {
						broj ++;
					}

				}
				else {

					if (pocetniDatum.compareTo(rv.getDatumOdlaska()) <= 0 && krajnjiDatum.compareTo(rv.getDatumDolaska()) >= 0) {
						broj ++;
					}
				}						
			}
			
			if (!zaSedam.after(krajnjiDatum)) {
				pocetniDatum = zaSedam;
			}
			else {
				pocetniDatum = krajnjiDatum;
			}
			izvjestajDTO.getBrojeviYOsa().add(broj);
			xOsa = xOsa + "-" + sdf.format(pocetniDatum);
			c.setTime(pocetniDatum); 
			c.add(Calendar.DATE, 1);
			pocetniDatum = c.getTime();
			izvjestajDTO.getVrijednostiXOse().add(xOsa);
		}
		return izvjestajDTO;
	}

	public IzvjestajDTO mjesecniIzvjestaj(Long idHotela, DatumiZaPrihodDTO datumiDto) throws NevalidniPodaciException{
		
		IzvjestajDTO izvjestajDTO = new IzvjestajDTO(new ArrayList<Integer>(), new ArrayList<String>());
		
		Optional<Hotel> pretragaHotel = hotelRepository.findById(idHotela);
		if (!pretragaHotel.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		Hotel hotel = pretragaHotel.get();
		
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		Collection<HotelskaSoba> sobe = sobaRepository.findByHotel(hotel);
		Collection<RezervacijaSobe> rezervacijeUOkviruDatuma = new ArrayList<>();

		for (HotelskaSoba hSoba : sobe) {
			Collection<RezervacijaSobe> sveRezervacije = rezervacijaRepository.findAllByRezervisanaSoba(hSoba);
			for (RezervacijaSobe rs : sveRezervacije) {
				
				if (sobeService.sobaJeRezervisana(rs.getRezervisanaSoba(), pocetniDatum, krajnjiDatum)) {
					rezervacijeUOkviruDatuma.add(rs);
				}
			}
		
		}	
		
		while(!pocetniDatum.after(krajnjiDatum)) {
			
			Object zaSedam_ = pocetniDatum.clone();
			
			Date zaSedam = (Date) zaSedam_;
			Calendar c = Calendar.getInstance(); 
			c.setTime(pocetniDatum); 
			c.add(Calendar.MONTH, 1);
			zaSedam = c.getTime();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.");
			
			String xOsa =  sdf.format(pocetniDatum);
			
			int broj = 0;
			for (RezervacijaSobe rv : rezervacijeUOkviruDatuma) {
				if (!zaSedam.after(krajnjiDatum)) {
					
					if (pocetniDatum.compareTo(rv.getDatumOdlaska()) <= 0 && zaSedam.compareTo(rv.getDatumDolaska()) >= 0) {
						broj ++;
					}

				}
				else {
					if (pocetniDatum.compareTo(rv.getDatumOdlaska()) <= 0 && krajnjiDatum.compareTo(rv.getDatumDolaska()) >= 0) {
						broj ++;
					}
				}						
			}
			
			if (!zaSedam.after(krajnjiDatum)) {
				pocetniDatum = zaSedam;
			}
			else {
				pocetniDatum = krajnjiDatum;
			}
			izvjestajDTO.getBrojeviYOsa().add(broj);
			xOsa = xOsa + "-" + sdf.format(pocetniDatum);
			c.setTime(pocetniDatum); 
			c.add(Calendar.DATE, 1);
			pocetniDatum = c.getTime();
			izvjestajDTO.getVrijednostiXOse().add(xOsa);
		}
		return izvjestajDTO;
	}

}
