package rs.ac.uns.ftn.isa9.tim8.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	protected final Log LOGGER = LogFactory.getLog(getClass());

	
	@Autowired
	protected KorisnikRepository korisnikRepository;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected AuthenticationManager authenticationManager;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Osoba user = korisnikRepository.findOneByKorisnickoIme(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}
	
	// Funkcija pomocu koje korisnik menja svoju lozinku
		public void changePassword(String oldPassword, String newPassword) {

			Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
			String username = currentUser.getName();

			if (authenticationManager != null) {
				LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
			} else {
				LOGGER.debug("No authentication manager set. can't change Password!");

				return;
			}

			LOGGER.debug("Changing password for user '" + username + "'");

			Osoba user = (Osoba) loadUserByUsername(username);

			// pre nego sto u bazu upisemo novu lozinku, potrebno ju je hesirati
			// ne zelimo da u bazi cuvamo lozinke u plain text formatu
			user.setKorisnickoIme(passwordEncoder.encode(newPassword));
			korisnikRepository.save(user);

		}
		

}
