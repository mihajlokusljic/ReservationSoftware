package rs.ac.uns.ftn.isa9.tim8.dto;

public class PretragaSobaDTO {
	protected String datumDolaska;
	protected String datumOdlaska;
	protected Long idHotela;
	protected Double minCijenaBoravka;
	protected Double maxCijenaBoravka;
	public PretragaSobaDTO() {
		super();
	}
	public PretragaSobaDTO(String datumDolaska, String datumOdlaska, Long idHotela) {
		super();
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
		this.idHotela = idHotela;
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
	public Long getIdHotela() {
		return idHotela;
	}
	public void setIdHotela(Long idHotela) {
		this.idHotela = idHotela;
	}
	public Double getMinCijenaBoravka() {
		return minCijenaBoravka;
	}
	public void setMinCijenaBoravka(Double minCijenaBoravka) {
		this.minCijenaBoravka = minCijenaBoravka;
	}
	public Double getMaxCijenaBoravka() {
		return maxCijenaBoravka;
	}
	public void setMaxCijenaBoravka(Double maxCijenaBoravka) {
		this.maxCijenaBoravka = maxCijenaBoravka;
	}
	
}
