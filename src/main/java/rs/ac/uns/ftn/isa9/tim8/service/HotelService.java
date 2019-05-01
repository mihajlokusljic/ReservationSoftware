package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.PotrebnoSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaHotelaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;

@Service
public class HotelService {
	@Autowired
	protected HotelRepository hotelRepository;

	@Autowired
	protected AdresaRepository adresaRepository;

	@Autowired
	protected HotelskeSobeService sobeService;

	public Hotel dodajHotel(Hotel noviHotel) throws NevalidniPodaciException {

		Hotel hotel = hotelRepository.findOneByNaziv(noviHotel.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(noviHotel.getAdresa().getPunaAdresa());
		if (hotel != null) {
			throw new NevalidniPodaciException("Vec postoji hotel sa zadatim nazivom.");
		}
		if (adresa != null) {
			throw new NevalidniPodaciException("Vec postoji poslovnica na zadatoj adresi");
		}
		if(noviHotel.getNaziv().contentEquals("")) {
			throw new NevalidniPodaciException("Naziv hotela mora biti zadat.");
		}
		if(noviHotel.getAdresa().getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa hotela mora biti zadata.");
		}
		Hotel h = new Hotel(noviHotel.getNaziv(), noviHotel.getPromotivniOpis(), noviHotel.getAdresa());
		hotelRepository.save(h);
		return h;
	}

	public Collection<Hotel> dobaviHotele() {
		return hotelRepository.findAll();
	}

	/**
	 * Daje broj slobodnih n-krevetnih soba u datom hotelu za dati vremenski period.
	 * 
	 * Parametri: 
	 * hotel - hotel cije se sobe pretrazuju pocetniDatum - pocetak
	 * vremenskog intervala u kom se provjerava dostupnost soba krajnjiDatum -
	 * pocetak vremenskog intervala u kom se provjerava dostupnost soba Rezultat:
	 * HashMap<Integer, Integer> - mapa koja odredjuje broj slobodnih soba za dati
	 * broj kreveta. Kljuc je broj kreveta u sobi a vrijednost je broj slobodnih
	 * soba sa tim brojem kreveta.
	 */
	HashMap<Integer, Integer> slobodneSobe(Hotel hotel, Date pocetniDatum, Date krajnjiDatum) {
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
	 * Metoda koja pretrazuje letove po zadatim kriterijumima: naziv hotela ili destinacije,
	 * datumi dolaska i odlaska, vrsta i broj potrebnih soba (jednokrevetnih, dvokrevetnih...).
	 * Funkcionise tako sto od svih hotela odstranjuje one koji ne zadovoljavaju kriterijume pretrage.
	 * 
	 * Parametri:
	 * PretragaHotelaDTO kriterijumiPretrage - sadrzi vrijednosti kriterijuma po kojima se vrsi pretraga hotela
	 * Rezultat:
	 * Collection<Hotel> - lista hotela koji zadovoljavaju kriterijume pretrage
	 * @throws NevalidniPodaciException - u slucaju nevalidnog formata datuma
	 * */
	public Collection<Hotel> pretraziHotele(PretragaHotelaDTO kriterijumiPretrage) throws NevalidniPodaciException {
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if(!kriterijumiPretrage.getDatumDolaska().equals("") && kriterijumiPretrage.getDatumDolaska() != null) {
				pocetniDatum = df.parse(kriterijumiPretrage.getDatumDolaska());
			}
			
			if(!kriterijumiPretrage.getDatumOdlaska().equals("") && kriterijumiPretrage.getDatumOdlaska() != null) {
				krajnjiDatum = df.parse(kriterijumiPretrage.getDatumOdlaska());
			}
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		// inicijalno su svi hoteli u rezultatu
		Collection<Hotel> rezultat = this.hotelRepository.findAll();

		// odbacujemo hotele koji ne zadovoljavaju naziv hotela ili destinacije
		Iterator<Hotel> it = rezultat.iterator();
		Hotel tekuciHotel;

		while (it.hasNext()) {
			tekuciHotel = it.next();
			if (!tekuciHotel.getNaziv().toUpperCase()
					.contains(kriterijumiPretrage.getNazivHotelaIliDestinacije().toUpperCase())
					&& !tekuciHotel.getAdresa().getPunaAdresa().toUpperCase()
							.contains(kriterijumiPretrage.getNazivHotelaIliDestinacije().toUpperCase())) {
				it.remove();
			}
		}

		// odbacujemo hotele koji ne sadrze potreban broj slobodnih soba
		HashMap<Integer, Integer> raspoloziveSobe;
		it = rezultat.iterator();
		boolean ukloniHotel;
		
		//provjeriti svaki hotel
		while (it.hasNext()) {
			tekuciHotel = it.next();
			ukloniHotel = false;
			raspoloziveSobe = this.slobodneSobe(tekuciHotel, pocetniDatum, krajnjiDatum);
			
			//za svaki hotel provjeriti svaki zahtjev za bro slobodnih n-krevetnih soba
			for(PotrebnoSobaDTO zahtjev : kriterijumiPretrage.getPotrebneSobe()) {
				if(!raspoloziveSobe.containsKey(zahtjev.getBrKrevetaPoSobi())) {
					ukloniHotel = true;
					break;
				}
				if(zahtjev.getBrSoba() > raspoloziveSobe.get(zahtjev.getBrKrevetaPoSobi())) {
					ukloniHotel = true;
					break;
				}
			}
			
			if(ukloniHotel) {
				it.remove();
			}
		}

		return rezultat;
	}
}
