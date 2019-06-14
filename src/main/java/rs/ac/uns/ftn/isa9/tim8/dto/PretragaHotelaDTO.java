package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;

public class PretragaHotelaDTO {
	protected String nazivHotelaIliDestinacije;
	protected String datumDolaska;
	protected String datumOdlaska;
	protected Collection<PotrebnoSobaDTO> potrebneSobe;
	public PretragaHotelaDTO() {
		super();
	}
	public String getNazivHotelaIliDestinacije() {
		return nazivHotelaIliDestinacije;
	}
	public void setNazivHotelaIliDestinacije(String nazivHotelaIliDestinacije) {
		this.nazivHotelaIliDestinacije = nazivHotelaIliDestinacije;
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
