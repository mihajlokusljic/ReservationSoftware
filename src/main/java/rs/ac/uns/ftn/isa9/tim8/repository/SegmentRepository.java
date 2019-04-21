package rs.ac.uns.ftn.isa9.tim8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.ac.uns.ftn.isa9.tim8.model.Segment;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
	Segment findOneByNaziv(String naziv);
}
