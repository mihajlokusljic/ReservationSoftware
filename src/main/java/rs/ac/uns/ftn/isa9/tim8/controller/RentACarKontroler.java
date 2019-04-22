package rs.ac.uns.ftn.isa9.tim8.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.dto.VoziloDTO;
import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.service.RentACarServisService;

@RestController
@RequestMapping(value = "/rentACar")
public class RentACarKontroler {
	
	@Autowired
	protected RentACarServisService servis;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "Hello from RAC controller.";
	}
	
	@RequestMapping(value = "/sviServisi", method = RequestMethod.GET)
	public ResponseEntity<Collection<RentACarServis>> racServisi() {
		return new ResponseEntity<Collection<RentACarServis>>(servis.dobaviRentACarServise(),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajServis", method = RequestMethod.POST)
	public ResponseEntity<String> dodajRacServis(@RequestBody RentACarServis noviRacServis) {
		return new ResponseEntity<String>(servis.dodajRentACarServis(noviRacServis),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajVozilo/{imeRacServisa}", method = RequestMethod.POST)
	public ResponseEntity<String> dodajNovoVozilo(@PathVariable("imeRacServisa") String nazivServisa,@RequestBody Vozilo vozilo) {
		return new ResponseEntity<String>(servis.dodajVozilo(nazivServisa, vozilo),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/svaVozilaServisa", method = RequestMethod.POST)
	public ResponseEntity<?> svaVozila(@RequestParam("nazivServisa") String nazivServisa){
		if (servis.servisPostoji(nazivServisa) == false) {
			return new ResponseEntity<String>("Servis koji ste unijeli ne postoji.",HttpStatus.OK);
		}
		return new ResponseEntity<Collection<VoziloDTO>>(servis.vratiVozilaOsnovno(servis.vratiVozilaServisa(nazivServisa)),HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/prikazServisa", method = RequestMethod.POST)
	public ResponseEntity<Poslovnica> vratiServis(@RequestParam("nazivServisa") String nazivServisa) {
		
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
	public ResponseEntity<String> izmjeniServis(@RequestBody RentACarServis rServis) {
		return new ResponseEntity<String>(servis.izmjeniRentACarServis(rServis),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/ukloniVozilo", method = RequestMethod.POST)
	public ResponseEntity<String> ukloniVozilo(@RequestParam("idVozila") String idVozila) {
		Long idVoz = Long.parseLong(idVozila);
		return new ResponseEntity<String>(servis.ukloniVozilo(idVoz),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/izmjeniVozilo/{voziloId}", method = RequestMethod.POST)
	public ResponseEntity<String> izmjeniVozilo(@PathVariable("voziloId") String voziloId, @RequestBody VoziloDTO vozilo) {
		vozilo.setId(Long.parseLong(voziloId));
		return new ResponseEntity<String>(servis.izmjeniVozilo(vozilo),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dodajFilijalu", method = RequestMethod.POST)
	public ResponseEntity<String> dodajFilijalu(@RequestParam("nazivServisa") String nazivServisa, @RequestParam("adresa") String adresa) {
		return new ResponseEntity<String>(servis.dodajFilijalu(nazivServisa,adresa),HttpStatus.OK);
	}
	
	@RequestMapping(value = "/dobaviFilijale", method = RequestMethod.GET)
	public ResponseEntity<Collection<Filijala>> dobaviFilijale(@RequestParam("nazivServisa") String nazivServisa) {
		return new ResponseEntity<Collection<Filijala>>(servis.vratiFilijale(nazivServisa),HttpStatus.OK);
	}
}
