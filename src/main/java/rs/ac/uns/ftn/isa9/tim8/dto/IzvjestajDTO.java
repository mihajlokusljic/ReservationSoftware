package rs.ac.uns.ftn.isa9.tim8.dto;

import java.util.ArrayList;

public class IzvjestajDTO {
	
	protected ArrayList<Integer> brojeviYOsa;
	
	protected ArrayList<String> vrijednostiXOse;

	public IzvjestajDTO(ArrayList<Integer> brojeviYOsa, ArrayList<String> vrijednostiXOse) {
		super();
		this.brojeviYOsa = brojeviYOsa;
		this.vrijednostiXOse = vrijednostiXOse;
	}

	public IzvjestajDTO() {
		super();
	}

	public ArrayList<Integer> getBrojeviYOsa() {
		return brojeviYOsa;
	}

	public void setBrojeviYOsa(ArrayList<Integer> brojeviYOsa) {
		this.brojeviYOsa = brojeviYOsa;
	}

	public ArrayList<String> getVrijednostiXOse() {
		return vrijednostiXOse;
	}

	public void setVrijednostiXOse(ArrayList<String> vrijednostiXOse) {
		this.vrijednostiXOse = vrijednostiXOse;
	}
	
}
