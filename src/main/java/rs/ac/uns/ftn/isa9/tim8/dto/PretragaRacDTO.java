package rs.ac.uns.ftn.isa9.tim8.dto;

public class PretragaRacDTO {
	
	protected String nazivRacIliDestinacije;
	protected String datumDolaska;
	protected String datumOdlaska;
	
	public PretragaRacDTO(String nazivRacIliDestinacije, String datumDolaska, String datumOdlaska) {
		super();
		this.nazivRacIliDestinacije = nazivRacIliDestinacije;
		this.datumDolaska = datumDolaska;
		this.datumOdlaska = datumOdlaska;
	}

	public PretragaRacDTO() {
		super();
	}

	public String getNazivRacIliDestinacije() {
		return nazivRacIliDestinacije;
	}

	public void setNazivRacIliDestinacije(String nazivRacIliDestinacije) {
		this.nazivRacIliDestinacije = nazivRacIliDestinacije;
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


