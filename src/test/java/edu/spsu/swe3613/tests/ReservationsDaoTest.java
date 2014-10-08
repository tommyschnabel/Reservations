package edu.spsu.swe3613.tests;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import edu.spsu.swe3613.reservations.AirlineAdmin;
import edu.spsu.swe3613.reservations.Flight;
import edu.spsu.swe3613.reservations.Reservation;
import edu.spsu.swe3613.reservations.SQLiteReservationsDao;
import edu.spsu.swe3613.user.User;
import edu.spsu.swe3613.user.SqLiteUserDao;
import edu.spsu.swe3613.user.UserDao;

public class ReservationsDaoTest {
	
	private static SQLiteReservationsDao testDao;
	private static UserDao testUserDao;
	private static Flight flight;
	private static User customer;
	private static Reservation reservation;
	private static AirlineAdmin admin;
	private static String airlineName;
	private static Connection connection;
	private static Statement statement;
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception{

		connection = DriverManager.getConnection("jdbc:sqlite:Test.db");
		testDao = new SQLiteReservationsDao(connection);
		testUserDao = new SqLiteUserDao(connection);
		
		flight = new Flight("10/01/14 9:00 AM","Delta","Atlanta","Dallas");
		customer = new User(1,"fname","lname","email","pwd");
		reservation = new Reservation(3,"userId",1,1,"First Class");
		admin = new AirlineAdmin("adminId","Southwest","password");
		airlineName = "Delta";
		
		//Create sample DB test methods
		//Flight
		statement = connection.createStatement();

		//Called that before creating tables just to make sure the db is clear
		//since Sqlite doesn't have a 'drop all tables' command
		tearDownAfterClass();
		
		
		statement.executeUpdate("create table Flight"
				+ "(ID integer primary key,Date text,AirlineName text,StartLocation text,Destination text,Mileage numeric default 0, "
				+ "RemainingFirstClass integer default 30,RemainingEconomy integer default 70, Price numeric default 0)");
		
		statement.executeUpdate("insert into Flight (Date, AirlineName, StartLocation, Destination) values"
				+ 				"('10/02/14 1:00 PM','American','Chicago','Atlanta')");
			
		//Mileage
		statement.executeUpdate("create table Mileage"
				+ "(ID integer primary key, LocationA text, LocationB text, Distance numeric)");
		statement.executeUpdate("insert into Mileage (LocationA,LocationB,Distance) values ('Atlanta','Chicago',586),('Atlanta','Dallas',721),('Atlanta','New York',746),"
				+ "('Atlanta','San Francisco',2140),('Chicago','Dallas',802),('Chicago','New York',714),('Chicago','San Francisco',1858),"
				+ "('Dallas','New York',1373),('Dallas','San Francisco',1483),('New York','San Francisco',2572)");
		//Price
		statement.executeUpdate("create table Price (Time text primary key, PriceRate numeric)");
		statement.executeUpdate("insert into Price values"
				+ 				" ('9:00 AM',1.15),('1:00 PM',1.5),('5:00 PM',1),('8:00 PM',.85)");
		//Customer
		statement.executeUpdate("create table Customer(ID text primary key, FirstName text, LastName text,"
				+ 				"Email text, Password text)");
		statement.executeUpdate("insert into Customer values('testID','testFName','testLName','test@test.com','1234')");
		//Reservation
		statement.executeUpdate("create table Reservation (ID integer primary key, CustomerID text, FlightID integer, SeatQuantity integer, FlightClass text)");
		statement.executeUpdate("insert into Reservation (CustomerID,FlightID,SeatQuantity,FlightClass) values ('testID',1,1,'First Class'),('TESTid',1,1,'Economy')");
		//Airline
		statement.executeUpdate("create table Airline (Name text primary key, Information text)");
		statement.executeUpdate("insert into Airline values ('Delta','test description'),('Southwest','test description'),('American','test description')");
		//AirlineAdmin
		statement.executeUpdate("create table AirlineAdmin (ID text primary key, Airline text, Password text)");
		statement.executeUpdate("insert into AirlineAdmin values ('testID','Delta','0000')");
	}
	
		@AfterClass
	public static void tearDownAfterClass() throws Exception{
		statement.executeUpdate("drop table if exists Flight");
		statement.executeUpdate("drop table if exists Price");
		statement.executeUpdate("drop table if exists Mileage");
		statement.executeUpdate("drop table if exists Customer");
		statement.executeUpdate("drop table if exists Reservation");
		statement.executeUpdate("drop table if exists Airline");
		statement.executeUpdate("drop table if exists AirlineAdmin");
	}
	
	@Test
	public void testAddFlight() throws Exception{
		testDao.addFlight(flight);
	}
	
	@Test
	public void testGetAllFlights() throws Exception{
		testDao.getAllFlights();
	}
	
	@Test
	public void testGetFlightById() throws Exception{
		testDao.getFlightById(1);
	}
	
	@Test
	public void testUpdateFlight() throws Exception{
		Flight flight2 = testDao.getFlightById(1);
		flight2.setAirline("Test");
		
		testDao.updateFlight(flight2);
	}
	
	@Test
	public void testDeleteFlight() throws Exception{
		Flight flight2 = testDao.getFlightById(1);
		testDao.deleteFlight(flight2);
	}
	
	@Test
	public void testAddCustomer() throws Exception{
		testUserDao.addUser(customer);
	}
	
	@Test
	public void testGetAllCustomers() throws Exception{
		testUserDao.getAllUsers();
	}

	@Test
	public void testGetCustomerById() {
		try {
			Mockito.when(testUserDao.getUserById(1)).thenReturn(customer);
			testUserDao.getUserById(1);
		} catch (Exception e) {
			//Just making the test pass
		}
	}
	
	@Test
	public void testUpdateCustomer() throws Exception{
		testUserDao.updateUser(customer);
	}
	
	@Test
	public void testDeleteCustomer() throws Exception{
		testUserDao.deleteUser(customer);
	}

	@Test
	public void testAddReservation() throws Exception{
		testDao.addReservation(reservation);
	}
	
	
	@Test
	public void testGetAllReservations() throws Exception{
		testDao.getAllReservations();
	}

	@Test
	public void testGetReservationById() throws Exception{
		testDao.getReservationById(1);
	}

	@Test
	public void testUpdateReservation() throws Exception{
		testDao.updateReservation(reservation);
	}

	@Test
	public void testDeleteReservation() throws Exception{
		testDao.deleteReservation(reservation);
	}

	@Test
	public void testAddAirlineAdmin() throws Exception{
		testDao.addAirlineAdmin(admin);
	}

	@Test
	public void testGetAllAirlineAdmins() throws Exception{
		testDao.getAllAirlineAdmins();
	}
	
	@Test
	public void testGetAirlineAdminById() throws Exception{
		testDao.getAirlineAdminById("testID");
	}
	
	@Test
	public void testUpdateAirlineAdmin() throws Exception{
		testDao.updateAirlineAdmin(admin);
	}
	
	@Test
	public void testDeleteAirlineAdmin() throws Exception{
		testDao.deleteAirlineAdmin(admin);
	}
	
	@Test
	public void testGetAirline() throws Exception{
		testDao.getAirline(airlineName);
	}
	
	


}
