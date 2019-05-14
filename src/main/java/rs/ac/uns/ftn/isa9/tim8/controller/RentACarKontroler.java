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
import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorRentACar;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
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
		System.out.println("Filijala id " + vozilo.getFilijala().getId());
		return new ResponseEntity<String>(servis.dodajVozilo(nazivServisa, vozilo),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/svaVozilaServisa/{nazivServisa}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
 	public ResponseEntity<?> svaVozila(@PathVariable("nazivServisa") String nazivServisa){
		System.out.println(nazivServisa);
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
	
	@RequestMapping(value = "/dobaviFilijale/{nazivServisa}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<?> dobaviFilijale(@PathVariable("nazivServisa") String nazivServisa) {
		return new ResponseEntity<Collection<FilijalaDTO>>(servis.vratiFilijale(nazivServisa),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ukloniFilijalu/{idFilijale}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> ukloniFilijalu(@PathVariable("idFilijale") String idFilijale) {
		return new ResponseEntity<String>(servis.ukloniFilijalu(Long.parseLong(idFilijale)),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/izmjeniFilijalu/{idFilijale}/{novaLokacija}", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<String> izmjeniFilijalu(@PathVariable("idFilijale") String idFilijale, @PathVariable("novaLokacija") String novaLokacija) {
		return new ResponseEntity<String>(servis.izmjeniFilijalu(Long.parseLong(idFilijale), novaLokacija),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/podaciOServisu", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('AdministratorRentACar')")
	public ResponseEntity<Poslovnica> podaciOServisu() {
		AdministratorRentACar admin = (AdministratorRentACar) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return new ResponseEntity<Poslovnica>(
				new Poslovnica(admin.getRentACarServis().getNaziv(), admin.getRentACarServis().getPromotivniOpis(), admin.getRentACarServis().getAdresa()),
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
}
