package edu.spsu.swe3613.main;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Module;
import com.squarespace.jersey2.guice.JerseyGuiceServletContextListener;

import edu.spsu.swe3613.examples.ExampleModule;
import edu.spsu.swe3613.login.LoginModule;
import edu.spsu.swe3613.reservations.ReservationsModule;

public class Main extends JerseyGuiceServletContextListener {

	@Override
	protected List<? extends Module> modules() {
		List<Module> modules = new ArrayList<Module>();
		modules.add(new ExampleModule());
		modules.add(new LoginModule());
		modules.add(new ReservationsModule());
		
		return modules;
	}
	
}
