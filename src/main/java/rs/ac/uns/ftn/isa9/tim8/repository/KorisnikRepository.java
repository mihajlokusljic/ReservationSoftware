package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Osoba;

public interface KorisnikRepository extends JpaRepository<Osoba, Long>{
    Osoba findOneByEmail(String email);

}
