package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

public class PrikazBrzeRezVozilaDTO {
	
	protected Long idRezervacije;
	
	protected String nazivVozila;
	
	protected String nazivServisa;
	
	protected Date pocetniDatum;
	
	protected Date krajnjiDatum;
	
	protected double punaCijena;
	
	protected double cijenaSaPopustom;

	public PrikazBrzeRezVozilaDTO(Long idRezervacije, String nazivVozila, String nazivServisa, Date pocetniDatum,
			Date krajnjiDatum, double punaCijena, double cijenaSaPopustom) {
		super();
		this.idRezervacije = idRezervacije;
		this.nazivVozila = nazivVozila;
		this.nazivServisa = nazivServisa;
		this.pocetniDatum = pocetniDatum;
		this.krajnjiDatum = krajnjiDatum;
		this.punaCijena = punaCijena;
		this.cijenaSaPopustom = cijenaSaPopustom;
	}

	public PrikazBrzeRezVozilaDTO() {
		super();
	}

	public Long getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(Long idRezervacije) {
		this.idRezervacije = idRezervacije;
	}

	public String getNazivVozila() {
		return nazivVozila;
	}

	public void setNazivVozila(String nazivVozila) {
		this.nazivVozila = nazivVozila;
	}

	public String getNazivServisa() {
		return nazivServisa;
	}

	public void setNazivServisa(String nazivServisa) {
		this.nazivServisa = nazivServisa;
	}

	public Date getPocetniDatum() {
		return pocetniDatum;
	}

	public void setPocetniDatum(Date pocetniDatum) {
		this.pocetniDatum = pocetniDatum;
	}

	public Date getKrajnjiDatum() {
		return krajnjiDatum;
	}

	public void setKrajnjiDatum(Date krajnjiDatum) {
		this.krajnjiDatum = krajnjiDatum;
	}

	public double getPunaCijena() {
		return punaCijena;
	}

	public void setPunaCijena(double punaCijena) {
		this.punaCijena = punaCijena;
	}

	public double getCijenaSaPopustom() {
		return cijenaSaPopustom;
	}

	public void setCijenaSaPopustom(double cijenaSaPopustom) {
		this.cijenaSaPopustom = cijenaSaPopustom;
	}
	
}
