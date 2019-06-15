package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;

public interface HotelskaSobaRepository extends JpaRepository<HotelskaSoba, Long> {

	public Collection<HotelskaSoba> findByHotel(Hotel hotel);
	
}
