package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filijala")
public class Filijala {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "adresa_id", unique = true, referencedColumnName = "id")
	protected Adresa adresa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rent_a_car_servis_id")
	protected RentACarServis rentACar;

	public Filijala() {
		super();
	}

	public Filijala(Adresa adresa) {
		super();
		this.adresa = adresa;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RentACarServis getRentACar() {
		return rentACar;
	}

	public void setRentACar(RentACarServis rentACar) {
		this.rentACar = rentACar;
	}
	
	

}
