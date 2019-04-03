package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

public class HotelskaSoba {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int brojSobe;
	
	//@Column(name = "broj_kreveta", nullable = false)
	protected int brojKreveta;
	
	//@Column(name = "sprat", nullable = false)
	protected int sprat;
	
	//@Column(name = "red", nullable = false)
	protected int vrsta;
	
	//@Column(name = "kolona", nullable = false)
	protected int kolona;
	
	
	//@Column(name = "cijena", nullable = false)
	protected double cijena;
	
	//@ManyToMany(fetch = FetchType.LAZY)
	protected Set<RezervacijaSobe> rezervacije;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	protected Hotel hotel;
	
	protected int sumaOcjena;
	
	protected int brojOcjena;
	
	public HotelskaSoba() {
		super();
	}

	public HotelskaSoba(int brojSobe, int brojKreveta, int sprat, int vrsta, int kolona, double cijena,
			Set<RezervacijaSobe> rezervacije, Hotel hotel) {
		super();
		this.brojSobe = brojSobe;
		this.brojKreveta = brojKreveta;
		this.sprat = sprat;
		this.vrsta = vrsta;
		this.kolona = kolona;
		this.cijena = cijena;
		this.rezervacije = rezervacije;
		this.hotel = hotel;
		this.sumaOcjena = 0;
		this.brojOcjena = 0;
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

	public Set<RezervacijaSobe> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(Set<RezervacijaSobe> rezervacije) {
		this.rezervacije = rezervacije;
	}

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
	
}
