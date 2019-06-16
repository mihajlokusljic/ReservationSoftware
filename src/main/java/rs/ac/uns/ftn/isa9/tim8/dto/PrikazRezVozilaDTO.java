package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import rs.ac.uns.ftn.isa9.tim8.common.CustomDateSerializer;
import rs.ac.uns.ftn.isa9.tim8.model.Vozilo;

public class PrikazRezVozilaDTO {
	
	protected Long idRezervacije;
	
	protected String nazivServisa;
	
	protected Vozilo rezervisanoVozilo;
	
	protected double cijena;
	
	protected String mjestoPreuzimanja;
	
	protected String mjestoVracanja;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	protected Date datumPreuzimanja;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	protected Date datumVracanja;

	public PrikazRezVozilaDTO(Long idRezervacije, String nazivServisa, Vozilo rezervisanoVozilo, double cijena,
			String mjestoPreuzimanja, String mjestoVracanja, Date datumPreuzimanja, Date datumVracanja) {
		super();
		this.idRezervacije = idRezervacije;
		this.nazivServisa = nazivServisa;
		this.rezervisanoVozilo = rezervisanoVozilo;
		this.cijena = cijena;
		this.mjestoPreuzimanja = mjestoPreuzimanja;
		this.mjestoVracanja = mjestoVracanja;
		this.datumPreuzimanja = datumPreuzimanja;
		this.datumVracanja = datumVracanja;
	}

	public PrikazRezVozilaDTO() {
		super();
	}

	public Long getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(Long idRezervacije) {
		this.idRezervacije = idRezervacije;
	}

	public String getNazivServisa() {
		return nazivServisa;
	}

	public void setNazivServisa(String nazivServisa) {
		this.nazivServisa = nazivServisa;
	}

	public Vozilo getRezervisanoVozilo() {
		return rezervisanoVozilo;
	}

	public void setRezervisanoVozilo(Vozilo rezervisanoVozilo) {
		this.rezervisanoVozilo = rezervisanoVozilo;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public String getMjestoPreuzimanja() {
		return mjestoPreuzimanja;
	}

	public void setMjestoPreuzimanja(String mjestoPreuzimanja) {
		this.mjestoPreuzimanja = mjestoPreuzimanja;
	}

	public String getMjestoVracanja() {
		return mjestoVracanja;
	}

	public void setMjestoVracanja(String mjestoVracanja) {
		this.mjestoVracanja = mjestoVracanja;
	}

	public Date getDatumPreuzimanja() {
		return datumPreuzimanja;
	}

	public void setDatumPreuzimanja(Date datumPreuzimanja) {
		this.datumPreuzimanja = datumPreuzimanja;
	}

	public Date getDatumVracanja() {
		return datumVracanja;
	}

	public void setDatumVracanja(Date datumVracanja) {
		this.datumVracanja = datumVracanja;
	}
	
	
	
	
	
}
