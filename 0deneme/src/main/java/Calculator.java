package cs401caseClasses;
public class Calculator {
	public int add(int value1, int value2) {
		return value1 + value2;
	}

	public int subtract(int value1, int value2) {
		return value1 - value2;
	}

	public int multiply(int value1, int value2) {
		int result = 0;
		for (int i = 0; i < value2; i++) {
			result += value1;
		}
		return result;
	}

	public int divide(int value1, int value2) {
		return value1 / value2;
	}
}
