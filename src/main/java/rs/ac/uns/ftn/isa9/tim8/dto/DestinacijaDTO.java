package rs.ac.uns.ftn.isa9.tim8.dto;

import rs.ac.uns.ftn.isa9.tim8.model.Adresa;

public class DestinacijaDTO {
	protected String naziv;
	protected Adresa adresa;
	protected Long idAviokompanije;

	public DestinacijaDTO() {
		super();
	}

	public DestinacijaDTO(String naziv, Adresa adresa, Long idAviokompanije) {
		super();
		this.naziv = naziv;
		this.adresa = adresa;
		this.idAviokompanije = idAviokompanije;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Adresa getAdresa() {
		return adresa;
	}

	public void setAdresa(Adresa adresa) {
		this.adresa = adresa;
	}

	public Long getIdAviokompanije() {
		return idAviokompanije;
	}

	public void setIdAviokompanije(Long idAviokompanije) {
		this.idAviokompanije = idAviokompanije;
	}

}
