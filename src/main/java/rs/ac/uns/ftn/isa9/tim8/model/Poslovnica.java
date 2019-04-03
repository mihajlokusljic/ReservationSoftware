package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

public abstract class Poslovnica extends Ocjenjivo {
	
	//@Column(unique = true, nullable = false)
	protected String naziv;
	
	//@Column(name = "promotivni_opis")
	protected String promotivniOpis;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Adresa adresa;
	
	public Poslovnica() {
		super();
	}

	public Poslovnica(String naziv, String promotivniOpis, Adresa adresa) {
		super();
		this.naziv = naziv;
		this.promotivniOpis = promotivniOpis;
		this.adresa = adresa;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPromotivniOpis() {
		return promotivniOpis;
	}

	public void setPromotivniOpis(String promotivniOpis) {
		this.promotivniOpis = promotivniOpis;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
	
}
