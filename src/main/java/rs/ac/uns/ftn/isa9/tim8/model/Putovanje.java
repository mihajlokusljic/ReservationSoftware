package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class Putovanje {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RezervacijaSjedista rezervacijaSjedista;
	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "putovanje")
	protected Set<Pozivnica> pozivnice;
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RezervacijaSoba rezervacijaSoba;
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RezervacijaVozila rezervacijaVozila;
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RegistrovanKorisnik inicijatorPutovanja;

	public Putovanje() {
		super();
	}

	public RezervacijaSjedista getRezervacijaSjedista() {
		return rezervacijaSjedista;
	}

	public void setRezervacijaSjedista(RezervacijaSjedista rezervacijaSjedista) {
		this.rezervacijaSjedista = rezervacijaSjedista;
	}

	public Set<Pozivnica> getPozivnice() {
		return pozivnice;
	}

	public void setPozivnice(Set<Pozivnica> pozivnice) {
		this.pozivnice = pozivnice;
	}

	public RezervacijaSoba getRezervacijaSoba() {
		return rezervacijaSoba;
	}

	public void setRezervacijaSoba(RezervacijaSoba rezervacijaSoba) {
		this.rezervacijaSoba = rezervacijaSoba;
	}

	public RezervacijaVozila getRezervacijaVozila() {
		return rezervacijaVozila;
	}

	public void setRezervacijaVozila(RezervacijaVozila rezervacijaVozila) {
		this.rezervacijaVozila = rezervacijaVozila;
	}

	public RegistrovanKorisnik getInicijatorPutovanja() {
		return inicijatorPutovanja;
	}

	public void setInicijatorPutovanja(RegistrovanKorisnik inicijatorPutovanja) {
		this.inicijatorPutovanja = inicijatorPutovanja;
	}

}
