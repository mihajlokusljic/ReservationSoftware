package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;

public class BrzaRezervacijaSobeDTO {
	protected Long id;
	protected Long idSobe;
	protected String datumDolaska;
	protected String datumOdlaska;
	protected double cijenaBoravka;
	protected int procenatPopusta;
	protected Collection<Long> dodatneUslugeIds;
	public BrzaRezervacijaSobeDTO() {
		super();
	}
	public BrzaRezervacijaSobeDTO(Long id, Long idSobe, String datumDolaska, String datumOdlaska, double cijenaBoravka,
			int procenatPopusta, Collection<Long> dodatneUslugeIds) {
		super();
		this.id = id;
		this.idSobe = idSobe;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.cijenaBoravka = cijenaBoravka;
		this.procenatPopusta = procenatPopusta;
		this.dodatneUslugeIds = dodatneUslugeIds;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdSobe() {
		return idSobe;
	}
	public void setIdSobe(Long idSobe) {
		this.idSobe = idSobe;
	}
	public String getDatumDolaska() {
		return datumDolaska;
	}
	public void setDatumDolaska(String datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	public String getDatumOdlaska() {
		return datumOdlaska;
	}
	public void setDatumOdlaska(String datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}
	public double getCijenaBoravka() {
		return cijenaBoravka;
	}
	public void setCijenaBoravka(double cijenaBoravka) {
		this.cijenaBoravka = cijenaBoravka;
	}
	public int getProcenatPopusta() {
		return procenatPopusta;
	}
	public void setProcenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}
	public Collection<Long> getDodatneUslugeIds() {
		return dodatneUslugeIds;
	}
	public void setDodatneUslugeIds(Collection<Long> dodatneUslugeIds) {
		this.dodatneUslugeIds = dodatneUslugeIds;
	}
	
}
