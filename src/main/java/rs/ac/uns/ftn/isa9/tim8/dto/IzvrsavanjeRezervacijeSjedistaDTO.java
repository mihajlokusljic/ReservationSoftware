package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.List;

public class IzvrsavanjeRezervacijeSjedistaDTO {
	protected List<Long> rezervisanaSjedistaIds;

	protected List<Long> pozvaniPrijateljiIds;

	protected List<PodaciPutnikaDTO> podaciNeregistrovanihPutnika;

	protected Long idPutovanja;

	protected Long idLeta;

	public IzvrsavanjeRezervacijeSjedistaDTO() {
		super();
	}

	public IzvrsavanjeRezervacijeSjedistaDTO(List<Long> rezervisanaSjedistaIds, List<Long> pozvaniPrijateljiIds,
			List<PodaciPutnikaDTO> podaciNeregistrovanihPutnika, Long idPutovanja, Long idLeta) {
		super();
		this.rezervisanaSjedistaIds = rezervisanaSjedistaIds;
		this.pozvaniPrijateljiIds = pozvaniPrijateljiIds;
		this.podaciNeregistrovanihPutnika = podaciNeregistrovanihPutnika;
		this.idPutovanja = idPutovanja;
		this.idLeta = idLeta;
	}

	public List<Long> getRezervisanaSjedistaIds() {
		return rezervisanaSjedistaIds;
	}

	public void setRezervisanaSjedistaIds(List<Long> rezervisanaSjedistaIds) {
		this.rezervisanaSjedistaIds = rezervisanaSjedistaIds;
	}

	public List<Long> getPozvaniPrijateljiIds() {
		return pozvaniPrijateljiIds;
	}

	public void setPozvaniPrijateljiIds(List<Long> pozvaniPrijateljiIds) {
		this.pozvaniPrijateljiIds = pozvaniPrijateljiIds;
	}

	public List<PodaciPutnikaDTO> getPodaciNeregistrovanihPutnika() {
		return podaciNeregistrovanihPutnika;
	}

	public void setPodaciNeregistrovanihPutnika(List<PodaciPutnikaDTO> podaciNeregistrovanihPutnika) {
		this.podaciNeregistrovanihPutnika = podaciNeregistrovanihPutnika;
	}

	public Long getIdPutovanja() {
		return idPutovanja;
	}

	public void setIdPutovanja(Long idPutovanja) {
		this.idPutovanja = idPutovanja;
	}

	public Long getIdLeta() {
		return idLeta;
	}

	public void setIdLeta(Long idLeta) {
		this.idLeta = idLeta;
	}

}
