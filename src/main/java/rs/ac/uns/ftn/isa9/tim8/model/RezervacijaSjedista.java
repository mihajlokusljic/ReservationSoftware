package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class RezervacijaSjedista {

	//@Column(name = "imePutnika", unique = false, nullable = false)
	protected String imePutnika;
	
	//@Column(name = "prezimePutnika", unique = false, nullable = false)
	protected String prezimePutnika;
	
	//@Column(name = "brojPasosaPutnika", unique = true, nullable = false)
	protected String brojPasosaPutnika;
	
	protected double cijena;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Sjediste sjediste;
	
	protected IzvodjenjeLeta izvodjenjeLeta;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RegistrovanKorisnik putnik; //opciono, ne mora se rezervisati za registrovanog korisnika

	public RezervacijaSjedista() {
		super();
	}
	
	public RezervacijaSjedista(String imePutnika, String prezimePutnika, String brojPasosaPutnika, double cijena,
			Sjediste sjediste, IzvodjenjeLeta izvodjenjeLeta, RegistrovanKorisnik putnik) {
		super();
		this.imePutnika = imePutnika;
		this.prezimePutnika = prezimePutnika;
		this.brojPasosaPutnika = brojPasosaPutnika;
		this.cijena = cijena;
		this.sjediste = sjediste;
		this.izvodjenjeLeta = izvodjenjeLeta;
		this.putnik = putnik;
	}

	public String getImePutnika() {
		return imePutnika;
	}

	public void setImePutnika(String imePutnika) {
		this.imePutnika = imePutnika;
	}

	public String getPrezimePutnika() {
		return prezimePutnika;
	}

	public void setPrezimePutnika(String prezimePutnika) {
		this.prezimePutnika = prezimePutnika;
	}

	public String getBrojPasosaPutnika() {
		return brojPasosaPutnika;
	}

	public void setBrojPasosaPutnika(String brojPasosaPutnika) {
		this.brojPasosaPutnika = brojPasosaPutnika;
	}

	public Sjediste getSjediste() {
		return sjediste;
	}

	public void setSjediste(Sjediste sjediste) {
		this.sjediste = sjediste;
	}

	public RegistrovanKorisnik getPutnik() {
		return putnik;
	}

	public void setPutnik(RegistrovanKorisnik putnik) {
		this.putnik = putnik;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public IzvodjenjeLeta getIzvodjenjeLeta() {
		return izvodjenjeLeta;
	}

	public void setIzvodjenjeLeta(IzvodjenjeLeta izvodjenjeLeta) {
		this.izvodjenjeLeta = izvodjenjeLeta;
	}

}
