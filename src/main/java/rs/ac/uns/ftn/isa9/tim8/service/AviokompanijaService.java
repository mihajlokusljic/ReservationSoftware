package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.LetDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.LetoviRepository;

@Service
public class AviokompanijaService {
	
	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;
	
	@Autowired
	protected LetoviRepository letoviRepository;
	
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
	
	public Collection<LetDTO> vratiLetoveOsnovno(Collection<Let> letovi){
		Collection<LetDTO> letDTO = new ArrayList<LetDTO>();
		for (Let let : letovi) {
			LetDTO letd = new LetDTO(let.getBrojLeta(),let.getPolaziste(),let.getOdrediste(),let.getDatumPoletanja(),
					let.getDatumSletanja(),let.getDuzinaPutovanja(),let.getPresjedanja().size(),let.getKapacitetPrvaKlasa(),let.getKapacitetBiznisKlasa(),
					let.getKapacitetEkonomskaKlasa(),let.getCijenaKarte());
			letDTO.add(letd);
		}
		
		return letDTO;
	}
	
	public String dodajLet(LetDTO letDTO) {
		Let let = napraviLet(letDTO);
		if (let == null) {
			return "Broj leta zauzet";
		}
		return null;
		
		
	}
	
	public Let napraviLet(LetDTO letDTO) {
		Let postojiLet = letoviRepository.findOneByBrojLeta(letDTO.getBrojLeta());
		if (postojiLet != null) {
			return null;
		}
		Let let = new Let();
		let.setBrojLeta(letDTO.getBrojLeta());
		let.setDatumPoletanja(letDTO.getDatumPoletanja());
		let.setDatumSletanja(letDTO.getDatumSletanja());
		let.setCijenaKarte(letDTO.getCijenaKarte());
		let.setDuzinaPutovanja(letDTO.getDuzinaPutovanja());
		let.setKapacitetPrvaKlasa(letDTO.getKapacitetPrvaKlasa());
		let.setKapacitetBiznisKlasa(letDTO.getKapacitetBiznisKlasa());
		let.setKapacitetEkonomskaKlasa(letDTO.getKapacitetEkonomskaKlasa());
		let.setPolaziste(letDTO.getPolaziste());
		let.setOdrediste(letDTO.getOdrediste());
		
		
		letoviRepository.save(let);
		return let;
		
		
	}

}
