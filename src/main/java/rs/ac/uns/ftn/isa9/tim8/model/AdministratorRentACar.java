package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
public class AdministratorRentACar extends Osoba {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6987577935608408308L;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
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
