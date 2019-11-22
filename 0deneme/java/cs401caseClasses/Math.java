package cs401caseClasses;
public class Math {

	public Math() {

	}

	public Boolean validate(final Integer primeNumber) {
		for (int i = 2; i < (primeNumber / 2); i++) {
			if (primeNumber % i == 0) {
				return false;
			}
		}
		return true;
	}

	public void print() {
		System.out.println("String");
	}

	public void emptyMethod() {

	}

	public String str(String strn) {
		return strn;
	}

	public int add(int a, int b) {
		int sum = a + b;
		return sum;
	}
}
