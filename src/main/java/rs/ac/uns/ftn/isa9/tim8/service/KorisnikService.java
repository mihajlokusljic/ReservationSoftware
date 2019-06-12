package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.FiltriranjePrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaPrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSjedistaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.UklanjanjePrijateljaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.ZahtjevZaPrijateljstvoDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.ZahtjevZaPrijateljstvo;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijaVozilaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.Rezervacija_sjedistaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijeSobaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.ZahtjevZaPrijateljstvoRepository;

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
		if (o.getAdresa() == null) {
			o.setAdresa(noviPodaci.getAdresa());
		} else if (!noviPodaci.getAdresa().getPunaAdresa().equals(o.getAdresa().getPunaAdresa())) {
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

	public Collection<PretragaPrijateljaDTO> dobaviKorisnikeZaPrijateljstvo(Long korisnikId)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository.findById(korisnikId);

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Korisnik nije ulogovan.");
		}
		// od registrovanog korisnika u primljenim zahtjevima za prijateljstvo nema
		// korisnika kojeg hos doadti
		RegistrovanKorisnik registrovaniKor = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();
		PretragaPrijateljaDTO dtoObj = null;
		List<Osoba> korisnici = korisnikRepository.findAll();
		Collection<PretragaPrijateljaDTO> nisuPrijatelji = new HashSet<PretragaPrijateljaDTO>();

		A: for (Osoba korisnik : korisnici) {
			if (korisnik instanceof RegistrovanKorisnik) {
				RegistrovanKorisnik regKor = (RegistrovanKorisnik) korisnik;
				if (!daLiSuPrijatelji(registrovaniKor, regKor) && regKor.getId() != registrovaniKor.getId()) {
					for (ZahtjevZaPrijateljstvo zahtjevZaPr : registrovaniKor.getPrimljeniZahtjevi()) {
						if (zahtjevZaPr.getPosiljalac().getEmail().equalsIgnoreCase(regKor.getEmail())) {
							continue A;
						}
					}
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

	public Boolean dodajPrijatelja(ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvoDTO)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository
				.findById(zahtjevZaPrijateljstvoDTO.getPosiljalacId());

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Korisnik nije ulogovan.");
		}

		RegistrovanKorisnik posiljalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();

		pretragaKonkretnogKorisnika = korisnikRepository.findById(zahtjevZaPrijateljstvoDTO.getPrimalacId());

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

	public Boolean prihvatiZahtjevZaPrijateljstvo(ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvoDTO)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository
				.findById(zahtjevZaPrijateljstvoDTO.getPrimalacId());

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takav posiljalac.");
		}

		RegistrovanKorisnik posiljalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();

		pretragaKonkretnogKorisnika = korisnikRepository.findById(zahtjevZaPrijateljstvoDTO.getPosiljalacId());

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takav primalac.");
		}

		RegistrovanKorisnik primalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();

		for (ZahtjevZaPrijateljstvo zahtjev : zahtjevZaPrijateljstvoRepository.findAll()) {
			if (zahtjev.getPrimalac().getEmail().equalsIgnoreCase(primalac.getEmail())
					&& zahtjev.getPosiljalac().getEmail().equalsIgnoreCase(posiljalac.getEmail())) {
				zahtjev.getPrimalac().getPrimljeniZahtjevi().remove(zahtjev);

				zahtjevZaPrijateljstvoRepository.delete(zahtjev);

				zahtjev.getPrimalac().getPrijatelji().add(zahtjev.getPosiljalac());
				zahtjev.getPosiljalac().getPrijatelji().add(zahtjev.getPrimalac());

				korisnikRepository.save(zahtjev.getPrimalac());
				korisnikRepository.save(zahtjev.getPosiljalac());

				return true;
			}
		}
		return false;
	}

	public Boolean odbijZahtjevZaPrijateljstvo(ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvoDTO)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository
				.findById(zahtjevZaPrijateljstvoDTO.getPrimalacId());

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takav posiljalac.");
		}

		RegistrovanKorisnik posiljalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();

		pretragaKonkretnogKorisnika = korisnikRepository.findById(zahtjevZaPrijateljstvoDTO.getPosiljalacId());

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji takav primalac.");
		}

		RegistrovanKorisnik primalac = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();

		for (ZahtjevZaPrijateljstvo zahtjev : zahtjevZaPrijateljstvoRepository.findAll()) {
			if (zahtjev.getPrimalac().getEmail().equalsIgnoreCase(primalac.getEmail())
					&& zahtjev.getPosiljalac().getEmail().equalsIgnoreCase(posiljalac.getEmail())) {
				zahtjev.getPrimalac().getPrimljeniZahtjevi().remove(zahtjev);

				zahtjevZaPrijateljstvoRepository.delete(zahtjev);

				korisnikRepository.save(zahtjev.getPrimalac());
				korisnikRepository.save(zahtjev.getPosiljalac());

				return true;
			}
		}
		return false;
	}

	public Collection<PretragaPrijateljaDTO> dobaviZahtjeveZaPrijateljstvo(Long korisnikId)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKonkretnogKorisnika = korisnikRepository.findById(korisnikId);

		if (!pretragaKonkretnogKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Korisnik nije ulogovan.");
		}

		RegistrovanKorisnik registrovaniKor = (RegistrovanKorisnik) pretragaKonkretnogKorisnika.get();
		PretragaPrijateljaDTO dtoObj = null;
		Collection<PretragaPrijateljaDTO> zahtjeviZaPrijateljstvo = new HashSet<PretragaPrijateljaDTO>();

		for (ZahtjevZaPrijateljstvo sviZahtjevi : registrovaniKor.getPrimljeniZahtjevi()) {
			dtoObj = new PretragaPrijateljaDTO(sviZahtjevi.getPosiljalac().getId(),
					sviZahtjevi.getPosiljalac().getIme(), sviZahtjevi.getPosiljalac().getPrezime());
			zahtjeviZaPrijateljstvo.add(dtoObj);
		}

		return zahtjeviZaPrijateljstvo;
	}

	public Boolean daLiJeZahtjevVecPoslat(ZahtjevZaPrijateljstvoDTO zahtjevZaPrijateljstvoDTO)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKorisnika = korisnikRepository.findById(zahtjevZaPrijateljstvoDTO.getPosiljalacId());

		if (!pretragaKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji posiljalac.");
		}

		RegistrovanKorisnik posiljalac = (RegistrovanKorisnik) pretragaKorisnika.get();

		pretragaKorisnika = korisnikRepository.findById(zahtjevZaPrijateljstvoDTO.getPrimalacId());

		if (!pretragaKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji primalac");
		}

		RegistrovanKorisnik primalac = (RegistrovanKorisnik) pretragaKorisnika.get();

		List<ZahtjevZaPrijateljstvo> zahtjevi = zahtjevZaPrijateljstvoRepository.findAll();

		for (ZahtjevZaPrijateljstvo zzp : zahtjevi) {
			if (zzp.getPosiljalac().getEmail().equalsIgnoreCase(posiljalac.getEmail())
					&& zzp.getPrimalac().getEmail().equalsIgnoreCase(primalac.getEmail())) {
				return true;
			}
		}

		return false;
	}

	public Boolean uklanjanjePrijatelja(UklanjanjePrijateljaDTO uklanjanjePrijateljaDTO)
			throws NevalidniPodaciException {
		Optional<Osoba> pretragaKorisnika = korisnikRepository.findById(uklanjanjePrijateljaDTO.getKorisnikId());

		if (!pretragaKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji korisnik sa zadatim id-jem.");
		}

		RegistrovanKorisnik korisnikKojiBrise = (RegistrovanKorisnik) pretragaKorisnika.get();

		pretragaKorisnika = korisnikRepository.findById(uklanjanjePrijateljaDTO.getPrijateljZaUklonitiId());

		if (!pretragaKorisnika.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji korisnik za obrisati sa zadatim id-jem.");
		}

		RegistrovanKorisnik korisnikZaObrisati = (RegistrovanKorisnik) pretragaKorisnika.get();

		korisnikKojiBrise.getPrijatelji().remove(korisnikZaObrisati);
		korisnikZaObrisati.getPrijatelji().remove(korisnikKojiBrise);

		korisnikRepository.save(korisnikKojiBrise);
		korisnikRepository.save(korisnikZaObrisati);

		return true;
	}

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
			rezVozilaDTO.add(
					new PrikazRezVozilaDTO(rv.getId(), rv.getRentACarServis().getNaziv(), rv.getRezervisanoVozilo(),
							rv.getCijena(), rv.getMjestoPreuzimanjaVozila().getAdresa().getPunaAdresa(),
							rv.getMjestoVracanjaVozila().getAdresa().getPunaAdresa(), rv.getDatumPreuzimanjaVozila(),
							rv.getDatumVracanjaVozila()));
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
		Collection<RezervacijaSjedista> rezSjediste = rezervacijeSjedistaRepository
				.findAllByPutnik(registrovaniKorisnik);
		Collection<PrikazRezSjedistaDTO> rezSjedistaDTO = new ArrayList<>();

		for (RezervacijaSjedista rs : rezSjediste) {
			rezSjedistaDTO.add(new PrikazRezSjedistaDTO(rs.getId(), rs.getLet().getPolaziste().getNazivDestinacije(),
					rs.getLet().getOdrediste().getNazivDestinacije(), rs.getLet().getDatumPoletanja(),
					rs.getLet().getDatumSletanja(), rs.getSjediste(), rs.getCijena(), rs.getAviokompanija().getNaziv(),
					rs.getLet().getBrojLeta()));
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

		System.out.println("Ukupno rezervisanih soba: " + rezSoba.size());
		for (RezervacijaSobe rs : rezSoba) {
			rezSobeDTO.add(new PrikazRezSobeDTO(rs.getId(), rs.getRezervisanaSoba().getHotel().getNaziv(),
					rs.getRezervisanaSoba().getBrojSobe(), rs.getRezervisanaSoba().getBrojKreveta(), rs.getCijena(),
					rs.getDatumDolaska(), rs.getDatumOdlaska()));
		}
		System.out.println("Ukupno rezervisanih soba dto: " + rezSobeDTO.size());

		return rezSobeDTO;
	}

	public Collection<PretragaPrijateljaDTO> pretragaPrijatelja(FiltriranjePrijateljaDTO filtriranjePrijateljaDTO)
			throws NevalidniPodaciException {
		if (filtriranjePrijateljaDTO.getPregledPrijatelja()) {
			Optional<Osoba> pretragaOsoba = korisnikRepository.findById(filtriranjePrijateljaDTO.getIdKorisnika());

			if (!pretragaOsoba.isPresent()) {
				throw new NevalidniPodaciException("Ne postoji korisnik sa datim id-jem");
			}

			RegistrovanKorisnik tekuciKorisnik = (RegistrovanKorisnik) pretragaOsoba.get();

			Collection<PretragaPrijateljaDTO> prijateljiFilter = new HashSet<PretragaPrijateljaDTO>();

			for (RegistrovanKorisnik korisnik : tekuciKorisnik.getPrijatelji()) {
				prijateljiFilter
						.add(new PretragaPrijateljaDTO(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime()));
			}

			if (filtriranjePrijateljaDTO.getImePrezime().equals("")) {
				return prijateljiFilter;
			}

			Iterator<PretragaPrijateljaDTO> it = prijateljiFilter.iterator();
			PretragaPrijateljaDTO trenutniPrijatelj;

			while (it.hasNext()) {
				trenutniPrijatelj = it.next();

				String trenutniPrijateljStr = trenutniPrijatelj.getIme() + " " + trenutniPrijatelj.getPrezime();

				if (!trenutniPrijateljStr.toUpperCase()
						.contains(filtriranjePrijateljaDTO.getImePrezime().toUpperCase())) {
					it.remove();
				}
			}
			return prijateljiFilter;

		} else {
			// Dodavanje prijatelja

			Optional<Osoba> pretragaOsoba = korisnikRepository.findById(filtriranjePrijateljaDTO.getIdKorisnika());

			if (!pretragaOsoba.isPresent()) {
				throw new NevalidniPodaciException("Ne postoji korisnik sa datim id-jem");
			}

			RegistrovanKorisnik tekuciKorisnik = (RegistrovanKorisnik) pretragaOsoba.get();

			Collection<PretragaPrijateljaDTO> potencijalniPrijateljiFilter = new HashSet<PretragaPrijateljaDTO>();

			for (Osoba korisnik : korisnikRepository.findAll()) {
				if (korisnik instanceof RegistrovanKorisnik) {
					RegistrovanKorisnik regKor = (RegistrovanKorisnik) korisnik;
					ZahtjevZaPrijateljstvoDTO zahtjevDTO = new ZahtjevZaPrijateljstvoDTO(regKor.getId(),
							tekuciKorisnik.getId());
					if (!daLiSuPrijatelji(regKor, tekuciKorisnik)
							&& !regKor.getEmail().equalsIgnoreCase(tekuciKorisnik.getEmail())
							&& !daLiJeZahtjevVecPoslat(zahtjevDTO)) {
						potencijalniPrijateljiFilter.add(
								new PretragaPrijateljaDTO(korisnik.getId(), korisnik.getIme(), korisnik.getPrezime()));
					}
				}

			}

			if (filtriranjePrijateljaDTO.getImePrezime().equals("")) {
				return potencijalniPrijateljiFilter;
			}

			Iterator<PretragaPrijateljaDTO> it = potencijalniPrijateljiFilter.iterator();
			PretragaPrijateljaDTO trenutniPrijatelj;

			while (it.hasNext()) {
				trenutniPrijatelj = it.next();

				String trenutniPrijateljStr = trenutniPrijatelj.getIme() + " " + trenutniPrijatelj.getPrezime();

				if (!trenutniPrijateljStr.toUpperCase()
						.contains(filtriranjePrijateljaDTO.getImePrezime().toUpperCase())) {
					it.remove();
				}
			}
			return potencijalniPrijateljiFilter;
		}

	}

}
