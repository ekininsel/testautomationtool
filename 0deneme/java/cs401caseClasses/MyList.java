package cs401caseClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyList {

	private List lstFruits = new ArrayList<>();

	public void add(String fruit) {
		lstFruits.add(fruit);
	}

	public void remove(String fruit) {
		if (!lstFruits.contains(fruit)) {
			throw new NoSuchElementException();
		}
		lstFruits.remove(fruit);
	}

	public int size() {
		return lstFruits.size();
	}

	public void removeAll() {
		lstFruits.clear();
	}
}