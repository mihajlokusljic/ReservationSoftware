package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class Putovanje {
	protected RezervacijaSjedista rezervacijaSjedista;
	protected Set<Pozivnica> pozivnice;
	protected RezervacijaSoba rezervacijaSoba;
	protected RezervacijaVozila rezervacijaVozila;
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
