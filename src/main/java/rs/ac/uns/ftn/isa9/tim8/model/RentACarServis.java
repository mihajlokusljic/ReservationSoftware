package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rent_a_car_servis")
public class RentACarServis extends Poslovnica {
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rentACar")
	protected Set<Vozilo> vozila;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rentACar")
	protected Set<Filijala> filijale;
	
	@OneToMany(mappedBy = "rentACarServis",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected Set<RezervacijaVozila> rezervisanaVozila;
	
	@OneToMany(mappedBy = "rentACarServis", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<AdministratorRentACar> admini;
	
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

	public Set<AdministratorRentACar> getAdmini() {
		return admini;
	}

	public void setAdmini(Set<AdministratorRentACar> admini) {
		this.admini = admini;
	}
	
	
}
