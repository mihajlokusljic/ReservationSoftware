package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usluga")
public class Usluga {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "naziv", nullable = false)
	protected String naziv;
	
	@Column(name = "cijena", nullable = false)
	@ColumnDefault("0")
	protected double cijena = 0;
	
	//odredjuje procenat popusta na ukupnu cijenu usluga poslovnice koji donosi ova usluga
	@Column(name = "procenat_popusta", nullable = false)
	protected int procenatPopusta;
	
	@Column(name = "nacin_placanja")
	@Enumerated(EnumType.STRING)
	protected NacinPlacanjaUsluge nacinPlacanja;
	
	@Column(name = "opis", nullable = true, columnDefinition = "TEXT")
	protected String opis;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "poslovnica_id")
	protected Poslovnica poslovnica;
	
	public Usluga() {
		super();
	}
	
	public Usluga(String naziv, double cijena) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
	}
	
	public Usluga(String naziv, double cijena, Poslovnica poslovnica) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
		this.poslovnica = poslovnica;
	}
	
	public Usluga(String naziv, double cijena, int procenatPopusta, NacinPlacanjaUsluge nacinPlacanja, String opis,
			Poslovnica poslovnica) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
		this.procenatPopusta = procenatPopusta;
		this.nacinPlacanja = nacinPlacanja;
		this.opis = opis;
		this.poslovnica = poslovnica;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public int getProcenatPopusta() {
		return procenatPopusta;
	}

	public void setProcenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}

	public Poslovnica getPoslovnica() {
		return poslovnica;
	}

	public void setPoslovnica(Poslovnica poslovnica) {
		this.poslovnica = poslovnica;
	}

	public NacinPlacanjaUsluge getNacinPlacanja() {
		return nacinPlacanja;
	}

	public void setNacinPlacanja(NacinPlacanjaUsluge nacinPlacanja) {
		this.nacinPlacanja = nacinPlacanja;
	}
	
}
