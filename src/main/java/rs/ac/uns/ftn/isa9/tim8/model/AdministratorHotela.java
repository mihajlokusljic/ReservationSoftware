package rs.ac.uns.ftn.isa9.tim8.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AdministratorHotela extends Osoba {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3294064177720081642L;
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name="hotel_id")
	protected Hotel hotel;
	
	public AdministratorHotela() {
		super();
	}

	public AdministratorHotela(Hotel hotel) {
		super();
		this.hotel = hotel;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	
}
