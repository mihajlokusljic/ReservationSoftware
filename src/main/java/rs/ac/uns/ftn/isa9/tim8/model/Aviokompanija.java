package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class Aviokompanija extends Poslovnica {
	protected Set<Destinacija> destinacije;
	protected Set<Let> letovi;
	protected Set<RezervacijaSjedista> brzeRezervacije;
	protected double cijenaPrtljagaKomad;
	protected Set<Avion> avioni;
	protected Set<RezervacijaSjedista> rezervacije;

	public Aviokompanija() {
		super();
	}

	public Aviokompanija(Set<Destinacija> destinacije, Set<Let> letovi, Set<RezervacijaSjedista> brzeRezervacije,
			double cijenaPrtljagaKomad, Set<Avion> avioni, Set<RezervacijaSjedista> rezervacije) {
		super();
		this.destinacije = destinacije;
		this.letovi = letovi;
		this.brzeRezervacije = brzeRezervacije;
		this.cijenaPrtljagaKomad = cijenaPrtljagaKomad;
		this.avioni = avioni;
		this.rezervacije = rezervacije;
	}

	public Set<Destinacija> getDestinacije() {
		return destinacije;
	}

	public void setDestinacije(Set<Destinacija> destinacije) {
		this.destinacije = destinacije;
	}

	public Set<Let> getLetovi() {
		return letovi;
	}

	public void setLetovi(Set<Let> letovi) {
		this.letovi = letovi;
	}

	public Set<RezervacijaSjedista> getBrzeRezervacije() {
		return brzeRezervacije;
	}

	public void setBrzeRezervacije(Set<RezervacijaSjedista> brzeRezervacije) {
		this.brzeRezervacije = brzeRezervacije;
	}

	public double getCijenaPrtljagaKomad() {
		return cijenaPrtljagaKomad;
	}

	public void setCijenaPrtljagaKomad(double cijenaPrtljagaKomad) {
		this.cijenaPrtljagaKomad = cijenaPrtljagaKomad;
	}

	public Set<Avion> getAvioni() {
		return avioni;
	}

	public void setAvioni(Set<Avion> avioni) {
		this.avioni = avioni;
	}

	public Set<RezervacijaSjedista> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(Set<RezervacijaSjedista> rezervacije) {
		this.rezervacije = rezervacije;
	}

}
