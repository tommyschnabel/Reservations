package edu.spsu.swe3613.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import edu.spsu.swe3613.examples.ExampleModule;
import edu.spsu.swe3613.login.LoginModule;
import edu.spsu.swe3613.reservations.ReservationsModule;

public class Main extends GuiceServletContextListener {
	
	static Injector injector;

	@Override
	protected Injector getInjector() {
		
		injector = Guice.createInjector(new ExampleModule(),
										new CommonModule(),
				 						new ReservationsModule(),
				 						new LoginModule());

		return injector;
	}

	
}
