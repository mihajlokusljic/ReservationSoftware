package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

public class Sjediste {
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	Segment segment;
	
	@Column(name = "red", unique = false, nullable = false)
	int red;
	
	@Column(name = "kolona", unique = false, nullable = false)
	int kolona;

	public Sjediste() {
		super();
	}

	public Segment getSegment() {
		return segment;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
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

}
