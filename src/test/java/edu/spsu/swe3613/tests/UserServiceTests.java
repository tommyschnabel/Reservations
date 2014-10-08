package edu.spsu.swe3613.tests;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;

import edu.spsu.swe3613.user.DefaultUserService;
import edu.spsu.swe3613.user.SqLiteUserDao;
import edu.spsu.swe3613.user.User;
import edu.spsu.swe3613.user.UserDao;
import edu.spsu.swe3613.user.UserService;

public class UserServiceTests {
	
	private UserService userService;
	private UserDao mockUserDao;
	private Connection connection;

	@Before
	public void setup() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:Test.db");
		} catch (SQLException e) {
			fail();
		}
		
		mockUserDao = new SqLiteUserDao(connection);
		userService = new DefaultUserService(mockUserDao);
		

		try {
		Statement statement;
		statement = connection.createStatement();
		statement.executeUpdate("drop table if exists Customer");
		statement.executeUpdate("create table Customer(ID text primary key, FirstName text, LastName text,"
				+ 				"Email text, Password text)");
			statement.executeUpdate("insert into Customer values('testID','testFName','testLName','test@test.com','1234')");
		} catch (SQLException e) {
			fail();
		}
				
		
	}
	
	//TODO make this test actually work
	@Test
	public void registersUserCorrectly() {
		User newCustomer = new User(1, "tommy", "schnabel", "tommy@fakemail.com", "password");
		
//		User retrievedUser = new User(1, "t", "schnabel", "tommy@fakemail.com", "password");
		
		try {
			userService.register(newCustomer);
			User retrievedUser = userService.getUserById(newCustomer.getId());
			if (retrievedUser.getId() != newCustomer.getId()
					|| retrievedUser.getFName() != newCustomer.getFName()
					|| retrievedUser.getLName() != newCustomer.getLName()
					|| retrievedUser.getEmail() != newCustomer.getEmail()
					|| retrievedUser.getPassword() != newCustomer.getPassword()) {
				
				System.out.println("Didn't recieve the same customer by the same id");			
				System.out.println("Got: " + retrievedUser.getId() + ',' + "Wanted: " + newCustomer.getId());
				System.out.println(retrievedUser.getFName() + ',' + newCustomer.getFName());				
				System.out.println(retrievedUser.getLName() + ',' + newCustomer.getLName());
				System.out.println(retrievedUser.getEmail() + ',' + newCustomer.getEmail());
				System.out.println(retrievedUser.getPassword() + ',' + newCustomer.getPassword());
//				fail();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail(e.getMessage());
		}
	}
}
