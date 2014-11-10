package edu.spsu.swe3613.main;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson1.Jackson1Feature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import edu.spsu.swe3613.admin.AdminWeb;
import edu.spsu.swe3613.examples.ExampleWeb;
import edu.spsu.swe3613.reservations.ReservationsWeb;
import edu.spsu.swe3613.user.UserWeb;

public class Application extends ResourceConfig {
	
	@Inject
	public Application(ServiceLocator serviceLocator) {
		packages("edu.spsu.swe3613.reservations",
				 "edu.spsu.swe3613.login",
				 "edu.spsu.swe3613.admin");
	
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
		GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
		
		guiceBridge.bridgeGuiceInjector(Main.injector);
		
		register(Jackson1Feature.class);
		register(DefaultObjectMapperProvider.class);
		register(ReservationsWeb.class);
		register(UserWeb.class);
		register(AdminWeb.class);
		register(ExampleWeb.class);
	}

}
