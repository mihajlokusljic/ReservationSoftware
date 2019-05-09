package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, name = "token", unique = false)
	private String token;

	@OneToOne(targetEntity = RegistrovanKorisnik.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "korisnik")
	private RegistrovanKorisnik korisnik;

	public VerificationToken() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RegistrovanKorisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(RegistrovanKorisnik korisnik) {
		this.korisnik = korisnik;
	}

	
}
