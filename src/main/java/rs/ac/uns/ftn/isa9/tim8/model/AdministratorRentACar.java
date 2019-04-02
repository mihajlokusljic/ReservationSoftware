package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity
public class AdministratorRentACar extends Osoba {
	@OneToOne(fetch = FetchType.LAZY)
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
