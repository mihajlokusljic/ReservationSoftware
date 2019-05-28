package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;

public class BrzaRezervacijaKarteDTO {

	protected Long id;

	protected Long letId;

	protected Long aviokompanijaId;

	protected Long sjedisteId;

	protected Date datumPolaska;

	protected Date datumDolaska;

	protected double cijena;

	protected int procenatPopusta;

	public BrzaRezervacijaKarteDTO() {
		super();
	}

	public BrzaRezervacijaKarteDTO(Long id, Long letId, Long aviokompanijaId, Long sjedisteId, Date datumPolaska,
			Date datumDolaska, double cijena, int procenatPopusta) {
		super();
		this.id = id;
		this.letId = letId;
		this.aviokompanijaId = aviokompanijaId;
		this.sjedisteId = sjedisteId;
		this.datumPolaska = datumPolaska;
		this.datumDolaska = datumDolaska;
		this.cijena = cijena;
		this.procenatPopusta = procenatPopusta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLetId() {
		return letId;
	}

	public void setLetId(Long letId) {
		this.letId = letId;
	}

	public Long getAviokompanijaId() {
		return aviokompanijaId;
	}

	public void setAviokompanijaId(Long aviokompanijaId) {
		this.aviokompanijaId = aviokompanijaId;
	}

	public Long getSjedisteId() {
		return sjedisteId;
	}

	public void setSjedisteId(Long sjedisteId) {
		this.sjedisteId = sjedisteId;
	}

	public Date getDatumPolaska() {
		return datumPolaska;
	}

	public void setDatumPolaska(Date datumPolaska) {
		this.datumPolaska = datumPolaska;
	}

	public Date getDatumDolaska() {
		return datumDolaska;
	}

	public void setDatumDolaska(Date datumDolaska) {
		this.datumDolaska = datumDolaska;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public int getProcenatPopusta() {
		return procenatPopusta;
	}

	public void setProcenatPopusta(int procenatPopusta) {
		this.procenatPopusta = procenatPopusta;
	}

}
