package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "registrovani_korsnik")
public class RegistrovanKorisnik extends Osoba {
	
	@Column(name = "bonus_poeni", nullable = true)
	protected double bonusPoeni;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RegistrovanKorisnik> prijatelji;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "primalac")
	protected Set<Pozivnica> primjenePozivnice;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "primalac")
	protected Set<ZahtjevZaPrijateljstvo> primljeniZahtjevi;
	
	public RegistrovanKorisnik() {
		super();
	}

	
	public RegistrovanKorisnik(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			String brojTelefona, String putanjaSlike, double bonusPoeni, Set<RegistrovanKorisnik> prijatelji, Set<Pozivnica> primjenePozivnice ) {
		super(id, korisnickoIme, lozinka, ime, prezime, email, brojTelefona, putanjaSlike, false);
		this.bonusPoeni = bonusPoeni;
		this.prijatelji = prijatelji;
		this.primjenePozivnice = primjenePozivnice;
	}


	public double getBonusPoeni() {
		return bonusPoeni;
	}

	public void setBonusPoeni(double bonusPoeni) {
		this.bonusPoeni = bonusPoeni;
	}

	public Set<RegistrovanKorisnik> getPrijatelji() {
		return prijatelji;
	}

	public void setPrijatelji(Set<RegistrovanKorisnik> prijatelji) {
		this.prijatelji = prijatelji;
	}

	public Set<Pozivnica> getPrimjenePozivnice() {
		return primjenePozivnice;
	}

	public void setPrimjenePozivnice(Set<Pozivnica> primjenePozivnice) {
		this.primjenePozivnice = primjenePozivnice;
	}
	
	
}
