package rs.ac.uns.ftn.isa9.tim8.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{
	Hotel findOneByNaziv(String naziv);
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("SELECT h FROM Hotel h WHERE h.id = :id")
	Hotel getHotelById(Long id);

}
