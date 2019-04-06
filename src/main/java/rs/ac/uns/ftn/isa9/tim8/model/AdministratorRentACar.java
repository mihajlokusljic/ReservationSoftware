package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "administrator_rent_a_car")
public class AdministratorRentACar extends Osoba {
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="rentACar_id")
	protected RentACarServis rentACarServis;
	
	
	public AdministratorRentACar() {
		super();
	}

	public AdministratorRentACar(RentACarServis rentACarServis) {
		super();
		this.rentACarServis = rentACarServis;
	}

	public RentACarServis getRentACarServis() {
		return rentACarServis;
	}

	public void setRentACarServis(RentACarServis rentACarServis) {
		this.rentACarServis = rentACarServis;
	}
	

}
