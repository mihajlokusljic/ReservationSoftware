package rs.ac.uns.ftn.isa9.tim8.dto;

public class UslugaDTO {
	protected Long idPoslovnice;
	protected String naziv;
	protected double cijena;
	protected int procenatPopusta;
	protected int nacinPlacanjaId;
	protected String opis;
	
	public UslugaDTO() {
		super();
	}

	public UslugaDTO(Long idPoslovnice, String naziv, double cijena, int procenatPopusta, int nacinPlacanjaId,
			String opis) {
		super();
		this.idPoslovnice = idPoslovnice;
		this.naziv = naziv;
		this.cijena = cijena;
		this.procenatPopusta = procenatPopusta;
		this.nacinPlacanjaId = nacinPlacanjaId;
		this.opis = opis;
	}

	public Long getIdPoslovnice() {
		return idPoslovnice;
	}

	public void setIdPoslovnice(Long idPoslovnice) {
		this.idPoslovnice = idPoslovnice;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
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

	public int getNacinPlacanjaId() {
		return nacinPlacanjaId;
	}

	public void setNacinPlacanjaId(int nacinPlacanjaId) {
		this.nacinPlacanjaId = nacinPlacanjaId;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

}
