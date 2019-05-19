package rs.ac.uns.ftn.isa9.tim8.dto;

public class PretragaPrijateljaDTO {
	protected Long Id;

	protected String ime;

	protected String prezime;

	public PretragaPrijateljaDTO() {
		super();
	}

	public PretragaPrijateljaDTO(Long id, String ime, String prezime) {
		super();
		Id = id;
		this.ime = ime;
		this.prezime = prezime;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

}
