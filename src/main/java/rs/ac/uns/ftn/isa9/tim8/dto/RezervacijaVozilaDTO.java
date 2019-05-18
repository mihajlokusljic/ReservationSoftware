package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public class RezervacijaVozilaDTO {
	
	protected Vozilo rezervisanoVozilo;

	protected Long mjestoPreuzimanjaVozila;
	
	protected Date datumPreuzimanjaVozila;
	
	protected Long mjestoVracanjaVozila;
	
	protected Date datumVracanjaVozila;
	
	protected double cijena;
	
	protected Long putnik; //opciono, ne mora se rezervisati za registrovanog korisnika
	
	protected Putovanje putovanje;

	public RezervacijaVozilaDTO(Vozilo rezervisanoVozilo, Long mjestoPreuzimanjaVozila, Date datumPreuzimanjaVozila,
			Long mjestoVracanjaVozila, Date datumVracanjaVozila, double cijena, Long putnik,
			Putovanje putovanje) {
		super();
		this.rezervisanoVozilo = rezervisanoVozilo;
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
		this.datumVracanjaVozila = datumVracanjaVozila;
		this.cijena = cijena;
		this.putnik = putnik;
		this.putovanje = putovanje;
	}

	public RezervacijaVozilaDTO(Vozilo rezervisanoVozilo, Long mjestoPreuzimanjaVozila, Date datumPreuzimanjaVozila,
			Long mjestoVracanjaVozila, Date datumVracanjaVozila, double cijena, Long putnik) {
		super();
		this.rezervisanoVozilo = rezervisanoVozilo;
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
		this.datumVracanjaVozila = datumVracanjaVozila;
		this.cijena = cijena;
		this.putnik = putnik;
	}

	public RezervacijaVozilaDTO() {
		super();
	}

	public Vozilo getRezervisanoVozilo() {
		return rezervisanoVozilo;
	}

	public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
		this.rezervisanoVozilo = rezervisanoVozilo;
	}

	public Long getMjestoPreuzimanjaVozila() {
		return mjestoPreuzimanjaVozila;
	}

	public void setMjestoPreuzimanjaVozila(Long mjestoPreuzimanjaVozila) {
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
	}

	public Date getDatumPreuzimanjaVozila() {
		return datumPreuzimanjaVozila;
	}

	public void setDatumPreuzimanjaVozila(Date datumPreuzimanjaVozila) {
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
	}

	public Long getMjestoVracanjaVozila() {
		return mjestoVracanjaVozila;
	}

	public void setMjestoVracanjaVozila(Long mjestoVracanjaVozila) {
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


	public Long getPutnik() {
		return putnik;
	}

	public void setPutnik(Long putnik) {
		this.putnik = putnik;
	}

	public Putovanje getPutovanje() {
		return putovanje;
	}

	public void setPutovanje(Putovanje putovanje) {
		this.putovanje = putovanje;
	}
	
	
}

