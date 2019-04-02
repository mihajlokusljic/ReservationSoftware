package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

public abstract class Rezervacija {
	protected Date pocetniDatumVazenja;
	protected Date krajnjiDatumVazenja;
	protected double cijena;
	protected boolean rezervisano = false;
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
