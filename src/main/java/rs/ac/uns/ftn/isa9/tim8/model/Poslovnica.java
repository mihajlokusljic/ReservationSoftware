package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Poslovnica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long Id;
	
	@Column(name = "naziv", nullable = false)
	protected String naziv;
	
	@Column(name = "promotivni_opis", nullable = true)
	protected String promotivniOpis;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "adresa_id", unique = true, referencedColumnName = "id")
	protected Adresa adresa;
	
	@Column(name = "suma_ocjena", nullable = true)
	protected int sumaOcjena;
	
	@Column(name = "broj_ocjena", nullable = true)
	protected int brojOcjena;
	
	@OneToMany(mappedBy = "poslovnica",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}
	
	
}
