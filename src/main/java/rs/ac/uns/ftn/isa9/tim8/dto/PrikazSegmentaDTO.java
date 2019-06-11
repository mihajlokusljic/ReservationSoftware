package rs.ac.uns.ftn.isa9.tim8.dto;

public class PrikazSegmentaDTO {
	protected String oznakaSegmenta;

	protected double cijenaSegmenta;

	protected String nazivSegmenta;

	public PrikazSegmentaDTO() {
		super();
	}

	public PrikazSegmentaDTO(String oznakaSegmenta, double cijenaSegmenta, String nazivSegmenta) {
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

	public double getCijenaSegmenta() {
		return cijenaSegmenta;
	}

	public void setCijenaSegmenta(double cijenaSegmenta) {
		this.cijenaSegmenta = cijenaSegmenta;
	}

	public String getNazivSegmenta() {
		return nazivSegmenta;
	}

	public void setNazivSegmenta(String nazivSegmenta) {
		this.nazivSegmenta = nazivSegmenta;
	}

}
