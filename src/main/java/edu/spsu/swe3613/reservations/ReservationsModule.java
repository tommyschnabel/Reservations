package edu.spsu.swe3613.reservations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;

public class ReservationsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(SQLiteReservationsDao.class);
		bind(DefaultReservationsService.class);
		
		try {
			bind(Connection.class).toInstance(DriverManager.getConnection("jdbc:sqlite:WebReserve.db"));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
