package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class Servis extends Poslovnica{
	protected Set<Usluga> dodatneUsluge;

	public Servis() {
		super();
	}

	public Servis(String naziv, String promotivniOpis, Adresa adresa, Set<Usluga> usluge) {
		super(naziv, promotivniOpis, adresa);
		this.dodatneUsluge = usluge;
	}

	public Set<Usluga> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(Set<Usluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}
	
}
