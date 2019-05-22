package rs.ac.uns.ftn.isa9.tim8.dto;

public class SobaDTO {
	protected Long Id;
	protected int brojSobe;
	protected int brojKreveta;
	protected int sprat;
	protected int vrsta;
	protected int kolona;
	protected double cijenaBoravka;
	protected int sumaOcjena;
	protected int brojOcjena;
	
	public SobaDTO() {
		super();
	}
	
	public SobaDTO(Long id, int brojSobe, int brojKreveta, int sprat, int vrsta, int kolona, double cijenaBoravka,
			int sumaOcjena, int brojOcjena) {
		super();
		Id = id;
		this.brojSobe = brojSobe;
		this.brojKreveta = brojKreveta;
		this.sprat = sprat;
		this.vrsta = vrsta;
		this.kolona = kolona;
		this.cijenaBoravka = cijenaBoravka;
		this.sumaOcjena = sumaOcjena;
		this.brojOcjena = brojOcjena;
	}

	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
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
	public int getSprat() {
		return sprat;
	}
	public void setSprat(int sprat) {
		this.sprat = sprat;
	}
	public int getVrsta() {
		return vrsta;
	}
	public void setVrsta(int vrsta) {
		this.vrsta = vrsta;
	}
	public int getKolona() {
		return kolona;
	}
	public void setKolona(int kolona) {
		this.kolona = kolona;
	}
	public double getCijenaBoravka() {
		return cijenaBoravka;
	}
	public void setCijenaBoravka(double cijenaBoravka) {
		this.cijenaBoravka = cijenaBoravka;
	}
	public int getSumaOcjena() {
		return sumaOcjena;
	}
	public void setSumaOcjena(int sumaOcjena) {
		this.sumaOcjena = sumaOcjena;
	}
	public int getBrojOcjena() {
		return brojOcjena;
	}
	public void setBrojOcjena(int brojOcjena) {
		this.brojOcjena = brojOcjena;
	}

}
