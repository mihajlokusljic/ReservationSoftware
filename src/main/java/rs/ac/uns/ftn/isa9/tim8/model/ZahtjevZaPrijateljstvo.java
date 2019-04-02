package rs.ac.uns.ftn.isa9.tim8.model;

public class ZahtjevZaPrijateljstvo {
	protected boolean potvrdjen;
	protected RegistrovanKorisnik posiljalac;
	
	public ZahtjevZaPrijateljstvo() {
		super();
	}

	public ZahtjevZaPrijateljstvo(boolean potvrdjen, RegistrovanKorisnik posiljalac) {
		super();
		this.potvrdjen = potvrdjen;
		this.posiljalac = posiljalac;
	}

	public boolean isPotvrdjen() {
		return potvrdjen;
	}

	public void setPotvrdjen(boolean potvrdjen) {
		this.potvrdjen = potvrdjen;
	}

	public RegistrovanKorisnik getPosiljalac() {
		return posiljalac;
	}

	public void setPosiljalac(RegistrovanKorisnik posiljalac) {
		this.posiljalac = posiljalac;
	}
	
	
	
	
}
