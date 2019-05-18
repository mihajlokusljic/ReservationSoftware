package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "let")
public class Let {

	@Id
	@GeneratedValue
	protected Long Id;

	@Column(name = "broj_leta", nullable = false)
	protected String brojLeta;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "polaziste_id", referencedColumnName = "id")
	protected Destinacija polaziste;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "odrediste_id", referencedColumnName = "id")
	protected Destinacija odrediste;

	@Column(name = "datum_poletanja", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumPoletanja;

	@Column(name = "datum_sletanja", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date datumSletanja; // Napisan u formatu dd.MM.yyyy HH:mm

	@Column(name = "duzina_putovanja", nullable = false)
	@Temporal(TemporalType.DATE)
	protected Date duzinaPutovanja; // Kojeg datuma i u koliko casova se ocekivano vracamo

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "let_presjedanje", joinColumns = @JoinColumn(name = "let_id"), inverseJoinColumns = @JoinColumn(name = "presjedanje_id"))
	protected Set<Destinacija> presjedanja; // Zato sto ce biti potrebno cuvati i lokacije
											// (vjerovatno kroz Yandex mape)
	@Column(name = "suma_ocjena", nullable = true)
	protected int sumaOcjena;

	@Column(name = "broj_ocjena", nullable = true)
	protected int brojOcjena;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "avion_id")
	protected Avion avion;

	@JsonIgnore
	@OneToMany(mappedBy = "let", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Set<RezervacijaSjedista> rezervacije;

	@Column(name = "cijena_karte", nullable = true)
	@ColumnDefault("0")
	protected double cijenaKarte;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "let")
	protected CjenovnikLeta cjenovnikLeta;

	public Let() {
		super();
	}

	public Let(String brojLeta, Destinacija polaziste, Destinacija odrediste, Date datumPoletanja, Date datumSletanja,
			Date duzinaPutovanja, Set<Destinacija> presjedanja, Avion avion, int kapacitetPrvaKlasa,
			int kapacitetBiznisKlasa, int kapacitetEkonomskaKlasa, Set<RezervacijaSjedista> rezervacije,
			double cijenaKarte) {
		super();
		this.brojLeta = brojLeta;
		this.polaziste = polaziste;
		this.odrediste = odrediste;
		this.datumPoletanja = datumPoletanja;
		this.datumSletanja = datumSletanja;
		this.duzinaPutovanja = duzinaPutovanja;
		this.presjedanja = presjedanja;
		this.avion = avion;
		this.rezervacije = rezervacije;
		this.cijenaKarte = cijenaKarte;
		this.sumaOcjena = 0;
		this.brojOcjena = 0;
	}

	public String getBrojLeta() {
		return brojLeta;
	}

	public void setBrojLeta(String brojLeta) {
		this.brojLeta = brojLeta;
	}

	public Destinacija getPolaziste() {
		return polaziste;
	}

	public void setPolaziste(Destinacija polaziste) {
		this.polaziste = polaziste;
	}

	public Destinacija getOdrediste() {
		return odrediste;
	}

	public void setOdrediste(Destinacija odrediste) {
		this.odrediste = odrediste;
	}

	public Date getDatumPoletanja() {
		return datumPoletanja;
	}

	public void setDatumPoletanja(Date datumPoletanja) {
		this.datumPoletanja = datumPoletanja;
	}

	public Date getDatumSletanja() {
		return datumSletanja;
	}

	public void setDatumSletanja(Date datumSletanja) {
		this.datumSletanja = datumSletanja;
	}

	public Date getDuzinaPutovanja() {
		return duzinaPutovanja;
	}

	public void setDuzinaPutovanja(Date duzinaPutovanja) {
		this.duzinaPutovanja = duzinaPutovanja;
	}

	public Set<Destinacija> getPresjedanja() {
		return presjedanja;
	}

	public void setPresjedanja(Set<Destinacija> presjedanja) {
		this.presjedanja = presjedanja;
	}

	public Avion getAvion() {
		return avion;
	}

	public void setAvion(Avion avion) {
		this.avion = avion;
	}

	public double getCijenaKarte() {
		return cijenaKarte;
	}

	public void setCijenaKarte(double cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}

	public int getSumaOcjena() {
		return sumaOcjena;
	}

	public void setSumaOcjena(int sumaOcjena) {
		this.sumaOcjena = sumaOcjena;
	}

	public int getBrojOcjena() {
		return brojOcjena;
	}

	public void setBrojOcjena(int brojOcjena) {
		this.brojOcjena = brojOcjena;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

}
