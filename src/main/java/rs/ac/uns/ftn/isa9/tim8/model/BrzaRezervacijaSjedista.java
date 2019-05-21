package rs.ac.uns.ftn.isa9.tim8.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "brza_rezervacija_sjedista")
public class BrzaRezervacijaSjedista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "let_id")
	protected Let let;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sjediste_id", referencedColumnName = "id")
	protected Sjediste sjediste;

	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;

	public BrzaRezervacijaSjedista() {
		super();
	}

	public BrzaRezervacijaSjedista(Long id, Let let, Sjediste sjediste, double cijena, Aviokompanija aviokompanija) {
		super();
		Id = id;
		this.let = let;
		this.sjediste = sjediste;
		this.cijena = cijena;
		this.aviokompanija = aviokompanija;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Sjediste getSjediste() {
		return sjediste;
	}

	public void setSjediste(Sjediste sjediste) {
		this.sjediste = sjediste;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}

}
