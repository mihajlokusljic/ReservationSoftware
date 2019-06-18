package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;

public interface RentACarRepository extends JpaRepository<RentACarServis, Long> {
	RentACarServis findOneByNaziv(String naziv);
	RentACarServis findOneByAdresa(Adresa adresa);
	
	public Optional<RentACarServis> findById(Long id);
}
