package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface VoziloRepository extends JpaRepository<Vozilo, Long> {
	
	Vozilo findOneById(Long id);
	Vozilo findOneByNaziv(String naziv);

}
