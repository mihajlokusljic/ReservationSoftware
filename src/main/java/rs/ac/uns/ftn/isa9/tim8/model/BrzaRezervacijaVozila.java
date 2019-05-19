package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "brza_rezervacija_vozila")
public class BrzaRezervacijaVozila {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "vozilo_id", referencedColumnName = "id")
	protected Vozilo vozilo;
	
	@Column(name = "datum_preuzimanja_vozila", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumPreuzimanjaVozila;
	
	@Column(name = "datum_vracanja_vozila", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumVracanjaVozila;
	
	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena;
	
	@Column(name = "procenat_popusta", nullable = false)
	protected int procenatPopusta;

	public BrzaRezervacijaVozila() {
		super();
	}

	public Date getDatumPreuzimanjaVozila() {
		return datumPreuzimanjaVozila;
	}

	public void setDatumPreuzimanjaVozila(Date datumPreuzimanjaVozila) {
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
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
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}
	
	
}
