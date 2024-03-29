package rs.ac.uns.ftn.isa9.tim8.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "hotelska_soba")
public class HotelskaSoba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "broj_sobe", nullable = false)
	protected int brojSobe;

	@Column(name = "broj_kreveta", nullable = false)
	protected int brojKreveta;

	@Column(name = "sprat", nullable = false)
	protected int sprat;

	@Column(name = "red", nullable = false)
	protected int vrsta;

	@Column(name = "kolona", nullable = false)
	protected int kolona;

	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena = 0;

	// @ManyToMany(fetch = FetchType.LAZY)
	// protected Set<RezervacijaSobe> rezervacije;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hotel_id")
	protected Hotel hotel;

	@Column(name = "suma_ocjena", nullable = true)
	protected int sumaOcjena;

	@Column(name = "broj_ocjena", nullable = true)
	protected int brojOcjena;

	public HotelskaSoba() {
		super();
	}

	public HotelskaSoba(int brojSobe, int brojKreveta, int sprat, int vrsta, int kolona, double cijena, Hotel hotel) {
		super();
		this.brojSobe = brojSobe;
		this.brojKreveta = brojKreveta;
		this.sprat = sprat;
		this.vrsta = vrsta;
		this.kolona = kolona;
		this.cijena = cijena;
		this.hotel = hotel;
		this.sumaOcjena = 0;
		this.brojOcjena = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj == this) {
			return true;
		}
		if(obj instanceof HotelskaSoba) {
			HotelskaSoba other = (HotelskaSoba) obj;
			return this.Id.equals(other.Id);
		}
		return false;
	}

	public int getBrojSobe() {
		return brojSobe;
	}

	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public int getSprat() {
		return sprat;
	}

	public void setSprat(int sprat) {
		this.sprat = sprat;
	}

	public int getVrsta() {
		return vrsta;
	}

	public void setVrsta(int vrsta) {
		this.vrsta = vrsta;
	}

	public int getKolona() {
		return kolona;
	}

	public void setKolona(int kolona) {
		this.kolona = kolona;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	/*
	 * public Set<RezervacijaSobe> getRezervacije() { return rezervacije; }
	 * 
	 * public void setRezervacije(Set<RezervacijaSobe> rezervacije) {
	 * this.rezervacije = rezervacije; }
	 */

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}



}
