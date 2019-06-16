package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Collection;
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
import rs.ac.uns.ftn.isa9.tim8.repository.PutovanjeRepository;

@Service
public class PutovanjeService {

	@Autowired
	protected PutovanjeRepository putovanjeRepository;

	@Autowired
	protected KorisnikRepository korisnikRepository;

	@Autowired
	protected EmailService emailService;

	public Putovanje dobaviPutovanje(Long idPutovanja) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji putovanje sa datim id-jem.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		return putovanje;
	}

	public Collection<PrikazRezVozilaDTO> dobaviVozila(Long idPutovanja) throws NevalidniPodaciException {
		Collection<PrikazRezVozilaDTO> rezultat = new ArrayList<PrikazRezVozilaDTO>();

		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji putovanje sa datim id-jem.");
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
		putovanjeRepository.save(putovanje);

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
				}
			}
		}

		return null;
	}

	public Boolean prihvatiPozivNaPutovanje(OdgovorNaPozivnicuDTO odgovor) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(odgovor.getIdPutovanja());
		
		if(!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Nije prepoznato proslijeđeno putovanje.");
		}
		
		Putovanje putovanje = putovanjeSearch.get();
		
		Optional<Osoba> korisnikSearch = korisnikRepository.findById(odgovor.getIdPozvanogPrijatelja());
		
		if(!korisnikSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji korisnik sa datim id-em.");
		}
		
		RegistrovanKorisnik pozvaniKorisnik = (RegistrovanKorisnik) korisnikSearch.get();
		
		//Pozivnica pozivnica = new Pozivnica(true, rokPrihvatanja, putovanje, putovanje.getInicijatorPutovanja(), pozvaniKorisnik);
		return null;
	}

}
