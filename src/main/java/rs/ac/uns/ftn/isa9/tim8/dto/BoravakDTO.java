package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import rs.ac.uns.ftn.isa9.tim8.common.CustomDateSerializer;

public class BoravakDTO {
	
	@JsonSerialize(using = CustomDateSerializer.class)
	protected Date datumDolaska;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	protected Date datumPovratka;
	
	protected String nazivDestinacije;
	
	public BoravakDTO() {
		super();
	}
	public BoravakDTO(Date datumDolaska, Date datumPovratka, String nazivDestinacije) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumPovratka = datumPovratka;
		this.nazivDestinacije = nazivDestinacije;
	}
	public Date getDatumDolaska() {
		return datumDolaska;
	}
	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	public Date getDatumPovratka() {
		return datumPovratka;
	}
	public void setDatumPovratka(Date datumPovratka) {
		this.datumPovratka = datumPovratka;
	}
	public String getNazivDestinacije() {
		return nazivDestinacije;
	}
	public void setNazivDestinacije(String nazivDestinacije) {
		this.nazivDestinacije = nazivDestinacije;
	}
	
}
