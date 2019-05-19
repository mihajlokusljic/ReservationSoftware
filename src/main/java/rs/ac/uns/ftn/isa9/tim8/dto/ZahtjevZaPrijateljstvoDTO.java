package rs.ac.uns.ftn.isa9.tim8.dto;

public class ZahtjevZaPrijateljstvoDTO {
	protected Long posiljalacId;

	protected Long primalacId;

	public ZahtjevZaPrijateljstvoDTO() {
		super();
	}

	public ZahtjevZaPrijateljstvoDTO(Long posiljalacId, Long primalacId) {
		super();
		this.posiljalacId = posiljalacId;
		this.primalacId = primalacId;
	}

	public Long getPosiljalacId() {
		return posiljalacId;
	}

	public void setPosiljalacId(Long posiljalacId) {
		this.posiljalacId = posiljalacId;
	}

	public Long getPrimalacId() {
		return primalacId;
	}

	public void setPrimalacId(Long primalacId) {
		this.primalacId = primalacId;
	}

}
