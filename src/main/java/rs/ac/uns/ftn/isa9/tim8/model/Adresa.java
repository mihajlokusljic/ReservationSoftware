package rs.ac.uns.ftn.isa9.tim8.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Klasa koja cuva potrebne informacije o adresi radi
 * njenog prikaza na mapi.
 * Napomena: u tekucoj verziji aplikacije cuva samo punu adresu kao String.*/
@Entity
@Table(name = "adresa")
public class Adresa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7232709856068011030L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "puna_adresa", nullable = false)
	protected String punaAdresa;
	
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) { 
			return false; 
		}
		if (obj == this) {
			return true;
		}
		if (obj instanceof Adresa) {
			Adresa other = (Adresa) obj;
			return this.getPunaAdresa().equals(other.getPunaAdresa());
		} else {
			return false;
		}
	}
	
	
}
