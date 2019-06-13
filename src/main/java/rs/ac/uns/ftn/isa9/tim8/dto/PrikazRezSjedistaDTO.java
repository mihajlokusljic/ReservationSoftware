package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import rs.ac.uns.ftn.isa9.tim8.common.CustomDateTimeSerializer;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public class PrikazRezSjedistaDTO {

	protected Long idRezervacije;

	protected String nazivPolazista;

	protected String nazivOdredista;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	protected Date datumPolaska;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	protected Date datumDolaska;

	protected Sjediste sjediste;

	protected double originalnaCijena;

	protected double popust;
	
	protected String nazivAviokompanije;
	
	protected String brojLeta;

	public PrikazRezSjedistaDTO() {
		super();
	}

	public PrikazRezSjedistaDTO(Long idRezervacije, String nazivPolazista, String nazivOdredista, Date datumPolaska,
			Date datumDolaska, Sjediste sjediste, double originalnaCijena, double popust) {
		super();
		this.idRezervacije = idRezervacije;
		this.nazivPolazista = nazivPolazista;
		this.nazivOdredista = nazivOdredista;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
		this.sjediste = sjediste;
		this.originalnaCijena = originalnaCijena;
		this.popust = popust;
	}

	public PrikazRezSjedistaDTO(Long id, String nazivPolazista, String nazivOdredista, Date datumPolaska, Date datumDolaska,
			Sjediste sjediste, double originalnaCijena, String nazivAviokompanije, String brojLeta) {
		super();
		this.idRezervacije = id;
		this.nazivPolazista = nazivPolazista;
		this.nazivOdredista = nazivOdredista;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
		this.sjediste = sjediste;
		this.originalnaCijena = originalnaCijena;
		this.nazivAviokompanije = nazivAviokompanije;
		this.brojLeta = brojLeta;
	}

	public Long getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(Long idRezervacije) {
		this.idRezervacije = idRezervacije;
	}

	public String getNazivPolazista() {
		return nazivPolazista;
	}

	public void setNazivPolazista(String nazivPolazista) {
		this.nazivPolazista = nazivPolazista;
	}

	public String getNazivOdredista() {
		return nazivOdredista;
	}

	public void setNazivOdredista(String nazivOdredista) {
		this.nazivOdredista = nazivOdredista;
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

	public String getNazivAviokompanije() {
		return nazivAviokompanije;
	}

	public void setNazivAviokompanije(String nazivAviokompanije) {
		this.nazivAviokompanije = nazivAviokompanije;
	}

	public String getBrojLeta() {
		return brojLeta;
	}

	public void setBrojLeta(String brojLeta) {
		this.brojLeta = brojLeta;
	}
	
}
