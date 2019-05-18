package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public class PrikazRezSjedistaDTO {
	
	protected Long idRezervacije;
	
	protected String nazivAviokompanije;
	
	protected String brojLeta;
	
	protected double cijena;

	protected Sjediste sjediste;	
	
	protected String destinacijaPolaska;
	
	protected String destinacijaDolaska;
	
	protected Date datumPolaska;
	
	protected Date datumDolaska;

	public PrikazRezSjedistaDTO(Long idRezervacije, String nazivAviokompanije, String brojLeta, double cijena,
			Sjediste sjediste, String destinacijaPolaska, String destinacijaDolaska, Date datumPolaska,
			Date datumDolaska) {
		super();
		this.idRezervacije = idRezervacije;
		this.nazivAviokompanije = nazivAviokompanije;
		this.brojLeta = brojLeta;
		this.cijena = cijena;
		this.sjediste = sjediste;
		this.destinacijaPolaska = destinacijaPolaska;
		this.destinacijaDolaska = destinacijaDolaska;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
	}

	public PrikazRezSjedistaDTO() {
		super();
	}

	public Long getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(Long idRezervacije) {
		this.idRezervacije = idRezervacije;
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

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Sjediste getSjediste() {
		return sjediste;
	}

	public void setSjediste(Sjediste sjediste) {
		this.sjediste = sjediste;
	}

	public String getDestinacijaPolaska() {
		return destinacijaPolaska;
	}

	public void setDestinacijaPolaska(String destinacijaPolaska) {
		this.destinacijaPolaska = destinacijaPolaska;
	}

	public String getDestinacijaDolaska() {
		return destinacijaDolaska;
	}

	public void setDestinacijaDolaska(String destinacijaDolaska) {
		this.destinacijaDolaska = destinacijaDolaska;
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
	
	
}
