package rs.ac.uns.ftn.isa9.tim8.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "filijala")
public class Filijala implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7431243822504518706L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "adresa_id", referencedColumnName = "id")
	protected Adresa adresa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rent_a_car_servis_id")
	protected RentACarServis rentACar;
	
	@Column(name = "brojVozila")
	protected int brojVozila;

	public Filijala() {
		super();
		this.brojVozila = 0;
	}

	public Filijala(Adresa adresa) {
		super();
		this.adresa = adresa;
		this.brojVozila = 0;
	}
	
	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public RentACarServis getRentACar() {
		return rentACar;
	}

	public void setRentACar(RentACarServis rentACar) {
		this.rentACar = rentACar;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getBrojVozila() {
		return brojVozila;
	}

	public void setBrojVozila(int brojVozila) {
		this.brojVozila = brojVozila;
	}
	
	

}
