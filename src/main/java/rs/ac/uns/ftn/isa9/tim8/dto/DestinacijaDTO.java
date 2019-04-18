package rs.ac.uns.ftn.isa9.tim8.dto;

public class DestinacijaDTO {
	protected String naziv;
	protected String punaAdresa;

	public DestinacijaDTO() {
		super();
	}

	public DestinacijaDTO(String naziv, String punaAdresa) {
		super();
		this.naziv = naziv;
		this.punaAdresa = punaAdresa;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getPunaAdresa() {
		return punaAdresa;
	}

	public void setPunaAdresa(String punaAdresa) {
		this.punaAdresa = punaAdresa;
	}

}
