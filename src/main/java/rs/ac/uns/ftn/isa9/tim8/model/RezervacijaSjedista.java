package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RezervacijaSjedista")
public class RezervacijaSjedista extends Rezervacija {

	@Column(name = "imePutnika", unique = false, nullable = false)
	protected String imePutnika;
	
	@Column(name = "prezimePutnika", unique = false, nullable = false)
	protected String prezimePutnika;
	
	@Column(name = "brojPasosaPutnika", unique = true, nullable = false)
	protected String brojPasosaPutnika;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Sjediste sjediste;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RegistrovanKorisnik putnik;

	public RezervacijaSjedista() {
		super();
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

}
