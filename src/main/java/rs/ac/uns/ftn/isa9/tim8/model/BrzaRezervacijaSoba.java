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

@Entity
@Table(name = "brza_rezervacija_soba")
public class BrzaRezervacijaSoba {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "datum_dolaska", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumDolaska;
	
	@Column(name = "datum_odlaska", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumOdlaska;
	
	@Column(name = "cijena", nullable = false)
	protected double cijena;
	
//	@OneToMany(mappedBy = "brzaRezervacijaSobe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	protected Set<Usluga> dodatneUsluge;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "soba_id", referencedColumnName = "id")
	protected HotelskaSoba sobaZaRezervaciju;
	
	public BrzaRezervacijaSoba() {
		super();
	}

	public BrzaRezervacijaSoba(Date datumDolaska, Date datumOdlaska, double cijena, 
			HotelskaSoba sobaZaRezervaciju) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.cijena = cijena;
		//this.dodatneUsluge = dodatneUsluge;
		this.sobaZaRezervaciju = sobaZaRezervaciju;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HotelskaSoba getSobaZaRezervaciju() {
		return sobaZaRezervaciju;
	}

	public void setSobaZaRezervaciju(HotelskaSoba sobaZaRezervaciju) {
		this.sobaZaRezervaciju = sobaZaRezervaciju;
	}

	/*public Set<Usluga> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(Set<Usluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}*/

	
	
	
}
