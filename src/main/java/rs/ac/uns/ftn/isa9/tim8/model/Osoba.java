package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "osobe")
public abstract class Osoba {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Column(name = "korisnicko_ime", nullable = false, unique = true)
	protected String korisnickoIme;
	@Column(name = "loznika", nullable = false)
	protected String lozinka;
	@Column(name = "ime", nullable = false)
	protected String ime;
	@Column(name = "prezime", nullable = false)
	protected String prezime;
	@Column(name = "email", nullable = false)
	protected String email;
	@Column(name = "broj_telefona", nullable = true)
	protected String brojTelefona;
	@Column(name = "putanja_slike", nullable = true)
	protected String putanjaSlike;
	@Column(name = "sistem_admin", nullable = false)
	protected boolean sistemAdmin = false;
	
	public Osoba() {
		super();
	}

	public Osoba(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			String brojTelefona, String putanjaSlike, boolean sistemAdmin) {
		super();
		this.id = id;
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.brojTelefona = brojTelefona;
		this.putanjaSlike = putanjaSlike;
		this.sistemAdmin = sistemAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getPutanjaSlike() {
		return putanjaSlike;
	}

	public void setPutanjaSlike(String putanjaSlike) {
		this.putanjaSlike = putanjaSlike;
	}

	public boolean isSistemAdmin() {
		return sistemAdmin;
	}

	public void setSistemAdmin(boolean sistemAdmin) {
		this.sistemAdmin = sistemAdmin;
	}
	
	
	
	
}
