package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface RentACarServisi {
	
	public boolean dodajRentACarServis(RentACarServis noviServis);
	public boolean obrisiRentACarServis(RentACarServis servisZaBrisanje);
	public RentACarServis izmjeniRentACarServis(RentACarServis noviPodaci);
	public Collection<RentACarServis> dobaviRentACarServise();
	public RentACarServis pronadjiRentACarServis(String nazivServisa);
	public boolean dodajVozilo(String nazivServisa, Vozilo vozilo);
	public Collection<Vozilo> prikaziVozilaServisa(String nazivServisa);
}
