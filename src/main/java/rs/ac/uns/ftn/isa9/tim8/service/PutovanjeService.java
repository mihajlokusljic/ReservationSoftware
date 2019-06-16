package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.PutovanjeRepository;

@Service
public class PutovanjeService {

	@Autowired
	protected PutovanjeRepository putovanjeRepository;

	@Autowired
	protected KorisnikRepository korisnikRepository;

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

		return null;
	}

}
