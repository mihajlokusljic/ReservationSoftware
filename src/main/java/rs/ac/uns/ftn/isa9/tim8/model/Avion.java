package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "avion")
public class Avion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "nazivAviona", nullable = false)
	protected String naziv;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="avion_segment", joinColumns=@JoinColumn(name="avion_id"), inverseJoinColumns=@JoinColumn(name="segment_id"))	
	protected Set<Segment> segmenti;
	
	@OneToMany(mappedBy = "avion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy("red ASC kolona ASC")
	protected Set<Sjediste> sjedista;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "aviokompanija_id")
	protected Aviokompanija aviokompanija;

	public Avion() {
		this.segmenti = new HashSet<Segment>();
		this.sjedista = new HashSet<Sjediste>();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<Segment> getSegment() {
		return segmenti;
	}

	public void setSegment(Set<Segment> segmenti) {
		this.segmenti = segmenti;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public Set<Segment> getSegmenti() {
		return segmenti;
	}

	public void setSegmenti(Set<Segment> segmenti) {
		this.segmenti = segmenti;
	}

	public Set<Sjediste> getSjedista() {
		return sjedista;
	}

	public void setSjedista(Set<Sjediste> sjedista) {
		this.sjedista = sjedista;
	}

	public Aviokompanija getAviokompanija() {
		return aviokompanija;
	}

	public void setAviokompanija(Aviokompanija aviokompanija) {
		this.aviokompanija = aviokompanija;
	}
	


}
