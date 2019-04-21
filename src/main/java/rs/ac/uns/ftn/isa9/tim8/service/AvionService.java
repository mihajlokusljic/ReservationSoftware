package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.AvionDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.model.Segment;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.SegmentRepository;

@Service
public class AvionService {
	
	@Autowired
	protected AvionRepository avionRepository;
	
	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;
	
	@Autowired
	protected SegmentRepository segmentRepository;
	
	public Collection<Avion> dobaviAvione() {
		return avionRepository.findAll();
	}
	
	public Collection<Segment>dobaviSegmenteZaAvion(Long idAviona) {
		Optional<Avion> avionSearch = avionRepository.findById(idAviona); // optional - rezultat pretrage
		Avion a = null;
		
		if (avionSearch.isPresent()) {
			a = avionSearch.get();
			return a.getSegmenti();
		}

		return null;
	}
	
	public Segment dodajSegment(Long idAviona, String nazivSegmenta) {
		Optional<Avion> avionSearch = avionRepository.findById(idAviona);
		Avion a = null;
		
		if (avionSearch.isPresent()) {
			a = avionSearch.get();
			for (Segment s : a.getSegmenti()) {
				if (s.getNaziv().equalsIgnoreCase(nazivSegmenta)) {
					return s;
				}
			}
			
			Segment noviSegment = new Segment(nazivSegmenta);
			a.getSegmenti().add(noviSegment);
			
			segmentRepository.save(noviSegment);
			avionRepository.save(a);
			return noviSegment;
		}
		
		return null;
	}

/*
 		
		Aviokompanija avio = aviokompanijaRepository.findOneByNaziv(novaAviokompanija.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(novaAviokompanija.getAdresa().getPunaAdresa());
		if (avio != null) {
			
			return "Zauzet naziv aviokompanije";
		}
		if (adresa != null) {
			
			return "Zauzeta adresa";
		}
		aviokompanijaRepository.save(novaAviokompanija);
		return null;
		
 */
	
	public Avion dodajAvion(AvionDTO noviAvion) {
		Avion a = avionRepository.findOneByNaziv(noviAvion.getNaziv());
		if (a != null) {
			// Ako vec postoji avion sa zadatim nazivom, onda ce kao rezultat dodavanja da se vrati NULL
			return null;
		}
		
		Optional<Aviokompanija> aviokompanijaSearchOptional = aviokompanijaRepository.findById(noviAvion.getIdAviokompanije());
		
		if (!aviokompanijaSearchOptional.isPresent()) {
			return null;
		}
		
		a = new Avion();
		a.setNaziv(noviAvion.getNaziv());
		
		Aviokompanija aviokompanija = aviokompanijaSearchOptional.get();
		
		a.setAviokompanija(aviokompanija);
		
		aviokompanija.getAvioni().add(a);
		
		avionRepository.save(a);
		aviokompanijaRepository.save(aviokompanija);
		return a;
	}

}
