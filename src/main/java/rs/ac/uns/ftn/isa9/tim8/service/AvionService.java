package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Avion;
import rs.ac.uns.ftn.isa9.tim8.repository.AvionRepository;

@Service
public class AvionService {
	
	@Autowired
	protected AvionRepository avionRepository;
	
	public Collection<Avion> dobaviAvione() {
		return avionRepository.findAll();
	}

	public Avion dodajAvion(Avion noviAvion) {
		Avion a = new Avion();
		return a;
	}

	
	/*

	
	 */
}
