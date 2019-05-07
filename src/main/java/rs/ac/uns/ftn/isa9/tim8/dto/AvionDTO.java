package rs.ac.uns.ftn.isa9.tim8.dto;

public class AvionDTO {
	protected String naziv;
	protected Long idAviokompanije;
	protected int brojVrsta;
	protected int brojKolona;
	protected int brojSegmenata;

	public AvionDTO() {
		super();
	}

	public AvionDTO(String naziv, Long idAviokompanije, int brojVrsta, int brojKolona, int brojSegmenata) {
		super();
		this.naziv = naziv;
		this.idAviokompanije = idAviokompanije;
		this.brojVrsta = brojVrsta;
		this.brojKolona = brojKolona;
		this.brojSegmenata = brojSegmenata;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Long getIdAviokompanije() {
		return idAviokompanije;
	}

	public void setIdAviokompanije(Long idAviokompanije) {
		this.idAviokompanije = idAviokompanije;
	}

	public int getBrojVrsta() {
		return brojVrsta;
	}

	public void setBrojVrsta(int brojVrsta) {
		this.brojVrsta = brojVrsta;
	}

	public int getBrojKolona() {
		return brojKolona;
	}

	public void setBrojKolona(int brojKolona) {
		this.brojKolona = brojKolona;
	}

	public int getBrojSegmenata() {
		return brojSegmenata;
	}

	public void setBrojSegmenata(int brojSegmenata) {
		this.brojSegmenata = brojSegmenata;
	}

}
