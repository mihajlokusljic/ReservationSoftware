package rs.ac.uns.ftn.isa9.tim8.model;

public class AdministratorRentACar extends Osoba {
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
