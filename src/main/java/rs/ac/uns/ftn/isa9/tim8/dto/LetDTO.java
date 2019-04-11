package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;

public class LetDTO {
	
	protected String brojLeta;
	
	protected Destinacija polaziste;

	protected Destinacija odrediste;
	
	protected Date datumPoletanja;
	
	protected Date datumSletanja; // Napisan u formatu dd.MM.yyyy HH:mm
	
	protected Date duzinaPutovanja; // Kojeg datuma i u koliko casova se ocekivano vracamo

	protected int brojPresjedanja;
	
	protected int sumaOcjena;
	
	protected int brojOcjena;
	
	protected int kapacitetPrvaKlasa;
	
	protected int kapacitetBiznisKlasa;
	
	protected int kapacitetEkonomskaKlasa;
	
	protected double cijenaKarte;
	
	public LetDTO() {
		super();
	}

	public LetDTO(String brojLeta, Destinacija polaziste, Destinacija odrediste, Date datumPoletanja,
			Date datumSletanja, Date duzinaPutovanja, int brojPresjedanja, int kapacitetPrvaKlasa,
			int kapacitetBiznisKlasa, int kapacitetEkonomskaKlasa, double cijenaKarte) {
		super();
		this.brojLeta = brojLeta;
		this.polaziste = polaziste;
		this.odrediste = odrediste;
		this.datumPoletanja = datumPoletanja;
		this.datumSletanja = datumSletanja;
		this.duzinaPutovanja = duzinaPutovanja;
		this.brojPresjedanja = brojPresjedanja;
		this.kapacitetPrvaKlasa = kapacitetPrvaKlasa;
		this.kapacitetBiznisKlasa = kapacitetBiznisKlasa;
		this.kapacitetEkonomskaKlasa = kapacitetEkonomskaKlasa;
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

	public int getBrojPresjedanja() {
		return brojPresjedanja;
	}

	public void setBrojPresjedanja(int brojPresjedanja) {
		this.brojPresjedanja = brojPresjedanja;
	}

	public int getSumaOcjena() {
		return sumaOcjena;
	}

	public void setSumaOcjena(int sumaOcjena) {
		this.sumaOcjena = sumaOcjena;
	}

	public int getBrojOcjena() {
		return brojOcjena;
	}

	public void setBrojOcjena(int brojOcjena) {
		this.brojOcjena = brojOcjena;
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

	public double getCijenaKarte() {
		return cijenaKarte;
	}

	public void setCijenaKarte(double cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}
	
	
}
