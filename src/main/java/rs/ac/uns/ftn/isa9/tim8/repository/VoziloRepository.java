package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface VoziloRepository extends JpaRepository<Vozilo, Long> {
	
	Vozilo findOneByNaziv(String naziv);
	List<Vozilo> findAllByFilijala(Filijala filijala);
	public Collection<Vozilo> findAllByRentACar(RentACarServis rentACar);
}
