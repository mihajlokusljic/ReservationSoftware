package rs.ac.uns.ftn.isa9.tim8.service;

public class NevalidniPodaciException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NevalidniPodaciException() {
		super();
	}
	
	public NevalidniPodaciException(String poruka) {
		super(poruka);
	}

}
