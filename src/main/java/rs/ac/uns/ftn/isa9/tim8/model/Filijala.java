package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class Filijala {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Adresa adresa;
	
	//@ManyToOne
	protected RentACarServis rentACar;

	public Filijala() {
		super();
	}

	public Filijala(Adresa adresa) {
		super();
		this.adresa = adresa;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
	

}
