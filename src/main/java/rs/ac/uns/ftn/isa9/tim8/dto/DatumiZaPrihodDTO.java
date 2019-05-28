package rs.ac.uns.ftn.isa9.tim8.dto;

public class DatumiZaPrihodDTO {
	
	protected String datumPocetni;
	
	protected String datumKrajnji;
	
	public DatumiZaPrihodDTO(String datumPocetni, String datumKrajnji) {
		super();
		this.datumPocetni = datumPocetni;
		this.datumKrajnji = datumKrajnji;
	}

	public DatumiZaPrihodDTO() {
		super();
	}

	public String getDatumPocetni() {
		return datumPocetni;
	}

	public void setDatumPocetni(String datumPocetni) {
		this.datumPocetni = datumPocetni;
	}

	public String getDatumKrajnji() {
		return datumKrajnji;
	}

	public void setDatumKrajnji(String datumKrajnji) {
		this.datumKrajnji = datumKrajnji;
	}
	
}
