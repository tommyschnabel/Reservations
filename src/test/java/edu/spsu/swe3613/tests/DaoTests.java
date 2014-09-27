package edu.spsu.swe3613.tests;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoTests {

	
	//This test won't ever fail and I don't know why, feel free to take a swing at it
	//I'm not sure if the db is ever hit
	public void failsIfCannotConnectToDB() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:WebReserve.db");
			if (!connection.isValid(10)) {
				fail();
			}
			String testQuery = "create table test(id text); drop table test;";
			Statement statement = connection.createStatement();
			statement.execute(testQuery); 
			
		} catch (SQLException e) {
			fail();
		}
	}
}
