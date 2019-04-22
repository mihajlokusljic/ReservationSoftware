package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sjediste")
public class Sjediste {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "segment_id")
	protected Segment segment;

	@Column(name = "red", nullable = false)
	int red;

	@Column(name = "kolona", nullable = false)
	int kolona;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avion_id")
	protected Avion avion;

	public Sjediste() {
		super();
	}

	public Sjediste(Segment segment, int red, int kolona, Avion avion) {
		super();
		this.segment = segment;
		this.red = red;
		this.kolona = kolona;
		this.avion = avion;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (obj instanceof Sjediste) {
			Sjediste other = (Sjediste) obj;
			return other.getAvion().getId() == this.getAvion().getId() && other.getRed() == this.getRed()
					&& other.getKolona() == this.getKolona();
		}

		return false;
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

}
