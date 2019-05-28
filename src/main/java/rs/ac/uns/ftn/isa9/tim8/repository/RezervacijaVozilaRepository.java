package rs.ac.uns.ftn.isa9.tim8.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.RentACarServis;
import rs.ac.uns.ftn.isa9.tim8.model.RezervacijaVozila;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public interface RezervacijaVozilaRepository extends JpaRepository<RezervacijaVozila, Long> {
	
	List<RezervacijaVozila> findAllByRezervisanoVozilo(Vozilo vozilo);

	List<RezervacijaVozila> findAllByPutnik(RegistrovanKorisnik registrovaniKorisnik);
	
	List<RezervacijaVozila> findAllByRentACarServis(RentACarServis rac);
	
}
