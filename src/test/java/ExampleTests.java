package test.java;

import static org.junit.Assert.fail;
import org.junit.Test;

public class ExampleTests {

	@Test (expected = AssertionError.class)
	public void failsJustBecause() {
		fail();
	}
}
