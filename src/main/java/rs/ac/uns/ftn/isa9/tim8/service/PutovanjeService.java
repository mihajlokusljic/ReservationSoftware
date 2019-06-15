package rs.ac.uns.ftn.isa9.tim8.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Putovanje;
import rs.ac.uns.ftn.isa9.tim8.repository.PutovanjeRepository;

@Service
public class PutovanjeService {

	@Autowired
	protected PutovanjeRepository putovanjeRepository;

	public Putovanje dobaviPutovanje(Long idPutovanja) throws NevalidniPodaciException {
		Optional<Putovanje> putovanjeSearch = putovanjeRepository.findById(idPutovanja);

		if (!putovanjeSearch.isPresent()) {
			throw new NevalidniPodaciException("Ne postoji putovanje sa datim id-jem.");
		}

		Putovanje putovanje = putovanjeSearch.get();

		return putovanje;
	}

}
