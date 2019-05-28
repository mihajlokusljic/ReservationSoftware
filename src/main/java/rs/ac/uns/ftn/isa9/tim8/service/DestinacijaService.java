package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.DestinacijaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.DestinacijeRepository;

@Service
public class DestinacijaService {

	@Autowired
	protected DestinacijeRepository destinacijeRepository;

	@Autowired
	protected AdresaRepository adresaRepository;

	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;
	
	public Collection<Destinacija> dobaviDestinacije() {
		return destinacijeRepository.findAll();
	}
	
	public Collection<Destinacija> dobaviDestinacije(Long idAviokompanije) {
		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(idAviokompanije);
		
		Aviokompanija trazenaAviokompanija = aviokompanijaSearch.get();

		return trazenaAviokompanija.getDestinacije();
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

	public Destinacija napraviDestinaciju(DestinacijaDTO destinacijaDTO) throws NevalidniPodaciException {
		// Adresa a = new Adresa(destinacijaDTO.getPunaAdresa());
		
		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(destinacijaDTO.getIdAviokompanije());
		
		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa datim id-jem.");
		}
		
		Aviokompanija aviokompanija = aviokompanijaSearch.get();
		
		Adresa a = adresaRepository.findOneByPunaAdresa(destinacijaDTO.getAdresa().getPunaAdresa());
		if (a == null) {
			a = destinacijaDTO.getAdresa();
		} else {
			throw new NevalidniPodaciException("Ne mogu postojati dvije destinacije na istoj adresi.");
		}
		
		Destinacija postojiLiDestinacija = destinacijeRepository.findOneByNazivDestinacije(destinacijaDTO.getNaziv());
		if (postojiLiDestinacija != null) {
			// Ukoliko takva destinacija vec postoji necemo je ponovo praviti
			throw new NevalidniPodaciException("Takva destinacija vec postoji.");
		}
		
		Destinacija destinacija = new Destinacija();
		destinacija.setNazivDestinacije(destinacijaDTO.getNaziv());

		destinacija.setAdresa(a);

		adresaRepository.save(a);
		destinacijeRepository.save(destinacija);
		
		aviokompanija.getDestinacije().add(destinacija);
		
		aviokompanijaRepository.save(aviokompanija);
		
		return destinacija;
	}

	public Destinacija dodajDestinaciju(DestinacijaDTO novaDestinacija) throws NevalidniPodaciException {
		Destinacija destinacija = napraviDestinaciju(novaDestinacija);
		return destinacija; // Ako je NULL onda to znaci da takva destinacija vec postoji pa se ne moze
							// dodati!
	}
}