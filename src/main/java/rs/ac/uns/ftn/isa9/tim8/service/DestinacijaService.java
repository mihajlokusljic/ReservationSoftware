package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.DestinacijaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.DestinacijeRepository;

@Service
public class DestinacijaService {

	@Autowired
	protected DestinacijeRepository destinacijeRepository;

	@Autowired
	protected AdresaRepository adresaRepository;

	public Collection<Destinacija> dobaviDestinacije() {
		return destinacijeRepository.findAll();
	}

	public Collection<Destinacija> vratiDestinacijeOsnovno(Collection<Destinacija> destinacije) {
		return destinacije;
		/*
		 * Collection<DestinacijaDTO> destinacijaDTO = new ArrayList<DestinacijaDTO>();
		 * for (Destinacija dest : destinacije) { DestinacijaDTO destD = new
		 * DestinacijaDTO(dest.getNazivDestinacije(), dest.getAdresa().getPunaAdresa());
		 * destinacijaDTO.add(destD); } return destinacijaDTO;
		 */
	}

	public Destinacija napraviDestinaciju(DestinacijaDTO destinacijaDTO) {
		// Adresa a = new Adresa(destinacijaDTO.getPunaAdresa());
		Adresa a = adresaRepository.findOneByPunaAdresa(destinacijaDTO.getPunaAdresa());
		if (a == null) {
			a = new Adresa(destinacijaDTO.getPunaAdresa());
		}
		Destinacija postojiLiDestinacija = destinacijeRepository.findOneByNazivDestinacije(destinacijaDTO.getNaziv());
		if (postojiLiDestinacija != null) {
			// Ukoliko takva destinacija vec postoji necemo je ponovo praviti
			return null;
		}
		Destinacija destinacija = new Destinacija();
		destinacija.setNazivDestinacije(destinacijaDTO.getNaziv());

		destinacija.setAdresa(a);

		adresaRepository.save(a);
		destinacijeRepository.save(destinacija);
		return destinacija;
	}

	public Destinacija dodajDestinaciju(DestinacijaDTO novaDestinacija) {
		Destinacija destinacija = napraviDestinaciju(novaDestinacija);
		return destinacija; // Ako je NULL onda to znaci da takva destinacija vec postoji pa se ne moze
							// dodati!
	}
}