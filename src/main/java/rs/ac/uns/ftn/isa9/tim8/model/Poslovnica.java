package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

public abstract class Poslovnica {
	
	//@Column(unique = true, nullable = false)
	protected String naziv;
	
	//@Column(name = "promotivni_opis")
	protected String promotivniOpis;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Adresa adresa;
	
	protected int sumaOcjena;
	
	protected int brojOcjena;
	
	protected Set<Usluga> cjenovnikDodatnihUsluga;
	
	public Poslovnica() {
		super();
	}

	public Poslovnica(String naziv, String promotivniOpis, Adresa adresa) {
		super();
		this.naziv = naziv;
		this.promotivniOpis = promotivniOpis;
		this.adresa = adresa;
		this.sumaOcjena = 0;
		this.brojOcjena = 0;
	}
	
	public Poslovnica(String naziv, String promotivniOpis, Adresa adresa, int sumaOcjena, int brojOcjena,
			Set<Usluga> cjenovnikDodatnihUsluga) {
		super();
		this.naziv = naziv;
		this.promotivniOpis = promotivniOpis;
		this.adresa = adresa;
		this.sumaOcjena = sumaOcjena;
		this.brojOcjena = brojOcjena;
		this.cjenovnikDodatnihUsluga = cjenovnikDodatnihUsluga;
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

	public int getSumaOcjena() {
		return sumaOcjena;
	}

	public void setSumaOcjena(int sumaOcjena) {
		this.sumaOcjena = sumaOcjena;
	}

	public int getBrojOcjena() {
		return brojOcjena;
	}

	public void setBrojOcjena(int brojOcjena) {
		this.brojOcjena = brojOcjena;
	}

	public Set<Usluga> getCjenovnikDodatnihUsluga() {
		return cjenovnikDodatnihUsluga;
	}

	public void setCjenovnikDodatnihUsluga(Set<Usluga> cjenovnikDodatnihUsluga) {
		this.cjenovnikDodatnihUsluga = cjenovnikDodatnihUsluga;
	}
	
}
