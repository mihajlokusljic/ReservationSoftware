package rs.ac.uns.ftn.isa9.tim8.model;

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

@Entity
@Table(name = "zahtjev_za_prijateljstvo")
public class ZahtjevZaPrijateljstvo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Column(name = "potvrdjen")
	protected boolean potvrdjen;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "posiljalac_id", unique = true, referencedColumnName = "id")
	protected RegistrovanKorisnik posiljalac;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primalac_id")
	protected RegistrovanKorisnik primalac;

	public ZahtjevZaPrijateljstvo() {
		super();
	}

	public ZahtjevZaPrijateljstvo(RegistrovanKorisnik posiljalac, RegistrovanKorisnik primalac) {
		super();
		this.posiljalac = posiljalac;
		this.primalac = primalac;
	}

	public ZahtjevZaPrijateljstvo(boolean potvrdjen, RegistrovanKorisnik posiljalac, RegistrovanKorisnik primalac) {
		super();
		this.potvrdjen = potvrdjen;
		this.posiljalac = posiljalac;
		this.primalac = primalac;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public boolean isPotvrdjen() {
		return potvrdjen;
	}

	public void setPotvrdjen(boolean potvrdjen) {
		this.potvrdjen = potvrdjen;
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
