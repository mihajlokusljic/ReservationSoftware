package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class RentACarServis extends Servis {
	
	protected Set<Vozilo> vozila;
	protected Set<Filijala> filijale;
	protected Set<RezervacijaVozila> rezervisanaVozila;
	protected Set<AdministratorRentACar> administratoriSistema;
	
	public RentACarServis() {
		super();
	}

	public Set<Vozilo> getVozila() {
		return vozila;
	}

	public void setVozila(Set<Vozilo> vozila) {
		this.vozila = vozila;
	}

	public Set<Filijala> getFilijale() {
		return filijale;
	}

	public void setFilijale(Set<Filijala> filijale) {
		this.filijale = filijale;
	}

	public Set<RezervacijaVozila> getRezervisanaVozila() {
		return rezervisanaVozila;
	}

	public void setRezervisanaVozila(Set<RezervacijaVozila> rezervisanaVozila) {
		this.rezervisanaVozila = rezervisanaVozila;
	}

	public Set<AdministratorRentACar> getAdministratoriSistema() {
		return administratoriSistema;
	}

	public void setAdministratoriSistema(Set<AdministratorRentACar> administratoriSistema) {
		this.administratoriSistema = administratoriSistema;
	}
	
	
	
}
