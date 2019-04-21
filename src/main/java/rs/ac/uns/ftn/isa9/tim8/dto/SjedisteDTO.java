package rs.ac.uns.ftn.isa9.tim8.dto;

public class SjedisteDTO {
	protected int red;
	protected int kolona;
	protected Long idAviona;
	protected Long idSegmenta;

	public SjedisteDTO() {
		super();
	}

	public SjedisteDTO(int red, int kolona, Long idAviona, Long idSegmenta) {
		super();
		this.red = red;
		this.kolona = kolona;
		this.idAviona = idAviona;
		this.idSegmenta = idSegmenta;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getKolona() {
		return kolona;
	}

	public void setKolona(int kolona) {
		this.kolona = kolona;
	}

	public Long getIdAviona() {
		return idAviona;
	}

	public void setIdAviona(Long idAviona) {
		this.idAviona = idAviona;
	}

	public Long getIdSegmenta() {
		return idSegmenta;
	}

	public void setIdSegmenta(Long idSegmenta) {
		this.idSegmenta = idSegmenta;
	}

}
