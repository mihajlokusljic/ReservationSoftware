package rs.ac.uns.ftn.isa9.tim8.dto;

public class PrikazSegmentaDTO {
	protected String oznakaSegmenta;

	protected float cijenaSegmenta;

	protected String nazivSegmenta;

	public PrikazSegmentaDTO() {
		super();
	}

	public PrikazSegmentaDTO(String oznakaSegmenta, float cijenaSegmenta, String nazivSegmenta) {
		super();
		this.oznakaSegmenta = oznakaSegmenta;
		this.cijenaSegmenta = cijenaSegmenta;
		this.nazivSegmenta = nazivSegmenta;
	}

	public String getOznakaSegmenta() {
		return oznakaSegmenta;
	}

	public void setOznakaSegmenta(String oznakaSegmenta) {
		this.oznakaSegmenta = oznakaSegmenta;
	}

	public float getCijenaSegmenta() {
		return cijenaSegmenta;
	}

	public void setCijenaSegmenta(float cijenaSegmenta) {
		this.cijenaSegmenta = cijenaSegmenta;
	}

	public String getNazivSegmenta() {
		return nazivSegmenta;
	}

	public void setNazivSegmenta(String nazivSegmenta) {
		this.nazivSegmenta = nazivSegmenta;
	}

}
