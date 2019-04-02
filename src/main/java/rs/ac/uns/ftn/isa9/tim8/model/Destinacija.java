package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Destinacija")
public class Destinacija {
	@Column(name = "nazivDestinacije", unique = true, nullable = false)
	String nazivDestinacije;

	public Destinacija() {
		super();
	}

	public Destinacija(String nazivDestinacije) {
		super();
		this.nazivDestinacije = nazivDestinacije;
	}

	public String getNazivDestinacije() {
		return nazivDestinacije;
	}

	public void setNazivDestinacije(String nazivDestinacije) {
		this.nazivDestinacije = nazivDestinacije;
	}

	
}
