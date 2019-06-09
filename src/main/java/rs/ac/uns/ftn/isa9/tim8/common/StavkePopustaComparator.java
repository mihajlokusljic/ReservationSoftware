package rs.ac.uns.ftn.isa9.tim8.common;

import java.util.Comparator;

import rs.ac.uns.ftn.isa9.tim8.model.BonusPopust;

public class StavkePopustaComparator implements Comparator<BonusPopust> {

	@Override
	public int compare(BonusPopust o1, BonusPopust o2) {
		return o1.getDonjaGranicaBonusPoeni().compareTo(o2.getDonjaGranicaBonusPoeni());
	}

}
