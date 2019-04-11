package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Let;

public interface LetoviRepository extends JpaRepository<Let, Long> {
	Let findOneByBrojLeta(String brojLeta);
}
