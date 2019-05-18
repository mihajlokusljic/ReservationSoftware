package rs.ac.uns.ftn.isa9.tim8.dto;


public class PretragaVozilaDTO {
	
	protected Long idRac;
	protected String datumPreuzimanja;
	protected String datumVracanja;
	protected String vrijemePreuzimanja;
	protected String vrijemeVracanja;
	protected Long idMjestoPreuzimanja;
	protected Long idMjestoVracanja;
	protected String tipVozila;
	protected int brojPutnika;
	protected Integer minimalnaCijenaPoDanu;
	protected Integer maksimalnaCijenaPoDanu;
	
	


	public PretragaVozilaDTO(Long idRac, String datumPreuzimanja, String datumVracanja, String vrijemePreuzimanja,
			String vrijemeVracanja, Long idMjestoPreuzimanja, Long idMjestoVracanja, String tipVozila, int brojPutnika,
			Integer minimalnaCijenaPoDanu, Integer maksimalnaCijenaPoDanu) {
		super();
		this.idRac = idRac;
		this.datumPreuzimanja = datumPreuzimanja;
		this.datumVracanja = datumVracanja;
		this.vrijemePreuzimanja = vrijemePreuzimanja;
		this.vrijemeVracanja = vrijemeVracanja;
		this.idMjestoPreuzimanja = idMjestoPreuzimanja;
		this.idMjestoVracanja = idMjestoVracanja;
		this.tipVozila = tipVozila;
		this.brojPutnika = brojPutnika;
		this.minimalnaCijenaPoDanu = minimalnaCijenaPoDanu;
		this.maksimalnaCijenaPoDanu = maksimalnaCijenaPoDanu;
	}

	public PretragaVozilaDTO() {
		super();
	}

	public Long getIdRac() {
		return idRac;
	}

	public void setIdRac(Long idRac) {
		this.idRac = idRac;
	}

	public String getDatumPreuzimanja() {
		return datumPreuzimanja;
	}

	public void setDatumPreuzimanja(String datumPreuzimanja) {
		this.datumPreuzimanja = datumPreuzimanja;
	}

	public String getDatumVracanja() {
		return datumVracanja;
	}

	public void setDatumVracanja(String datumVracanja) {
		this.datumVracanja = datumVracanja;
	}

	public String getVrijemePreuzimanja() {
		return vrijemePreuzimanja;
	}

	public void setVrijemePreuzimanja(String vrijemePreuzimanja) {
		this.vrijemePreuzimanja = vrijemePreuzimanja;
	}

	public String getVrijemeVracanja() {
		return vrijemeVracanja;
	}

	public void setVrijemeVracanja(String vrijemeVracanja) {
		this.vrijemeVracanja = vrijemeVracanja;
	}

	public Long getIdMjestoPreuzimanja() {
		return idMjestoPreuzimanja;
	}

	public void setIdMjestoPreuzimanja(Long idMjestoPreuzimanja) {
		this.idMjestoPreuzimanja = idMjestoPreuzimanja;
	}

	public Long getIdMjestoVracanja() {
		return idMjestoVracanja;
	}

	public void setIdMjestoVracanja(Long idMjestoVracanja) {
		this.idMjestoVracanja = idMjestoVracanja;
	}

	public String getTipVozila() {
		return tipVozila;
	}

	public void setTipVozila(String tipVozila) {
		this.tipVozila = tipVozila;
	}

	public int getBrojPutnika() {
		return brojPutnika;
	}

	public void setBrojPutnika(int brojPutnika) {
		this.brojPutnika = brojPutnika;
	}

	public Integer getMinimalnaCijenaPoDanu() {
		return minimalnaCijenaPoDanu;
	}

	public void setMinimalnaCijenaPoDanu(Integer minimalnaCijenaPoDanu) {
		this.minimalnaCijenaPoDanu = minimalnaCijenaPoDanu;
	}

	public Integer getMaksimalnaCijenaPoDanu() {
		return maksimalnaCijenaPoDanu;
	}

	public void setMaksimalnaCijenaPoDanu(Integer maksimalnaCijenaPoDanu) {
		this.maksimalnaCijenaPoDanu = maksimalnaCijenaPoDanu;
	}
		
	
}
