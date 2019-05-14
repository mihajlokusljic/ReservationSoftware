package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;

@Service
public class KorisnikService {

	@Autowired
	protected KorisnikRepository korisnikRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;

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
}
