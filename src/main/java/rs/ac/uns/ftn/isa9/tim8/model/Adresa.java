package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Klasa koja cuva potrebne informacije o adresi radi
 * njenog prikaza na mapi.
 * Napomena: u tekucoj verziji aplikacije cuva samo punu adresu kao String.*/
@Entity
@Table(name = "adresa")
public class Adresa {
	@Column(name = "puna_adresa", nullable = false, unique = true)
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
