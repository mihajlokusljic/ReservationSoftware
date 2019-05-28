package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public class PrikazRezSjedistaDTO {

	protected Long idRezervacije;

	protected Date datumPolaska;

	protected Date datumDolaska;

	protected Sjediste sjediste;

	protected double originalnaCijena;

	protected double popust;

	public PrikazRezSjedistaDTO() {
		super();
	}

	public PrikazRezSjedistaDTO(Long idRezervacije, Date datumPolaska, Date datumDolaska, Sjediste sjediste,
			double originalnaCijena, double popust) {
		super();
		this.idRezervacije = idRezervacije;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
		this.sjediste = sjediste;
		this.originalnaCijena = originalnaCijena;
		this.popust = popust;
	}

	public Long getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(Long idRezervacije) {
		this.idRezervacije = idRezervacije;
	}

	public Date getDatumPolaska() {
		return datumPolaska;
	}

	public void setDatumPolaska(Date datumPolaska) {
		this.datumPolaska = datumPolaska;
	}

	public Date getDatumDolaska() {
		return datumDolaska;
	}

	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public Sjediste getSjediste() {
		return sjediste;
	}

	public void setSjediste(Sjediste sjediste) {
		this.sjediste = sjediste;
	}

	public double getOriginalnaCijena() {
		return originalnaCijena;
	}

	public void setOriginalnaCijena(double originalnaCijena) {
		this.originalnaCijena = originalnaCijena;
	}

	public double getPopust() {
		return popust;
	}

	public void setPopust(double popust) {
		this.popust = popust;
	}

}
