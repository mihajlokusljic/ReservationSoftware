package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "usluga")
public class Usluga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "naziv", nullable = false)
	protected String naziv;
	
	@Column(name = "cijena", nullable = false)
	protected double cijena;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "poslovnica_id")
	protected Poslovnica poslovnica;
	
	public Usluga() {
		super();
	}
	
	public Usluga(String naziv, double cijena) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
	}
	
	public Usluga(String naziv, double cijena, Poslovnica poslovnica) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
		this.poslovnica = poslovnica;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	
}
