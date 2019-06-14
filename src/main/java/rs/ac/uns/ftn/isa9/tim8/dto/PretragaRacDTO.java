package rs.ac.uns.ftn.isa9.tim8.dto;

public class PretragaRacDTO {
	
	protected String nazivRacServisa;
	protected String nazivDestinacije;
	protected String datumDolaska;
	protected String datumOdlaska;
	
	public PretragaRacDTO() {
		super();
	}
	public PretragaRacDTO(String nazivRacServisa, String nazivDestinacije, String datumDolaska, String datumOdlaska) {
		super();
		this.nazivRacServisa = nazivRacServisa;
		this.nazivDestinacije = nazivDestinacije;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
	}
	public String getNazivRacServisa() {
		return nazivRacServisa;
	}
	public void setNazivRacServisa(String nazivRacServisa) {
		this.nazivRacServisa = nazivRacServisa;
	}
	public String getNazivDestinacije() {
		return nazivDestinacije;
	}
	public void setNazivDestinacije(String nazivDestinacije) {
		this.nazivDestinacije = nazivDestinacije;
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
	
}


