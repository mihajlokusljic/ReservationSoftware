package rs.ac.uns.ftn.isa9.tim8.dto;

public class OdgovorNaPozivnicuDTO {
	
	protected Long idPozvanogPrijatelja;
	protected Long idPutovanja;
	
	public OdgovorNaPozivnicuDTO() {
		super();
	}
	public OdgovorNaPozivnicuDTO(Long idPozvanogPrijatelja, Long idPutovanja) {
		super();
		this.idPozvanogPrijatelja = idPozvanogPrijatelja;
		this.idPutovanja = idPutovanja;
	}
	public Long getIdPozvanogPrijatelja() {
		return idPozvanogPrijatelja;
	}
	public void setIdPozvanogPrijatelja(Long idPozvanogPrijatelja) {
		this.idPozvanogPrijatelja = idPozvanogPrijatelja;
	}
	public Long getIdPutovanja() {
		return idPutovanja;
	}
	public void setIdPutovanja(Long idPutovanja) {
		this.idPutovanja = idPutovanja;
	}

}
