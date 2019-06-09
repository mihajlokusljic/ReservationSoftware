package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Klasa koja modeluje procenat popusta koji korisnik ostvaruje na
 * putovanju zavisno od skupljenih bonus poena. Ukoliko je broj bonus poena korisnika
 * veci ili jednak donjoj granici i manji od gornje granice korisnik dobija zadati popust.
 * Ako gornja granica nije definisana (null) korisnik dobija zadati popust ako mu je broj bonus
 * poena veci ili jednak donjoj granici.
 */
@Entity
@Table(name = "SkalaPopusta")
public class BonusPopust {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name = "donja_granica_poeni", nullable = false)
	protected Double donjaGranicaBonusPoeni;
	
	@Column(name = "gornja_granica_poeni", nullable = true)
	protected Double gornjaGranicaBonusPoeni;
	
	@Column(name = "procenat_popusta", nullable = false)
	protected int ostvarenProcenatPopusta;

	public BonusPopust() {
		super();
	}

	public BonusPopust(Double donjaGranicaBonusPoeni, Double gornjaGranicaBonusPoeni, int ostvarenProcenatPopusta) {
		super();
		this.donjaGranicaBonusPoeni = donjaGranicaBonusPoeni;
		this.gornjaGranicaBonusPoeni = gornjaGranicaBonusPoeni;
		this.ostvarenProcenatPopusta = ostvarenProcenatPopusta;
	}

	public BonusPopust(Long id, Double donjaGranicaBonusPoeni, Double gornjaGranicaBonusPoeni,
			int ostvarenProcenatPopusta) {
		super();
		Id = id;
		this.donjaGranicaBonusPoeni = donjaGranicaBonusPoeni;
		this.gornjaGranicaBonusPoeni = gornjaGranicaBonusPoeni;
		this.ostvarenProcenatPopusta = ostvarenProcenatPopusta;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Double getDonjaGranicaBonusPoeni() {
		return donjaGranicaBonusPoeni;
	}

	public void setDonjaGranicaBonusPoeni(Double donjaGranicaBonusPoeni) {
		this.donjaGranicaBonusPoeni = donjaGranicaBonusPoeni;
	}

	public Double getGornjaGranicaBonusPoeni() {
		return gornjaGranicaBonusPoeni;
	}

	public void setGornjaGranicaBonusPoeni(Double gornjaGranicaBonusPoeni) {
		this.gornjaGranicaBonusPoeni = gornjaGranicaBonusPoeni;
	}

	public int getOstvarenProcenatPopusta() {
		return ostvarenProcenatPopusta;
	}

	public void setOstvarenProcenatPopusta(int ostvarenProcenatPopusta) {
		this.ostvarenProcenatPopusta = ostvarenProcenatPopusta;
	}

}
