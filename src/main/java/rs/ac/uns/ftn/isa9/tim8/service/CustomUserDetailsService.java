package rs.ac.uns.ftn.isa9.tim8.service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;

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

import rs.ac.uns.ftn.isa9.tim8.dto.RegistracijaAdminaDTO;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorAviokompanije;
import rs.ac.uns.ftn.isa9.tim8.model.Authority;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.TipKorisnika;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	protected KorisnikRepository korisnikRepository;

	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Osoba user = korisnikRepository.findOneByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return user;
		}
	}

	public boolean emailZauzet(String email) {
		Osoba korisnik = korisnikRepository.findOneByEmail(email);
		if (korisnik != null) {
			return true;
		}
		return false;
	}

	public String dodajRegistrovanogKorisnika(RegistrovanKorisnik korisnik) {

		korisnikRepository.save(korisnik);
		return null;
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
		user.setLozinka(passwordEncoder.encode(newPassword));
		korisnikRepository.save(user);

	}

	public String encodePassword(String password) {
		return this.passwordEncoder.encode(password);
	}

	public boolean dodajAdminaAviokompanije(RegistracijaAdminaDTO adminReg) {
		// TODO Auto-generated method stub
		if (this.emailZauzet(adminReg.getEmail())) {
			return false;
		}

		Optional<Aviokompanija> aviokompanijaSearch = this.aviokompanijaRepository.findById(adminReg.getIdPoslovnice());
		if (!aviokompanijaSearch.isPresent()) {
			return false;
		}

		Aviokompanija aviokompanija = aviokompanijaSearch.get();
		AdministratorAviokompanije noviAdmin = new AdministratorAviokompanije();
		noviAdmin.setEmail(adminReg.getEmail());
		noviAdmin.setLozinka(this.encodePassword(adminReg.getLozinka()));
		noviAdmin.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		noviAdmin.setIme(adminReg.getIme());
		noviAdmin.setPrezime(adminReg.getPrezime());
		noviAdmin.setBrojTelefona(adminReg.getBrojTelefona());
		noviAdmin.setEnabled(true);
		noviAdmin.setPutanjaSlike("");
		noviAdmin.setSistemAdmin(false);
		Authority a = new Authority();
		a.setTipKorisnika(TipKorisnika.AdministratorAviokompanije);
		HashSet<Authority> authorities = new HashSet<Authority>();
		authorities.add(a);
		noviAdmin.setAuthorities(authorities);
		noviAdmin.setAviokompanija(aviokompanija);
		aviokompanija.getAdmini().add(noviAdmin);
		this.aviokompanijaRepository.save(aviokompanija);
		return true;
	}

}
