package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rs.ac.uns.ftn.isa9.tim8.model.Osoba;

public interface KorisnikRepository extends JpaRepository<Osoba, Long>{
    Osoba findOneByEmail(String email);
    
    @Query(value = "select * from korisnik kor where kor.id =(select t.korisnik from verification_token t where t.token = ?1)", nativeQuery = true)
	Osoba findByToken(String token);
}
