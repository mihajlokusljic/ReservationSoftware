package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RegistrovanKorisnik extends Osoba {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4317984380302878520L;

	@Column(name = "bonus_poeni", nullable = true)
	protected double bonusPoeni;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "prijatelji", joinColumns = @JoinColumn(name = "korisnik", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "prijatelj", referencedColumnName = "id"))
	@JsonIgnore
	protected Set<RegistrovanKorisnik> prijatelji;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "primalac")
	@JsonIgnore
	protected Set<Pozivnica> primljenePozivnice;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "primalac")
	@JsonIgnore
	protected Set<ZahtjevZaPrijateljstvo> primljeniZahtjevi;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "inicijatorPutovanja")
	protected Set<Putovanje> putovanja;

	public RegistrovanKorisnik() {
		super();
	}

	public RegistrovanKorisnik(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			String brojTelefona, Adresa adresa, String putanjaSlike, double bonusPoeni,
			Set<RegistrovanKorisnik> prijatelji, Set<Pozivnica> primjenePozivnice) {
		super(id, lozinka, ime, prezime, email, brojTelefona, adresa, putanjaSlike);
		this.bonusPoeni = bonusPoeni;
		this.prijatelji = prijatelji;
		this.primljenePozivnice = primjenePozivnice;
	}

	public RegistrovanKorisnik(double bonusPoeni, Set<RegistrovanKorisnik> prijatelji,
			Set<Pozivnica> primljenePozivnice, Set<ZahtjevZaPrijateljstvo> primljeniZahtjevi, Set<Putovanje> putovanja,
			String brojPasosa) {
		super();
		this.bonusPoeni = bonusPoeni;
		this.prijatelji = prijatelji;
		this.primljenePozivnice = primljenePozivnice;
		this.primljeniZahtjevi = primljeniZahtjevi;
		this.putovanja = putovanja;
		this.brojPasosa = brojPasosa;
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

	public Set<Pozivnica> getPrimljenePozivnice() {
		return primljenePozivnice;
	}

	public void setPrimjenePozivnice(Set<Pozivnica> primljenePozivnice) {
		this.primljenePozivnice = primljenePozivnice;
	}

	public Set<ZahtjevZaPrijateljstvo> getPrimljeniZahtjevi() {
		return primljeniZahtjevi;
	}

	public void setPrimljeniZahtjevi(Set<ZahtjevZaPrijateljstvo> primljeniZahtjevi) {
		this.primljeniZahtjevi = primljeniZahtjevi;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPrimljenePozivnice(Set<Pozivnica> primljenePozivnice) {
		this.primljenePozivnice = primljenePozivnice;
	}

	public String getBrojPasosa() {
		return brojPasosa;
	}

	public void setBrojPasosa(String brojPasosa) {
		this.brojPasosa = brojPasosa;
	}

	public Set<Putovanje> getPutovanja() {
		return putovanja;
	}

	public void setPutovanja(Set<Putovanje> putovanja) {
		this.putovanja = putovanja;
	}

}
