package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;
import java.util.Date;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.Let;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public interface BrzaRezervacijaSjedistaRepository extends JpaRepository<BrzaRezervacijaSjedista, Long> {
	public Collection<BrzaRezervacijaSjedista> findAllBySjediste(Sjediste sjedisteZaRezervaciju);

	public Collection<BrzaRezervacijaSjedista> findBySjedisteAndDatumPolaskaAndDatumDolaska(Sjediste sjediste,
			Date datumPolaska, Date datumDolaska);
	
	public Collection<BrzaRezervacijaSjedista> findAllByLet(Let let);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT brza FROM BrzaRezervacijaSjedista brza WHERE brza.id = :id")
	public BrzaRezervacijaSjedista getBrzaRezervacijaById(Long id);

}
