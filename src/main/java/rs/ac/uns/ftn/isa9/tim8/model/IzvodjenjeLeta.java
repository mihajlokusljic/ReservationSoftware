package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

public class IzvodjenjeLeta {
	protected Date datumVrijemePoletanja;
	protected Date datumVrijemeSletanja;
	protected Let let;
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
