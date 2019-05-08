package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;
import java.util.Date;

public class LetDTO {

	protected String brojLeta;

	protected Long idAviokompanije;

	protected Long idPolaziste;

	protected Long idOdrediste;

	protected String datumPoletanja;

	protected String datumSletanja; // Napisan u formatu dd.MM.yyyy HH:mm

	protected String duzinaPutovanja; // Kojeg datuma i u koliko casova se ocekivano vracamo

	protected Collection<Long> idDestinacijePresjedanja;

	protected Long idAviona;

	protected double cijenaKarte;

	public LetDTO() {
		super();
	}

	public LetDTO(String brojLeta, Long idAviokompanije, Long idPolaziste, Long idOdrediste, String datumPoletanja,
			String datumSletanja, String duzinaPutovanja, Collection<Long> idDestinacijePresjedanja, Long idAviona,
			double cijenaKarte) {
		super();
		this.brojLeta = brojLeta;
		this.idAviokompanije = idAviokompanije;
		this.idPolaziste = idPolaziste;
		this.idOdrediste = idOdrediste;
		this.datumPoletanja = datumPoletanja;
		this.datumSletanja = datumSletanja;
		this.duzinaPutovanja = duzinaPutovanja;
		this.idDestinacijePresjedanja = idDestinacijePresjedanja;
		this.idAviona = idAviona;
		this.cijenaKarte = cijenaKarte;
	}

	public String getBrojLeta() {
		return brojLeta;
	}

	public void setBrojLeta(String brojLeta) {
		this.brojLeta = brojLeta;
	}

	public Long getIdAviokompanije() {
		return idAviokompanije;
	}

	public void setIdAviokompanije(Long idAviokompanije) {
		this.idAviokompanije = idAviokompanije;
	}

	public Long getIdPolaziste() {
		return idPolaziste;
	}

	public void setIdPolaziste(Long idPolaziste) {
		this.idPolaziste = idPolaziste;
	}

	public Long getIdOdrediste() {
		return idOdrediste;
	}

	public void setIdOdrediste(Long idOdrediste) {
		this.idOdrediste = idOdrediste;
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

	public Collection<Long> getIdDestinacijePresjedanja() {
		return idDestinacijePresjedanja;
	}

	public void setIdDestinacijePresjedanja(Collection<Long> idDestinacijePresjedanja) {
		this.idDestinacijePresjedanja = idDestinacijePresjedanja;
	}

	public Long getIdAviona() {
		return idAviona;
	}

	public void setIdAviona(Long idAviona) {
		this.idAviona = idAviona;
	}

	public double getCijenaKarte() {
		return cijenaKarte;
	}

	public void setCijenaKarte(double cijenaKarte) {
		this.cijenaKarte = cijenaKarte;
	}

}
