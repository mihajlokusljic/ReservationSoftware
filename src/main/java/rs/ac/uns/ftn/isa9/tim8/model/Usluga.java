package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class Usluga {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	//@Column(unique = true, nullable = false)
	protected String naziv;
	
	//@Column(nullable = false)
	protected double cijena;
	
	//@ManyToOne
	protected Servis servis;
	
	public Usluga() {
		super();
	}
	
	public Usluga(String naziv, double cijena) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
		
	
}
