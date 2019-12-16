package cs401caseClasses;

public class Calculator {
	public int add(int value1, int value2) {
		return value1 + value2;
	}

	public int subtract(int value1, int value2) {
		return value1 - value2;
	}

	public int multiply(int value1, int value2) {
		return value1 * value2;
	}

	public int divide(int value1, int value2) {
		if (value2 == 0)
			return -1;
		return value1 / value2;
	}

	public int m(int value1) {
		return value1 + 1;
	}
}
