package rs.ac.uns.ftn.isa9.tim8.model;

public class Usluga {
	protected String naziv;
	protected double cijena;
	
	public Usluga() {
		super();
	}
	
	public Usluga(String naziv, double cijena) {
		super();
		this.naziv = naziv;
		this.cijena = cijena;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
		
	
}
