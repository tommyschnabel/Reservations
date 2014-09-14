package main.java.reservations;

import com.google.inject.AbstractModule;

public class ReservationsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ReservationsDao.class).to(MockReservationsDao.class);
		bind(ReservationsService.class).to(DefaultReservationsService.class);
	}

}
