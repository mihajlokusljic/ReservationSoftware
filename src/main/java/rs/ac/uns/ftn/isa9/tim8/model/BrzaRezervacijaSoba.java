package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

public class BrzaRezervacijaSoba {
	
	protected Date datumDolaska;
	protected Date datumOdlaska;
	protected double cijena;
	protected Set<Usluga> dodatneUsluge;
	protected Set<HotelskaSoba> sobeZaRezervaciju;
	
	public BrzaRezervacijaSoba() {
		super();
	}

	public BrzaRezervacijaSoba(Date datumDolaska, Date datumOdlaska, double cijena, Set<Usluga> dodatneUsluge,
			Set<HotelskaSoba> sobeZaRezervaciju) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.cijena = cijena;
		this.dodatneUsluge = dodatneUsluge;
		this.sobeZaRezervaciju = sobeZaRezervaciju;
	}

	public Date getDatumDolaska() {
		return datumDolaska;
	}

	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public Date getDatumOdlaska() {
		return datumOdlaska;
	}

	public void setDatumOdlaska(Date datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Set<Usluga> getDodatneUsluge() {
		return dodatneUsluge;
	}

	public void setDodatneUsluge(Set<Usluga> dodatneUsluge) {
		this.dodatneUsluge = dodatneUsluge;
	}

	public Set<HotelskaSoba> getSobeZaRezervaciju() {
		return sobeZaRezervaciju;
	}

	public void setSobeZaRezervaciju(Set<HotelskaSoba> sobeZaRezervaciju) {
		this.sobeZaRezervaciju = sobeZaRezervaciju;
	}
	
	
}
