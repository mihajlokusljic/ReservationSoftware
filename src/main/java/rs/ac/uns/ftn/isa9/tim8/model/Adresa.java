package rs.ac.uns.ftn.isa9.tim8.model;

/**
 * Klasa koja cuva potrebne informacije o adresi radi
 * njenog prikaza na mapi.
 * Napomena: u tekucoj verziji aplikacije cuva samo punu adresu kao String.*/
public class Adresa {
	String punaAdresa;

	public Adresa() {
		super();
	}

	public Adresa(String punaAdresa) {
		super();
		this.punaAdresa = punaAdresa;
	}

	public String getPunaAdresa() {
		return punaAdresa;
	}

	public void setPunaAdresa(String punaAdresa) {
		this.punaAdresa = punaAdresa;
	}
	
}
