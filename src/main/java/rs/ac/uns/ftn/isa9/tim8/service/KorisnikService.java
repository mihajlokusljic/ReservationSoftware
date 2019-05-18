package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.RezervacijaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijaVozilaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.Rezervacija_sjedistaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijeSobaRepository;

@Service
public class KorisnikService {

	@Autowired
	protected KorisnikRepository korisnikRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;
	
	@Autowired
	protected RezervacijaVozilaRepository rezervacijaVozilaRepository;
	
	@Autowired
	protected Rezervacija_sjedistaRepository rezervacijeSjedistaRepository;
	
	@Autowired
	protected RezervacijeSobaRepository rezervacijeSobaRepository;

	public KorisnikDTO izmjeniPorfilKorisnika(KorisnikDTO noviPodaci) throws NevalidniPodaciException {
		Osoba o;
		try {
			o = (Osoba) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new NevalidniPodaciException("Niste ulogovani.");
		}
		if(noviPodaci.getIme() == null || noviPodaci.getPrezime() == null) {
			throw new NevalidniPodaciException("Ime i prezime moraju biti zadati.");
		}
		if(noviPodaci.getIme().equals("") || noviPodaci.getPrezime().equals("")) {
			throw new NevalidniPodaciException("Ime i prezime moraju biti zadati.");
		}
		if(noviPodaci.getAdresa() == null) {
			throw new NevalidniPodaciException("Adresa mora biti zadata.");
		}
		if(noviPodaci.getAdresa().getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa mora biti zadata.");
		}
		
		o.setIme(noviPodaci.getIme());
		o.setPrezime(noviPodaci.getPrezime());
		o.setBrojTelefona(noviPodaci.getBrojTelefona());
		Adresa staraAdresa = null;
		if(!noviPodaci.getAdresa().getPunaAdresa().equals(o.getAdresa().getPunaAdresa())) {
			staraAdresa = o.getAdresa();
			Adresa a = this.adresaRepository.findOneByPunaAdresa(noviPodaci.getAdresa().getPunaAdresa());
			if(a != null) {
				throw new NevalidniPodaciException("Zadata adresa je vec zauzeta.");
			}
			o.setAdresa(noviPodaci.getAdresa());
		}
		korisnikRepository.save(o);
		if(staraAdresa != null) {
			adresaRepository.delete(staraAdresa);
		}
		return noviPodaci;
	}

	public KorisnikDTO izmjeniAdminaRentACar(KorisnikDTO korisnik) {
		Optional<Osoba> pretragaOsoba = korisnikRepository.findById(korisnik.getId());

		if (!pretragaOsoba.isPresent()) {
			return null;
		}

		Osoba o = pretragaOsoba.get();

		o.setIme(korisnik.getIme());
		o.setPrezime(korisnik.getPrezime());
		o.setBrojTelefona(korisnik.getBrojTelefona());
		o.setAdresa(korisnik.getAdresa());
		korisnikRepository.save(o);
		return korisnik;

	}

	public Osoba vratiKorisnikaPoTokenu(String token) {
		return korisnikRepository.findByToken(token);
	}

	public KorisnikDTO izmjeniProfilRegistrovanogKorisnika(KorisnikDTO korisnik) {
		Optional<Osoba> pretragaOsoba = korisnikRepository.findById(korisnik.getId());

		if (!pretragaOsoba.isPresent()) {
			return null;
		}

		Osoba o = pretragaOsoba.get();

		o.setIme(korisnik.getIme());
		o.setPrezime(korisnik.getPrezime());
		o.setBrojTelefona(korisnik.getBrojTelefona());
		o.setAdresa(korisnik.getAdresa());
		korisnikRepository.save(o);
		return korisnik;	}

	public Collection<PrikazRezVozilaDTO> vratiRezervacijeVozila(RegistrovanKorisnik regKor) {
		Optional<Osoba> pretragaOsoba = korisnikRepository.findById(regKor.getId());

		if (!pretragaOsoba.isPresent()) {
			return null;
		}

		Osoba o = pretragaOsoba.get();
		RegistrovanKorisnik registrovaniKorisnik = (RegistrovanKorisnik) o;
		Collection<RezervacijaVozila> rezVozila = rezervacijaVozilaRepository.findAllByPutnik(registrovaniKorisnik);
		Collection<PrikazRezVozilaDTO> rezVozilaDTO = new ArrayList<>();
		
		for (RezervacijaVozila rv : rezVozila) {
			rezVozilaDTO.add(new PrikazRezVozilaDTO(rv.getId(), rv.getRentACarServis().getNaziv(), rv.getRezervisanoVozilo(),
					rv.getCijena(), rv.getMjestoPreuzimanjaVozila().getAdresa().getPunaAdresa(), rv.getMjestoVracanjaVozila().getAdresa().getPunaAdresa(),
					rv.getDatumPreuzimanjaVozila(), rv.getDatumVracanjaVozila()));
		}

		
		
		return rezVozilaDTO;
	}

	public Collection<PrikazRezSjedistaDTO> vratiRezervacijeLetova(RegistrovanKorisnik regKor) {
		Optional<Osoba> pretragaOsoba = korisnikRepository.findById(regKor.getId());

		if (!pretragaOsoba.isPresent()) {
			return null;
		}

		Osoba o = pretragaOsoba.get();
		RegistrovanKorisnik registrovaniKorisnik = (RegistrovanKorisnik) o;
		Collection<RezervacijaSjedista> rezSjediste = rezervacijeSjedistaRepository.findAllByPutnik(registrovaniKorisnik);
		Collection<PrikazRezSjedistaDTO> rezSjedistaDTO = new ArrayList<>();
		
		for (RezervacijaSjedista rs : rezSjediste) {
			rezSjedistaDTO.add(new PrikazRezSjedistaDTO(rs.getId(), rs.getAviokompanija().getNaziv(), rs.getLet().getBrojLeta(),
					rs.getCijena(), rs.getSjediste(), rs.getLet().getPolaziste().getNazivDestinacije(), rs.getLet().getOdrediste().getNazivDestinacije(),
					rs.getLet().getDatumPoletanja(), rs.getLet().getDatumSletanja()));
		}
		
		return rezSjedistaDTO;
	}

	public Collection<PrikazRezSobeDTO> vratiRezervacijeSoba(RegistrovanKorisnik regKor) {
		Optional<Osoba> pretragaOsoba = korisnikRepository.findById(regKor.getId());

		if (!pretragaOsoba.isPresent()) {
			return null;
		}

		Osoba o = pretragaOsoba.get();
		RegistrovanKorisnik registrovaniKorisnik = (RegistrovanKorisnik) o;
		Collection<RezervacijaSobe> rezSoba = rezervacijeSobaRepository.findAllByPutnik(registrovaniKorisnik);
		Collection<PrikazRezSobeDTO> rezSobeDTO = new ArrayList<>();
		
		for (RezervacijaSobe rs : rezSoba) {
			rezSobeDTO.add(new PrikazRezSobeDTO(rs.getId(), rs.getRezervisanaSoba().getHotel().getNaziv(), rs.getRezervisanaSoba().getBrojSobe(),
					rs.getRezervisanaSoba().getBrojKreveta(), rs.getCijena(), rs.getDatumDolaska(), rs.getDatumOdlaska()));
		}
		
		return rezSobeDTO;
	}
	
	
}
