package rs.ac.uns.ftn.isa9.tim8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.VerificationToken;
import rs.ac.uns.ftn.isa9.tim8.repository.VerificationRepository;

@Service
public class VerificationService {

	@Autowired
	protected VerificationRepository verificationRepository;
	
	public void saveToken(VerificationToken token) {
		verificationRepository.save(token);
	}
}
