package edu.spsu.swe3613.main;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import edu.spsu.swe3613.examples.ExampleWeb;
import edu.spsu.swe3613.login.LoginWeb;
import edu.spsu.swe3613.reservations.ReservationsWeb;

public class Application extends ResourceConfig {
	
	@Inject
	public Application(ServiceLocator serviceLocator) {
		packages("edu.spsu.swe3613.reservations",
				 "edu.spsu.swe3613.login");
	
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		
		guiceBridge.bridgeGuiceInjector(Main.injector);
		
		register(ReservationsWeb.class);
		register(LoginWeb.class);
		register(ExampleWeb.class);
	}

}
