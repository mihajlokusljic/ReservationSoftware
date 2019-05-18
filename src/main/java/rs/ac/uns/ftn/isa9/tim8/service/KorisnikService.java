package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaPrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.ZahtjevZaPrijateljstvo;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.ZahtjevZaPrijateljstvoRepository;

@Service
public class KorisnikService {

	@Autowired
	protected KorisnikRepository korisnikRepository;

	@Autowired
	protected AdresaRepository adresaRepository;

	@Autowired
	protected ZahtjevZaPrijateljstvoRepository zahtjevZaPrijateljstvoRepository;
	
	public KorisnikDTO izmjeniPorfilKorisnika(KorisnikDTO noviPodaci) throws NevalidniPodaciException {
		Osoba o;
		try {
			o = (Osoba) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			throw new NevalidniPodaciException("Niste ulogovani.");
		}
		if (noviPodaci.getIme() == null || noviPodaci.getPrezime() == null) {
			throw new NevalidniPodaciException("Ime i prezime moraju biti zadati.");
		}
		if (noviPodaci.getIme().equals("") || noviPodaci.getPrezime().equals("")) {
			throw new NevalidniPodaciException("Ime i prezime moraju biti zadati.");
		}
		if (noviPodaci.getAdresa() == null) {
			throw new NevalidniPodaciException("Adresa mora biti zadata.");
		}
		if (noviPodaci.getAdresa().getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa mora biti zadata.");
		}

		o.setIme(noviPodaci.getIme());
		o.setPrezime(noviPodaci.getPrezime());
		o.setBrojTelefona(noviPodaci.getBrojTelefona());
		Adresa staraAdresa = null;
		if (!noviPodaci.getAdresa().getPunaAdresa().equals(o.getAdresa().getPunaAdresa())) {
			staraAdresa = o.getAdresa();
			Adresa a = this.adresaRepository.findOneByPunaAdresa(noviPodaci.getAdresa().getPunaAdresa());
			if (a != null) {
				throw new NevalidniPodaciException("Zadata adresa je vec zauzeta.");
			}
			o.setAdresa(noviPodaci.getAdresa());
		}
		korisnikRepository.save(o);
		if (staraAdresa != null) {
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
		return korisnik;
	}

	public Collection<PretragaPrijateljaDTO> dobaviPrijatelje(Long korisnikId) throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository.findById(korisnikId);

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Korisnik nije ulogovan.");
		}

		RegistrovanKorisnik registrovaniKor = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();
		PretragaPrijateljaDTO dtoObj = null;
		Collection<PretragaPrijateljaDTO> prijatelji = new HashSet<PretragaPrijateljaDTO>();

		for (RegistrovanKorisnik prijatelj : registrovaniKor.getPrijatelji()) {
			dtoObj = new PretragaPrijateljaDTO(prijatelj.getId(), prijatelj.getIme(), prijatelj.getPrezime());
			prijatelji.add(dtoObj);
		}

		return prijatelji;
	}
	
	public Collection<PretragaPrijateljaDTO> dobaviKorisnikeZaPrijateljstvo(Long korisnikId) throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository.findById(korisnikId);

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Korisnik nije ulogovan.");
		}
		
		RegistrovanKorisnik registrovaniKor = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();
		PretragaPrijateljaDTO dtoObj = null;
		List<Osoba> korisnici = korisnikRepository.findAll();
		Collection<PretragaPrijateljaDTO> nisuPrijatelji = new HashSet<PretragaPrijateljaDTO>();

		for (Osoba korisnik : korisnici) {
			if (korisnik instanceof RegistrovanKorisnik) {
				RegistrovanKorisnik regKor = (RegistrovanKorisnik) korisnik;
				if (!daLiSuPrijatelji(registrovaniKor, regKor)) {
					dtoObj = new PretragaPrijateljaDTO(regKor.getId(), regKor.getIme(), regKor.getPrezime());
					nisuPrijatelji.add(dtoObj);
				}
			}
		}
		return nisuPrijatelji;
	}

	public Boolean daLiSuPrijatelji(RegistrovanKorisnik prviKorisnik, RegistrovanKorisnik drugiKorisnik) {
		for (RegistrovanKorisnik prijatelj : prviKorisnik.getPrijatelji()) {
			if (prijatelj.getEmail().equalsIgnoreCase(drugiKorisnik.getEmail())) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean dodajPrijatelja(Long korisnikId, Long primalacId) throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository.findById(korisnikId);

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Korisnik nije ulogovan.");
		}

		RegistrovanKorisnik posiljalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();

		pretragaKonkretnogKorisnika = korisnikRepository.findById(primalacId);

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takva osoba u sistemu za slanje zahtjeva za prijateljstvo.");
		}

		RegistrovanKorisnik primalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();
		
		ZahtjevZaPrijateljstvo zahtjevZaPrijateljstvo = new ZahtjevZaPrijateljstvo(false, posiljalac, primalac);
		primalac.getPrimljeniZahtjevi().add(zahtjevZaPrijateljstvo);
		
		
		zahtjevZaPrijateljstvoRepository.save(zahtjevZaPrijateljstvo);
		korisnikRepository.save(primalac);
		
		return true;
	}

	public Boolean prihvatiZahtjevZaPrijateljstvo(Long idZahtjeva) throws NevalidniPodaciException {
		Optional<ZahtjevZaPrijateljstvo> pretragaZahtjeva = zahtjevZaPrijateljstvoRepository.findById(idZahtjeva);
		
		if (!pretragaZahtjeva.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takav zahtjev.");
		}
		
		ZahtjevZaPrijateljstvo zahtjev = pretragaZahtjeva.get();
		
		zahtjev.getPrimalac().getPrimljeniZahtjevi().remove(zahtjev);
		
		zahtjevZaPrijateljstvoRepository.delete(zahtjev);
		
		zahtjev.getPrimalac().getPrijatelji().add(zahtjev.getPosiljalac());
		zahtjev.getPosiljalac().getPrijatelji().add(zahtjev.getPrimalac());
		
		korisnikRepository.save(zahtjev.getPrimalac());
		korisnikRepository.save(zahtjev.getPosiljalac());
		
		return true;
	}

	public Boolean odbijZahtjevZaPrijateljstvo(Long idZahtjeva) throws NevalidniPodaciException {
		Optional<ZahtjevZaPrijateljstvo> pretragaZahtjeva = zahtjevZaPrijateljstvoRepository.findById(idZahtjeva);
		
		if (!pretragaZahtjeva.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takav zahtjev.");
		}

		ZahtjevZaPrijateljstvo zahtjev = pretragaZahtjeva.get();
		
		zahtjev.getPrimalac().getPrimljeniZahtjevi().remove(zahtjev);
		
		zahtjevZaPrijateljstvoRepository.delete(zahtjev);
		
		korisnikRepository.save(zahtjev.getPrimalac());
		korisnikRepository.save(zahtjev.getPosiljalac());

		return true;
	}

}
