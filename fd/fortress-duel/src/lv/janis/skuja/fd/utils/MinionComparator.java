package lv.janis.skuja.fd.utils;

import java.util.Comparator;

import lv.janis.skuja.fd.model.Minion;

/**
 * @author Janis Skuja
 * 
 */
public class MinionComparator implements Comparator<Minion> {
	@Override
	public int compare(Minion m1, Minion m2) {
		return m2.getY().compareTo(m1.getY());
	}
}
