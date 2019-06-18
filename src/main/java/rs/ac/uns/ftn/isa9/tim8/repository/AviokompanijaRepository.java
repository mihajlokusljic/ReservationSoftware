package rs.ac.uns.ftn.isa9.tim8.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;

public interface AviokompanijaRepository extends JpaRepository<Aviokompanija, Long> {

	Aviokompanija findOneByNaziv(String naziv);
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("SELECT a FROM Aviokompanija a WHERE a.id = :id")
	Aviokompanija getHotelById(Long id);
	
}
