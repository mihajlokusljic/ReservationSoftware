package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CjenovnikLeta")
public class CjenovnikLeta {
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Let let;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Segment segment;
	
	@Column(name = "cijenaLeta", unique = false, nullable = false)
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
