package junit.generate.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cs401caseClasses.Calculator;

public class HandWrittenTest {

	Calculator calc = new Calculator();

	@Test
	public final void testAdd() {
		int actual = calc.add(5, 10);
		assertEquals(15, actual);
	}

	@Test
	public final void testSubtract() {
		int actual = calc.subtract(5, 10);
		assertEquals(-5, actual);
	}


	@Test
	public final void testMultiply() {
		int actual = calc.multiply(5, 10);
		assertEquals(50, actual);

		assertEquals(-20, calc.multiply(-4, 5));

		assertEquals(-10, calc.multiply(5, -2));

		assertEquals(12, calc.multiply(-4, -3));
	}

	@Test
	public final void testDivide() {
		int actual = calc.divide(10, 5);
		assertEquals(2, actual);
	}
}
