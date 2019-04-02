package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rentACarServis")
public class RentACarServis extends Servis {
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rentACar")
	protected Set<Vozilo> vozila;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rentACar")
	protected Set<Filijala> filijale;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rentACar")
	protected Set<RezervacijaVozila> rezervisanaVozila;
	
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
	
}
