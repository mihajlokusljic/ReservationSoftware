package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;

public interface HotelskaSobaRepository extends JpaRepository<HotelskaSoba, Long> {

	public Collection<HotelskaSoba> findByHotel(Hotel hotel);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM HotelskaSoba s WHERE s.id = :id")
	HotelskaSoba zakljucajSobu(Long id);
	
}
