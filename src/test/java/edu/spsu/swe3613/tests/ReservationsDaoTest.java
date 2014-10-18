package edu.spsu.swe3613.tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import edu.spsu.swe3613.edu.spsu.swe3613.admin.AdminDao;
import edu.spsu.swe3613.edu.spsu.swe3613.admin.SqLiteAdminDao;
import jersey.repackaged.com.google.common.base.Objects;

import org.junit.Before;
import org.junit.Test;

import edu.spsu.swe3613.reservations.Airline;
import edu.spsu.swe3613.edu.spsu.swe3613.admin.AirlineAdmin;
import edu.spsu.swe3613.reservations.City;
import edu.spsu.swe3613.reservations.Flight;
import edu.spsu.swe3613.reservations.Reservation;
import edu.spsu.swe3613.reservations.SQLiteReservationsDao;
import edu.spsu.swe3613.user.SqLiteUserDao;
import edu.spsu.swe3613.user.UserDao;

public class ReservationsDaoTest {
	
	private static SQLiteReservationsDao testDao;
    private static AdminDao testAdminDao;
	
	private Flight flight;
	private static Reservation reservation;
	private static AirlineAdmin admin = new AirlineAdmin("adminId","Southwest","password");
	
	private static Connection connection;
	private static Statement statement;
		
	@Before
	public void setUpBefore() throws Exception{

        flight = new Flight(1,"10/01/14 9:00 AM",Airline.Delta,City.Atlanta,City.Dallas, 5f,10,10,45.00f);
        reservation = new Reservation(1,1,1, Reservation.SeatClass.firstClass);
		
		connection = DriverManager.getConnection("jdbc:sqlite:Test.db");
		testDao = new SQLiteReservationsDao(connection);
        testAdminDao = new SqLiteAdminDao(connection);
		statement = connection.createStatement();
		//Create sample DB test methods
		//Flight
		statement.executeUpdate("drop table if exists Flight");
		statement.executeUpdate("create table Flight"
				+ "(ID integer primary key,Date text,AirlineName text,StartLocation text,Destination text,Mileage numeric default 0, "
				+ "RemainingFirstClass integer default 30,RemainingEconomy integer default 70, Price numeric default 0)");
		//Mileage
		statement.executeUpdate("drop table if exists Mileage");
		statement.executeUpdate("create table Mileage"
				+ "(ID integer primary key, LocationA text, LocationB text, Distance numeric)");
		statement.executeUpdate("insert into Mileage (LocationA,LocationB,Distance) values ('Atlanta','Chicago',586),('Atlanta','Dallas',721),('Atlanta','NewYork',746),"
				+ "('Atlanta','SanFrancisco',2140),('Chicago','Dallas',802),('Chicago','NewYork',714),('Chicago','SanFrancisco',1858),"
				+ "('Dallas','NewYork',1373),('Dallas','SanFrancisco',1483),('NewYork','SanFrancisco',2572)");
		//Price
		statement.executeUpdate("drop table if exists Price");
		statement.executeUpdate("create table Price (Time text primary key, PriceRate numeric)");
		statement.executeUpdate("insert into Price values"
				+ 				" ('9:00 AM',1.15),('1:00 PM',1.5),('5:00 PM',1),('8:00 PM',.85)");
		//Reservation
		statement.executeUpdate("drop table if exists Reservation");
		statement.executeUpdate("create table Reservation (ID integer primary key, CustomerID integer, FlightID integer, FlightClass text)");
		//Airline
		statement.executeUpdate("drop table if exists Airline");
		statement.executeUpdate("create table Airline (Name text primary key, Information text)");
		statement.executeUpdate("insert into Airline values ('Delta','test description'),('Southwest','test description'),('American','test description')");
		//AirlineAdmin
		statement.executeUpdate("drop table if exists AirlineAdmin");
		statement.executeUpdate("create table AirlineAdmin (ID text primary key, Airline text, Password text)");
		
	}
		
	@Test
	public void testAddFlight(){
		try{
			Flight testFlight = testDao.addFlight(flight);
			if(!flight.getDate().equals(testFlight.getDate())
					|| !flight.getAirline().equals(testFlight.getAirline())
					|| !flight.getStartingCity().equals(testFlight.getStartingCity())
					|| !flight.getDestination().equals(testFlight.getDestination()) 
			){
				fail("Flight added incorrectly");
			}
		}
		catch (SQLException e){
			fail();
		}
	}

	@Test
	public void testGetAllFlights(){
		try{
			testDao.addFlight(flight);
			List<Flight> resultList = testDao.getAllFlights();
            Boolean foundAddedFlight = false;
            for (Flight f : resultList) {
                if (Objects.equal(f.getId(), flight.getId())
                        && Objects.equal(f.getDate(), flight.getDate())) {
                    foundAddedFlight = true;
                }
            }
            if (!foundAddedFlight) {
                fail("Dao didn't return added flight when all flights requested");
            }
		}
		catch (SQLException|ParseException e){
			fail();
		}
	}
	

	@Test
	public void testGetFlightById(){
		try{
			testDao.addFlight(flight);
			Flight resultFlight = testDao.getFlightById(flight.getId());
			if (!Objects.equal(flight.getId(), resultFlight.getId())
                    && !Objects.equal(flight.getDate(), resultFlight.getDate())) {
                fail("Flight ID error");
            }
		}
		catch (SQLException|ParseException e){
			fail();
		}
	}
	
	@Test
	public void testUpdateFlight() {
		try{
			testDao.addFlight(flight);
			String date = "12/12/12 1:00 PM";
			String time = date.substring(9);
			flight.setDate(date);
			flight.setAirline(Airline.Southwest);
            flight.setStartingCity(City.SanFrancisco);
            flight.setDestination(City.NewYork);
			float distance = testDao.getDistance(City.SanFrancisco.toString(),City.NewYork.toString());
			float priceRate = testDao.getPrice(time);
            flight.setDistance(distance);
            flight.setPrice(distance*priceRate);
			testDao.updateFlight(flight);
            Flight updatedFlight = testDao.getFlightById(flight.getId());
            if(!Objects.equal(updatedFlight.getId(), flight.getId())
                    || !Objects.equal(updatedFlight.getDate(), flight.getDate())) {
                fail("the updated flight was not equal to the original flight");
            }
		}
		catch (SQLException|ParseException e){
			fail();
		}
	}
	
	@Test
	public void testDeleteFlight(){
		try{
            testDao.addFlight(flight);
			testDao.deleteFlight(flight);
			List<Flight> resultList = testDao.getAllFlights();
			if(resultList.contains(flight))
				fail();
		}
		catch (SQLException|ParseException e){
			fail();
		}
	}
		
	@Test
	public void testAddReservation() throws Exception{
		try{
			Flight testFlight = testDao.addFlight(flight);
			Reservation testReservation = testDao.addReservation(reservation);
			Flight resultFlight = testDao.getFlightById(flight.getId());
			if(!reservation.equals(testReservation))
				fail();
			if(resultFlight.getSeatsInEconomy() != (testFlight.getSeatsInEconomy()-1)
				&& resultFlight.getSeatsInFirstClass()	 != (testFlight.getSeatsInFirstClass()-1))
				fail();
		}
		catch(SQLException e){
			fail();
		}
	}
	
	@Test
	public void testGetAllReservations(){
		try{
			testDao.addFlight(flight);
			Reservation testReservation = testDao.addReservation(reservation);
			List<Reservation> testList = new ArrayList<Reservation>(); 
			testList.add(testReservation);		
			List<Reservation> resultList = testDao.getAllReservations();
			if (testList.size() != resultList.size()) {
                fail("List is not the right size");
            }
			for (int i=0; i<resultList.size();i++){
                assertEquals(resultList.get(i),testList.get(i));
            }
		}
		catch (SQLException e){
			fail();
		}
	}
		
	@Test
	public void testGetReservationById(){
		try{
			testDao.addFlight(flight);
			testDao.addReservation(reservation);
			Reservation testReservation = testDao.getReservationById(reservation.getId());
			if(!reservation.equals(testReservation))
				fail("Reservation ID error");
		}
		catch (SQLException e){
		}
	}
	
	@Test
	public void testUpdateReservation() throws Exception{
		try{
			testDao.addFlight(flight);
			testDao.addReservation(reservation);
			reservation.setFlightId(2);
			reservation.setUserId(2);
			reservation.setFlightClass(Reservation.SeatClass.economy);
			testDao.updateReservation(reservation);
			Reservation testReservation = testDao.getReservationById(reservation.getId());
			if(!reservation.equals(testReservation))
				fail();		
		}
		catch (SQLException e){
			fail();
		}
	}

	@Test
	public void testDeleteReservation(){
		try{
			testDao.addFlight(flight);
			Reservation testReservation = testDao.addReservation(reservation);
			List<Reservation> testList = testDao.getAllReservations();
			if(!testList.contains(testReservation))
				fail();
			testDao.deleteReservation(testReservation);
			List<Reservation> resultList = testDao.getAllReservations();
			if(resultList.contains(testReservation))
				fail();
		}
		catch (SQLException e){
			fail();
		}
	}

	@Test
	public void testAddAirlineAdmin(){
		try{
			AirlineAdmin testAdmin = testAdminDao.addAirlineAdmin(admin);
			if(!testAdmin.equals(admin))
				fail();
		}
		catch(SQLException e){
			fail();
		}
	}

	@Test
	public void testGetAllAirlineAdmins(){
		try{
			List<AirlineAdmin> testList = new ArrayList<AirlineAdmin>(); 
			testList.add(admin);
			testAdminDao.addAirlineAdmin(admin);
			List<AirlineAdmin> resultList = testAdminDao.getAllAirlineAdmins();
			if (testList.size() != resultList.size())
				fail("List is not the right size");
			for (int i=0; i<resultList.size();i++)
				if(!resultList.get(i).equals(testList.get(i)))
					fail("List contains incorrect data");
		}
		catch (SQLException e){
			fail();
		}
	}
	
	@Test
	public void testGetAirlineAdminById(){
		try{
			testAdminDao.addAirlineAdmin(admin);
			AirlineAdmin testAdmin = testAdminDao.getAirlineAdminById(admin.getId());
			if(!admin.equals(testAdmin))
				fail("Admin ID error");
		}
		catch (SQLException e){
			fail();
		}
	}
	
	@Test
	public void testUpdateAirlineAdmin(){
		try{
			testAdminDao.addAirlineAdmin(admin);
			admin.setAirline("American");
			admin.setPassword("tiger");
			testAdminDao.updateAirlineAdmin(admin);
			AirlineAdmin testAdmin = testAdminDao.getAirlineAdminById(admin.getId());
			if(!admin.equals(testAdmin))
				fail();	
		}
		catch (SQLException e){
			fail();
		}
	}
	
	@Test
	public void testDeleteAirlineAdmin(){
		try{
			AirlineAdmin testAdmin = testAdminDao.addAirlineAdmin(admin);
			List<AirlineAdmin> testList = testAdminDao.getAllAirlineAdmins();
			if(!testList.contains(testAdmin))
				fail();
            testAdminDao.deleteAirlineAdmin(admin);
			List<AirlineAdmin> resultList = testAdminDao.getAllAirlineAdmins();
			if(resultList.contains(testAdmin))
				fail();
		}
		catch (SQLException e){
			fail();
		}
	}

//    Commented out since refactoring of Airline turned it into an enum
//	@Test
//	public void testGetAirline(){
//		try{
//			Airline testAirline = testAdminDao.getAirline(airline.getName());
//			if(!airline.getName().equals(testAirline.getName())
//			|| !airline.getInfo().equals(testAirline.getInfo()))
//				fail();
//		}
//		catch (SQLException e){
//			fail();
//		}
//	}
	



}
