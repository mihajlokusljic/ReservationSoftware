package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

public class RegistrovanKorisnik extends Osoba {
	protected double bonusPoeni;
	protected Set<RegistrovanKorisnik> prijatelji;
	protected Set<Pozivnica> primjenePozivnice;
	
	public RegistrovanKorisnik() {
		super();
	}

	
	public RegistrovanKorisnik(String korisnickoIme, String lozinka, String ime, String prezime, String email,
			String brojTelefona, String putanjaSlike, double bonusPoeni, Set<RegistrovanKorisnik> prijatelji, Set<Pozivnica> primjenePozivnice ) {
		super(korisnickoIme, lozinka, ime, prezime, email, brojTelefona, putanjaSlike, false);
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
