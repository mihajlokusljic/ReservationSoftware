package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Destinacija;

public interface DestinacijeRepository extends JpaRepository<Destinacija, Long> {
	Destinacija findOneByAdresa(Adresa adresa);
	Destinacija findOneByNazivDestinacije(String nazivDestinacije);
}
