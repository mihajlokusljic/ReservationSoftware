package rs.ac.uns.ftn.isa9.tim8.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6846861633011332044L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;
	
	@Column(name="tip_korisnika")
	@Enumerated(EnumType.STRING)
    protected TipKorisnika tipKorisnika;
	
	@Override
	public String getAuthority() {
		return tipKorisnika.toString();
	}
	
	@JsonIgnore
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}
	
	@JsonIgnore
	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}
	
	
	
	

}
