package rs.ac.uns.ftn.isa9.tim8.model;

public abstract class Ocjenjivo {
	
	protected int sumaOcjena;
	protected int brojOcjena;
	
	
	public Ocjenjivo() {
		super();
	}


	public Ocjenjivo(int sumaOcjena, int brojOcjena) {
		super();
		this.sumaOcjena = sumaOcjena;
		this.brojOcjena = brojOcjena;
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
	
	public double azurirajPodatke(int novaOcjena) {
		this.brojOcjena++;
		this.sumaOcjena += novaOcjena;
		return (double)sumaOcjena/brojOcjena;
	}
}
