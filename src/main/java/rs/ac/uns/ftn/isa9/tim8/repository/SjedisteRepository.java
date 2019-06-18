package rs.ac.uns.ftn.isa9.tim8.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public interface SjedisteRepository extends JpaRepository<Sjediste, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT s FROM Sjediste s WHERE s.id = :id")
	Sjediste getSjedisteById(Long id);
	
}
