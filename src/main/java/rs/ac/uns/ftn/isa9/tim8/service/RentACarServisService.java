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

import rs.ac.uns.ftn.isa9.tim8.dto.FilijalaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PotrebnoSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaRacDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.RezervacijaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorRentACar;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
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
				p.add(new Poslovnica(r.getNaziv(), r.getPromotivniOpis(), r.getAdresa()));
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
			p.add(new Poslovnica(r.getNaziv(), r.getPromotivniOpis(), r.getAdresa()));
		}
		
		return p;
	}

	private List<Vozilo> slobodnaVozila(RentACarServis rac, Date pocetniDatum, Date krajnjiDatum) {
		List<Vozilo> rezultat = new ArrayList<Vozilo>();
		Date trenutniDatum = new Date();
		boolean dodajVozilo = true;
		
		
		for (Vozilo voz : rac.getVozila()) {
			
			if (pocetniDatum == null || krajnjiDatum == null) {
				rezultat.add(voz);
				continue;
			}	
			
			else if (trenutniDatum.compareTo(pocetniDatum) > 0  || trenutniDatum.compareTo(krajnjiDatum) > 0 || pocetniDatum.compareTo(krajnjiDatum) > 0) {
				dodajVozilo = false;
				break;
			}
			
			for(RezervacijaVozila r : this.rezervacijaVozilaRepository.findAllByRezervisanoVozilo(voz)) {
				
				if (pocetniDatum.compareTo(r.getDatumVracanjaVozila()) < 0 && krajnjiDatum.compareTo(r.getDatumPreuzimanjaVozila()) > 0) {
					dodajVozilo = false;
					break;
				}				
				else {
					dodajVozilo = true;
				}
			}
			
			if (dodajVozilo) {
				rezultat.add(voz);
			}
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
	
}
