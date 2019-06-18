package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import rs.ac.uns.ftn.isa9.tim8.common.CustomDateTimeSerializer;

public class PozivnicaDTO {
	
	protected String posiljalac;
	
	protected String polaziste;
	
	protected String odrediste;
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	protected Date datumPolaska;
	
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	protected Date datumPovratka;
	
	public PozivnicaDTO() {
		super();
	}
	public PozivnicaDTO(String posiljalac, String polaziste, String odrediste, Date datumPolaska, Date datumPovratka) {
		super();
		this.posiljalac = posiljalac;
		this.polaziste = polaziste;
		this.odrediste = odrediste;
		this.datumPolaska = datumPolaska;
		this.datumPovratka = datumPovratka;
	}
	public String getPosiljalac() {
		return posiljalac;
	}
	public void setPosiljalac(String posiljalac) {
		this.posiljalac = posiljalac;
	}
	public String getPolaziste() {
		return polaziste;
	}
	public void setPolaziste(String polaziste) {
		this.polaziste = polaziste;
	}
	public String getOdrediste() {
		return odrediste;
	}
	public void setOdrediste(String odrediste) {
		this.odrediste = odrediste;
	}
	public Date getDatumPolaska() {
		return datumPolaska;
	}
	public void setDatumPolaska(Date datumPolaska) {
		this.datumPolaska = datumPolaska;
	}
	public Date getDatumPovratka() {
		return datumPovratka;
	}
	public void setDatumPovratka(Date datumPovratka) {
		this.datumPovratka = datumPovratka;
	}

}
