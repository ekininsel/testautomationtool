package CSseniorProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Math {

	 public int iMethod(int a, int b, int c) {
	 	return a + b + c;
	 }

	public List<Integer> deneme(int a, int b){
		List<Integer> aa = new ArrayList<> ();
		aa.add (a);
		aa.add (b);
		return aa;
	}

	public List<String> deneme1(String a, String b){
		List<String> aa = new ArrayList<> ();
		aa.add (a);
		aa.add (b);
		return aa;
	}

	public List<Boolean> denemee(boolean a, boolean b){
		List<Boolean> aa = new ArrayList<> ();
		aa.add (a);
		aa.add (b);
		return aa;
	}

	public List<Double> denemeee(double a, double b){
		List<Double> aa = new ArrayList<> ();
		aa.add (a);
		aa.add (b);
		return aa;
	}

	public double add(double a, int b) {
	 double sum = a + b;
	 return sum;
	 }

	public Boolean bol(boolean v) {
		return true;
	}

	public Boolean validate(int primeNumber) {
		for (int i = 2; i < (primeNumber / 2); i++) {
			if (primeNumber % i == 0) {
				return false;
			}
		}
		return true;
	}

	public Object m3(String s) {
		return s;
	}

}
