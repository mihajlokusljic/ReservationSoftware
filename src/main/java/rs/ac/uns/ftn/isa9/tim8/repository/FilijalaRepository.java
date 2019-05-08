package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Filijala;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;


public interface FilijalaRepository extends JpaRepository<Filijala, Long>  {
	List<Filijala> findAllByRentACar(RentACarServis rentACar);
	Filijala findOneByAdresa(Adresa adresa);
}
