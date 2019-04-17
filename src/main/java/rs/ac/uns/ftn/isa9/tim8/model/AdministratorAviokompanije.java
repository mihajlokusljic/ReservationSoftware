package rs.ac.uns.ftn.isa9.tim8.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
public class AdministratorAviokompanije extends Osoba {
	
	/*
	 * The annotation @JoinColumn indicates that this entity is the owner of the relationship 
	 * (that is: the corresponding table has a column with a foreign key to the referenced table)
	 * 
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2173872186530239645L;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="aviokompanija_id")
	protected Aviokompanija aviokompanija;

	public AdministratorAviokompanije() {
		super();
	}

	public AdministratorAviokompanije(Aviokompanija aviokompanija) {
		super();
		this.aviokompanija = aviokompanija;
	}

	public AdministratorAviokompanije(Long id, String lozinka, String ime, String prezime, String email,
			String brojTelefona, Adresa adresa, String putanjaSlike, boolean enabled, Timestamp lastPasswordResetDate,
			Set<Authority> authorities, Aviokompanija aviokompanija) {
		super(id, lozinka, ime, prezime, email, brojTelefona, adresa, putanjaSlike, enabled, lastPasswordResetDate,
				authorities);
		this.aviokompanija = aviokompanija;
	}

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}
	
	

}
