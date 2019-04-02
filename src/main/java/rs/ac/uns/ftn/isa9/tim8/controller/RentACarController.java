package rs.ac.uns.ftn.isa9.tim8.controller;


import java.util.Collection;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;
import rs.ac.uns.ftn.isa9.tim8.repository.MemorijskoSkladiste;

@RestController
@RequestMapping(value = "/rentACar")
public class RentACarController {
	
	@Autowired
	protected MemorijskoSkladiste bpMenadzer;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String ping() {
		return "Hello from RAC controller.";
	}
	
	@RequestMapping(value = "/sviServisi", method = RequestMethod.GET)
	public Collection<RentACarServis> racServisi() {
		return bpMenadzer.dobaviRentACarServise();
	}
	
	@RequestMapping(value = "/dodajServis", method = RequestMethod.POST)
	public boolean dodajRacServis(@RequestBody RentACarServis noviRacServis) {
		return bpMenadzer.dodajRentACarServis(noviRacServis);
	}
	
	@RequestMapping(value = "/dodajVozilo/{imeRacServisa}", method = RequestMethod.POST)
	public boolean dodajNovoVozilo(@PathVariable("imeRacServisa") String naziv_servisa,@RequestBody Vozilo vozilo) {
		return bpMenadzer.dodajVozilo(naziv_servisa, vozilo);
	}
	
	@RequestMapping(value = "/svaVozilaServisa", method = RequestMethod.POST)
	public Collection<Vozilo> svaVozila(@RequestParam("nazivServisa") String naziv_servisa){
		return bpMenadzer.prikaziVozilaServisa(naziv_servisa);
	}
	
	@RequestMapping(value = "/prikazServisa", method = RequestMethod.POST)
	public RentACarServis vratiServis(@RequestParam("nazivServisa") String naziv_servisa) {
		return bpMenadzer.pronadjiRentACarServis(naziv_servisa);
	}
	
	@RequestMapping(value = "/izmjeniProfil", method = RequestMethod.POST)
	public RentACarServis izmjeniServis(@RequestBody RentACarServis servis) {
		return bpMenadzer.izmjeniRentACarServis(servis);
	}

}
