package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "Segment")
public class Segment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Column(name = "naziv")
	protected String naziv;

	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena;

	public Segment() {
		super();
		this.naziv = "";
		this.cijena = 0;
	}

	public Segment(Long id, String naziv, double cijena) {
		super();
		Id = id;
		this.naziv = naziv;
		this.cijena = cijena;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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
