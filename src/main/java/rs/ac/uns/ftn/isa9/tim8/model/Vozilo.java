package rs.ac.uns.ftn.isa9.tim8.model;

public class Vozilo extends Ocjenjivo{
	protected String naziv;
	protected String marka;
	protected String model;
	protected int godina_proizvodnje;
	protected int broj_sjedista;
    protected String tip_vozila;
    protected int broj_vrata;
    protected int kilovati;
    protected int cijena_po_danu;
    protected Filijala filijala;
    
	public Vozilo(String naziv, String marka, String model, int godina_proizvodnje, int broj_sjedista,
			String tip_vozila, int broj_vrata, int kilovati, int cijena_po_danu, Filijala filijala) {
		super();
		this.naziv = naziv;
		this.marka = marka;
		this.model = model;
		this.godina_proizvodnje = godina_proizvodnje;
		this.broj_sjedista = broj_sjedista;
		this.tip_vozila = tip_vozila;
		this.broj_vrata = broj_vrata;
		this.kilovati = kilovati;
		this.cijena_po_danu = cijena_po_danu;
		this.filijala = filijala;
	}

	public Vozilo() {
		super();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getGodina_proizvodnje() {
		return godina_proizvodnje;
	}

	public void setGodina_proizvodnje(int godina_proizvodnje) {
		this.godina_proizvodnje = godina_proizvodnje;
	}

	public int getBroj_sjedista() {
		return broj_sjedista;
	}

	public void setBroj_sjedista(int broj_sjedista) {
		this.broj_sjedista = broj_sjedista;
	}

	public String getTip_vozila() {
		return tip_vozila;
	}

	public void setTip_vozila(String tip_vozila) {
		this.tip_vozila = tip_vozila;
	}

	public int getBroj_vrata() {
		return broj_vrata;
	}

	public void setBroj_vrata(int broj_vrata) {
		this.broj_vrata = broj_vrata;
	}

	public int getKilovati() {
		return kilovati;
	}

	public void setKilovati(int kilovati) {
		this.kilovati = kilovati;
	}

	public int getCijena_po_danu() {
		return cijena_po_danu;
	}

	public void setCijena_po_danu(int cijena_po_danu) {
		this.cijena_po_danu = cijena_po_danu;
	}

	public Filijala getFilijala() {
		return filijala;
	}

	public void setFilijala(Filijala filijala) {
		this.filijala = filijala;
	}
	
	
    
	
    
}
