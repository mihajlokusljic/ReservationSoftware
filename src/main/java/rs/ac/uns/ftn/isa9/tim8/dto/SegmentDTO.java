package rs.ac.uns.ftn.isa9.tim8.dto;

public class SegmentDTO {
	protected String naziv;
	protected Long idAviona;

	public SegmentDTO() {
		super();
	}

	public SegmentDTO(String naziv, Long idAviona) {
		super();
		this.naziv = naziv;
		this.idAviona = idAviona;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Long getIdAviona() {
		return idAviona;
	}

	public void setIdAviona(Long idAviona) {
		this.idAviona = idAviona;
	}

}
