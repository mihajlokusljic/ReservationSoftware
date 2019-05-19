package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.NacinPlacanjaUsluge;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.repository.BrzeRezervacijeSobaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.UslugeRepository;

@Service
public class RezervacijeSobaService {
	
	@Autowired
	protected BrzeRezervacijeSobaRepository brzeRezervacijeRepository;
	
	@Autowired
	protected HotelRepository hotelRepository;
	
	@Autowired
	protected UslugeRepository uslugeRepository;

	public BrzaRezervacijaSobeDTO dodajUslugeBrzeRezervacije(BrzaRezervacijaSobeDTO brzaRezervacija) throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSoba> rezervacijaSearch = brzeRezervacijeRepository.findById(brzaRezervacija.getId());
		if(!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata brza rezervacija");
		}
		BrzaRezervacijaSoba target = rezervacijaSearch.get();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if(!target.getSobaZaRezervaciju().getHotel().getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException("Niste logovani kao ovlascen administrator hotela za zadatu rezervaciju.");
		}
		int sumaPopusta = 0;
		double ukupnaCijena = target.getBaznaCijena();
		Optional<Usluga> uslugaSearch = null;
		Usluga tekucaUsluga = null;
		long brojNocenja = target.izracunajBrojNocenja();
		
		for(Long uslugaId : brzaRezervacija.getDodatneUslugeIds()) {
			uslugaSearch = uslugeRepository.findById(uslugaId);
			if(!uslugaSearch.isPresent()) {
				throw new NevalidniPodaciException("Zadali ste nepostojecu uslugu.");
			}
			tekucaUsluga = uslugaSearch.get();
			target.getDodatneUsluge().add(tekucaUsluga);
			sumaPopusta += tekucaUsluga.getProcenatPopusta();
			if(tekucaUsluga.getNacinPlacanja() == NacinPlacanjaUsluge.DNEVNO_PO_OSOBI) {
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
		if(ukupnaCijena < 0) {
			ukupnaCijena = 0;
		}
		target.setBaznaCijena(ukupnaCijena);
		brzeRezervacijeRepository.save(target);
		brzaRezervacija.setCijenaBoravka(ukupnaCijena);
		return brzaRezervacija;
	}

	public BrzaRezervacijaSoba zadajPopustBrzeRezervacije(BrzaRezervacijaSobeDTO brzaRezervacija) throws NevalidniPodaciException {
		Optional<BrzaRezervacijaSoba> rezervacijaSearch = brzeRezervacijeRepository.findById(brzaRezervacija.getId());
		if(!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata brza rezervacija.");
		}
		BrzaRezervacijaSoba rez = rezervacijaSearch.get();
		AdministratorHotela admin = (AdministratorHotela) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Hotel administriraniHotel = admin.getHotel();
		if(!rez.getSobaZaRezervaciju().getHotel().getId().equals(administriraniHotel.getId())) {
			throw new NevalidniPodaciException("Niste ulogovani kao ovlascen administrator za datu rezervaciju.");
		}
		int popust = brzaRezervacija.getProcenatPopusta();
		if(popust < 0) {
			throw new NevalidniPodaciException("Popust ne može biti negativan.");
		}
		if(popust > 100) {
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

	public Collection<BrzaRezervacijaSoba> dobaviSveBrzeRezervacijeZaHotel(Long idHotela) throws NevalidniPodaciException {
		Optional<Hotel> hotelSearch = hotelRepository.findById(idHotela);
		if(!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadati hotel.");
		}
		Hotel target = hotelSearch.get();
		Collection<BrzaRezervacijaSoba> rezultat = brzeRezervacijeRepository.findAll();
		Iterator<BrzaRezervacijaSoba> it = rezultat.iterator();
		BrzaRezervacijaSoba tekucaRezervacija = null;
		while(it.hasNext()) {
			tekucaRezervacija = it.next();
			if(!tekucaRezervacija.getSobaZaRezervaciju().getHotel().getId().equals(target.getId())) {
				it.remove();
			}
		}
		return rezultat;
	}

}
