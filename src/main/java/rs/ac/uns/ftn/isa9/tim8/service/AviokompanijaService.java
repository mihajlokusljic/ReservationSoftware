package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.LetoviRepository;

@Service
public class AviokompanijaService {
	
	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;
	
	@Autowired
	protected LetoviRepository letoviRepository;
	
	@Autowired
	protected AvionRepository avionRepository;
	
	public String dodajAviokompaniju(Aviokompanija novaAviokompanija) {
		
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
	}
	
	public Collection<Aviokompanija> dobaviAviokompanije() {
		return aviokompanijaRepository.findAll();
	}
	
	public Collection<Let> dobaviLetove(){
		return letoviRepository.findAll();
	}
	
	public Let dodajLet(LetDTO letDTO) throws NevalidniPodaciException {
		Let let = napraviLet(letDTO);
		if (let == null) {
			throw new NevalidniPodaciException("Broj leta je zauzet.");
		}
		return let;
			
	}
	
	public boolean destinacijePostojeuAviokompaniji(Collection<Long> potencijalneDestinacije, Collection<Destinacija> destinacijeAviokompanije) {
		int counter = 0;
		
		for (Long idDest : potencijalneDestinacije) {
			for (Destinacija dest : destinacijeAviokompanije) {
				if (idDest == dest.getId()) {
					counter++;
					break;
				}
			}
		}
		
		return (counter == potencijalneDestinacije.size());
	}
	
	public HashSet<Destinacija> vratiDestinacijePoIDevima(Collection<Long> potencijalneDestinacije, Collection<Destinacija> destinacijeAviokompanije) {
		HashSet<Destinacija> retVal = new HashSet<Destinacija>();
		
		for (Long idDest : potencijalneDestinacije) {
			for (Destinacija dest : destinacijeAviokompanije) {
				if (idDest == dest.getId()) {
					retVal.add(dest);
					break;
				}
			}
		}
		
		return retVal;
	}
	
	public Let napraviLet(LetDTO letDTO) throws NevalidniPodaciException {
		Let postojiLet = letoviRepository.findOneByBrojLeta(letDTO.getBrojLeta());
		if (postojiLet != null) {
			throw new NevalidniPodaciException("Vec postoji let sa datim brojem leta.");
		}
		
		Optional<Aviokompanija> aviokompanijaSearch = aviokompanijaRepository.findById(letDTO.getIdAviokompanije());
		
		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji aviokompanija sa datim ID-jem.");
		}
		
		Collection<Destinacija> destinacije = aviokompanijaSearch.get().getDestinacije();
		
		if (destinacije.size() < 2) {
			throw new NevalidniPodaciException("Ne postoje makar dvije destinacije definisane za datu aviokompaniju.");
		}
		
		
		if (letDTO.getDatumSletanja().before(letDTO.getDatumPoletanja())) {
			throw new NevalidniPodaciException("Datum poletanja mora biti prije datuma sletanja");
		}
		
		if (letDTO.getDuzinaPutovanja().before(letDTO.getDatumSletanja())) {
			throw new NevalidniPodaciException("Datum povratka mora biti nakon datuma sletanja.");
		}
		
		// Provjera postoji li avion
		Optional<Avion> avionSearch = avionRepository.findById(letDTO.getIdAviona());
		
		if (!avionSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji avion sa datim ID-jem.");
		}
		
		if (letDTO.getCijenaKarte() < 0) {
			throw new NevalidniPodaciException("Cijena karte ne moze biti negativna.");
		}
		
		Long idPolaziste = letDTO.getIdPolaziste();
		Long idOdrediste = letDTO.getIdOdrediste();
		
		boolean nadjenoPolaziste = false;
		boolean nadjenoOdrediste = false;
		
		Destinacija polazna = new Destinacija();
		Destinacija odredisna = new Destinacija();
		
		for (Destinacija dest : destinacije) {
			if (dest.getId() == idPolaziste) {
				nadjenoPolaziste = true;
				polazna = dest;
			}
		}
		
		if (nadjenoPolaziste == false) {
			throw new NevalidniPodaciException("Ne postoji polaziste sa ID-jem u aviokompanijinim destinacijama.");
		}
		
		for (Destinacija dest : destinacije) {
			if (dest.getId() == idOdrediste) {
				nadjenoOdrediste = true;
				odredisna = dest;
			}
		}
		
		if (nadjenoOdrediste == false) {
			throw new NevalidniPodaciException("Ne postoji odrediste sa ID-jem u aviokompanijinim destinacijama.");
		}
		
		boolean postojeLiSveDestinacijeZaPresjedanja = destinacijePostojeuAviokompaniji(letDTO.getIdDestinacijePresjedanja(), destinacije);
		
		if (!postojeLiSveDestinacijeZaPresjedanja) {
			throw new NevalidniPodaciException("Sve destinacije presjedanja moraju postojati u aviokompaniji.");
		}
		
		Aviokompanija avio = aviokompanijaSearch.get(); // potrebno joj je dodati let
		Avion avion = avionSearch.get();
		HashSet<Destinacija> presjedanja = vratiDestinacijePoIDevima(letDTO.getIdDestinacijePresjedanja(), destinacije);
		
		Let let = new Let();
		
		let.setBrojLeta(letDTO.getBrojLeta());
		let.setDatumPoletanja(letDTO.getDatumPoletanja());
		let.setDatumSletanja(letDTO.getDatumSletanja());
		let.setDuzinaPutovanja(letDTO.getDuzinaPutovanja());
		let.setCijenaKarte(letDTO.getCijenaKarte());
		let.setPolaziste(polazna);
		let.setOdrediste(odredisna);
		let.setAvion(avion);
		let.setPresjedanja(presjedanja);
		
		avio.getLetovi().add(let);
		
		letoviRepository.save(let);
		aviokompanijaRepository.save(avio);
		
		return let;
			
	}

}
