package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "aviokompanija")
public class Aviokompanija extends Poslovnica {
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="aviokompanija_destinacija", joinColumns=@JoinColumn(name="aviokompanija_id"), inverseJoinColumns=@JoinColumn(name="destinacija_id"))
	protected Set<Destinacija> destinacije;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="aviokompanija_let", joinColumns=@JoinColumn(name="aviokompanija_id"), inverseJoinColumns=@JoinColumn(name="let_id"))
	protected Set<Let> letovi;

	@OneToMany(mappedBy = "aviokompanija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<BrzaRezervacijaSjedista> brzeRezervacije;
	
	@Column(name = "cijena_prtljaga_komad", nullable = false)
	protected double cijenaPrtljagaKomad;
	
	@JsonIgnore
	@OneToMany(mappedBy = "aviokompanija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Avion> avioni;

	@OneToMany(mappedBy = "aviokompanija", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaSjedista> rezervacije;
	

	public Aviokompanija() {
		super();
	}

	public Aviokompanija(Set<Destinacija> destinacije, Set<Let> letovi, Set<BrzaRezervacijaSjedista> brzeRezervacije,
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

	public Set<BrzaRezervacijaSjedista> getBrzeRezervacije() {
		return brzeRezervacije;
	}

	public void setBrzeRezervacije(Set<BrzaRezervacijaSjedista> brzeRezervacije) {
		this.brzeRezervacije = brzeRezervacije;
	}

	public Set<RezervacijaSjedista> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(Set<RezervacijaSjedista> rezervacije) {
		this.rezervacije = rezervacije;
	}

}
