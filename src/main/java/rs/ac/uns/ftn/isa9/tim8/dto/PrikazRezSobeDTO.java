package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Date;


public class PrikazRezSobeDTO {
	
	protected Long idRezervacije;
	
	protected String nazivHotela;
	
	protected int brojSobe;
	
	protected int brojKreveta;
	
	protected double cijena;
	
	protected Date datumDolaksa;
	
	protected Date datumOdlaksa;

	

	public PrikazRezSobeDTO(Long idRezervacije, String nazivHotela, int brojSobe, int brojKreveta, double cijena,
			Date datumDolaksa, Date datumOdlaksa) {
		super();
		this.idRezervacije = idRezervacije;
		this.nazivHotela = nazivHotela;
		this.brojSobe = brojSobe;
		this.brojKreveta = brojKreveta;
		this.cijena = cijena;
		this.datumDolaksa = datumDolaksa;
		this.datumOdlaksa = datumOdlaksa;
	}

	public PrikazRezSobeDTO() {
		super();
	}
	
	public Long getIdRezervacije() {
		return idRezervacije;
	}

	public void setIdRezervacije(Long idRezervacije) {
		this.idRezervacije = idRezervacije;
	}

	public String getNazivHotela() {
		return nazivHotela;
	}

	public void setNazivHotela(String nazivHotela) {
		this.nazivHotela = nazivHotela;
	}

	public int getBrojSobe() {
		return brojSobe;
	}

	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
	}

	public int getBrojKreveta() {
		return brojKreveta;
	}

	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}

	public double getCijena() {
		return cijena;
	}

	public void setCijena(double cijena) {
		this.cijena = cijena;
	}

	public Date getDatumDolaksa() {
		return datumDolaksa;
	}

	public void setDatumDolaksa(Date datumDolaksa) {
		this.datumDolaksa = datumDolaksa;
	}

	public Date getDatumOdlaksa() {
		return datumOdlaksa;
	}

	public void setDatumOdlaksa(Date datumOdlaksa) {
		this.datumOdlaksa = datumOdlaksa;
	}
	
	
}
