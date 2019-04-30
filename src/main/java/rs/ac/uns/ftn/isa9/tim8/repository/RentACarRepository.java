package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;

public interface RentACarRepository extends JpaRepository<RentACarServis, Long> {
	RentACarServis findOneByNaziv(String naziv);
	RentACarServis findOneByAdresa(Adresa adresa);
}
