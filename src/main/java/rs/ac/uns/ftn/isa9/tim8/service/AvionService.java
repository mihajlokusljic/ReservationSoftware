package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.AvionDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.SjedisteDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.Segment;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.Rezervacija_sjedistaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.SegmentRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.SjedisteRepository;

@Service
public class AvionService {
	
	@Autowired
	protected AvionRepository avionRepository;
	
	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;
	
	@Autowired
	protected SegmentRepository segmentRepository;
	
	@Autowired
	protected SjedisteRepository sjedisteRepository;
	
	@Autowired
	protected Rezervacija_sjedistaRepository rezervacija_sjedistaRepository;
	
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
	
	public String ukloniSjediste(Long idSjedista) {
		// REZERVISANO MJESTO se ne moze ukloniti
		Optional<Sjediste> sjedisteSearch = sjedisteRepository.findById(idSjedista);
		
		if (!sjedisteSearch.isPresent()) {
			return "Sjediste sa datim id-jem ne postoji.";
		}
		
		Sjediste s = sjedisteSearch.get();
		
		Collection<RezervacijaSjedista> rezervacijeSjedista = rezervacija_sjedistaRepository.findAllBySjediste(s);
		
		if (!rezervacijeSjedista.isEmpty()) {
			return "Postoji rezervacija za dato sjediste.";
		}
		
		s.getAvion().getSjedista().remove(s);
		sjedisteRepository.delete(s);
		return "Sjediste je uspjesno obrisano.";
	}
	
	public Sjediste dodajSjediste(SjedisteDTO sjedisteDTO) throws NevalidniPodaciException {
		if (sjedisteDTO.getRed() < 1 || sjedisteDTO.getKolona() < 1) {
			throw new NevalidniPodaciException("Red i kolona moraju biti pozitivni cijeli brojevi.");
		}
		
		Optional<Avion> avionSearch = avionRepository.findById(sjedisteDTO.getIdAviona());
		Optional<Segment> segmentSearch = segmentRepository.findById(sjedisteDTO.getIdSegmenta());
						
		if (!avionSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji avion sa datim ID-jem.");
		}
		if (!segmentSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji segment sa datim ID-jem.");
		}
		
		Avion a = null;
		Segment seg = null;
		
		a = avionSearch.get();
		seg = segmentSearch.get();
		
		for (Segment segment : a.getSegmenti()) {
			if (segment.getId() == seg.getId()) {
				Sjediste s = new Sjediste(seg, sjedisteDTO.getRed(), sjedisteDTO.getKolona(), a);
				
				sjedisteRepository.save(s);
				a.getSjedista().add(s);
				
				avionRepository.save(a);
				return s;
			}
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