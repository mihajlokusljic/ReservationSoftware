package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


public class Hotel extends Servis {
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "hotel")
	protected Set<HotelskaSoba> sobe;
	
	public Hotel() {
		super();
	}

	public Hotel(Set<HotelskaSoba> sobe) {
		super();
		this.sobe = sobe;
	}

	public Set<HotelskaSoba> getSobe() {
		return sobe;
	}

	public void setSobe(Set<HotelskaSoba> sobe) {
		this.sobe = sobe;
	}

}
