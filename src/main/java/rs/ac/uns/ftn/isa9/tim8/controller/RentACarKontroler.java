package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.FilijalaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.KorisnikDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaRacDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaSobaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.PretragaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.RezervacijaVozilaDTO;
import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorRentACar;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.service.KorisnikService;
import rs.ac.uns.ftn.isa9.tim8.service.NevalidniPodaciException;
import rs.ac.uns.ftn.isa9.tim8.service.RentACarServisService;

@RestController
@RequestMapping(value = "/rentACar")
public class RentACarKontroler {
	
	@Autowired
	protected RentACarServisService servis;
	
	@Autowired
	protected KorisnikService servisKorisnik;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "Hello from RAC controller.";
	}
	
	@RequestMapping(value = "/sviServisi", method = RequestMethod.GET)
	public ResponseEntity<Collection<Poslovnica>> racServisi() {
		return new ResponseEntity<Collection<Poslovnica>>(servis.dobaviRentACarServise(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajServis", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorSistema')")
	public ResponseEntity<?> dodajRacServis(@RequestBody RentACarServis noviRacServis) {
		try {
			return new ResponseEntity<RentACarServis>(servis.dodajRentACarServis(noviRacServis),HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/dodajVozilo/{imeRacServisa}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> dodajNovoVozilo(@PathVariable("imeRacServisa") String nazivServisa,@RequestBody Vozilo vozilo) {
		return new ResponseEntity<String>(servis.dodajVozilo(nazivServisa, vozilo),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/svaVozilaServisa/{nazivServisa}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
 	public ResponseEntity<?> svaVozila(@PathVariable("nazivServisa") String nazivServisa){
		if (servis.servisPostoji(nazivServisa) == false) {
			return new ResponseEntity<String>("Servis koji ste unijeli ne postoji.",HttpStatus.OK);
		}
		return new ResponseEntity<Collection<VoziloDTO>>(servis.vratiVozilaOsnovno(servis.vratiVozilaServisa(nazivServisa)),HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/prikazServisa/{nazivServisa}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<Poslovnica> vratiServis(@PathVariable("nazivServisa") String nazivServisa) {
		
		RentACarServis racS = servis.pronadjiRentACarServis(nazivServisa);
		if (racS != null) {
			Poslovnica rentACarOsnovno = new Poslovnica(racS.getNaziv(),racS.getPromotivniOpis(),racS.getAdresa());
			
			return new ResponseEntity<Poslovnica>(rentACarOsnovno,HttpStatus.OK);
		}
		else {
			return null;
		}
		
	}
	
	@RequestMapping(value = "/izmjeniProfil", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> izmjeniServis(@RequestBody RentACarServis rServis) {
		return new ResponseEntity<String>(servis.izmjeniRentACarServis(rServis),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ukloniVozilo/{idVozila}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> ukloniVozilo(@PathVariable("idVozila") String idVozila) {
		Long idVoz = Long.parseLong(idVozila);
		return new ResponseEntity<String>(servis.ukloniVozilo(idVoz),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/izmjeniVozilo/{voziloId}", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> izmjeniVozilo(@PathVariable("voziloId") String voziloId, @RequestBody VoziloDTO vozilo) {
		vozilo.setId(Long.parseLong(voziloId));
		return new ResponseEntity<String>(servis.izmjeniVozilo(vozilo),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajFilijalu/{nazivServisa}/{adresa}", method = RequestMethod.GET)
	public ResponseEntity<String> dodajFilijalu(@PathVariable("nazivServisa") String nazivServisa, @PathVariable("adresa") String adresa) {
		return new ResponseEntity<String>(servis.dodajFilijalu(nazivServisa,adresa),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajFilijalu", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<?> dodajFilijalu(@RequestBody Adresa adresaFilijale) {
		try {
			return new ResponseEntity<Filijala>(servis.dodajFilijalu(adresaFilijale), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/dobaviFilijale/{nazivServisa}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<?> dobaviFilijale(@PathVariable("nazivServisa") String nazivServisa) {
		return new ResponseEntity<Collection<FilijalaDTO>>(servis.vratiFilijale(nazivServisa),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/adresaFilijale/{idFilijale}", method = RequestMethod.GET)
	public ResponseEntity<?> adresaFilijale(@PathVariable("idFilijale") Long idFilijale) {
		try {
			return new ResponseEntity<Adresa>(servis.adresaFilijale(idFilijale), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/ukloniFilijalu/{idFilijale}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> ukloniFilijalu(@PathVariable("idFilijale") String idFilijale) {
		return new ResponseEntity<String>(servis.ukloniFilijalu(Long.parseLong(idFilijale)),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/izmjeniFilijalu/{idFilijale}/{novaLokacija}", method = RequestMethod.PUT)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> izmjeniFilijalu(@PathVariable("idFilijale") String idFilijale, 
			@PathVariable("novaLokacija") String novaLokacija, @RequestBody Adresa novaAdresa) {
		return new ResponseEntity<String>(servis.izmjeniFilijalu(Long.parseLong(idFilijale), novaLokacija, novaAdresa),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/podaciOServisu", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<Poslovnica> podaciOServisu() {
		AdministratorRentACar admin = (AdministratorRentACar) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<Poslovnica>(
				new Poslovnica(admin.getRentACarServis().getId(),admin.getRentACarServis().getNaziv(), admin.getRentACarServis().getPromotivniOpis(), admin.getRentACarServis().getAdresa()),
				HttpStatus.OK);
	}
	@RequestMapping(value = "/izmjeniProfilKorisnika", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<?> izmjeniKorisnika(@RequestBody KorisnikDTO korisnik) {
		return new ResponseEntity<KorisnikDTO>(servisKorisnik.izmjeniAdminaRentACar(korisnik), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pretrazi", method = RequestMethod.POST)
	public ResponseEntity<?> pretragaRac(@RequestBody PretragaRacDTO kriterijumiPretrage) {	
		Collection<Poslovnica> servisi = null;
		try {
			servisi = servis.pretraziRac(kriterijumiPretrage);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		
		return new ResponseEntity<Collection<Poslovnica> >(servisi, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/dobavi/{idRac}", method = RequestMethod.GET)
	public ResponseEntity<?> dobaviRac(@PathVariable("idRac") Long idRac) {
		try {
			return new ResponseEntity<RentACarServis>(servis.pronadjiServisPoId(idRac), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/pretraziVozila", method = RequestMethod.POST)
	public ResponseEntity<?> pretraziVozilaServisa(@RequestBody PretragaVozilaDTO kriterijumiPretrage) {
		try {
			return new ResponseEntity<Collection<Vozilo> >(this.servis.pretraziVozilaServisa(kriterijumiPretrage), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/rezervisiVozilo/{idServisa}", method = RequestMethod.POST)
	public ResponseEntity<?> rezervacijaVozila(@PathVariable("idServisa") String idServisa,@RequestBody RezervacijaVozilaDTO rezervacija) {
		try {
			return new ResponseEntity<String>(this.servis.rezervisiVozilo(rezervacija,Long.parseLong(idServisa)), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/pretraziZaBrzuRezervaciju", method = RequestMethod.POST)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<?> pretraziVozilaZaBrzuRezervaciju(@RequestBody PretragaVozilaDTO kriterijumiPretrage) {
		try {
			return new ResponseEntity<Collection<Vozilo> >(this.servis.pretraziVozilazaBrzuRezervaciju(kriterijumiPretrage), HttpStatus.OK);
		} catch (NevalidniPodaciException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
