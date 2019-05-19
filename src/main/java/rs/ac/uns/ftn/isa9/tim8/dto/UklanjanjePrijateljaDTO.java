package rs.ac.uns.ftn.isa9.tim8.dto;

public class UklanjanjePrijateljaDTO {
	protected Long korisnikId;

	protected Long prijateljZaUklonitiId;

	public UklanjanjePrijateljaDTO() {
		super();
	}

	public UklanjanjePrijateljaDTO(Long korisnikId, Long prijateljZaUklonitiId) {
		super();
		this.korisnikId = korisnikId;
		this.prijateljZaUklonitiId = prijateljZaUklonitiId;
	}

	public Long getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(Long korisnikId) {
		this.korisnikId = korisnikId;
	}

	public Long getPrijateljZaUklonitiId() {
		return prijateljZaUklonitiId;
	}

	public void setPrijateljZaUklonitiId(Long prijateljZaUklonitiId) {
		this.prijateljZaUklonitiId = prijateljZaUklonitiId;
	}

}
