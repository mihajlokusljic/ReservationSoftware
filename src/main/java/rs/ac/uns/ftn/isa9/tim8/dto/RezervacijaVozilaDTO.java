package rs.ac.uns.ftn.isa9.tim8.dto;

import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public class RezervacijaVozilaDTO {

	protected Vozilo rezervisanoVozilo;

	protected Long mjestoPreuzimanjaVozila;
	
	protected String datumPreuzimanjaVozila;

	protected Long mjestoVracanjaVozila;

	protected String datumVracanjaVozila;

	protected double cijena;

	protected Long putnik; // opciono, ne mora se rezervisati za registrovanog korisnika

	protected Long idPutovanja;

	public RezervacijaVozilaDTO() {
		super();
	}

	public RezervacijaVozilaDTO(Vozilo rezervisanoVozilo, Long mjestoPreuzimanjaVozila, String datumPreuzimanjaVozila,
			Long mjestoVracanjaVozila, String datumVracanjaVozila, double cijena, Long putnik, Long idPutovanja) {
		super();
		this.rezervisanoVozilo = rezervisanoVozilo;
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
		this.datumVracanjaVozila = datumVracanjaVozila;
		this.cijena = cijena;
		this.putnik = putnik;
		this.idPutovanja = idPutovanja;
	}

	public Vozilo getRezervisanoVozilo() {
		return rezervisanoVozilo;
	}

	public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
		this.rezervisanoVozilo = rezervisanoVozilo;
	}

	public Long getMjestoPreuzimanjaVozila() {
		return mjestoPreuzimanjaVozila;
	}

	public void setMjestoPreuzimanjaVozila(Long mjestoPreuzimanjaVozila) {
		this.mjestoPreuzimanjaVozila = mjestoPreuzimanjaVozila;
	}

	
	public String getDatumPreuzimanjaVozila() {
		return datumPreuzimanjaVozila;
	}

	public Long getMjestoVracanjaVozila() {
		return mjestoVracanjaVozila;
	}

	public void setMjestoVracanjaVozila(Long mjestoVracanjaVozila) {
		this.mjestoVracanjaVozila = mjestoVracanjaVozila;
	}

	public String getDatumVracanjaVozila() {
		return datumVracanjaVozila;
	}

	public void setDatumVracanjaVozila(String datumVracanjaVozila) {
		this.datumVracanjaVozila = datumVracanjaVozila;
	}

	public void setDatumPreuzimanjaVozila(String datumPreuzimanjaVozila) {
		this.datumPreuzimanjaVozila = datumPreuzimanjaVozila;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Long getPutnik() {
		return putnik;
	}

	public void setPutnik(Long putnik) {
		this.putnik = putnik;
	}

	public Long getIdPutovanja() {
		return idPutovanja;
	}

	public void setIdPutovanja(Long idPutovanja) {
		this.idPutovanja = idPutovanja;
	}

}
