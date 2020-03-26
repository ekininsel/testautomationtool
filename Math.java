package CSseniorProject;

public class Math {

	 public String methodSt(String s, int a, String d, int e) {
	 return s + a + d + e;
	 }

	// public int iMethod(int a, int b, int c) {
	// return a + b + c;
	// }

	// public String strMethod(String str, int a) {
	// return str + a;
	// }
	//
	// public String strMethod1(int a, String str) {
	// return str + a;
	// }
	//
	// public int add(int a, int b) {
	// int sum = a + b;
	// return sum;
	// }

	public Boolean bol(Boolean v) {
		return true;
	}

	//public Boolean bol11(Boolean v, int y) {
	//	return true;
	//}

	public Boolean validate(int primeNumber) {
		for (int i = 2; i < (primeNumber / 2); i++) {
			if (primeNumber % i == 0) {
				return false;
			}
		}
		return true;
	}

	public int mm() {
		return 0;
	}

	public String str(String strn) {
		return strn;
	}

	 public int m1(String s, int b) {
	 return b;
	 }

	public int m2() {
		return 1;
	}

	public Object m3(String s) {
		return s;
	}

}
