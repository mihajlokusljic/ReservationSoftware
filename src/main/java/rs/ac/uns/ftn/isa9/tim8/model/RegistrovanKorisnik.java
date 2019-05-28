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
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@OneToOne(fetch = FetchType.LAZY, cascade= CascadeType.ALL, mappedBy= "inicijatorPutovanja")
	protected Putovanje putovanje;
	
	public RegistrovanKorisnik() {
		super();
	}

	
	public RegistrovanKorisnik(Long id, String korisnickoIme, String lozinka, String ime, String prezime, String email,
			String brojTelefona, Adresa adresa, String putanjaSlike, double bonusPoeni, Set<RegistrovanKorisnik> prijatelji, Set<Pozivnica> primjenePozivnice ) {
		super(id, lozinka, ime, prezime, email, brojTelefona, adresa, putanjaSlike);
		this.bonusPoeni = bonusPoeni;
		this.prijatelji = prijatelji;
		this.primljenePozivnice = primjenePozivnice;
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


	public Putovanje getPutovanje() {
		return putovanje;
	}


	public void setPutovanje(Putovanje putovanje) {
		this.putovanje = putovanje;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public void setPrimljenePozivnice(Set<Pozivnica> primljenePozivnice) {
		this.primljenePozivnice = primljenePozivnice;
	}
	
	
}
