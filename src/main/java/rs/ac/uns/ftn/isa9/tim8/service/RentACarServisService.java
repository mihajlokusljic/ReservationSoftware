package rs.ac.uns.ftn.isa9.tim8.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaSobeDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.BrzaRezervacijaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.DatumiZaPrihodDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.FilijalaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PotrebnoSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaRacDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazBrzeRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PrikazRezVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.RezervacijaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorRentACar;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Usluga;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.BrzeRezervacijeVozilaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.FilijalaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;
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
	
	@Autowired
	protected KorisnikRepository korisnikRepository;
	
	@Autowired
	protected BrzeRezervacijeVozilaRepository brzaRezervacijaVozilaRepository;
	
	public Collection<Poslovnica> dobaviRentACarServise() {
		Collection<RentACarServis> rentACarLista = rentACarRepository.findAll();
		Collection<Poslovnica> servisi = new ArrayList<Poslovnica>();
		if (rentACarLista.isEmpty()) {
			return null;
		}
		for( RentACarServis r : rentACarLista) {
			Poslovnica p = new Poslovnica(r.getId(),r.getNaziv(), r.getPromotivniOpis(), r.getAdresa());
			p.setId(r.getId());
			p.setSumaOcjena(r.getSumaOcjena());
			p.setBrojOcjena(r.getBrojOcjena());
			servisi.add(p);
		}

		return servisi;
	}
	
	public RentACarServis dodajRentACarServis(RentACarServis noviServis) throws NevalidniPodaciException {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(noviServis.getNaziv());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(noviServis.getAdresa().getPunaAdresa());
		if (rentACar != null) {
			throw new NevalidniPodaciException("Vec postoji rent-a-car servis sa zadatim nazivom.");
		}
		if (adresa != null) {
			throw new NevalidniPodaciException("Vec postoji poslovnica na zadatoj adresi.");
		}
		if(noviServis.getNaziv().equals("")) {
			throw new NevalidniPodaciException("Naziv rent-a-car servisa mora biti zadat.");
		}
		if(noviServis.getAdresa().getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa rent-a-car servisa mora biti zadata.");
		}
		RentACarServis rac = new RentACarServis(noviServis.getNaziv(), noviServis.getPromotivniOpis(), noviServis.getAdresa());
		rentACarRepository.save(rac);
		return rac;
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
			String adresa = null;
			if (vozilo.getFilijala() != null) {
				adresa = vozilo.getFilijala().getAdresa().getPunaAdresa();
			}
			VoziloDTO vozDto = new VoziloDTO(vozilo.getNaziv(), vozilo.getMarka(), vozilo.getModel(), vozilo.getGodina_proizvodnje(), vozilo.getBroj_sjedista(), 
					vozilo.getTip_vozila(), vozilo.getBroj_vrata(), vozilo.getKilovati(), vozilo.getCijena_po_danu(), adresa, vozilo.getId());
			vozilaDTO.add(vozDto);
		}
		
		return vozilaDTO;
	}
	
	public String dodajVozilo(String nazivServisa, Vozilo vozilo) {
		RentACarServis rentACar = rentACarRepository.findOneByNaziv(nazivServisa);
		if (rentACar == null) {
			return "Ne postoji servis sa unijetim nazivom.";
		}		
		
		boolean postojiFilijala = true;
		Filijala filijala = null;
		
		if (vozilo.getFilijala() == null) {
			postojiFilijala = false;
		}
		if (postojiFilijala == true) {
			Optional<Filijala> pretragaFilijala = filijalaRepository.findById(vozilo.getFilijala().getId());
			
			if (!pretragaFilijala.isPresent()) {
				return "Nevalidan id";
			}
			
			filijala = pretragaFilijala.get();
			filijala.setBrojVozila(filijala.getBrojVozila()+1);

		}
				
		vozilo.setRentACar(rentACar);
		vozilo.setId(null);
		vozilo.setFilijala(filijala);
		rentACar.getVozila().add(vozilo);
		rentACarRepository.save(rentACar);
		if (postojiFilijala) {
			filijalaRepository.save(filijala);		
		}
	
		
		return null;
	}
	
	public String izmjeniRentACarServis(RentACarServis rentACar) {
		RentACarServis rentACarStari = rentACarRepository.findOneByNaziv(rentACar.getNaziv());
		rentACarStari.getAdresa().setLatituda(rentACar.getAdresa().getLatituda());
		rentACarStari.getAdresa().setLongituda(rentACar.getAdresa().getLongituda());
		Adresa adresa = adresaRepository.findOneByPunaAdresa(rentACar.getAdresa().getPunaAdresa());		
		if (adresa != null) {
			
			RentACarServis rs = rentACarRepository.findOneByAdresa(adresa);
			if (rs != null) {
				if(	rs.getId() != rentACarStari.getId()) {
					return "Zauzeta adresa";
				}
			}
			else {
				return "Zauzeta adresa";
			}
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
		Filijala f = filijalaRepository.findOneByAdresa(voz.getFilijala().getAdresa());
		if (f != null) {
			f.setBrojVozila(f.getBrojVozila()-1);
			filijalaRepository.save(f);
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
		Adresa adresa = adresaRepository.findOneByPunaAdresa(vozilo.getFilijala());
		Filijala filijala = null;
		if (adresa != null) {
			filijala = filijalaRepository.findOneByAdresa(adresa);
		}
		if (voz.getFilijala() == null && !vozilo.getFilijala().isEmpty() ) {
			filijala.setBrojVozila(filijala.getBrojVozila()+1);
			voz.setFilijala(filijala);
			filijalaRepository.save(filijala);
		}
		else if (vozilo.getFilijala().isEmpty()) {
			voz.setFilijala(filijala);
		}
		else if (vozilo.getFilijala() != voz.getFilijala().getAdresa().getPunaAdresa()) {
			Filijala fil = filijalaRepository.findOneByAdresa(voz.getFilijala().getAdresa());
			fil.setBrojVozila(fil.getBrojVozila()-1);
			filijala.setBrojVozila(filijala.getBrojVozila()+1);
			voz.setFilijala(filijala);
			filijalaRepository.save(fil);
			filijalaRepository.save(filijala);
			
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
		
		List<Vozilo> vozila = voziloRepository.findAllByFilijala(f);
		if (vozila.size()>0) {
			for (Vozilo vozilo : vozila) {
				vozilo.setFilijala(null);
			}
			voziloRepository.saveAll(vozila);
		}
		filijalaRepository.delete(f);
		return null;
	}
	
	public String izmjeniFilijalu( Long idFilijale, String novLokacija, Adresa novaAdresa) {
		Optional<Filijala> pretragaFilijale = filijalaRepository.findById(idFilijale);
		
		if (!pretragaFilijale.isPresent()) {
			return "Nevalidan id";
		}
		
		Filijala f = pretragaFilijale.get();
		f.getAdresa().setLatituda(novaAdresa.getLatituda());
		f.getAdresa().setLongituda(novaAdresa.getLongituda()); //uvijek dozvoljavamo izmjenu geolokacije
		Adresa a = adresaRepository.findOneByPunaAdresa(novLokacija);
		if (a != null) {
			if(!novLokacija.equals(f.getAdresa().getPunaAdresa())) {
				return "Zauzeta adresa"; //ako nova lokacija vec postoji i ne odgovara adresi date filijale
			}
		}
		f.getAdresa().setPunaAdresa(novLokacija);
		List<Vozilo> vozila = voziloRepository.findAllByFilijala(f);
		if (vozila.size()>0) {
			for (Vozilo vozilo : vozila) {
				vozilo.setFilijala(f);
			}
			voziloRepository.saveAll(vozila);
		}

		filijalaRepository.save(f);
		return null;
	}

	public Collection<Poslovnica> pretraziRac(PretragaRacDTO kriterijumiPretrage) throws NevalidniPodaciException{
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			if (!kriterijumiPretrage.getDatumDolaska().equals("") && kriterijumiPretrage.getDatumDolaska() != null) {
				pocetniDatum = df.parse(kriterijumiPretrage.getDatumDolaska());
			}

			if (!kriterijumiPretrage.getDatumOdlaska().equals("") && kriterijumiPretrage.getDatumOdlaska() != null) {
				krajnjiDatum = df.parse(kriterijumiPretrage.getDatumOdlaska());
			}
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		
		// inicijalno su svi servisi u rezultatu
		Collection<RentACarServis> rezultat = this.rentACarRepository.findAll();
		
		if (kriterijumiPretrage.getNazivRacIliDestinacije().isEmpty() && pocetniDatum == null && krajnjiDatum == null) {
			Collection<Poslovnica> p = new ArrayList<>();
			for (RentACarServis r:rezultat) {
				Poslovnica posl = new Poslovnica(r.getId(),r.getNaziv(), r.getPromotivniOpis(), r.getAdresa());
				posl.setBrojOcjena(r.getBrojOcjena());
				posl.setSumaOcjena(r.getSumaOcjena());
				p.add(posl);
			}
			
			return p;
		}
		
		// odbacujemo servise koji ne zadovoljavaju naziv ili lokaciju
		Iterator<RentACarServis> it = rezultat.iterator();
		RentACarServis tekuciRac;

		while (it.hasNext()) {
			tekuciRac = it.next();
			if (!tekuciRac.getNaziv().toUpperCase()
					.contains(kriterijumiPretrage.getNazivRacIliDestinacije().toUpperCase())
					&& !tekuciRac.getAdresa().getPunaAdresa().toUpperCase()
							.contains(kriterijumiPretrage.getNazivRacIliDestinacije().toUpperCase())) {
				it.remove();
			}
		}
	
		
		// odbacujemo servise koji ne sadrze nijedno vozilo
		List<Vozilo> raspolozivaVozila;
		it = rezultat.iterator();
		boolean ukloniRac;
		
		while (it.hasNext()) {
			tekuciRac = it.next();
			ukloniRac = false;
			raspolozivaVozila = this.slobodnaVozila(tekuciRac, pocetniDatum, krajnjiDatum);

			if (raspolozivaVozila.size()==0) {
				ukloniRac = true;
			}
			else {
				ukloniRac = false;
			}

			if (ukloniRac) {
				it.remove();
			}
		}
		Collection<Poslovnica> p = new ArrayList<>();
		for (RentACarServis r:rezultat) {
			p.add(new Poslovnica(r.getId(),r.getNaziv(), r.getPromotivniOpis(), r.getAdresa()));
		}
		
		return p;
	}

	private List<Vozilo> slobodnaVozila(RentACarServis rac, Date pocetniDatum, Date krajnjiDatum) {
		List<Vozilo> rezultat = new ArrayList<Vozilo>();
		Date trenutniDatum = new Date();
		boolean dodajVozilo = true;
		boolean datumNull = false;
		
		if (pocetniDatum == null || krajnjiDatum == null) {
			datumNull = true;
		}
		
		else if (trenutniDatum.compareTo(pocetniDatum) > 0  || trenutniDatum.compareTo(krajnjiDatum) > 0 || pocetniDatum.compareTo(krajnjiDatum) > 0) {
			return rezultat;
		}
		
		for (Vozilo voz : rac.getVozila()) {
			if (datumNull == true) {
				dodajVozilo = true;
			}
			else {
				for(RezervacijaVozila r : this.rezervacijaVozilaRepository.findAllByRezervisanoVozilo(voz)) {
										
					if (pocetniDatum.compareTo(r.getDatumVracanjaVozila()) < 0 && krajnjiDatum.compareTo(r.getDatumPreuzimanjaVozila()) > 0) {
						dodajVozilo = false;
						break;
					}				
					else {
						dodajVozilo = true;
					}
				
				}
			}
							
			if (dodajVozilo) {
				rezultat.add(voz);
				
			}
			dodajVozilo = true;
		}
		return rezultat;
		
	}
	
	public RentACarServis pronadjiServisPoId(Long idRac) throws NevalidniPodaciException {
		Optional<RentACarServis> pretragaRac = rentACarRepository.findById(idRac);
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		RentACarServis rac = pretragaRac.get();
		return rac;
	}

	public Collection<Vozilo> pretraziVozilaServisa(PretragaVozilaDTO kriterijumiPretrage) throws NevalidniPodaciException{
		Optional<RentACarServis> pretragaRac = rentACarRepository.findById(kriterijumiPretrage.getIdRac());
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		RentACarServis rac = pretragaRac.get();		
		
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			pocetniDatum = df.parse(kriterijumiPretrage.getDatumPreuzimanja());
			krajnjiDatum = df.parse(kriterijumiPretrage.getDatumVracanja());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		
		if (pocetniDatum.compareTo(krajnjiDatum) == 0) {
			throw new NevalidniPodaciException("Između datuma preuzimanja i datuma vraćanja vozila mora biti barem jedan dan razlike.");
		}
		List<Vozilo> slobodnaVozila = slobodnaVozila(rac, pocetniDatum, krajnjiDatum);
		
		List<Vozilo> vozilaZaRezervaciju = new ArrayList<Vozilo>();
		if (slobodnaVozila.isEmpty()) {
			return vozilaZaRezervaciju;
		}
		for (Vozilo vozilo : slobodnaVozila) {
			
			if (vozilo.getFilijala() == null) {
				continue;
			}
			
			if (voziloJeNaBrzojRezervaciji(vozilo, pocetniDatum, krajnjiDatum)){
				continue;
			}
			
			if (vozilo.getTip_vozila().equalsIgnoreCase(kriterijumiPretrage.getTipVozila()) && 
					 vozilo.getBroj_sjedista()>=kriterijumiPretrage.getBrojPutnika() && vozilo.getFilijala().getId() == kriterijumiPretrage.getIdMjestoPreuzimanja()){
				
				
				if (kriterijumiPretrage.getMinimalnaCijenaPoDanu() != null && kriterijumiPretrage.getMaksimalnaCijenaPoDanu()!= null) {
					if (vozilo.getCijena_po_danu() >= kriterijumiPretrage.getMinimalnaCijenaPoDanu() && vozilo.getCijena_po_danu() <= kriterijumiPretrage.getMaksimalnaCijenaPoDanu()) {
						vozilaZaRezervaciju.add(vozilo);
					}
				}
				else if (kriterijumiPretrage.getMinimalnaCijenaPoDanu() != null) {
					if (vozilo.getCijena_po_danu() >= kriterijumiPretrage.getMinimalnaCijenaPoDanu()) {
						vozilaZaRezervaciju.add(vozilo);
					}
				}
				else if (kriterijumiPretrage.getMaksimalnaCijenaPoDanu() != null) {
					if (vozilo.getCijena_po_danu() <= kriterijumiPretrage.getMaksimalnaCijenaPoDanu()) {
						vozilaZaRezervaciju.add(vozilo);
					}
				}
				else {
					vozilaZaRezervaciju.add(vozilo);
				}

				
			}
		}
		return vozilaZaRezervaciju;
	}

	public String rezervisiVozilo(RezervacijaVozilaDTO rezervacijaDTO, Long idServisa) throws NevalidniPodaciException{
		// TODO Auto-generated method stub
		
		RezervacijaVozila rezervacija = new RezervacijaVozila();
		Optional<RentACarServis> pretragaRac = rentACarRepository.findById(idServisa);
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		RentACarServis rac = pretragaRac.get();
		
		Optional<Osoba> pretragaKor = korisnikRepository.findById(rezervacijaDTO.getPutnik());
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		Osoba regKor = pretragaKor.get();
		
		rezervacija.setRentACarServis(rac);
		rezervacija.setPutnik((RegistrovanKorisnik) regKor);
		rezervacija.setCijena(rezervacijaDTO.getCijena());
		rezervacija.setDatumPreuzimanjaVozila(rezervacijaDTO.getDatumPreuzimanjaVozila());
		rezervacija.setDatumVracanjaVozila(rezervacijaDTO.getDatumVracanjaVozila());
		
		Optional<Filijala> adresa1 = filijalaRepository.findById(rezervacijaDTO.getMjestoPreuzimanjaVozila());
		if (!adresa1.isPresent()) {
			throw new NevalidniPodaciException("Nevalidna filijala");
		}
		
		Filijala mjestoPreuzimanja = adresa1.get();
		
		Optional<Filijala> adresa2 = filijalaRepository.findById(rezervacijaDTO.getMjestoVracanjaVozila());
		if (!adresa2.isPresent()) {
			throw new NevalidniPodaciException("Nevalidna filijala");
		}
		
		Filijala mjestoVracanja = adresa2.get();
		
		Optional<Vozilo> rezervisano = voziloRepository.findById(rezervacijaDTO.getRezervisanoVozilo().getId());
		if (!rezervisano.isPresent()) {
			throw new NevalidniPodaciException("Nevalidno vozilo");
		}
		
		Vozilo  vozilo = rezervisano.get();
		
		rezervacija.setMjestoPreuzimanjaVozila(mjestoPreuzimanja);
		rezervacija.setMjestoVracanjaVozila(mjestoVracanja);
		rezervacija.setRezervisanoVozilo(vozilo);
		rezervacija.setPutovanje(rezervacijaDTO.getPutovanje());
		
		rac.getRezervisanaVozila().add(rezervacija);
		
		korisnikRepository.save(regKor);
		rentACarRepository.save(rac);
		rezervacijaVozilaRepository.save(rezervacija);
		
	
		
		return null;
	}
	
	public void validirajAdresu(Adresa adresa) throws NevalidniPodaciException {
		if (adresa == null) {
			throw new NevalidniPodaciException("Adresa mora biti zadata.");
		}
		if (adresa.getPunaAdresa().equals("")) {
			throw new NevalidniPodaciException("Adresa mora biti zadata.");
		}
		Adresa zauzimaAdresu = adresaRepository.findOneByPunaAdresa(adresa.getPunaAdresa());
		if (zauzimaAdresu != null) {
			throw new NevalidniPodaciException("Vec postoji poslovnica na zadatoj adresi");
		}
	}

	public Filijala dodajFilijalu(Adresa adresaFilijale) throws NevalidniPodaciException {
		validirajAdresu(adresaFilijale);
		AdministratorRentACar admin = (AdministratorRentACar) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RentACarServis target = admin.getRentACarServis();
		Filijala novaFilijala = new Filijala(adresaFilijale);
		novaFilijala.setRentACar(target);
		target.getFilijale().add(novaFilijala);
		filijalaRepository.save(novaFilijala);
		rentACarRepository.save(target);
		return novaFilijala;
	}

	public Adresa adresaFilijale(Long idFilijale) throws NevalidniPodaciException {
		Optional<Filijala> filijalaSearch = filijalaRepository.findById(idFilijale);
		if(!filijalaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji filijala sa zadatim id-em.");
		}
		return filijalaSearch.get().getAdresa();
	}

	public Collection<Vozilo> pretraziVozilazaBrzuRezervaciju(PretragaVozilaDTO kriterijumiPretrage) throws NevalidniPodaciException {
Optional<RentACarServis> pretragaRac = rentACarRepository.findById(kriterijumiPretrage.getIdRac());
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		RentACarServis rac = pretragaRac.get();		
		
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			pocetniDatum = df.parse(kriterijumiPretrage.getDatumPreuzimanja());
			krajnjiDatum = df.parse(kriterijumiPretrage.getDatumVracanja());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		
		if (pocetniDatum.compareTo(krajnjiDatum) == 0) {
			throw new NevalidniPodaciException("Između datuma preuzimanja i datuma vraćanja vozila mora biti barem jedan dan razlike.");
		}
		List<Vozilo> slobodnaVozila = slobodnaVozila(rac, pocetniDatum, krajnjiDatum);
		
		
		return slobodnaVozila;
	}

	public BrzaRezervacijaVozilaDTO dodajBrzuRezervaciju(BrzaRezervacijaVozilaDTO novaRezervacija) throws NevalidniPodaciException {
		Optional<Vozilo> voziloSearch = voziloRepository.findById(novaRezervacija.getIdVozila());
		if(!voziloSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadato vozilo.");
		}
		Vozilo target = voziloSearch.get();
		AdministratorRentACar admin = (AdministratorRentACar) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RentACarServis administriraniRac = admin.getRentACarServis();
		if(!target.getRentACar().getId().equals(administriraniRac.getId())) {
			throw new NevalidniPodaciException("Niste ulogovani kao ovlascen administrator za dati rent a car servis");
		}
		Date datumPreuzimanja = null;
		Date datumVracanja = null;
		Date danasnjiDatum = null;
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		try {
			datumPreuzimanja = novaRezervacija.getDatumPreuzimanjaVozila();
			datumVracanja = novaRezervacija.getDatumVracanjaVozila();
			danasnjiDatum = df.parse(df.format(new Date()));
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		if(datumPreuzimanja.before(danasnjiDatum) || datumVracanja.before(danasnjiDatum)) {
			throw new NevalidniPodaciException("Datumi ne smiju biti u proslosti.");
		}
		if(datumVracanja.before(datumPreuzimanja)) {
			throw new NevalidniPodaciException("Datum vraćanja ne moze biti prije datuma preuzimanja");
		}
		if(voziloJeRezervisano(target, datumPreuzimanja, datumVracanja)) {
			throw new NevalidniPodaciException("Dato vozilo je rezervisano u zadatom vremenskom periodu.");
		}
		if(voziloJeNaBrzojRezervaciji(target, datumPreuzimanja, datumVracanja)) {
			throw new NevalidniPodaciException("Dato vozilo se vec nalazi na brzoj rezervaciji u zadatom vremenskom periodu");
		}
		BrzaRezervacijaVozila brv = new BrzaRezervacijaVozila(target,datumPreuzimanja,datumVracanja,0,0);
		long diff = datumVracanja.getTime() - datumPreuzimanja.getTime(); //razlika u milisekundama
		long brojNocenja = diff / (24 * 60 * 60 * 1000);             //razlika u danima
		if(brojNocenja == 0) {
			brojNocenja = 1;
		}
		brv.setCijena(brojNocenja * target.getCijena_po_danu());
		brzaRezervacijaVozilaRepository.save(brv);
		novaRezervacija.setIdBrzeRezervacije(brv.getId());
		novaRezervacija.setBaznaCijena(brv.getCijena());
		return novaRezervacija;
	}
	
	public boolean voziloJeRezervisano(Vozilo vozilo, Date datumP, Date datumV) {
		if(datumP == null || datumV == null) {
			return false;
		}
		for(RezervacijaVozila r : rezervacijaVozilaRepository.findAllByRezervisanoVozilo(vozilo)) {
			if (datumP.compareTo(r.getDatumVracanjaVozila()) < 0 && datumV.compareTo(r.getDatumPreuzimanjaVozila()) > 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean voziloJeNaBrzojRezervaciji(Vozilo vozilo, Date datumP, Date datumV) {
		if(datumP == null || datumV == null) {
			return false;
		}
		for(BrzaRezervacijaVozila r : brzaRezervacijaVozilaRepository.findAllByVozilo(vozilo)) {
			if (datumP.compareTo(r.getDatumVracanjaVozila()) < 0 && datumV.compareTo(r.getDatumPreuzimanjaVozila()) > 0) {
				return true;
			}
		}
		return false;
	}

	public BrzaRezervacijaVozilaDTO zadajPopustBrzeRezervacije(BrzaRezervacijaVozilaDTO brzaRezervacija) throws NevalidniPodaciException {
		Optional<BrzaRezervacijaVozila> rezervacijaSearch = brzaRezervacijaVozilaRepository.findById(brzaRezervacija.getIdBrzeRezervacije());
		if(!rezervacijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji zadata brza rezervacija.");
		}
		BrzaRezervacijaVozila rez = rezervacijaSearch.get();
		AdministratorRentACar admin = (AdministratorRentACar) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RentACarServis administriraniServis = admin.getRentACarServis();
		if(!rez.getVozilo().getRentACar().getId().equals(administriraniServis.getId())) {
			throw new NevalidniPodaciException("Niste ulogovani kao ovlascen administrator za datu rezervaciju.");
		}
		int popust = brzaRezervacija.getProcenatPopusta();
		if(popust < 0) {
			throw new NevalidniPodaciException("Popust ne može biti negativan.");
		}
		if(popust > 100) {
			throw new NevalidniPodaciException("Popust ne može biti veći od 100%.");
		}
		rez.setProcenatPopusta(popust);
		brzaRezervacijaVozilaRepository.save(rez);
		return brzaRezervacija;
	}


	public PrikazBrzeRezVozilaDTO vratiZaPrikaz(BrzaRezervacijaVozilaDTO brzaRezervacija) throws NevalidniPodaciException {
		
		Optional<Vozilo> searchVozilo = voziloRepository.findById(brzaRezervacija.getIdVozila());
		if (!searchVozilo.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji vozilo.");

		}
		Vozilo vozilo = searchVozilo.get();
		
		double punaCijena = brzaRezervacija.getBaznaCijena();
		
		double cijenaSaPopustom = punaCijena - brzaRezervacija.getProcenatPopusta()*punaCijena/100;
		return new PrikazBrzeRezVozilaDTO(brzaRezervacija.getIdBrzeRezervacije(), vozilo.getNaziv(), vozilo.getRentACar().getNaziv(), 
				brzaRezervacija.getDatumPreuzimanjaVozila(), brzaRezervacija.getDatumVracanjaVozila(), punaCijena, cijenaSaPopustom);
	}

	public Collection<PrikazBrzeRezVozilaDTO> vratiBrzeZaPrikaz(Long idServisa) throws NevalidniPodaciException{
		
		RentACarServis rac = pronadjiServisPoId(idServisa);
		
		Collection<Vozilo> vozilaServisa = voziloRepository.findAllByRentACar(rac);
		
		if (vozilaServisa.isEmpty()) {
			return null;
		}
		
		Collection<PrikazBrzeRezVozilaDTO> brzeRezDTO = new ArrayList<>();
		for (Vozilo voz : vozilaServisa) {
			for(BrzaRezervacijaVozila brzaRezervacija : brzaRezervacijaVozilaRepository.findAllByVozilo(voz)) {
				double punaCijena = brzaRezervacija.getCijena();
				
				double cijenaSaPopustom = punaCijena - brzaRezervacija.getProcenatPopusta()*punaCijena/100;
				brzeRezDTO.add(new PrikazBrzeRezVozilaDTO(brzaRezervacija.getId(), voz.getNaziv(), voz.getRentACar().getNaziv(), 
						brzaRezervacija.getDatumPreuzimanjaVozila(), brzaRezervacija.getDatumVracanjaVozila(), punaCijena, cijenaSaPopustom));
			}
		}
		
		
		return brzeRezDTO;
	}

	public Collection<BrzaRezervacijaVozila> pretraziVozilaSaPopustom(PretragaVozilaDTO kriterijumiPretrage) throws NevalidniPodaciException {
		
		Optional<RentACarServis> pretragaRac = rentACarRepository.findById(kriterijumiPretrage.getIdRac());
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		RentACarServis rac = pretragaRac.get();		
		
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
			pocetniDatum = df.parse(kriterijumiPretrage.getDatumPreuzimanja());
			krajnjiDatum = df.parse(kriterijumiPretrage.getDatumVracanja());
		} catch (ParseException e) {
			throw new NevalidniPodaciException("Nevalidan format datuma.");
		}
		
		if (pocetniDatum.compareTo(krajnjiDatum) == 0) {
			throw new NevalidniPodaciException("Između datuma preuzimanja i datuma vraćanja vozila mora biti barem jedan dan razlike.");
		}
		Collection<Vozilo> svaVozila = voziloRepository.findAllByRentACar(rac);
		
		List<BrzaRezervacijaVozila> brzeRezervacije = new ArrayList<BrzaRezervacijaVozila>();
		if (svaVozila.isEmpty()) {
			return brzeRezervacije;
		}
		for (Vozilo vozilo : svaVozila) {
			
			if (vozilo.getFilijala() == null) {
				continue;
			}
			if (voziloJeNaBrzojRezervaciji(vozilo, pocetniDatum, krajnjiDatum) && !voziloJeRezervisano(vozilo, pocetniDatum, krajnjiDatum)) {
				
				Collection<BrzaRezervacijaVozila> sveBrzeRez = vratiBrzeRezervacijePoDatumu(vozilo, pocetniDatum, krajnjiDatum);
				
				
				if (vozilo.getTip_vozila().equalsIgnoreCase(kriterijumiPretrage.getTipVozila()) && 
						 vozilo.getBroj_sjedista()>=kriterijumiPretrage.getBrojPutnika() && vozilo.getFilijala().getId() == kriterijumiPretrage.getIdMjestoPreuzimanja()){
					
					brzeRezervacije.addAll(sveBrzeRez);

					
				}
			}
			
		}
		
		return brzeRezervacije;
	}
	
	public Collection<BrzaRezervacijaVozila> vratiBrzeRezervacijePoDatumu(Vozilo vozilo, Date datumP, Date datumV){
		
		Collection<BrzaRezervacijaVozila> brzeRez = new ArrayList<>();
		
		for(BrzaRezervacijaVozila r : brzaRezervacijaVozilaRepository.findAllByVozilo(vozilo)) {
			if (datumP.compareTo(r.getDatumVracanjaVozila()) < 0 && datumV.compareTo(r.getDatumPreuzimanjaVozila()) > 0) {
				brzeRez.add(r);
			}
		}
		
		return brzeRez;
	}

	public String otkaziRezervaciju(Long id) throws NevalidniPodaciException {
		
		Optional<RezervacijaVozila> pretragaRez = rezervacijaVozilaRepository.findById(id);
		
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rezervacija zadatim id-em");
		}
		
		RezervacijaVozila rez = pretragaRez.get();
		rezervacijaVozilaRepository.delete(rez);
		return "Uspjesno ste otkazali rezervaciju vozila";
	}

	public String izracunajPrihode(DatumiZaPrihodDTO datumiDto, Long idServisa) throws NevalidniPodaciException{
		Date pocetniDatum = null;
		Date krajnjiDatum = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		if (!datumiDto.getDatumPocetni().isEmpty()) {
			try {
				pocetniDatum = df.parse(datumiDto.getDatumPocetni());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		
		if (!datumiDto.getDatumKrajnji().isEmpty()) {
			try {
				krajnjiDatum = df.parse(datumiDto.getDatumKrajnji());
			} catch (ParseException e) {
				throw new NevalidniPodaciException("Nevalidan format datuma.");
			}
		}
		Optional<RentACarServis> pretragaRac = rentACarRepository.findById(idServisa);
		
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji rent-a-car servis sa zadatim id-em");
		}
		
		RentACarServis rac = pretragaRac.get();
		double prihodi = 0;
		
		Collection<RezervacijaVozila> sveRezervacije = rezervacijaVozilaRepository.findAllByRentACarServis(rac);
		
		for (RezervacijaVozila rVozila : sveRezervacije) {
			if (pocetniDatum != null && krajnjiDatum != null) {
				if (pocetniDatum.compareTo(rVozila.getDatumPreuzimanjaVozila()) <= 0 && krajnjiDatum.compareTo(rVozila.getDatumPreuzimanjaVozila()) >= 0) {
					prihodi = prihodi + rVozila.getCijena();
				}
			}
			else if (pocetniDatum != null) {
				if (pocetniDatum.compareTo(rVozila.getDatumPreuzimanjaVozila()) <= 0) {
					prihodi = prihodi + rVozila.getCijena();
				}
			}
			else if ( krajnjiDatum != null) {
				if (krajnjiDatum.compareTo(rVozila.getDatumPreuzimanjaVozila()) >= 0) {
					prihodi = prihodi + rVozila.getCijena();
				}
			}
			else {
				prihodi = prihodi + rVozila.getCijena();
			}
		}
		
		return Double.toString(prihodi);
	}
	
	//ocjenjivanje koristenog vozila i rent-a-car servisa od strane korisnika 
	public String ocjeniVozilo(Long id, int ratingValue) throws NevalidniPodaciException {
		Optional<RezervacijaVozila> pretragaRez = rezervacijaVozilaRepository.findById(id);
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		RezervacijaVozila rVozila = pretragaRez.get();
		
		Optional<RentACarServis> pretragaRac = rentACarRepository.findById(rVozila.getRentACarServis().getId());
		if (!pretragaRac.isPresent()) {
			throw new NevalidniPodaciException("U medjuvremenu je obrisan rent-a-car servis cije su usluge koristene.");
		}
		
		RentACarServis rac = pretragaRac.get();
		
		Optional<Vozilo> pretragaVozilo = voziloRepository.findById(rVozila.getRezervisanoVozilo().getId());
		if (!pretragaVozilo.isPresent()) {
			throw new NevalidniPodaciException("U medjuvremenu je uklonjeno vozilo cije su usluge koristene.");
		}
		
		Vozilo vozilo = pretragaVozilo.get();
		
		Date danasnjiDatum = new Date();
		if (rVozila.getDatumVracanjaVozila().after(danasnjiDatum)) {
			return "Ne mozete da ocjenite vozilo prije njegovog vracanja.";
		}
		
		vozilo.setSumaOcjena(vozilo.getSumaOcjena() + ratingValue);
		vozilo.setBrojOcjena(vozilo.getBrojOcjena() + 1);
		rVozila.setOcjenjeno(true);
		
		rac.setSumaOcjena(rac.getSumaOcjena() + ratingValue);
		rac.setBrojOcjena(rac.getBrojOcjena() + 1);
		
		rentACarRepository.save(rac);
		voziloRepository.save(vozilo);
		rezervacijaVozilaRepository.save(rVozila);
		
		return null;
	}

	public Boolean rezervacijaVozilaOcjenjena(Long idRezervacije) throws NevalidniPodaciException{
		Optional<RezervacijaVozila> pretragaRez = rezervacijaVozilaRepository.findById(idRezervacije);
		if (!pretragaRez.isPresent()) {
			throw new NevalidniPodaciException("Doslo je do greske.");
		}
		
		RezervacijaVozila rVozila = pretragaRez.get();
		
		if (rVozila.isOcjenjeno()) {
			return true;
		}
		else {
			return false;
		}
	}
}
