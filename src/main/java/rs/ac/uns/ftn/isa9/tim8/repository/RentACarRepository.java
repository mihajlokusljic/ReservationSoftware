package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;

public interface RentACarRepository extends JpaRepository<RentACarServis, Long> {
	
	RentACarServis findOneByNaziv(String naziv);
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query(value = "select rent from RentACarServis rent where rent.naziv = :naziv")
	RentACarServis getRentByNaziv(String naziv);
	
	RentACarServis findOneByAdresa(Adresa adresa);
	
	public Optional<RentACarServis> findById(Long id);
	
}
