package rs.ac.uns.ftn.isa9.tim8.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Klasa koja cuva potrebne informacije o adresi radi
 * njenog prikaza na mapi. */

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
	
	@Column(name = "latituda", nullable = true)
	protected Double latituda;
	
	@Column(name = "longituda", nullable = true)
	protected Double longituda;
	
	public Adresa() {
		super();
	}
	
	public Adresa(String punaAdresa) {
		super();
		this.punaAdresa = punaAdresa;
	}

	public Adresa(String punaAdresa, Double latituda, Double longituda) {
		super();
		this.punaAdresa = punaAdresa;
		this.latituda = latituda;
		this.longituda = longituda;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPunaAdresa() {
		return punaAdresa;
	}

	public void setPunaAdresa(String punaAdresa) {
		this.punaAdresa = punaAdresa;
	}

	public Double getLatituda() {
		return latituda;
	}

	public void setLatituda(Double latituda) {
		this.latituda = latituda;
	}

	public Double getLongituda() {
		return longituda;
	}

	public void setLongituda(Double longituda) {
		this.longituda = longituda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
