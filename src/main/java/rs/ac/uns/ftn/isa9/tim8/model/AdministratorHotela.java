package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class AdministratorHotela extends Osoba {
	
	//@ManyToOne(fetch = FetchType.EAGER)
	//@JoinColumn(name = "hotel_id")
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
