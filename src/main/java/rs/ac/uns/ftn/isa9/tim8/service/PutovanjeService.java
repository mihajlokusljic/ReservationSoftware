package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.OdgovorNaPozivnicuDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.Pozivnica;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.PozivnicaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.PutovanjeRepository;

@Service
public class PutovanjeService {

	@Autowired
	protected PutovanjeRepository putovanjeRepository;

	@Autowired
	protected KorisnikRepository korisnikRepository;

	@Autowired
	protected PozivnicaRepository pozivnicaRepository;

	@Autowired
	protected EmailService emailService;
	
	@Autowired
	protected AviokompanijaService aviokompanijaService;

	public Putovanje dobaviPutovanje(Long idPutovanja) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Putovanje je u međuvremenu otkazano.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		return putovanje;
	}

	public Collection<PrikazRezVozilaDTO> dobaviVozila(Long idPutovanja) throws NevalidniPodaciException {
		Collection<PrikazRezVozilaDTO> rezultat = new ArrayList<PrikazRezVozilaDTO>();

		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Putovanje je u međuvremenu otkazano.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		Collection<RezervacijaVozila> rezervisanaVozila = putovanje.getRezervacijeVozila();

		for (RezervacijaVozila rv : rezervisanaVozila) {
			PrikazRezVozilaDTO prv = new PrikazRezVozilaDTO(rv.getId(), rv.getRentACarServis().getNaziv(),
					rv.getRezervisanoVozilo(), rv.getCijena(),
					rv.getMjestoPreuzimanjaVozila().getAdresa().getPunaAdresa(),
					rv.getMjestoVracanjaVozila().getAdresa().getPunaAdresa(), rv.getDatumPreuzimanjaVozila(),
					rv.getDatumVracanjaVozila());
			rezultat.add(prv);
		}

		return rezultat;
	}

	public String potvrdaPutovanja(Long idPutovanja) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji putovanje sa datim id-jem.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		RegistrovanKorisnik inicijator = putovanje.getInicijatorPutovanja();

		inicijator.setBonusPoeni(inicijator.getBonusPoeni() + putovanje.getBonusPoeni());

		korisnikRepository.save(inicijator);

		Calendar c = Calendar.getInstance();

		Date triDanaNakonTekucegDatuma = new Date();

		c.setTime(triDanaNakonTekucegDatuma);

		c.add(Calendar.DATE, 3);
		triDanaNakonTekucegDatuma = c.getTime();

		Date triSataPredLet = null;

		for (RezervacijaSjedista rs : putovanje.getRezervacijeSjedista()) {
			triSataPredLet = rs.getLet().getDatumPoletanja();
		}

		if (triSataPredLet == null) {
			throw new NevalidniPodaciException("Došlo je do greške. Mora biti poznat datum tri sata pred let.");
		}

		c.setTime(triSataPredLet);
		c.add(Calendar.HOUR, -3);

		triSataPredLet = c.getTime();

		Date rokZaOdgovor = null;

		if (triSataPredLet.before(triDanaNakonTekucegDatuma)) {
			rokZaOdgovor = triSataPredLet;
		} else {
			rokZaOdgovor = triDanaNakonTekucegDatuma;
		}

		for (RezervacijaSjedista rs : putovanje.getRezervacijeSjedista()) {
			if (rs.getPutnik() != null) {
				if (!putovanje.getInicijatorPutovanja().getId().equals(rs.getPutnik().getId())) {
					/*
					 * Ako nije inicijator, onda nije ni neregistrovani putnik, pa se salje /
					 * prijateljima
					 */
					try {
						emailService.slanjeMejlaZaPozivnicu(rs.getPutnik(), putovanje);
					} catch (MailException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					putovanje.getPozivnice()
							.add(new Pozivnica(rokZaOdgovor, putovanje, inicijator, rs.getPutnik()));
				}
			}
		}

		putovanjeRepository.save(putovanje);
		return null;
	}

	public Boolean prihvatiPozivNaPutovanje(OdgovorNaPozivnicuDTO odgovor) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(odgovor.getIdPutovanja());

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Putovanje je u međuvremenu otkazano.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		Optional<Osoba> korisnikSearch = korisnikRepository.findById(odgovor.getIdPozvanogPrijatelja());

		if (!korisnikSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji korisnik sa datim id-jem.");
		}

		RegistrovanKorisnik pozvaniKorisnik = (RegistrovanKorisnik) korisnikSearch.get();

		// dodamo pozivnicu korisniku koji je pozvan na putovanje
		for (Pozivnica pozivnica : putovanje.getPozivnice()) {
			if (pozivnica.getPrimalac().getId().equals(pozvaniKorisnik.getId())) {
				if (pozivnica.getRokPrihvatanja().before(new Date())) {
					throw new NevalidniPodaciException("Prošao je rok za prihvatanje pozivnice.");
				}
				if(pozivnica.isPrihvacena()) {
					throw new NevalidniPodaciException("Već ste prihvatili poziv na ovo putovanje");
				} else if(pozivnica.isOdbijena()) {
					throw new NevalidniPodaciException("Već ste odbili poziv na ovo putovanje.");
				}
				pozivnica.setPrihvacena(true);
				pozivnica.setOdbijena(false);
				pozvaniKorisnik.getPrimljenePozivnice().add(pozivnica);
				pozivnicaRepository.save(pozivnica);
				break;
			}
		}

		// uvecamo bonus poene korisniku koji je pozvan na putovanje
		pozvaniKorisnik.setBonusPoeni(pozvaniKorisnik.getBonusPoeni() + putovanje.getBonusPoeni());

		korisnikRepository.save(pozvaniKorisnik);

		return true;
	}

	public Boolean odbijPozivNaPutovanje(OdgovorNaPozivnicuDTO odgovor) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(odgovor.getIdPutovanja());

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Putovanje je u međuvremenu otkazano.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		Optional<Osoba> korisnikSearch = korisnikRepository.findById(odgovor.getIdPozvanogPrijatelja());

		if (!korisnikSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji korisnik sa datim id-jem.");
		}

		RegistrovanKorisnik pozvaniKorisnik = (RegistrovanKorisnik) korisnikSearch.get();

		// odbijanje pozivnice za pozvanog korisnika
		for (Pozivnica pozivnica : putovanje.getPozivnice()) {
			if (pozivnica.getPrimalac().getId().equals(pozvaniKorisnik.getId())) {
				if (pozivnica.getRokPrihvatanja().before(new Date())) {
					throw new NevalidniPodaciException("Prošao je rok za odbijanje pozivnice.");
				}
				if(pozivnica.isPrihvacena()) {
					throw new NevalidniPodaciException("Već ste prihvatili poziv na ovo putovanje");
				} else if(pozivnica.isOdbijena()) {
					throw new NevalidniPodaciException("Već ste odbili poziv na ovo putovanje.");
				}
				pozivnica.setPrihvacena(false);
				pozivnica.setOdbijena(true);
				pozivnicaRepository.save(pozivnica);
				break;
			}
		}
		
		// otkazivanje rezervacije sjedista za pozvanog korisnika koji je odbio pozivnicu
		for (RezervacijaSjedista rs : putovanje.getRezervacijeSjedista()) {
			if(rs.getPutnik().getId().equals(pozvaniKorisnik.getId())) {
				aviokompanijaService.otkaziRezervaciju(rs.getId());
				break;
			}
		}

		return true;
	}

}
