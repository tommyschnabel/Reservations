package edu.spsu.swe3613.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;

public class ExampleModule extends AbstractModule {

	//Basically the point of these is to never <i>new</i> an object that depends on anything else
	//So we have all of our modules dependent on each other, and Guice takes care of Injecting what
	//we need where we need it
	
	@Override
	protected void configure() {
		//Any time we look for ExampleObject, it will give us ExampleA
		bind(ExampleObject.class).to(ExampleA.class);
		
		//Any time we look for ExampleObject, it will give us ExampleB
		//Usually we'll only have one of these
		//bind(ExampleObject.class).to(ExampleB.class);
		
//		try {
//			bind(Connection.class).toInstance(DriverManager.getConnection("jdbc:sqlite:ExampleWebReserve.db"));
//		} catch (SQLException e) {
//			System.out.print(e.getMessage());
//		}
		
		//This allows us to easily change whole files (we will use it for initial setup)
		//Once we have a real implementation we can then change what is injected
		//Dependency Injection with Guice 101 https://github.com/google/guice/wiki/Motivation
	}

}
