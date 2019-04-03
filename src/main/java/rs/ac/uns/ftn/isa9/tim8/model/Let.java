package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class Let extends Ocjenjivo {
	//@Column(name = "cijenaPrtljagaKomad", unique = false, nullable = false)
	protected String brojLeta;
	
	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Destinacija polaziste;

	//@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Destinacija odrediste;
	
	//@Column(name = "cijenaPrtljagaKomad", unique = false, nullable = false)
	protected Date datumPoletanja;
	
	//@Column(name = "cijenaPrtljagaKomad", unique = false, nullable = false)
	protected Date datumSletanja; // Napisan u formatu dd.MM.yyyy HH:mm
	
	//@Column(name = "cijenaPrtljagaKomad", unique = false, nullable = false)
	protected Date duzinaPutovanja; // Kojeg datuma i u koliko casova se ocekivano vracamo

	//@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Destinacija> presjedanja; // Zato sto ce biti potrebno cuvati i lokacije
																// (vjerovatno kroz
	// Yandex mape
	protected Avion avion;
	protected int kapacitetPrvaKlasa;
	protected int kapacitetBiznisKlasa;
	protected int kapacitetEkonomskaKlasa;
	protected ArrayList<RezervacijaSjedista> rezervacije;
	double cijenaKarte;

	public Let() {
		super();
	}

	public Let(String brojLeta, Destinacija polaziste, Destinacija odrediste, Date datumPoletanja, Date datumSletanja,
			Date duzinaPutovanja, Set<Destinacija> presjedanja, Avion avion, int kapacitetPrvaKlasa,
			int kapacitetBiznisKlasa, int kapacitetEkonomskaKlasa, ArrayList<RezervacijaSjedista> rezervacije,
			double cijenaKarte) {
		super();
		this.brojLeta = brojLeta;
		this.polaziste = polaziste;
		this.odrediste = odrediste;
		this.datumPoletanja = datumPoletanja;
		this.datumSletanja = datumSletanja;
		this.duzinaPutovanja = duzinaPutovanja;
		this.presjedanja = presjedanja;
		this.avion = avion;
		this.kapacitetPrvaKlasa = kapacitetPrvaKlasa;
		this.kapacitetBiznisKlasa = kapacitetBiznisKlasa;
		this.kapacitetEkonomskaKlasa = kapacitetEkonomskaKlasa;
		this.rezervacije = rezervacije;
		this.cijenaKarte = cijenaKarte;
	}

	public String getBrojLeta() {
		return brojLeta;
	}

	public void setBrojLeta(String brojLeta) {
		this.brojLeta = brojLeta;
	}

	public Destinacija getPolaziste() {
		return polaziste;
	}

	public void setPolaziste(Destinacija polaziste) {
		this.polaziste = polaziste;
	}

	public Destinacija getOdrediste() {
		return odrediste;
	}

	public void setOdrediste(Destinacija odrediste) {
		this.odrediste = odrediste;
	}

	public Date getDatumPoletanja() {
		return datumPoletanja;
	}

	public void setDatumPoletanja(Date datumPoletanja) {
		this.datumPoletanja = datumPoletanja;
	}

	public Date getDatumSletanja() {
		return datumSletanja;
	}

	public void setDatumSletanja(Date datumSletanja) {
		this.datumSletanja = datumSletanja;
	}

	public Date getDuzinaPutovanja() {
		return duzinaPutovanja;
	}

	public void setDuzinaPutovanja(Date duzinaPutovanja) {
		this.duzinaPutovanja = duzinaPutovanja;
	}

	public Set<Destinacija> getPresjedanja() {
		return presjedanja;
	}

	public void setPresjedanja(Set<Destinacija> presjedanja) {
		this.presjedanja = presjedanja;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public int getKapacitetPrvaKlasa() {
		return kapacitetPrvaKlasa;
	}

	public void setKapacitetPrvaKlasa(int kapacitetPrvaKlasa) {
		this.kapacitetPrvaKlasa = kapacitetPrvaKlasa;
	}

	public int getKapacitetBiznisKlasa() {
		return kapacitetBiznisKlasa;
	}

	public void setKapacitetBiznisKlasa(int kapacitetBiznisKlasa) {
		this.kapacitetBiznisKlasa = kapacitetBiznisKlasa;
	}

	public int getKapacitetEkonomskaKlasa() {
		return kapacitetEkonomskaKlasa;
	}

	public void setKapacitetEkonomskaKlasa(int kapacitetEkonomskaKlasa) {
		this.kapacitetEkonomskaKlasa = kapacitetEkonomskaKlasa;
	}

	public ArrayList<RezervacijaSjedista> getRezervacije() {
		return rezervacije;
	}

	public void setRezervacije(ArrayList<RezervacijaSjedista> rezervacije) {
		this.rezervacije = rezervacije;
	}

	public double getCijenaKarte() {
		return cijenaKarte;
	}

	public void setCijenaKarte(double cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}

	

}
