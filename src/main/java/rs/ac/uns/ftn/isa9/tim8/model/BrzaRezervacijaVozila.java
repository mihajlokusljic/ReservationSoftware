package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

public class BrzaRezervacijaVozila {
	
	protected Set<Vozilo> vozila;
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Filijala mjestoPreuzimanjaVozila;
	
	protected Date datumPreuzimanjaVozila;
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Filijala mjestoVracanjaVozila;
	
	protected Date datumVracanjaVozila;
	
	protected double cijena;

	public BrzaRezervacijaVozila() {
		super();
	}

	public BrzaRezervacijaVozila(Set<Vozilo> vozila, Filijala mjestoPreuzimanjaVozila, Date datumPreuzimanjaVozila,
			Filijala mjestoVracanjaVozila, Date datumVracanjaVozila, double cijena) {
		super();
		this.vozila = vozila;
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
		this.datumVracanjaVozila = datumVracanjaVozila;
		this.cijena = cijena;
	}

	public Set<Vozilo> getVozila() {
		return vozila;
	}

	public void setVozila(Set<Vozilo> vozila) {
		this.vozila = vozila;
	}

	public Filijala getMjestoPreuzimanjaVozila() {
		return mjestoPreuzimanjaVozila;
	}

	public void setMjestoPreuzimanjaVozila(Filijala mjestoPreuzimanjaVozila) {
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
	}

	public Date getDatumPreuzimanjaVozila() {
		return datumPreuzimanjaVozila;
	}

	public void setDatumPreuzimanjaVozila(Date datumPreuzimanjaVozila) {
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
	}

	public Filijala getMjestoVracanjaVozila() {
		return mjestoVracanjaVozila;
	}

	public void setMjestoVracanjaVozila(Filijala mjestoVracanjaVozila) {
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
	}

	public Date getDatumVracanjaVozila() {
		return datumVracanjaVozila;
	}

	public void setDatumVracanjaVozila(Date datumVracanjaVozila) {
		this.datumVracanjaVozila = datumVracanjaVozila;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	
}
