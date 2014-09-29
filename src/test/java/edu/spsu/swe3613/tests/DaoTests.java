package edu.spsu.swe3613.tests;

import static org.junit.Assert.fail;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DaoTests {

	@Test
	public void failsIfCannotConnectToDB() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:WebReserve.db");
			//If I take out the section below, the .db file is generated in the root folder of the project
			//so it is definitely connecting to sqlite. Maybe there is another way to test connection here
			
			//if (!connection.isValid(10)) {
			//	fail();
			//}
			
			String testQuery1 = "create table test(id text)";
			String testQuery2 = "drop table test";
			Statement statement = connection.createStatement();
			statement.executeUpdate(testQuery1);
			statement.executeUpdate(testQuery2);
			//It didn't seem to like taking two queries in one string, but I can rerun this without SQLException
		} catch (SQLException e) {
			fail();
		}
	}
}
