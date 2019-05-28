package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.ZahtjevRezervacijaSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.NacinPlacanjaUsluge;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.repository.BrzeRezervacijeSobaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelskaSobaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.PutovanjeRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijaSobeRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.UslugeRepository;

@Service
public class RezervacijeSobaService {

	@Autowired
	protected RezervacijaSobeRepository rezervacijeRepository;
	
	@Autowired
	protected BrzeRezervacijeSobaRepository brzeRezervacijeRepository;

	@Autowired
	protected HotelRepository hotelRepository;

	@Autowired
	protected UslugeRepository uslugeRepository;
	
	@Autowired
	protected PutovanjeRepository putovanjeRepository;
	
	@Autowired
	protected HotelskaSobaRepository sobeRepository;

	public BrzaRezervacijaSobeDTO dodajUslugeBrzeRezervacije(BrzaRezervacijaSobeDTO brzaRezervacija)
			throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSoba> rezervacijaSearch = brzeRezervacijeRepository.findById(brzaRezervacija.getId());
		if (!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata brza rezervacija");
		}
		BrzaRezervacijaSoba target = rezervacijaSearch.get();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if (!target.getSobaZaRezervaciju().getHotel().getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException(
					"Niste logovani kao ovlascen administrator hotela za zadatu rezervaciju.");
		}
		int sumaPopusta = 0;
		double ukupnaCijena = target.getBaznaCijena();
		Optional<Usluga> uslugaSearch = null;
		Usluga tekucaUsluga = null;
		long brojNocenja = target.izracunajBrojNocenja();

		for (Long uslugaId : brzaRezervacija.getDodatneUslugeIds()) {
			uslugaSearch = uslugeRepository.findById(uslugaId);
			if (!uslugaSearch.isPresent()) {
				throw new NevalidniPodaciException("Zadali ste nepostojecu uslugu.");
			}
			tekucaUsluga = uslugaSearch.get();
			target.getDodatneUsluge().add(tekucaUsluga);
			sumaPopusta += tekucaUsluga.getProcenatPopusta();
			if (tekucaUsluga.getNacinPlacanja() == NacinPlacanjaUsluge.DNEVNO_PO_OSOBI) {
				ukupnaCijena += tekucaUsluga.getCijena() * brojNocenja * target.getSobaZaRezervaciju().getBrojKreveta();
			} else if (tekucaUsluga.getNacinPlacanja() == NacinPlacanjaUsluge.DNEVNO) {
				ukupnaCijena += tekucaUsluga.getCijena() * brojNocenja;
			} else if (tekucaUsluga.getNacinPlacanja() == NacinPlacanjaUsluge.FIKSNO_PO_OSOBI) {
				ukupnaCijena += tekucaUsluga.getCijena() * target.getSobaZaRezervaciju().getBrojKreveta();
			} else if (tekucaUsluga.getNacinPlacanja() == NacinPlacanjaUsluge.FIKSNO) {
				ukupnaCijena += tekucaUsluga.getCijena();
			}
		}
		double popust = ukupnaCijena * sumaPopusta / 100;
		ukupnaCijena -= popust;
		if (ukupnaCijena < 0) {
			ukupnaCijena = 0;
		}
		target.setBaznaCijena(ukupnaCijena);
		brzeRezervacijeRepository.save(target);
		brzaRezervacija.setCijenaBoravka(ukupnaCijena);
		return brzaRezervacija;
	}

	public BrzaRezervacijaSoba zadajPopustBrzeRezervacije(BrzaRezervacijaSobeDTO brzaRezervacija)
			throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSoba> rezervacijaSearch = brzeRezervacijeRepository.findById(brzaRezervacija.getId());
		if (!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata brza rezervacija.");
		}
		BrzaRezervacijaSoba rez = rezervacijaSearch.get();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if (!rez.getSobaZaRezervaciju().getHotel().getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException("Niste ulogovani kao ovlascen administrator za datu rezervaciju.");
		}
		int popust = brzaRezervacija.getProcenatPopusta();
		if (popust < 0) {
			throw new NevalidniPodaciException("Popust ne može biti negativan.");
		}
		if (popust > 100) {
			throw new NevalidniPodaciException("Popust ne može biti veći od 100%.");
		}
		rez.setProcenatPopusta(popust);
		System.out.println("Datumi uneseni na sajtu: ");
		System.out.println("Datum dolaska: " + brzaRezervacija.getDatumDolaska());
		System.out.println("Datum odlaska: " + brzaRezervacija.getDatumOdlaska());
		System.out.println("Ucitano iz baze:");
		System.out.println("Datum dolaska: " + rez.getDatumDolaska());
		System.out.println("Datum odlaska: " + rez.getDatumOdlaska());
		brzeRezervacijeRepository.save(rez);
		return rez;
	}

	public Collection<BrzaRezervacijaSoba> dobaviSveBrzeRezervacijeZaHotel(Long idHotela)
			throws NevalidniPodaciException {
		Optional<Hotel> hotelSearch = hotelRepository.findById(idHotela);
		if (!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadati hotel.");
		}
		Hotel target = hotelSearch.get();
		Collection<BrzaRezervacijaSoba> rezultat = brzeRezervacijeRepository.findAll();
		Iterator<BrzaRezervacijaSoba> it = rezultat.iterator();
		BrzaRezervacijaSoba tekucaRezervacija = null;
		while (it.hasNext()) {
			tekucaRezervacija = it.next();
			if (!tekucaRezervacija.getSobaZaRezervaciju().getHotel().getId().equals(target.getId())) {
				it.remove();
			}
		}
		return rezultat;
	}

	public Collection<BrzaRezervacijaSoba> pretraziBrzeRezervacijeSoba(PretragaSobaDTO kriterijumiPretrage)
			throws NevalidniPodaciException {
		Date datumDolaska = null;
		Date datumOdlaska = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			datumDolaska = df.parse(kriterijumiPretrage.getDatumDolaska());
			datumOdlaska = df.parse(kriterijumiPretrage.getDatumOdlaska());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		if (datumOdlaska.before(datumDolaska)) {
			throw new NevalidniPodaciException("Datum odlaska ne smije biti prije datuma dolaska.");
		}

		Optional<Hotel> hotelSearch = hotelRepository.findById(kriterijumiPretrage.getIdHotela());
		if (!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadati hotel.");
		}
		Hotel hotel = hotelSearch.get();
		Collection<BrzaRezervacijaSoba> rezultat = brzeRezervacijeRepository.findAll();
		Iterator<BrzaRezervacijaSoba> it = rezultat.iterator();
		BrzaRezervacijaSoba tekucaRezervacija = null;
		Date tekuciDatumDolaska = null;
		Date tekuciDatumOdlaska = null;
		while (it.hasNext()) {
			tekucaRezervacija = it.next();
			if (!tekucaRezervacija.getSobaZaRezervaciju().getHotel().getId().equals(hotel.getId())) {
				it.remove();
				continue;
			}
			try {
				//izdvajanje datuma (svodjenje date objekta samo na datum u istom formatu kao parsirani parametri pretrage)
				tekuciDatumDolaska = df.parse(df.format(tekucaRezervacija.getDatumDolaska()));
				tekuciDatumOdlaska = df.parse(df.format(tekucaRezervacija.getDatumOdlaska()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ako je ponuda pocela prije dolaska ili traje i nakon odlaska punika uklanjamo je
			if (tekuciDatumDolaska.before(datumDolaska) || tekuciDatumOdlaska.after(datumOdlaska)) {
				it.remove();
				continue;
			}
		}
		return rezultat;
	}

	public String izvrsiBrzuRezervaciju(Long idBrzeRezervacije, Long idPutovanja) throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSoba> rezervacijaSearch = brzeRezervacijeRepository.findById(idBrzeRezervacije);
		if(!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata brza rezervacija.");
		}
		BrzaRezervacijaSoba brzaRez = rezervacijaSearch.get();
		Putovanje putovanje = null;
		RegistrovanKorisnik korisnik = (RegistrovanKorisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(idPutovanja != null) {
			Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);
			if(!putovanjeSearch.isPresent()) {
				throw new NevalidniPodaciException("Ne postoji zadato putovanje.");
			}
			putovanje = putovanjeSearch.get();
		}
		double popust = brzaRez.getBaznaCijena() * brzaRez.getProcenatPopusta() / 100.0;
		double cijena = brzaRez.getBaznaCijena() - popust;
		RezervacijaSobe rezervacija = new RezervacijaSobe(brzaRez.getDatumDolaska(), brzaRez.getDatumOdlaska(), cijena, brzaRez.getSobaZaRezervaciju());
		rezervacija.setPutnik(korisnik);
		if (putovanje != null) {
			putovanje.getRezervacijeSoba().add(rezervacija);
			for (Usluga dodatnaUsluga : brzaRez.getDodatneUsluge()) {
				putovanje.getDodatneUsluge().add(dodatnaUsluga);
			} 
		}
		brzeRezervacijeRepository.delete(brzaRez);
		rezervacijeRepository.save(rezervacija);
		if (putovanje != null) {
			putovanjeRepository.save(putovanje);
		}
		return "Uspjesno ste rezervisali sobu.";
	}

	public String izvrsiRezervacijuSoba(ZahtjevRezervacijaSobaDTO rezervacijaPodaci) throws NevalidniPodaciException {
		Date datumDolaska = null;
		Date datumOdlaska = null;
		Putovanje putovanje = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		int brojKreveta = 0;
		int brojOsoba = 0;
		RegistrovanKorisnik korisnik = (RegistrovanKorisnik) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			datumDolaska = df.parse(rezervacijaPodaci.getDatumDolaska());
			datumOdlaska = df.parse(rezervacijaPodaci.getDatumOdlaksa());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		if(datumOdlaska.before(datumDolaska)) {
			throw new NevalidniPodaciException("Datum odlaska ne može biti poslije datuma dolaska.");
		}
		if(datumDolaska.after(datumOdlaska)) {
			throw new NevalidniPodaciException("Datum dolaska ne može biti nakon datum odlaska.");
		}
		if (rezervacijaPodaci.getPutovanjeId() != null) {
			Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(rezervacijaPodaci.getPutovanjeId());
			if (putovanjeSearch.isPresent()) {
				putovanje = putovanjeSearch.get();
				brojOsoba = putovanje.getRezervacijeSjedista().size();
			} 
		}
		long diff = datumOdlaska.getTime() - datumDolaska.getTime(); //razlika u milisekundama
		long brojNocenja = diff / (24 * 60 * 60 * 1000);             //razlika u danima
		if(brojNocenja == 0) {
			brojNocenja = 1;
		}
		double ukupnaCijena = 0;
		int ukupanProcenatPopusta = 0;
		Optional<Usluga> uslugaSearch = null;
		Usluga usluga = null;
		if(putovanje != null) {
			for(Long idUsluge : rezervacijaPodaci.getDodatneUslugeIds()) {
				uslugaSearch = uslugeRepository.findById(idUsluge);
				if(!uslugaSearch.isPresent()) {
					throw new NevalidniPodaciException("Zadata je nepostojeća dodatna usluga.");
				}
				usluga = uslugaSearch.get();
				putovanje.getDodatneUsluge().add(usluga);
				ukupanProcenatPopusta += usluga.getProcenatPopusta();
				switch (usluga.getNacinPlacanja()) {
				case DNEVNO:
				{
					ukupnaCijena += usluga.getCijena() * brojNocenja;
					break;
				}
				case DNEVNO_PO_OSOBI:
				{
					ukupnaCijena += usluga.getCijena() * brojNocenja * brojOsoba;
					break;
				}
				case FIKSNO:
				{
					ukupnaCijena += usluga.getCijena();
					break;
				}
				case FIKSNO_PO_OSOBI:
				{
					ukupnaCijena += usluga.getCijena() * brojOsoba;
					break;
				}
				default:
					break;
				}
				
			}
		}
		
		Optional<HotelskaSoba> sobaSearch = null;
		HotelskaSoba soba = null;
		RezervacijaSobe rezervacijaSobe = null;
		double cijenaBoravka, popust = 0;
		for(Long idSobe : rezervacijaPodaci.getSobeZaRezervacijuIds()) {
			sobaSearch = sobeRepository.findById(idSobe);
			if(!sobaSearch.isPresent()) {
				throw new NevalidniPodaciException("Ne postoji zadata soba.");
			}
			soba = sobaSearch.get();
			brojKreveta += soba.getBrojKreveta();
			if(putovanje != null &&  brojKreveta > brojOsoba) {
				//uraditi rollback, rezervacija nece biti dozvoljena
				throw new NevalidniPodaciException("Ukupan broj kreveta rezervisanih soba premašuje broj rezervisanih karata.");
			}
			cijenaBoravka = soba.getCijena() * brojNocenja;
			popust = cijenaBoravka * ukupanProcenatPopusta / 100.0;
			cijenaBoravka -= popust;
			ukupnaCijena += cijenaBoravka;
			rezervacijaSobe = new RezervacijaSobe(datumDolaska, datumOdlaska, cijenaBoravka, soba);
			rezervacijaSobe.setPutnik(korisnik);
			rezervacijeRepository.save(rezervacijaSobe);
			if (putovanje != null) {
				putovanje.getRezervacijeSoba().add(rezervacijaSobe);
			}
		}
		
		if(putovanje != null) {
			putovanjeRepository.save(putovanje);
		}
		return "Uspješno ste rezervisali hotelski smještaj.";
	}

}
