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
import javax.persistence.Table;

@Entity
@Table(name = "pozivnica")
public class Pozivnica {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "prihvacena", nullable = false)
	protected boolean prihvacena;
	
	// uvedeno zbog provjere da li je korisnik vec odbio pozivnicu
	@Column(name = "odbijena", nullable = false)
	protected boolean odbijena;
	
	@Column(name = "rok_prihvatanja", nullable = false)
	protected Date rokPrihvatanja;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "putovanje_id")
	protected Putovanje putovanje;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posiljalac_id", referencedColumnName = "id")
	protected RegistrovanKorisnik posiljalac;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primalac_id")
	protected RegistrovanKorisnik primalac;

	public Pozivnica() {
		super();
	}

	public Pozivnica(Date rokPrihvatanja, Putovanje putovanje, RegistrovanKorisnik posiljalac,
			RegistrovanKorisnik primalac) {
		super();
		this.rokPrihvatanja = rokPrihvatanja;
		this.putovanje = putovanje;
		this.posiljalac = posiljalac;
		this.primalac = primalac;
		this.prihvacena = false;
		this.odbijena = false;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public boolean isPrihvacena() {
		return prihvacena;
	}

	public void setPrihvacena(boolean prihvacena) {
		this.prihvacena = prihvacena;
	}

	public boolean isOdbijena() {
		return odbijena;
	}

	public void setOdbijena(boolean odbijena) {
		this.odbijena = odbijena;
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

	public RegistrovanKorisnik getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(RegistrovanKorisnik posiljalac) {
		this.posiljalac = posiljalac;
	}

	public RegistrovanKorisnik getPrimalac() {
		return primalac;
	}

	public void setPrimalac(RegistrovanKorisnik primalac) {
		this.primalac = primalac;
	}
	
}
