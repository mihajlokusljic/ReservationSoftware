package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaSjedista;
import rs.ac.uns.ftn.isa9.tim8.model.Sjediste;

public interface BrzaRezervacijaSjedistaRepository extends JpaRepository<BrzaRezervacijaSjedista, Long> {
	public Collection<BrzaRezervacijaSjedista> findAllBySjediste(Sjediste sjedisteZaRezervaciju);

	public Collection<BrzaRezervacijaSjedista> findBySjedisteAndDatumPolaskaAndDatumDolaska(Sjediste sjediste,
			Date datumPolaska, Date datumDolaska);

}
