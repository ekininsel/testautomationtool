package junit.generate.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cs401caseClasses.Math;

public class HandWrittenMathTest {
	private Math math = new Math();

	@Test
	public final void testvalidate() {
		assertTrue(math.validate(11));
		assertFalse(math.validate(10));
	}

	@Test
	public final void testprint() {
		Object o = "String";

	}

	@Test
	public final void testemptyMethod() {

	}

	@Test
	public final void teststr() {
		String str = "sdda";
		assertEquals(str, math.str("sdda"));
	}

	@Test
	public final void testadd() {
		int a = 9;
		int b = 10;
		int sum = a + b;
		assertEquals(sum, math.add(a, b));

	}
}
