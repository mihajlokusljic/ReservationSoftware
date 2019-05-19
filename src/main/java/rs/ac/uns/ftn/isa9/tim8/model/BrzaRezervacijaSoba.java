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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "brza_rezervacija_soba")
public class BrzaRezervacijaSoba {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "datum_dolaska", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumDolaska;
	
	@Column(name = "datum_odlaska", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumOdlaska;
	
	@Column(name = "bazna_cijena", nullable = false)
	@ColumnDefault("0")
	protected double baznaCijena;
	
	@Column(name = "procenat_popusta", nullable = false)
	protected int procenatPopusta;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
		name = "brza_rezervacija_sobe_usluge",
		joinColumns = { @JoinColumn(name = "brza_rezervacija_sobe_id") },
		inverseJoinColumns = { @JoinColumn(name = "usluga_id") }
	)
	protected Set<Usluga> dodatneUsluge;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "soba_id", referencedColumnName = "id")
	protected HotelskaSoba sobaZaRezervaciju;
	
	public BrzaRezervacijaSoba() {
		super();
	}
	
	public long izracunajBrojNocenja() {
		long diff = datumOdlaska.getTime() - datumDolaska.getTime(); //razlika u milisekundama
		long brojNocenja = diff / (24 * 60 * 60 * 1000);             //razlika u danima
		if(brojNocenja == 0) {
			brojNocenja = 1;
		}
		return brojNocenja;
	}

	public BrzaRezervacijaSoba(Date datumDolaska, Date datumOdlaska, double baznaCijena, int procenatPopusta,
			Set<Usluga> dodatneUsluge, HotelskaSoba sobaZaRezervaciju) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.baznaCijena = baznaCijena;
		this.procenatPopusta = procenatPopusta;
		this.dodatneUsluge = dodatneUsluge;
		this.sobaZaRezervaciju = sobaZaRezervaciju;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public double getBaznaCijena() {
		return baznaCijena;
	}

	public void setBaznaCijena(double baznaCijena) {
		this.baznaCijena = baznaCijena;
	}

	public int getProcenatPopusta() {
		return procenatPopusta;
	}

	public void setProcenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}

	public Set<Usluga> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(Set<Usluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}

	public HotelskaSoba getSobaZaRezervaciju() {
		return sobaZaRezervaciju;
	}

	public void setSobaZaRezervaciju(HotelskaSoba sobaZaRezervaciju) {
		this.sobaZaRezervaciju = sobaZaRezervaciju;
	}
	
}
