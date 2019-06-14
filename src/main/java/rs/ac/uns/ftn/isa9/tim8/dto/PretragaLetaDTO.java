package rs.ac.uns.ftn.isa9.tim8.dto;

public class PretragaLetaDTO {
	protected String brojLeta;
	protected String nazivAviokompanije;
	protected String nazivPolazista;
	protected String nazivOdredista;
	protected String datumPoletanja;
	protected String datumSletanja;
	protected String duzinaPutovanja;
	protected double cijenaKarte;
	
	public PretragaLetaDTO() {
		super();
	}

	public PretragaLetaDTO(String brojLeta, String nazivAviokompanije, String nazivPolazista, String nazivOdredista,
			String datumPoletanja, String datumSletanja, String duzinaPutovanja, double cijenaKarte) {
		super();
		this.brojLeta = brojLeta;
		this.nazivAviokompanije = nazivAviokompanije;
		this.nazivPolazista = nazivPolazista;
		this.nazivOdredista = nazivOdredista;
		this.datumPoletanja = datumPoletanja;
		this.datumSletanja = datumSletanja;
		this.duzinaPutovanja = duzinaPutovanja;
		this.cijenaKarte = cijenaKarte;
	}

	public String getBrojLeta() {
		return brojLeta;
	}

	public void setBrojLeta(String brojLeta) {
		this.brojLeta = brojLeta;
	}

	public String getNazivAviokompanije() {
		return nazivAviokompanije;
	}

	public void setNazivAviokompanije(String nazivAviokompanije) {
		this.nazivAviokompanije = nazivAviokompanije;
	}

	public String getNazivPolazista() {
		return nazivPolazista;
	}

	public void setNazivPolazista(String nazivPolazista) {
		this.nazivPolazista = nazivPolazista;
	}

	public String getNazivOdredista() {
		return nazivOdredista;
	}

	public void setNazivOdredista(String nazivOdredista) {
		this.nazivOdredista = nazivOdredista;
	}

	public String getDatumPoletanja() {
		return datumPoletanja;
	}

	public void setDatumPoletanja(String datumPoletanja) {
		this.datumPoletanja = datumPoletanja;
	}

	public String getDatumSletanja() {
		return datumSletanja;
	}

	public void setDatumSletanja(String datumSletanja) {
		this.datumSletanja = datumSletanja;
	}

	public String getDuzinaPutovanja() {
		return duzinaPutovanja;
	}

	public void setDuzinaPutovanja(String duzinaPutovanja) {
		this.duzinaPutovanja = duzinaPutovanja;
	}

	public double getCijenaKarte() {
		return cijenaKarte;
	}

	public void setCijenaKarte(double cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}

}