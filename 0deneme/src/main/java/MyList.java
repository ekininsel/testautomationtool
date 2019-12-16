package cs401caseClasses;

import java.util.ArrayList;
import java.util.List;

public class MyList {

	private List lstFruits = new ArrayList<>();

	public MyList() {

	}

	public void add(String fruit) {
		lstFruits.add(fruit);
	}

	public void remove(String fruit) {
		if (!lstFruits.contains(fruit)) {
			System.out.println("Error");
		}
		lstFruits.remove(fruit);
	}

	public int sizeA(int a) {
		return a + 1;
	}

	public int sizeDefiner() {
		return lstFruits.size();
	}

	public String str(String s) {
		return s;
	}

	public String s1(String a, String b) {
		return a + b;
	}

	public int i1(int a, int b) {
		return a + b;
	}

	public String str1(int a, String b) {
		return a + b;
	}

	public void removeAll() {
		lstFruits.clear();
	}
}