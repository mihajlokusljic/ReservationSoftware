package rs.ac.uns.ftn.isa9.tim8.model;

public class HotelskaSoba extends Ocjenjivo {
	protected int sprat;
	protected int brojKreveta;
	protected int vrsta;
	protected int kolona;
	protected int brojSobe;
	protected double cijena;
	
	public HotelskaSoba() {
		super();
	}

	public HotelskaSoba(int sprat, int brojKreveta, int vrsta, int kolona, int brojSobe, double cijena) {
		super();
		this.sprat = sprat;
		this.brojKreveta = brojKreveta;
		this.vrsta = vrsta;
		this.kolona = kolona;
		this.brojSobe = brojSobe;
		this.cijena = cijena;
	}

	public int getSprat() {
		return sprat;
	}

	public void setSprat(int sprat) {
		this.sprat = sprat;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public int getVrsta() {
		return vrsta;
	}

	public void setVrsta(int vrsta) {
		this.vrsta = vrsta;
	}

	public int getKolona() {
		return kolona;
	}

	public void setKolona(int kolona) {
		this.kolona = kolona;
	}

	public int getBrojSobe() {
		return brojSobe;
	}

	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}
	
	
}
