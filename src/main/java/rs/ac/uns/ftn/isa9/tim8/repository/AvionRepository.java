package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Avion;

public interface AvionRepository extends JpaRepository<Avion, Long> {
	Avion findOneByNaziv(String naziv);
}
