package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.BrzaRezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface BrzeRezervacijeVozilaRepository extends JpaRepository<BrzaRezervacijaVozila, Long>{
	
	public Collection<BrzaRezervacijaVozila> findAllByVozilo(Vozilo voziloZaRezervaciju);
	public Collection<BrzaRezervacijaVozila> findByVoziloAndDatumPreuzimanjaVozilaAndDatumVracanjaVozila(Vozilo vozilo, Date datumP, Date datumV);
}
