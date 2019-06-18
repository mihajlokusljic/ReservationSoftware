package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;

public interface BrzeRezervacijeSobaRepository extends JpaRepository<BrzaRezervacijaSoba, Long> {

	public Collection<BrzaRezervacijaSoba> findAllBySobaZaRezervaciju(HotelskaSoba sobaZaRezervaciju);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT brza FROM BrzaRezervacijaSoba brza WHERE brza.id = :id")
	BrzaRezervacijaSoba zakljucajBrzuRezervaciju(Long id);
}
