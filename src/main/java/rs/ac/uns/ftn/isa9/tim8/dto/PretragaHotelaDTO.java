package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;
import java.util.Date;

public class PretragaHotelaDTO {
	protected String nazivHotelaIliDestinacije;
	protected Date datumDolaska;
	protected Date datumOdlaska;
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
	public Date getDatumDolaska() {
		return datumDolaska;
	}
	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}
	public Date getDatumOdlaska() {
		return datumOdlaska;
	}
	public void setDatumOdlaska(Date datumOdlaska) {
		this.datumOdlaska = datumOdlaska;
	}
	public Collection<PotrebnoSobaDTO> getPotrebneSobe() {
		return potrebneSobe;
	}
	public void setPotrebneSobe(Collection<PotrebnoSobaDTO> potrebneSobe) {
		this.potrebneSobe = potrebneSobe;
	}
	
}
