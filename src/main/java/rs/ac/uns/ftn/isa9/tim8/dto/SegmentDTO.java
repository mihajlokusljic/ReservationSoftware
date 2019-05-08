package rs.ac.uns.ftn.isa9.tim8.dto;

public class SegmentDTO {
	protected String naziv;
	protected Long idAviona;
	protected int pocetniRed; // po redovima dodjeljujemo segmente za sjedista
	protected int krajnjiRed; // ako stavis npr od 1 do 3, onda ces u prva tri reda dodijeliti ovaj segment

	public SegmentDTO() {
		super();
	}

	public SegmentDTO(String naziv, Long idAviona, int pocetniRed, int krajnjiRed) {
		super();
		this.naziv = naziv;
		this.idAviona = idAviona;
		this.pocetniRed = pocetniRed;
		this.krajnjiRed = krajnjiRed;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Long getIdAviona() {
		return idAviona;
	}

	public void setIdAviona(Long idAviona) {
		this.idAviona = idAviona;
	}

	public int getPocetniRed() {
		return pocetniRed;
	}

	public void setPocetniRed(int pocetniRed) {
		this.pocetniRed = pocetniRed;
	}

	public int getKrajnjiRed() {
		return krajnjiRed;
	}

	public void setKrajnjiRed(int krajnjiRed) {
		this.krajnjiRed = krajnjiRed;
	}

}
