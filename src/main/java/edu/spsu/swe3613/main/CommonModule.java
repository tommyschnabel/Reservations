package edu.spsu.swe3613.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.inject.AbstractModule;

public class CommonModule extends AbstractModule {

	@Override
	protected void configure() {
		try {
			bind(Connection.class).toInstance(DriverManager.getConnection("jdbc:sqlite:WebReserve.db"));
		} catch (SQLException e) {
			System.out.println("Couldn't get a DB connection, proceeding to break everything");
			System.out.println(e.getMessage());
			throw new RuntimeException("Couldn't make a connection to the Database");
		}
	}

}
