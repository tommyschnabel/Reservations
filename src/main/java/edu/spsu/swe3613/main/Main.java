package edu.spsu.swe3613.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

import edu.spsu.swe3613.admin.AdminModule;
import edu.spsu.swe3613.examples.ExampleModule;
import edu.spsu.swe3613.reservations.ReservationsModule;
import edu.spsu.swe3613.user.UserModule;

public class Main extends GuiceServletContextListener {
	
	static Injector injector;

	@Override
	protected Injector getInjector() {
		
		injector = Guice.createInjector(new ExampleModule(),
										new CommonModule(),
				 						new ReservationsModule(),
				 						new UserModule(),
                                        new AdminModule());

		return injector;
	}

	
}
