package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class Servis extends Poslovnica{
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "servis" )
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
