package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class AdministratorHotela extends Osoba {
	
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
