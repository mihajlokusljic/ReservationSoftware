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
import javax.persistence.Table;

@Entity
@Table(name = "brza_rezervacija_sjedista")
public class BrzaRezervacijaSjedista {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "izvodjenje_leta_id")
	protected IzvodjenjeLeta izvodjenjeLeta;
		
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="brza_rezervacija_sjediste", joinColumns=@JoinColumn(name="brza_rezervacija_id"), inverseJoinColumns=@JoinColumn(name="sjediste_id"))	
	protected Set<Sjediste> sjedista;
	
	@Column(name = "cijena", nullable = false)
	protected double cijena;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;
	
	public BrzaRezervacijaSjedista() {
		super();
	}
	public BrzaRezervacijaSjedista(IzvodjenjeLeta izvodjenjeLeta, Set<Sjediste> sjedista, double cijena) {
		super();
		this.izvodjenjeLeta = izvodjenjeLeta;
		this.sjedista = sjedista;
		this.cijena = cijena;
	}
	public IzvodjenjeLeta getIzvodjenjeLeta() {
		return izvodjenjeLeta;
	}
	public void setIzvodjenjeLeta(IzvodjenjeLeta izvodjenjeLeta) {
		this.izvodjenjeLeta = izvodjenjeLeta;
	}
	public Set<Sjediste> getSjedista() {
		return sjedista;
	}
	public void setSjedista(Set<Sjediste> sjedista) {
		this.sjedista = sjedista;
	}
	public double getCijena() {
		return cijena;
	}
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	
}
