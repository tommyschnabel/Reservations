package main.java.examples;

import com.google.inject.Inject;

public class DependencyInjectionExample {
	
	private ExampleObject exampleObject;

	@Inject
	public DependencyInjectionExample(ExampleObject exampleObject) {
		this.exampleObject = exampleObject;
	}
}
