package rs.ac.uns.ftn.isa9.tim8.dto;


public class VoziloDTO {
	
	protected String naziv;
	
	protected String marka;
	
	protected String model;
	
	protected int godina_proizvodnje;
	
	protected int broj_sjedista;
	
    protected String tip_vozila;
	
    protected int broj_vrata;
	
    protected int kilovati;
	
    protected int cijena_po_danu;
	
	protected int brojOcjena;
	
	protected int sumaOcjena;
	
	protected String filijala;
	
	protected Long Id;

	public VoziloDTO(String naziv, String marka, String model, int godina_proizvodnje, int broj_sjedista,
			String tip_vozila, int broj_vrata, int kilovati, int cijena_po_danu, String filijala, Long Id) {
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
		this.Id = Id;
	}
	
	
	public VoziloDTO(String naziv, String marka, String model, int godina_proizvodnje, int broj_sjedista,
			String tip_vozila, int broj_vrata, int kilovati, int cijena_po_danu, int brojOcjena, int sumaOcjena,
			String filijala, Long id) {
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
		this.brojOcjena = brojOcjena;
		this.sumaOcjena = sumaOcjena;
		this.filijala = filijala;
		this.Id = id;
	}


	public VoziloDTO() {
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

	public int getBrojOcjena() {
		return brojOcjena;
	}

	public void setBrojOcjena(int brojOcjena) {
		this.brojOcjena = brojOcjena;
	}

	public int getSumaOcjena() {
		return sumaOcjena;
	}

	public void setSumaOcjena(int sumaOcjena) {
		this.sumaOcjena = sumaOcjena;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getFilijala() {
		return filijala;
	}

	public void setFilijala(String filijala) {
		this.filijala = filijala;
	}
	
	
	
}
