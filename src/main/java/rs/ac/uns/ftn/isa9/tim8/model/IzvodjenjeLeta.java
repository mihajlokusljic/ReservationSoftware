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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class IzvodjenjeLeta {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	//@Column(name = "datumVrijemePoletanja", unique = false, nullable = false)
	protected Date datumVrijemePoletanja;
	
	//@Column(name = "datumVrijemeSletanja", unique = false, nullable = false)
	protected Date datumVrijemeSletanja;

	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Let let;

	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaSjedista> rezervisanaMjesta;

	public IzvodjenjeLeta() {
		super();
	}

	public Date getDatumVrijemePoletanja() {
		return datumVrijemePoletanja;
	}

	public void setDatumVrijemePoletanja(Date datumVrijemePoletanja) {
		this.datumVrijemePoletanja = datumVrijemePoletanja;
	}

	public Date getDatumVrijemeSletanja() {
		return datumVrijemeSletanja;
	}

	public void setDatumVrijemeSletanja(Date datumVrijemeSletanja) {
		this.datumVrijemeSletanja = datumVrijemeSletanja;
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Set<RezervacijaSjedista> getRezervisanaMjesta() {
		return rezervisanaMjesta;
	}

	public void setRezervisanaMjesta(Set<RezervacijaSjedista> rezervisanaMjesta) {
		this.rezervisanaMjesta = rezervisanaMjesta;
	}

}
