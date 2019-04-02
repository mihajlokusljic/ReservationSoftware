package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vozilo")
public class Vozilo extends Ocjenjivo{
	@Column(name = "naziv", nullable = false)
	protected String naziv;
	@Column(name = "marka", nullable = false)
	protected String marka;
	@Column(name = "model", nullable = false)
	protected String model;
	@Column(name = "godina_proizvodnje", nullable = false)
	protected int godina_proizvodnje;
	@Column(name = "broj_sjedista", nullable = false)
	protected int broj_sjedista;
	@Column(name = "tip_vozila", nullable = false)
    protected String tip_vozila;
	@Column(name = "broj_vrata", nullable = false)
    protected int broj_vrata;
	@Column(name = "kilovati", nullable = false)
    protected int kilovati;
	@Column(name = "cijena_po_danu", nullable = false)
    protected int cijena_po_danu;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected Filijala filijala;
	@ManyToOne
	protected RentACarServis rentACar;
    
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
