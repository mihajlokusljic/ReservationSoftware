package rs.ac.uns.ftn.isa9.tim8.dto;

public class DodavanjeSobeDTO {
	
	protected Long idHotela;
	protected int brojSobe;
	protected int sprat;
	protected int vrsta;
	protected int kolona;
	protected int brojKreveta;
	protected double cijenaNocenja;
	
	public DodavanjeSobeDTO() {
		super();
	}
	public Long getIdHotela() {
		return idHotela;
	}
	public void setIdHotela(Long idHotela) {
		this.idHotela = idHotela;
	}
	public int getBrojSobe() {
		return brojSobe;
	}
	public void setBrojSobe(int brojSobe) {
		this.brojSobe = brojSobe;
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
	public int getBrojKreveta() {
		return brojKreveta;
	}
	public void setBrojKreveta(int brojKreveta) {
		this.brojKreveta = brojKreveta;
	}
	public double getCijenaNocenja() {
		return cijenaNocenja;
	}
	public void setCijenaNocenja(double cijenaNocenja) {
		this.cijenaNocenja = cijenaNocenja;
	}
	
}
