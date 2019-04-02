package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rezervacija_vozila")
public class RezervacijaVozila extends Rezervacija{
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Vozilo rezervisanoVozilo;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Filijala mjestoPreuzimanjaVozila;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Filijala mjestoVracanjaVozila;
	@ManyToOne
	protected RentACarServis rentACar;
	
	public RezervacijaVozila() {
		super();
	}

	public Vozilo getRezervisanoVozilo() {
		return rezervisanoVozilo;
	}

	public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
		this.rezervisanoVozilo = rezervisanoVozilo;
	}

	public Filijala getMjestoPreuzimanjaVozila() {
		return mjestoPreuzimanjaVozila;
	}

	public void setMjestoPreuzimanjaVozila(Filijala mjestoPreuzimanjaVozila) {
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
	}

	public Filijala getMjestoVracanjaVozila() {
		return mjestoVracanjaVozila;
	}

	public void setMjestoVracanjaVozila(Filijala mjestoVracanjaVozila) {
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
	}
	
	
}
