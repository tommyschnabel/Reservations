package edu.spsu.swe3613.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.spsu.swe3613.examples.ExampleObject;

public class ExampleTests {
	
	private ExampleObject otherExample;
	
	public ExampleTests() {
		
	}
	
	//This runs before every test
	@Before
	public void testSetup() {
		//Now we have control over what this obejct does with Mockito.when
		otherExample = Mockito.mock(ExampleObject.class);
	}

	@Test (expected = AssertionError.class)
	public void failsJustBecause() {
		fail();
	}
	
	@Test
	public void mockitoExample() {
		//Now whenever we call otherExample.getInt(), 5 will always be returned
		//We can use this to control the environment and test whether certain things
		//are working correctly
		Mockito.when(otherExample.getInt()).thenReturn(5);
		Integer i = otherExample.getInt();
		if (i != 5) {
			fail(); //Test fails if i isn't 5
		}
		
		//This verifies that otherExample.getInt() was called in this test
		//or by an object that interacts with this test
		Mockito.verify(otherExample).getInt();
	}
}
