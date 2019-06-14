package rs.ac.uns.ftn.isa9.tim8.dto;


public class RegistracijaAdminaDTO {
	
	protected String email;
	protected String lozinka;
	protected String ime;
	protected String prezime;
	protected String brojTelefona;
	protected String punaAdresa;
	protected Long idPoslovnice;
	
	public RegistracijaAdminaDTO() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	public Long getIdPoslovnice() {
		return idPoslovnice;
	}
	public void setIdPoslovnice(Long idPoslovnice) {
		this.idPoslovnice = idPoslovnice;
	}
	public String getPunaAdresa() {
		return punaAdresa;
	}
	public void setPunaAdresa(String punaAdresa) {
		this.punaAdresa = punaAdresa;
	}
	
}