package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;

public interface RezervacijeSobaRepository extends JpaRepository<RezervacijaSobe, Long> {
	Collection<RezervacijaSobe> findAllByRezervisanaSoba(HotelskaSoba rezervisanaSoba);
}
