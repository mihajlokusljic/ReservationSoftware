package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class RezervacijaSobe {
	
	protected Date datumDolaska;
	protected Date datumOdlaska;
	protected double cijena;
	protected HotelskaSoba rezervisanaSoba;
	
	public RezervacijaSobe() {
		super();
	}
	public RezervacijaSobe(Date datumDolaska, Date datumOdlaska, double cijena, HotelskaSoba rezervisanaSoba) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.cijena = cijena;
		this.rezervisanaSoba = rezervisanaSoba;
	}
	public Date getDatumDolaska() {
		return datumDolaska;
	}
	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	public Date getDatumOdlaska() {
		return datumOdlaska;
	}
	public void setDatumOdlaska(Date datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}
	public double getCijena() {
		return cijena;
	}
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	public HotelskaSoba getRezervisanaSoba() {
		return rezervisanaSoba;
	}
	public void setRezervisanaSoba(HotelskaSoba rezervisanaSoba) {
		this.rezervisanaSoba = rezervisanaSoba;
	}
	
}
