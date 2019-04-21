package rs.ac.uns.ftn.isa9.tim8.dto;

public class AvionDTO {
	protected String naziv;
	protected Long idAviokompanije;

	public AvionDTO() {
		super();
	}

	public AvionDTO(String naziv, Long idAviokompanije) {
		super();
		this.naziv = naziv;
		this.idAviokompanije = idAviokompanije;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Long getIdAviokompanije() {
		return idAviokompanije;
	}

	public void setIdAviokompanije(Long idAviokompanije) {
		this.idAviokompanije = idAviokompanije;
	}

}
