package edu.spsu.swe3613.tests;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import edu.spsu.swe3613.user.User;
import edu.spsu.swe3613.user.UserDao;



public class DaoTests {

	@Test
	public void failsIfCannotConnectToDB() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:WebReserve.db");
			//If I take out the section below, the .db file is generated in the root folder of the project
			//so it is definitely connecting to sqlite. Maybe there is another way to test connection here
			
			if (connection.isClosed()) {
			fail();
			}
			
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
 
	
		
		private UserDao daoExample;
				
		//This runs before every test
		@Before
		public void testSetup() {
			//Now we have control over what this obejct does with Mockito.when
			daoExample = Mockito.mock(UserDao.class);
		}

		@Test (expected = AssertionError.class)
		public void failsJustBecause() {
			fail();
		}
		
		@Test
		public void getCustomerByIdTest() {
			try{			
				User customer = new User(1,"fname","lname","email","pwd");
				Mockito.when(daoExample.getUserById(1)).thenReturn(new User(1,"fname","lname","email","pwd"));
				if(daoExample.getUserById(1)!=customer)
				Mockito.verify(daoExample).getUserById(1);
			}
			catch (SQLException e){
				fail();
			}
		}
}
