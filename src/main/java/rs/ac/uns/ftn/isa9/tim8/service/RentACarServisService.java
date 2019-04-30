package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.FilijalaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.FilijalaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RentACarRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.RezervacijaVozilaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.VoziloRepository;

@Service
public class RentACarServisService {
	
	@Autowired
	protected RentACarRepository rentACarRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;
	
	@Autowired
	protected VoziloRepository voziloRepository;
	
	@Autowired
	protected RezervacijaVozilaRepository rezervacijaVozilaRepository;
	
	@Autowired
	protected FilijalaRepository filijalaRepository;
	
	public Collection<Poslovnica> dobaviRentACarServise() {
		Collection<RentACarServis> rentACarLista = rentACarRepository.findAll();
		Collection<Poslovnica> servisi = new ArrayList<Poslovnica>();
		if (rentACarLista.isEmpty()) {
			return null;
		}
		for( RentACarServis r : rentACarLista) {
			Poslovnica p = new Poslovnica(r.getNaziv(), r.getPromotivniOpis(), r.getAdresa());
			p.setId(r.getId());
			servisi.add(p);
		}

		return servisi;
	}
	
	public String dodajRentACarServis(RentACarServis noviServis) {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(noviServis.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(noviServis.getAdresa().getPunaAdresa());
		if (rentACar != null) {
			
			return "Zauzet naziv rent-a-car servisa";
		}
		if (adresa != null) {
			
			return "Zauzeta adresa";
		}
		Set<Filijala> filijale = new HashSet<Filijala>();
		noviServis.setFilijale(filijale);
		noviServis.setId(null);
		rentACarRepository.save(noviServis);
		return null;
	}
	
	public Collection<Vozilo> vratiVozilaServisa(String nazivServisa){
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(nazivServisa);
		if (rentACar == null) {
			return null;
		}
		
		return rentACar.getVozila();

	}
	
	public Collection<VoziloDTO> vratiVozilaOsnovno(Collection<Vozilo> vozila){
		Collection<VoziloDTO> vozilaDTO = new ArrayList<VoziloDTO>();
		if (vozila == null) {
			return null;
		}
		for (Vozilo vozilo : vozila) {
			VoziloDTO vozDto = new VoziloDTO(vozilo.getNaziv(), vozilo.getMarka(), vozilo.getModel(), vozilo.getGodina_proizvodnje(), vozilo.getBroj_sjedista(), 
					vozilo.getTip_vozila(), vozilo.getBroj_vrata(), vozilo.getKilovati(), vozilo.getCijena_po_danu(), vozilo.getId());
			vozilaDTO.add(vozDto);
		}
		
		return vozilaDTO;
	}
	
	public String dodajVozilo(String nazivServisa, Vozilo vozilo) {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(nazivServisa);
		if (rentACar == null) {
			return "Ne postoji servis sa unijetim nazivom.";
		}
		vozilo.setRentACar(rentACar);
		vozilo.setId(null);
		rentACar.getVozila().add(vozilo);
		rentACarRepository.save(rentACar);
		
		return null;
	}
	
	public String izmjeniRentACarServis(RentACarServis rentACar) {
		RentACarServis rentACarStari = rentACarRepository.findOneByNaziv(rentACar.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(rentACar.getAdresa().getPunaAdresa());		
		if (adresa != null) {
			
			return "Zauzeta adresa";
		}
		rentACarStari.getAdresa().setPunaAdresa(rentACar.getAdresa().getPunaAdresa());;
		rentACarStari.setPromotivniOpis(rentACar.getPromotivniOpis());
		rentACarRepository.save(rentACarStari);
		return null;
	}
	
	public RentACarServis pronadjiRentACarServis(String nazivServisa) {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(nazivServisa);
		if (rentACar == null) {
			return null;
		}
		else {
			return rentACar;
		}

	}
	
	public boolean servisPostoji(String nazivServisa) {
		RentACarServis racS = rentACarRepository.findOneByNaziv(nazivServisa);
		if (racS == null) {
			return false;
		}
		return true;
	}
	
	public String ukloniVozilo(Long idVozila) {				
		Optional<Vozilo> pretragaVozila = voziloRepository.findById(idVozila);
		
		if (!pretragaVozila.isPresent()) {
			return "Nevalidan id";
		}
		
		Vozilo voz = pretragaVozila.get();
		
		List<RezervacijaVozila> rVozila = rezervacijaVozilaRepository.findAllByRezervisanoVozilo(voz);
		if (!rVozila.isEmpty()) {
			return "Ne mozete obrisati rezervisano vozilo";
		}
		voziloRepository.delete(voz);
		return null;
	}
	
	public String izmjeniVozilo(VoziloDTO vozilo) {
		Optional<Vozilo> pretragaVozila = voziloRepository.findById(vozilo.getId());
		
		if (!pretragaVozila.isPresent()) {
			return "Nevalidan id";
		}
		
		Vozilo voz = pretragaVozila.get();
		
		voz.setNaziv(vozilo.getNaziv());
		voz.setMarka(vozilo.getMarka());
		voz.setModel(vozilo.getModel());
		voz.setGodina_proizvodnje(vozilo.getGodina_proizvodnje());;
		voz.setBroj_sjedista(vozilo.getBroj_sjedista());
		voz.setBroj_vrata(vozilo.getBroj_vrata());
		voz.setKilovati(vozilo.getKilovati());
		voz.setCijena_po_danu(vozilo.getCijena_po_danu());
		voz.setTip_vozila(vozilo.getTip_vozila());
		List<RezervacijaVozila> rVozila = rezervacijaVozilaRepository.findAllByRezervisanoVozilo(voz);
		if (!rVozila.isEmpty()) {
			return "Ne mozete izmjeniti rezervisano vozilo";
		}
		voziloRepository.save(voz);
		return null;
	}
	
	public String dodajFilijalu(String nazivServisa, String adresa) {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(nazivServisa);
		Adresa adresaPostoji = adresaRepository.findOneByPunaAdresa(adresa);
		if (adresaPostoji != null) {
			
			return "Zauzeta adresa";
		}
		Adresa a = new Adresa();
		a.setPunaAdresa(adresa);
		Filijala f = new Filijala();
		f.setAdresa(a);
		f.setRentACar(rentACar);
		rentACar.getFilijale().add(f);
		rentACarRepository.save(rentACar);
		return null;
	}
	
	public Collection<FilijalaDTO> vratiFilijale(String nazivServisa) {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(nazivServisa);

		Collection<FilijalaDTO> filijaleDTO = new ArrayList<FilijalaDTO>();
		Collection<Filijala> filijale = rentACar.getFilijale();
		if (filijale.isEmpty()) {
			return null;
		}
		for(Filijala f : filijale ) {
			filijaleDTO.add(new FilijalaDTO(f.getAdresa().getPunaAdresa(), f.getId(), f.getBrojVozila()));
		}

	
		return filijaleDTO;
	}
	
	public String ukloniFilijalu(Long idFilijale) {
		Optional<Filijala> pretragaFilijale = filijalaRepository.findById(idFilijale);
		
		if (!pretragaFilijale.isPresent()) {
			return "Nevalidan id";
		}
		
		Filijala f = pretragaFilijale.get();
		
		filijalaRepository.delete(f);
		return null;
	}
	
	public String izmjeniFilijalu( Long idFilijale, String novLokacija) {
		Optional<Filijala> pretragaFilijale = filijalaRepository.findById(idFilijale);
		
		if (!pretragaFilijale.isPresent()) {
			return "Nevalidan id";
		}
		
		Filijala f = pretragaFilijale.get();
		
		Adresa a = adresaRepository.findOneByPunaAdresa(novLokacija);
		if (a != null) {
			return "Zauzeta adresa";
		}
		f.getAdresa().setPunaAdresa(novLokacija);
		filijalaRepository.save(f);
		return null;
	}
}
