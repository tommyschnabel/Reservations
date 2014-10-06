package edu.spsu.swe3613.examples;

import com.google.inject.Inject;

public class DependencyInjectionExample {
	
	@SuppressWarnings("unused")
	private ExampleObject exampleObject;

	//This constructor should never be called, except when unit testing
	//Guice (the dependency injector) will give us everything, and we will
	//always ask Guice for an instance of this object
	@Inject
	public DependencyInjectionExample(ExampleObject exampleObject) {
		this.exampleObject = exampleObject;
	}
}
