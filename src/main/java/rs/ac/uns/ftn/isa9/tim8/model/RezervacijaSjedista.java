package rs.ac.uns.ftn.isa9.tim8.model;

public class RezervacijaSjedista extends Rezervacija {
	protected String imePutnika;
	protected String prezimePutnika;
	protected String brojPasosaPutnika;
	protected Sjediste sjediste;
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
