package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;

public interface AdresaRepository extends JpaRepository<Adresa, Long>{
	Adresa findOneByPunaAdresa(String punaAdresa);
}
