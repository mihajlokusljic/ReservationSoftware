package rs.ac.uns.ftn.isa9.tim8.model;

public class Poslovnica extends Ocjenjivo {
	protected String naziv;
	protected String promotivniOpis;
	protected Adresa adresa;
	
	public Poslovnica() {
		super();
	}

	public Poslovnica(String naziv, String promotivniOpis, Adresa adresa) {
		super();
		this.naziv = naziv;
		this.promotivniOpis = promotivniOpis;
		this.adresa = adresa;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPromotivniOpis() {
		return promotivniOpis;
	}

	public void setPromotivniOpis(String promotivniOpis) {
		this.promotivniOpis = promotivniOpis;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}
	
	
}
