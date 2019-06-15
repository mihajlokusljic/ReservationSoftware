package rs.ac.uns.ftn.isa9.tim8.model;

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

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "rezervacija_sjedista")
public class RezervacijaSjedista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Column(name = "ime_putnika")
	protected String imePutnika;

	@Column(name = "prezime_putnika")
	protected String prezimePutnika;

	@Column(name = "broj_pasosa_putnika")
	protected String brojPasosaPutnika;

	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sjediste_id", referencedColumnName = "id")
	protected Sjediste sjediste;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "putnik_id")
	protected RegistrovanKorisnik putnik; // opciono, ne mora se rezervisati za registrovanog korisnika

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "let_id")
	protected Let let;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "putovanje_id")
	@JsonIgnore
	protected Putovanje putovanje;
	
	@Column(name = "ocjenjeno")
    protected boolean ocjenjeno;
	
	public RezervacijaSjedista() {
		super();
		this.ocjenjeno = false;
	}

	public RezervacijaSjedista(Long id, String imePutnika, String prezimePutnika, String brojPasosaPutnika,
			double cijena, Sjediste sjediste, RegistrovanKorisnik putnik, Aviokompanija aviokompanija, Let let,
			Putovanje putovanje) {
		super();
		Id = id;
		this.imePutnika = imePutnika;
		this.prezimePutnika = prezimePutnika;
		this.brojPasosaPutnika = brojPasosaPutnika;
		this.cijena = cijena;
		this.sjediste = sjediste;
		this.putnik = putnik;
		this.aviokompanija = aviokompanija;
		this.let = let;
		this.putovanje = putovanje;
		this.ocjenjeno = false;
	}
	
	public RezervacijaSjedista(Long id, String imePutnika, String prezimePutnika, String brojPasosaPutnika,
			double cijena, Sjediste sjediste, RegistrovanKorisnik putnik, Aviokompanija aviokompanija, Let let,
			Putovanje putovanje, boolean ocjenjeno) {
		super();
		Id = id;
		this.imePutnika = imePutnika;
		this.prezimePutnika = prezimePutnika;
		this.brojPasosaPutnika = brojPasosaPutnika;
		this.cijena = cijena;
		this.sjediste = sjediste;
		this.putnik = putnik;
		this.aviokompanija = aviokompanija;
		this.let = let;
		this.putovanje = putovanje;
		this.ocjenjeno = ocjenjeno;
	}

	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
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

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Putovanje getPutovanje() {
		return putovanje;
	}

	public void setPutovanje(Putovanje putovanje) {
		this.putovanje = putovanje;
	}

	public boolean isOcjenjeno() {
		return ocjenjeno;
	}

	public void setOcjenjeno(boolean ocjenjeno) {
		this.ocjenjeno = ocjenjeno;
	}	

}
