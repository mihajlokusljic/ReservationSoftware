package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;

public interface AviokompanijaRepository extends JpaRepository<Aviokompanija, Long> {

	Aviokompanija findOneByNaziv(String naziv);
	
}
