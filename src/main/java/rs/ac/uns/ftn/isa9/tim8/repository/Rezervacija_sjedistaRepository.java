package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public interface Rezervacija_sjedistaRepository extends JpaRepository<RezervacijaSjedista, Long> {
	
	Collection<RezervacijaSjedista> findAllBySjediste(Sjediste sjediste);

	Collection<RezervacijaSjedista> findAllByPutnik(RegistrovanKorisnik registrovaniKorisnik);

	Collection<RezervacijaSjedista> findAllByLet(Let let);
	
	Collection<RezervacijaSjedista> findAllByAviokompanija(Aviokompanija aviokompanija);
}
