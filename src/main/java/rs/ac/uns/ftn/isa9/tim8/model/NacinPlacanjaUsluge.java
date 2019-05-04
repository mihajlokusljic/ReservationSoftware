package rs.ac.uns.ftn.isa9.tim8.model;

public enum NacinPlacanjaUsluge {
	DNEVNO_PO_OSOBI(1) {
		@Override
		public String toString() {
			return "Dnevno, po osobi";
		}
	}, 
	DNEVNO(2) {
		@Override
		public String toString() {
			return "Dnevno";
		}
	}, 
	FIKSNO_PO_OSOBI(3) {
		@Override
		public String toString() {
			return "Fiksna cijena, po osobi";
		}
	}, 
	FIKSNO(4) {
		@Override
		public String toString() {
			return "Fiksna cijena";
		}
	};
	
	int id;

	private NacinPlacanjaUsluge() {
	}

	NacinPlacanjaUsluge(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public boolean compare(int i) {
		return this.id == i;
	}
	
	public static NacinPlacanjaUsluge getValue(int id) {
		NacinPlacanjaUsluge[] naciniPlacanja = NacinPlacanjaUsluge.values();
		for(int i = 0; i < naciniPlacanja.length; i++) {
			if(naciniPlacanja[i].compare(id)) {
				return naciniPlacanja[i];
			}
		}
		return null;
	}
	
	
}
