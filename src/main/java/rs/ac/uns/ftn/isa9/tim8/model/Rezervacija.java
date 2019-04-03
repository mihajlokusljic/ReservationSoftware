package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

public abstract class Rezervacija {
	//@Id
	//@GeneratedValue
	protected Long id;
	//@Column(name = "pocetni_datum_vazenja", nullable = false)
	protected Date pocetniDatumVazenja;
	//@Column(name = "krajnji_datum_vazenja", nullable = false)
	protected Date krajnjiDatumVazenja;
	//@Column(name = "cijena", nullable = false)
	protected double cijena;
	//@Column(name = "rezervisano", nullable = false)
	protected boolean rezervisano = false;
	//@Column(name = "procenat_popusta", nullable = true)
	protected int procenatPopusta;
	
	public Rezervacija() {
		super();
	}

	public Rezervacija(Date pocetniDatumVazenja, Date krajnjiDatumVazenja, double cijena, boolean rezervisano,
			int procenatPopusta) {
		super();
		this.pocetniDatumVazenja = pocetniDatumVazenja;
		this.krajnjiDatumVazenja = krajnjiDatumVazenja;
		this.cijena = cijena;
		this.rezervisano = rezervisano;
		this.procenatPopusta = procenatPopusta;
	}

	public Date getPocetniDatumVazenja() {
		return pocetniDatumVazenja;
	}

	public void setPocetniDatumVazenja(Date pocetniDatumVazenja) {
		this.pocetniDatumVazenja = pocetniDatumVazenja;
	}

	public Date getKrajnjiDatumVazenja() {
		return krajnjiDatumVazenja;
	}

	public void setKrajnjiDatumVazenja(Date krajnjiDatumVazenja) {
		this.krajnjiDatumVazenja = krajnjiDatumVazenja;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public boolean isRezervisano() {
		return rezervisano;
	}

	public void setRezervisano(boolean rezervisano) {
		this.rezervisano = rezervisano;
	}

	public int getprocenatPopusta() {
		return procenatPopusta;
	}

	public void setprocenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}
	
	
}
