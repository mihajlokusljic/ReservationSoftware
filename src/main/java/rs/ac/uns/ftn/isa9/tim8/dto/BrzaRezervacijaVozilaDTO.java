package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

public class BrzaRezervacijaVozilaDTO {
	
	protected Long idBrzeRezervacije;
	
	protected Long idVozila;
	
	protected Date datumPreuzimanjaVozila;
	
	protected Date datumVracanjaVozila;
	
	protected double baznaCijena;
	
	protected int procenatPopusta;

	public BrzaRezervacijaVozilaDTO(Long idVozila, Date datumPreuzimanjaVozila, Date datumVracanjaVozila,
			double baznaCijena, int procenatPopusta) {
		super();
		this.idVozila = idVozila;
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
		this.datumVracanjaVozila = datumVracanjaVozila;
		this.baznaCijena = baznaCijena;
		this.procenatPopusta = procenatPopusta;
	}

	public BrzaRezervacijaVozilaDTO() {
		super();
	}

	public Long getIdVozila() {
		return idVozila;
	}

	public void setIdVozila(Long idVozila) {
		this.idVozila = idVozila;
	}

	public Date getDatumPreuzimanjaVozila() {
		return datumPreuzimanjaVozila;
	}

	public void setDatumPreuzimanjaVozila(Date datumPreuzimanjaVozila) {
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
	}

	public Date getDatumVracanjaVozila() {
		return datumVracanjaVozila;
	}

	public void setDatumVracanjaVozila(Date datumVracanjaVozila) {
		this.datumVracanjaVozila = datumVracanjaVozila;
	}

	public double getBaznaCijena() {
		return baznaCijena;
	}

	public void setBaznaCijena(double baznaCijena) {
		this.baznaCijena = baznaCijena;
	}

	public int getProcenatPopusta() {
		return procenatPopusta;
	}

	public void setProcenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}

	public Long getIdBrzeRezervacije() {
		return idBrzeRezervacije;
	}

	public void setIdBrzeRezervacije(Long idBrzeRezervacije) {
		this.idBrzeRezervacije = idBrzeRezervacije;
	}
	
}
