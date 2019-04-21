package rs.ac.uns.ftn.isa9.tim8.model;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "korisnik")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Osoba implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1655113308824460247L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long Id;
	
	@Column(name = "loznika", nullable = false)
	protected String lozinka;
	
	@Column(name = "ime", nullable = false)
	protected String ime;
	
	@Column(name = "prezime", nullable = false)
	protected String prezime;
	
	@Column(name = "email", nullable = false)
	protected String email;
	
	@Column(name = "broj_telefona", nullable = true)
	protected String brojTelefona;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "adresa_id")
	protected Adresa adresa;
	
	@Column(name = "putanja_slike", nullable = true)
	protected String putanjaSlike;
	
	@Column(name = "enabled")
    protected boolean enabled;
	
    @Column(name = "last_password_reset_date")
    protected Timestamp lastPasswordResetDate;
    
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "korisnik_autoritet", joinColumns = @JoinColumn(name = "korisnik_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "autoritet_id", referencedColumnName = "id"))
	protected Set<Authority> authorities;
	
	public Osoba() {
		super();
		authorities = new HashSet<Authority>();
	}

	public Osoba(Long id, String lozinka, String ime, String prezime, String email,
			String brojTelefona, Adresa adresa, String putanjaSlike) {
		super();
		this.Id = id;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.brojTelefona = brojTelefona;
		this.adresa = adresa;
		this.putanjaSlike = putanjaSlike;
	}
	
	public Osoba(Long id, String lozinka, String ime, String prezime, String email, String brojTelefona, Adresa adresa,
			String putanjaSlike, boolean enabled, Timestamp lastPasswordResetDate, Set<Authority> authorities) {
		super();
		this.Id = id;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.brojTelefona = brojTelefona;
		this.adresa = adresa;
		this.putanjaSlike = putanjaSlike;
		this.enabled = enabled;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.authorities = authorities;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getPutanjaSlike() {
		return putanjaSlike;
	}

	public void setPutanjaSlike(String putanjaSlike) {
		this.putanjaSlike = putanjaSlike;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return lozinka;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;

	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	
	
	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	
}
