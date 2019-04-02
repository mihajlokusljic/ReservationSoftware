package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pozivnica")
public class Pozivnica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@Column(name = "prihvacena", nullable = false)
	protected boolean prihvacena;
	
	@Column(name = "rok_prihvatanja", nullable = false)
	protected Date rokPrihvatanja;
	
	@ManyToOne(fetch = FetchType.LAZY)
	protected Putovanje putovanje;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RegistrovanKorisnik posiljalac;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected RegistrovanKorisnik primalac;
	
	public Pozivnica() {
		super();
	}

	public Pozivnica(Long id, boolean prihvacena, Date rokPrihvatanja, Putovanje putovanje,
			RegistrovanKorisnik posiljalac) {
		super();
		this.id = id;
		this.prihvacena = prihvacena;
		this.rokPrihvatanja = rokPrihvatanja;
		this.putovanje = putovanje;
		this.posiljalac = posiljalac;
	}

	public boolean isPrihvacena() {
		return prihvacena;
	}

	public void setPrihvacena(boolean prihvacena) {
		this.prihvacena = prihvacena;
	}

	public Date getRokPrihvatanja() {
		return rokPrihvatanja;
	}

	public void setRokPrihvatanja(Date rokPrihvatanja) {
		this.rokPrihvatanja = rokPrihvatanja;
	}

	public Putovanje getPutovanje() {
		return putovanje;
	}

	public void setPutovanje(Putovanje putovanje) {
		this.putovanje = putovanje;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RegistrovanKorisnik getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(RegistrovanKorisnik posiljalac) {
		this.posiljalac = posiljalac;
	}
	
}
