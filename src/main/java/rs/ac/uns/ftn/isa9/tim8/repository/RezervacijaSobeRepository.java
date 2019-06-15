package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.HotelskaSoba;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSobe;

public interface RezervacijaSobeRepository extends JpaRepository<RezervacijaSobe, Long> {
	
	public Collection<RezervacijaSobe> findAllByRezervisanaSoba(HotelskaSoba soba);
	
}
