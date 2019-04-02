package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class Avion {
	protected String naziv;
	protected Set<Segment> segment;
	protected Set<Sjediste> sjedista;

	public Avion() {
		super();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<Segment> getSegment() {
		return segment;
	}

	public void setSegment(Set<Segment> segment) {
		this.segment = segment;
	}

	public Set<Sjediste> getSjedista() {
		return sjedista;
	}

	public void setSjedista(Set<Sjediste> sjedista) {
		this.sjedista = sjedista;
	}

}
