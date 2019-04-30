package rs.ac.uns.ftn.isa9.tim8.dto;

public class FilijalaDTO {
	protected String punaAdresa;
	protected Long Id;
	protected int brojVozila;

	public String getPunaAdresa() {
		return punaAdresa;
	}

	public void setPunaAdresa(String punaAdresa) {
		this.punaAdresa = punaAdresa;
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public FilijalaDTO(String punaAdresa, Long ID, int brojVozila) {
		super();
		this.punaAdresa = punaAdresa;
		this.Id = ID;
		this.brojVozila = brojVozila;
	}
	
	public FilijalaDTO() {
		super();
		this.brojVozila = 0;

	}

	public int getBrojVozila() {
		return brojVozila;
	}

	public void setBrojVozila(int brojVozila) {
		this.brojVozila = brojVozila;
	}
	
	
}
