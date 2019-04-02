package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Aviokompanija")
public class Aviokompanija extends Poslovnica {
	@OneToMany(mappedBy = "aviokompanija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Destinacija> destinacije;

	@OneToMany(mappedBy = "aviokompanija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Let> letovi;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaSjedista> brzeRezervacije;
	
	@Column(name = "cijenaPrtljagaKomad", unique = false, nullable = false)
	protected double cijenaPrtljagaKomad;

	@OneToMany(mappedBy = "aviokompanija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Avion> avioni;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
