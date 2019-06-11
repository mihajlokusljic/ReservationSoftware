package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.Collection;

public class PrikazSjedistaDTO {
	protected Collection<String> sjedista;

	protected Collection<PrikazSegmentaDTO> segmenti;

	public PrikazSjedistaDTO() {
		super();
	}

	public PrikazSjedistaDTO(Collection<String> sjedista, Collection<PrikazSegmentaDTO> segmenti) {
		super();
		this.sjedista = sjedista;
		this.segmenti = segmenti;
	}

	public Collection<String> getSjedista() {
		return sjedista;
	}

	public void setSjedista(Collection<String> sjedista) {
		this.sjedista = sjedista;
	}

	public Collection<PrikazSegmentaDTO> getSegmenti() {
		return segmenti;
	}

	public void setSegmenti(Collection<PrikazSegmentaDTO> segmenti) {
		this.segmenti = segmenti;
	}

}
