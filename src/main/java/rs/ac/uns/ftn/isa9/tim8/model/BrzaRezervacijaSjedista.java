package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

	@Column(name = "procenat_popusta", nullable = false)
	protected int procenatPopusta;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;

	@Column(name = "datum_polaska", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date datumPolaska;

	@Column(name = "datum_dolaska", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	protected Date datumDolaska;

	public BrzaRezervacijaSjedista() {
		super();
	}

	public BrzaRezervacijaSjedista(Sjediste sjediste, Date datumPolaska, Date datumDolaska, double cijena,
			int procenatPopusta) {
		super();
		this.sjediste = sjediste;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
		this.cijena = cijena;
		this.procenatPopusta = procenatPopusta;
	}

	public BrzaRezervacijaSjedista(Long id, Let let, Sjediste sjediste, double cijena, int procenatPopusta,
			Aviokompanija aviokompanija, Date datumPolaska, Date datumDolaska) {
		super();
		Id = id;
		this.let = let;
		this.sjediste = sjediste;
		this.cijena = cijena;
		this.procenatPopusta = procenatPopusta;
		this.aviokompanija = aviokompanija;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
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

	public int getProcenatPopusta() {
		return procenatPopusta;
	}

	public void setProcenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}

	public Date getDatumPolaska() {
		return datumPolaska;
	}

	public void setDatumPolaska(Date datumPolaska) {
		this.datumPolaska = datumPolaska;
	}

	public Date getDatumDolaska() {
		return datumDolaska;
	}

	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

}
