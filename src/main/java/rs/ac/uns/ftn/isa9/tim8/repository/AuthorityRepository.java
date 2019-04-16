package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Authority;
import rs.ac.uns.ftn.isa9.tim8.model.TipKorisnika;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	
	Authority findOneByTipKorisnika(TipKorisnika tipKorisnika);
	
}
