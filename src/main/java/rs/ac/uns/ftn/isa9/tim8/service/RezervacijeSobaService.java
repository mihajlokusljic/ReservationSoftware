package rs.ac.uns.ftn.isa9.tim8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.repository.BrzeRezervacijeSobaRepository;

@Service
public class RezervacijeSobaService {
	
	@Autowired
	protected BrzeRezervacijeSobaRepository brzeRezervacijeRepository;

}
