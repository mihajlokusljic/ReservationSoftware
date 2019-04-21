package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "brza_rezervacija_sjedista")
public class BrzaRezervacijaSjedista {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "izvodjenje_leta_id")
	protected IzvodjenjeLeta izvodjenjeLeta;
		
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sjediste_id", referencedColumnName = "id")
	protected Sjediste sjediste;
	
	@Column(name = "cijena", nullable = false)
	protected double cijena;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;
	
	public BrzaRezervacijaSjedista() {
		super();
	}
	public BrzaRezervacijaSjedista(IzvodjenjeLeta izvodjenjeLeta,Sjediste sjedista, double cijena) {
		super();
		this.izvodjenjeLeta = izvodjenjeLeta;
		this.sjediste = sjedista;
		this.cijena = cijena;
	}
	public IzvodjenjeLeta getIzvodjenjeLeta() {
		return izvodjenjeLeta;
	}
	public void setIzvodjenjeLeta(IzvodjenjeLeta izvodjenjeLeta) {
		this.izvodjenjeLeta = izvodjenjeLeta;
	}
	
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		this.Id = id;
	}
	public Sjediste getSjediste() {
		return sjediste;
	}
	public void setSjediste(Sjediste sjediste) {
		this.sjediste = sjediste;
	}
	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}
	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}
	public double getCijena() {
		return cijena;
	}
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	
}
