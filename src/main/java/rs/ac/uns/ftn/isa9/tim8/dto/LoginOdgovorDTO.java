package rs.ac.uns.ftn.isa9.tim8.dto;

import rs.ac.uns.ftn.isa9.tim8.model.Poslovnica;

public class LoginOdgovorDTO {
	protected boolean logovanjeUspjesno;
	protected String poruka;
	protected String redirekcioniUrl;
	protected Poslovnica poslovnica;
	public LoginOdgovorDTO() {
		super();
	}
	public boolean isLogovanjeUspjesno() {
		return logovanjeUspjesno;
	}
	
	public LoginOdgovorDTO(boolean logovanjeUspjesno, String poruka, String redirekcioniUrl, Poslovnica poslovnica) {
		super();
		this.logovanjeUspjesno = logovanjeUspjesno;
		this.poruka = poruka;
		this.redirekcioniUrl = redirekcioniUrl;
		this.poslovnica = poslovnica;
	}
	
	public void setLogovanjeUspjesno(boolean logovanjeUspjesno) {
		this.logovanjeUspjesno = logovanjeUspjesno;
	}
	public String getPoruka() {
		return poruka;
	}
	public void setPoruka(String poruka) {
		this.poruka = poruka;
	}
	public String getRedirekcioniUrl() {
		return redirekcioniUrl;
	}
	public void setRedirekcioniUrl(String redirekcioniUrl) {
		this.redirekcioniUrl = redirekcioniUrl;
	}
	public Poslovnica getPoslovnica() {
		return poslovnica;
	}
	public void setPoslovnica(Poslovnica poslovnica) {
		this.poslovnica = poslovnica;
	}
	
}
