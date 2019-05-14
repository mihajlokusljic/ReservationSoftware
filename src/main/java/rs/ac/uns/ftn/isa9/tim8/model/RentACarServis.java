package rs.ac.uns.ftn.isa9.tim8.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "rent_a_car_servis")
public class RentACarServis extends Poslovnica implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5971300871523033595L;

//	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rentACar")
	protected Set<Vozilo> vozila;
	
//  @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "rentACar")	
	protected Set<Filijala> filijale;
	
//	@JsonIgnore
	@OneToMany(mappedBy = "rentACarServis",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	protected Set<RezervacijaVozila> rezervisanaVozila;
	
	public RentACarServis() {
		super();
	}

	public RentACarServis(String naziv, String promotivniOpis, Adresa adresa) {
		super(naziv, promotivniOpis, adresa, 0, 0, new HashSet<Usluga>());
		this.vozila = new HashSet<Vozilo>();
		this.filijale = new HashSet<Filijala>();
		this.rezervisanaVozila = new HashSet<RezervacijaVozila>();
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
