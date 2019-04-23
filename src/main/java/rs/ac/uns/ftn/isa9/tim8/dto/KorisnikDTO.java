package rs.ac.uns.ftn.isa9.tim8.dto;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;

public class KorisnikDTO {
	
	protected String ime;
	
	protected String prezime;
	
	protected String email;
	
	protected String lozinka;

	protected String brojTelefona;
	
	protected Adresa adresa;
	
	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(String ime, String prezime, String email, String lozinka, String brojTelefona, Adresa adresa) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.brojTelefona = brojTelefona;
		this.adresa = adresa;
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

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
}
