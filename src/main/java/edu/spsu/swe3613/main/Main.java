//package edu.spsu.swe3613.main;
//
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import com.google.inject.servlet.GuiceServletContextListener;
//
//import edu.spsu.swe3613.login.LoginModule;
//import edu.spsu.swe3613.reservations.ReservationsModule;
//
//public class Main extends GuiceServletContextListener {
//	@Override
//	protected Injector getInjector() {
//		return Guice.createInjector(new LoginModule(), new ReservationsModule());
//	}
//}
