package rs.ac.uns.ftn.isa9.tim8.dto;

public class FilijalaDTO {
	protected String punaAdresa;
	protected Long Id;

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

	public FilijalaDTO(String punaAdresa, Long ID) {
		super();
		this.punaAdresa = punaAdresa;
		this.Id = ID;
	}
	
	public FilijalaDTO() {
		super();
	}
	
}
