package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
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
	protected long id;
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	protected RezervacijaSjedista rezervacijaSjedista;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "putovanje")
	protected Set<Pozivnica> pozivnice;
	
//	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	protected RezervacijaSobe rezervacijaSoba;
	
	@OneToMany(mappedBy = "putovanje", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaVozila> rezervacijeVozila;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "inicijator_putovanja_id", referencedColumnName = "id")
	protected RegistrovanKorisnik inicijatorPutovanja;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="putovanje_usluga", joinColumns=@JoinColumn(name="putovanje_id"), inverseJoinColumns=@JoinColumn(name="usluga_id"))
	protected Set<Usluga> dodatne_usluge;

	public Putovanje() {
		super();
	}

//	public RezervacijaSjedista getRezervacijaSjedista() {
//		return rezervacijaSjedista;
//	}
//
//	public void setRezervacijaSjedista(RezervacijaSjedista rezervacijaSjedista) {
//		this.rezervacijaSjedista = rezervacijaSjedista;
//	}
//
	public Set<Pozivnica> getPozivnice() {
		return pozivnice;
	}

	public void setPozivnice(Set<Pozivnica> pozivnice) {
		this.pozivnice = pozivnice;
	}
//
//	public RezervacijaSobe getRezervacijaSoba() {
//		return rezervacijaSoba;
//	}
//
//	public void setRezervacijaSoba(RezervacijaSobe rezervacijaSoba) {
//		this.rezervacijaSoba = rezervacijaSoba;
//	}
//
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

	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	public Set<Usluga> getDodatne_usluge() {
		return dodatne_usluge;
	}

	public void setDodatne_usluge(Set<Usluga> dodatne_usluge) {
		this.dodatne_usluge = dodatne_usluge;
	}

	
}
