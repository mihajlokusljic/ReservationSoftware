package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rezervacija_sjedista")
public class RezervacijaSjedista {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "ime_putnika", nullable = false)
	protected String imePutnika;
	
	@Column(name = "prezime_putnika", nullable = false)
	protected String prezimePutnika;
	
	@Column(name = "broj_pasosa_putnika", nullable = false)
	protected String brojPasosaPutnika;
	
	@Column(name = "cijena", nullable = false)
	protected double cijena;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "sjediste_id", referencedColumnName = "id")
	protected Sjediste sjediste;
	
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="putnik_id")
	protected RegistrovanKorisnik putnik; //opciono, ne mora se rezervisati za registrovanog korisnika
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "let_id")
	protected Let let;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "izvodjenje_leta_id")
	protected IzvodjenjeLeta izvodjenjeLeta;

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
		//this.izvodjenjeLeta = izvodjenjeLeta;
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

	/*public IzvodjenjeLeta getIzvodjenjeLeta() {
		return izvodjenjeLeta;
	}

	public void setIzvodjenjeLeta(IzvodjenjeLeta izvodjenjeLeta) {
		this.izvodjenjeLeta = izvodjenjeLeta;
	}*/

}
