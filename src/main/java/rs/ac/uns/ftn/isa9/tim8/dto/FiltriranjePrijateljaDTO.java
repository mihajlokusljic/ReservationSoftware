package rs.ac.uns.ftn.isa9.tim8.dto;

public class FiltriranjePrijateljaDTO {
	protected String imePrezime;

	protected Long idKorisnika;

	protected Boolean pregledPrijatelja; // ako je FALSE onda se vrsi prikaz prijatelja za dodavanje

	public FiltriranjePrijateljaDTO() {
		super();
	}

	public FiltriranjePrijateljaDTO(String imePrezime, Long idKorisnika, Boolean pregledPrijatelja) {
		super();
		this.imePrezime = imePrezime;
		this.idKorisnika = idKorisnika;
		this.pregledPrijatelja = pregledPrijatelja;
	}

	public String getImePrezime() {
		return imePrezime;
	}

	public void setImePrezime(String imePrezime) {
		this.imePrezime = imePrezime;
	}

	public Long getIdKorisnika() {
		return idKorisnika;
	}

	public void setIdKorisnika(Long idKorisnika) {
		this.idKorisnika = idKorisnika;
	}

	public Boolean getPregledPrijatelja() {
		return pregledPrijatelja;
	}

	public void setPregledPrijatelja(Boolean pregledPrijatelja) {
		this.pregledPrijatelja = pregledPrijatelja;
	}

}
