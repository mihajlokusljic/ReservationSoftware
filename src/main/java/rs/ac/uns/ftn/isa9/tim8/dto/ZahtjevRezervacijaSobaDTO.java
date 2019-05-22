package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;

public class ZahtjevRezervacijaSobaDTO {
	protected Collection<Long> sobeZaRezervacijuIds;
	protected Collection<Long> dodatneUslugeIds;
	protected Long putovanjeId;
	protected String datumDolaska;
	protected String datumOdlaksa;
	
	public ZahtjevRezervacijaSobaDTO() {
		super();
	}
	public Collection<Long> getSobeZaRezervacijuIds() {
		return sobeZaRezervacijuIds;
	}
	public void setSobeZaRezervacijuIds(Collection<Long> sobeZaRezervacijuIds) {
		this.sobeZaRezervacijuIds = sobeZaRezervacijuIds;
	}
	public Collection<Long> getDodatneUslugeIds() {
		return dodatneUslugeIds;
	}
	public void setDodatneUslugeIds(Collection<Long> dodatneUslugeIds) {
		this.dodatneUslugeIds = dodatneUslugeIds;
	}
	public Long getPutovanjeId() {
		return putovanjeId;
	}
	public void setPutovanjeId(Long putovanjeId) {
		this.putovanjeId = putovanjeId;
	}
	public String getDatumDolaska() {
		return datumDolaska;
	}
	public void setDatumDolaska(String datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	public String getDatumOdlaksa() {
		return datumOdlaksa;
	}
	public void setDatumOdlaksa(String datumOdlaksa) {
		this.datumOdlaksa = datumOdlaksa;
	}
	
}
