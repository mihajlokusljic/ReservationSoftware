package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

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

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "rezervacija_sobe")
public class RezervacijaSobe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "datum_dolaska", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumDolaska;
	
	@Column(name = "datum_odlaska", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumOdlaska;
	
	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "soba_id", referencedColumnName = "id")
	protected HotelskaSoba rezervisanaSoba;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="putnik_id")
	protected RegistrovanKorisnik putnik; //opciono, ne mora se rezervisati za registrovanog korisnika
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "putovanje_id")	
	protected Putovanje putovanje;
	
	@Column(name = "ocjenjeno")
    protected boolean ocjenjeno;
	
	public RezervacijaSobe() {
		super();
		this.ocjenjeno = false;
	}
	public RezervacijaSobe(Date datumDolaska, Date datumOdlaska, double cijena, HotelskaSoba rezervisanaSoba) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.cijena = cijena;
		this.rezervisanaSoba = rezervisanaSoba;
		this.ocjenjeno = false;
	}
	
	public RezervacijaSobe(Date datumDolaska, Date datumOdlaska, double cijena, HotelskaSoba rezervisanaSoba, boolean ocjenjeno) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.cijena = cijena;
		this.rezervisanaSoba = rezervisanaSoba;
		this.ocjenjeno = ocjenjeno;
	}
	
	public Date getDatumDolaska() {
		return datumDolaska;
	}
	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	public Date getDatumOdlaska() {
		return datumOdlaska;
	}
	public void setDatumOdlaska(Date datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}
	public double getCijena() {
		return cijena;
	}
	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	public HotelskaSoba getRezervisanaSoba() {
		return rezervisanaSoba;
	}
	public void setRezervisanaSoba(HotelskaSoba rezervisanaSoba) {
		this.rezervisanaSoba = rezervisanaSoba;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		this.Id = id;
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
	public boolean isOcjenjeno() {
		return ocjenjeno;
	}
	public void setOcjenjeno(boolean ocjenjeno) {
		this.ocjenjeno = ocjenjeno;
	}
	
}
