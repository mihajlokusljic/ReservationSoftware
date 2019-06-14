package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;
import java.util.Date;

public class PretragaHotelaDTO {
	protected String nazivHotela;
	protected String nazivDestinacije;
	protected String datumDolaska;
	protected String datumOdlaska;
	protected Collection<PotrebnoSobaDTO> potrebneSobe;
	
	public PretragaHotelaDTO() {
		super();
	}
	public String getNazivHotela() {
		return nazivHotela;
	}

	public void setNazivHotela(String nazivHotela) {
		this.nazivHotela = nazivHotela;
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
	
	public Collection<PotrebnoSobaDTO> getPotrebneSobe() {
		return potrebneSobe;
	}
	
	public void setPotrebneSobe(Collection<PotrebnoSobaDTO> potrebneSobe) {
		this.potrebneSobe = potrebneSobe;
	}
	
}
