package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

public class Avion {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	//@Column(name = "nazivAviona", unique = true, nullable = false)
	protected String naziv;
	
	//@OneToMany(mappedBy = "segmenti", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<Segment> segment;
	
	//@OneToMany(mappedBy = "sjedista", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
