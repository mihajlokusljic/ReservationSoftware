package rs.ac.uns.ftn.isa9.tim8.dto;

public class PodaciPutnikaDTO {
	protected String ime;
	
	protected String prezime;
	
	protected String brojPasosa;
	
	public PodaciPutnikaDTO() {
		super();
	}
	public PodaciPutnikaDTO(String ime, String prezime, String brojPasosa) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.brojPasosa = brojPasosa;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public String getBrojPasosa() {
		return brojPasosa;
	}
	public void setBrojPasosa(String brojPasosa) {
		this.brojPasosa = brojPasosa;
	}
	
}
