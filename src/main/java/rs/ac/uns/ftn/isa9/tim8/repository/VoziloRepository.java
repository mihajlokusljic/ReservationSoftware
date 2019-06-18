package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface VoziloRepository extends JpaRepository<Vozilo, Long> {
	
	Vozilo findOneByNaziv(String naziv);
	List<Vozilo> findAllByFilijala(Filijala filijala);
	public Collection<Vozilo> findAllByRentACar(RentACarServis rentACar);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT v FROM Vozilo v WHERE v.id = :id")
	Vozilo zakljucajVozilo(Long id);
}
