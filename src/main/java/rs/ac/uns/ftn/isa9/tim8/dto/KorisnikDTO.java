package rs.ac.uns.ftn.isa9.tim8.dto;

public class KorisnikDTO {
	
	protected String username;
	
	protected String lozinka;
	
	public KorisnikDTO() {
		super();
	}

	public KorisnikDTO(String username, String lozinka) {
		super();
		this.username = username;
		this.lozinka = lozinka;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	
	
}
