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
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorHotela;
import rs.ac.uns.ftn.isa9.tim8.model.AdministratorRentACar;
import rs.ac.uns.ftn.isa9.tim8.model.Adresa;
import rs.ac.uns.ftn.isa9.tim8.model.Authority;
import rs.ac.uns.ftn.isa9.tim8.model.Aviokompanija;
import rs.ac.uns.ftn.isa9.tim8.model.Hotel;
import rs.ac.uns.ftn.isa9.tim8.model.Osoba;
import rs.ac.uns.ftn.isa9.tim8.model.RegistrovanKorisnik;
import rs.ac.uns.ftn.isa9.tim8.model.TipKorisnika;
import rs.ac.uns.ftn.isa9.tim8.repository.AdresaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AuthorityRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.AviokompanijaRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.HotelRepository;
import rs.ac.uns.ftn.isa9.tim8.repository.KorisnikRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	protected KorisnikRepository korisnikRepository;

	@Autowired
	protected AviokompanijaRepository aviokompanijaRepository;
	
	@Autowired
	protected HotelRepository hotelRepository;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected AuthenticationManager authenticationManager;
	
	@Autowired
	protected AuthorityRepository authorityRepository;
	
	@Autowired
	protected AdresaRepository adresaRepository;

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
	
	private void podesiOsnovnePodatkeAdmina(Osoba noviAdmin, RegistracijaAdminaDTO adminReg) throws NevalidniPodaciException {
		if(adminReg.getEmail() == null || adminReg.getEmail().equals("")) {
			throw new NevalidniPodaciException("E-mail mora biti zadat.");
		}
		noviAdmin.setEmail(adminReg.getEmail());
		if(adminReg.getLozinka() == null || adminReg.getLozinka().equals("")) {
			throw new NevalidniPodaciException("Lozinka mora biti zadata.");
		}
		noviAdmin.setLozinka(this.encodePassword(adminReg.getLozinka()));
		noviAdmin.setLastPasswordResetDate(new Timestamp(System.currentTimeMillis()));
		if(adminReg.getIme() == null || adminReg.getIme().equals("")) {
			throw new NevalidniPodaciException("Ime mora biti zadato.");
		}
		noviAdmin.setIme(adminReg.getIme());
		if(adminReg.getPrezime() == null || adminReg.getPrezime().equals("")) {
			throw new NevalidniPodaciException("Prezime mora biti zadato.");
		}
		noviAdmin.setPrezime(adminReg.getPrezime());
		noviAdmin.setBrojTelefona(adminReg.getBrojTelefona());
		Adresa a = this.adresaRepository.findOneByPunaAdresa(adminReg.getPunaAdresa());
		if(a == null) {
			a = new Adresa(adminReg.getPunaAdresa());
		}
		noviAdmin.setAdresa(a);
		noviAdmin.setEnabled(true);
		noviAdmin.setPutanjaSlike("");
	}
	
	public void podesiPrivilegije(Osoba noviKorisnik, TipKorisnika tipKorisnika) {
		Authority a = this.authorityRepository.findOneByTipKorisnika(tipKorisnika);
		if(a == null) {
			a = new Authority();
			a.setTipKorisnika(tipKorisnika);
		}
		HashSet<Authority> privilegije = new HashSet<Authority>();
		privilegije.add(a);
		noviKorisnik.setAuthorities(privilegije);
	}

	public void dodajAdminaAviokompanije(RegistracijaAdminaDTO adminReg) throws NevalidniPodaciException {
		// TODO Auto-generated method stub
		if (this.emailZauzet(adminReg.getEmail())) {
			throw new NevalidniPodaciException("Zadati email vec postoji.");
		}

		Optional<Aviokompanija> aviokompanijaSearch = this.aviokompanijaRepository.findById(adminReg.getIdPoslovnice());
		if (!aviokompanijaSearch.isPresent()) {
			throw new NevalidniPodaciException("Zadata aviokompanija ne postoji.");
		}

		Aviokompanija aviokompanija = aviokompanijaSearch.get();
		AdministratorAviokompanije noviAdmin = new AdministratorAviokompanije();
		this.podesiOsnovnePodatkeAdmina(noviAdmin, adminReg);
		this.podesiPrivilegije(noviAdmin, TipKorisnika.AdministratorAviokompanije);
		noviAdmin.setAviokompanija(aviokompanija);
		this.korisnikRepository.save(noviAdmin);
	}
	
	public void dodajAdminaHotela(RegistracijaAdminaDTO adminReg) throws NevalidniPodaciException {
		if (this.emailZauzet(adminReg.getEmail())) {
			throw new NevalidniPodaciException("Zadati email vec postoji.");
		}
		
		Optional<Hotel> hotelSearch = this.hotelRepository.findById(adminReg.getIdPoslovnice());
		if(!hotelSearch.isPresent()) {
			throw new NevalidniPodaciException("Zadati hotel ne postoji.");
		}
		
		Hotel hotel = hotelSearch.get();
		AdministratorHotela noviAdmin = new AdministratorHotela();
		this.podesiOsnovnePodatkeAdmina(noviAdmin, adminReg);
		this.podesiPrivilegije(noviAdmin, TipKorisnika.AdministratorHotela);
		noviAdmin.setHotel(hotel);
		this.korisnikRepository.save(noviAdmin);
	}

}
