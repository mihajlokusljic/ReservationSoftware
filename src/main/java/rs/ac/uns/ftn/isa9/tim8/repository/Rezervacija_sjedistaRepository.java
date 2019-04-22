package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public interface Rezervacija_sjedistaRepository extends JpaRepository<RezervacijaSjedista, Long> {
	Collection<RezervacijaSjedista> findAllBySjediste(Sjediste sjediste);
}
