package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class AdministratorAviokompanije extends Osoba {
	
	/*
	 * The annotation @JoinColumn indicates that this entity is the owner of the relationship 
	 * (that is: the corresponding table has a column with a foreign key to the referenced table)
	 * 
	 */
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "aviokompanija")
	protected Aviokompanija aviokompanija;

	public AdministratorAviokompanije() {
		super();
	}

	public AdministratorAviokompanije(Aviokompanija aviokompanija) {
		super();
		this.aviokompanija = aviokompanija;
	}

}
