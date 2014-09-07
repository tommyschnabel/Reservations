package main.java.examples;

import com.google.inject.AbstractModule;

public class ExampleModule extends AbstractModule {

	@Override
	protected void configure() {
		//Any time we look for ExampleObject, it will give us ExampleA
		bind(ExampleObject.class).to(ExampleA.class);
		
		//Any time we look for ExampleObject, it will give us ExampleB
		bind(ExampleObject.class).to(ExampleB.class);
		
		//This allows us to easily change whole files (we will use it for initial setup)
		//Once we have a real implementation we can then change what is injected
		//Dependency Injection with Guice 101 https://github.com/google/guice/wiki/Motivation
	}

}
