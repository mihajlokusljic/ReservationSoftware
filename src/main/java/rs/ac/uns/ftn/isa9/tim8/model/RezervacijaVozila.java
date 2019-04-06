package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "rezervacija_vozila")
public class RezervacijaVozila {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "rezervisano_vozilo_id", referencedColumnName = "id")
	protected Vozilo rezervisanoVozilo;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "mjesto_preuzimanja_vozila", referencedColumnName = "id")
	protected Filijala mjestoPreuzimanjaVozila;
	
	@Column(name = "datum_preuzimanja_vozila", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumPreuzimanjaVozila;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "mjesto_vracanja_vozila", referencedColumnName = "id")
	protected Filijala mjestoVracanjaVozila;
	
	@Column(name = "datum_vracanja_vozila", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumVracanjaVozila;
	
	@Column(name = "cijena", nullable = false)
	protected double cijena;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rent_a_car_servis_id")
	protected RentACarServis rentACarServis;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="putnik_id")
	protected RegistrovanKorisnik putnik; //opciono, ne mora se rezervisati za registrovanog korisnika
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "putovanje_id")	
	protected Putovanje putovanje;

	public RezervacijaVozila() {
		super();
	}

	public RezervacijaVozila(Vozilo rezervisanoVozilo, Filijala mjestoPreuzimanjaVozila, Date datumPreuzimanjaVozila,
			Filijala mjestoVracanjaVozila, Date datumVracanjaVozila, double cijena) {
		super();
		this.rezervisanoVozilo = rezervisanoVozilo;
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
		this.datumVracanjaVozila = datumVracanjaVozila;
		this.cijena = cijena;
	}

	public Vozilo getRezervisanoVozilo() {
		return rezervisanoVozilo;
	}

	public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
		this.rezervisanoVozilo = rezervisanoVozilo;
	}

	public Filijala getMjestoPreuzimanjaVozila() {
		return mjestoPreuzimanjaVozila;
	}

	public void setMjestoPreuzimanjaVozila(Filijala mjestoPreuzimanjaVozila) {
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
	}

	public Date getDatumPreuzimanjaVozila() {
		return datumPreuzimanjaVozila;
	}

	public void setDatumPreuzimanjaVozila(Date datumPreuzimanjaVozila) {
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
	}

	public Filijala getMjestoVracanjaVozila() {
		return mjestoVracanjaVozila;
	}

	public void setMjestoVracanjaVozila(Filijala mjestoVracanjaVozila) {
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
	}

	public Date getDatumVracanjaVozila() {
		return datumVracanjaVozila;
	}

	public void setDatumVracanjaVozila(Date datumVracanjaVozila) {
		this.datumVracanjaVozila = datumVracanjaVozila;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RentACarServis getRentACarServis() {
		return rentACarServis;
	}

	public void setRentACarServis(RentACarServis rentACarServis) {
		this.rentACarServis = rentACarServis;
	}

	public RegistrovanKorisnik getPutnik() {
		return putnik;
	}

	public void setPutnik(RegistrovanKorisnik putnik) {
		this.putnik = putnik;
	}

	public Putovanje getPutovanje() {
		return putovanje;
	}

	public void setPutovanje(Putovanje putovanje) {
		this.putovanje = putovanje;
	}

	
}
