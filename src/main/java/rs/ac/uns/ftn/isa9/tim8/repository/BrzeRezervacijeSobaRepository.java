package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;

public interface BrzeRezervacijeSobaRepository extends JpaRepository<BrzaRezervacijaSoba, Long> {

	public Collection<BrzaRezervacijaSoba> findAllBySobaZaRezervaciju(HotelskaSoba sobaZaRezervaciju);
}
