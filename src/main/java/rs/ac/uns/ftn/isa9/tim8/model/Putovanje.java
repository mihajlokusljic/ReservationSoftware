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
@Table(name = "putovanje")
public class Putovanje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long Id;
	
	@OneToMany(mappedBy = "putovanje", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaSjedista> rezervacijeSjedista;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "putovanje")
	protected Set<Pozivnica> pozivnice;
	
	@OneToMany(mappedBy = "putovanje", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaSobe> rezervacijeSoba;
	
	@OneToMany(mappedBy = "putovanje", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaVozila> rezervacijeVozila;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "inicijator_putovanja_id", referencedColumnName = "id")
	protected RegistrovanKorisnik inicijatorPutovanja;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="putovanje_usluga", joinColumns=@JoinColumn(name="putovanje_id"), inverseJoinColumns=@JoinColumn(name="usluga_id"))
	protected Set<Usluga> dodatneUsluge;

	@Column(name = "bonus_poeni", nullable = true)
	protected double bonusPoeni;

	
	public Putovanje() {
		super();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Set<RezervacijaSjedista> getRezervacijeSjedista() {
		return rezervacijeSjedista;
	}

	public void setRezervacijeSjedista(Set<RezervacijaSjedista> rezervacijeSjedista) {
		this.rezervacijeSjedista = rezervacijeSjedista;
	}

	public Set<Pozivnica> getPozivnice() {
		return pozivnice;
	}

	public void setPozivnice(Set<Pozivnica> pozivnice) {
		this.pozivnice = pozivnice;
	}

	public Set<RezervacijaSobe> getRezervacijeSoba() {
		return rezervacijeSoba;
	}

	public void setRezervacijeSoba(Set<RezervacijaSobe> rezervacijeSoba) {
		this.rezervacijeSoba = rezervacijeSoba;
	}

	public Set<RezervacijaVozila> getRezervacijeVozila() {
		return rezervacijeVozila;
	}

	public void setRezervacijeVozila(Set<RezervacijaVozila> rezervacijeVozila) {
		this.rezervacijeVozila = rezervacijeVozila;
	}

	public RegistrovanKorisnik getInicijatorPutovanja() {
		return inicijatorPutovanja;
	}

	public void setInicijatorPutovanja(RegistrovanKorisnik inicijatorPutovanja) {
		this.inicijatorPutovanja = inicijatorPutovanja;
	}

	public Set<Usluga> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(Set<Usluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}

}
