package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "hotel")
public class Hotel extends Poslovnica {
	
	@JsonIgnore
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
