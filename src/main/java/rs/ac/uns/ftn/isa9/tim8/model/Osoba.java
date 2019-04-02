package rs.ac.uns.ftn.isa9.tim8.model;

public abstract class Osoba {
	protected String korisnickoIme;
	protected String lozinka;
	protected String ime;
	protected String prezime;
	protected String email;
	protected String brojTelefona;
	protected String putanjaSlike;
	protected boolean sistemAdmin = false;
	
	public Osoba() {
		super();
	}

	public Osoba(String korisnickoIme, String lozinka, String ime, String prezime, String email, String brojTelefona,
			String putanjaSlike, boolean sistemAdmin) {
		super();
		this.korisnickoIme = korisnickoIme;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.brojTelefona = brojTelefona;
		this.putanjaSlike = putanjaSlike;
		this.sistemAdmin = sistemAdmin;
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
