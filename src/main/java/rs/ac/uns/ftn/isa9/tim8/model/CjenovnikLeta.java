package rs.ac.uns.ftn.isa9.tim8.model;

public class CjenovnikLeta {
	protected Let let;
	protected Segment segment;
	protected double cijena;

	public CjenovnikLeta() {
		super();
	}

	public Let getLet() {
		return let;
	}

	public void setLet(Let let) {
		this.let = let;
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

}
