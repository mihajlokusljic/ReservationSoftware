package rs.ac.uns.ftn.isa9.tim8.model;

import java.util.Date;

public class Pozivnica {
	protected boolean prihvacena;
	protected Date rokPrihvatanja;
	protected Putovanje putovanje;
	
	public Pozivnica() {
		super();
	}

	public Pozivnica(boolean prihvacena, Date rokPrihvatanja, Putovanje putovanje) {
		super();
		this.prihvacena = prihvacena;
		this.rokPrihvatanja = rokPrihvatanja;
		this.putovanje = putovanje;
	}

	public boolean isPrihvacena() {
		return prihvacena;
	}

	public void setPrihvacena(boolean prihvacena) {
		this.prihvacena = prihvacena;
	}

	public Date getRokPrihvatanja() {
		return rokPrihvatanja;
	}

	public void setRokPrihvatanja(Date rokPrihvatanja) {
		this.rokPrihvatanja = rokPrihvatanja;
	}

	public Putovanje getPutovanje() {
		return putovanje;
	}

	public void setPutovanje(Putovanje putovanje) {
		this.putovanje = putovanje;
	}
	
	
	
}
