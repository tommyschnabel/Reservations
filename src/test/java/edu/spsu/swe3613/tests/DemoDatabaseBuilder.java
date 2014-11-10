package edu.spsu.swe3613.tests;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import edu.spsu.swe3613.reservations.Airline;
import edu.spsu.swe3613.reservations.City;
import edu.spsu.swe3613.reservations.Flight;
import edu.spsu.swe3613.reservations.SQLiteReservationsDao;

public class DemoDatabaseBuilder {
	private static SQLiteReservationsDao testDao;
	private static Connection connection;
	private static Statement statement;
		
	@Before
	public void setUpBefore() throws Exception{

        connection = DriverManager.getConnection("jdbc:sqlite:Demo.db");
		testDao = new SQLiteReservationsDao(connection);
        
		statement = connection.createStatement();
		
		//Flight
		statement.executeUpdate("drop table if exists Flight");
		statement.executeUpdate("create table Flight"
														+ "(ID integer primary key,"
														+ "Date text,"
														+ "AirlineName text,"
														+ "StartLocation text,"
														+ "Destination text,"
														+ "Mileage numeric default 0, "
														+ "RemainingFirstClass integer default 30,"
														+ "RemainingEconomy integer default 70, "
														+ "EconomyPrice numeric default 0,"
														+ "FirstClassPrice numeric default 0)");
		//Mileage
		statement.executeUpdate("drop table if exists Mileage");
		statement.executeUpdate("create table Mileage"
														+ "(ID integer primary key,"
														+ "LocationA text,"
														+ "LocationB text,"
														+ "Distance numeric)");
		
		statement.executeUpdate("insert into Mileage (LocationA,LocationB,Distance) values "
														+ "('Atlanta','Chicago',586),"
														+ "('Atlanta','Dallas',721),"
														+ "('Atlanta','NewYork',746),"
														+ "('Atlanta','SanFrancisco',2140),"
														+ "('Chicago','Dallas',802),"
														+ "('Chicago','NewYork',714),"
														+ "('Chicago','SanFrancisco',1858),"
														+ "('Dallas','NewYork',1373),"
														+ "('Dallas','SanFrancisco',1483),"
														+ "('NewYork','SanFrancisco',2572)");
		//Price
		statement.executeUpdate("drop table if exists Price");
		statement.executeUpdate("create table Price (Time text primary key, PriceRate numeric)");
		statement.executeUpdate("insert into Price values "
														+ "('0900',1.15),"
														+ "('1300',1.5),"
														+ "('1700',1),"
														+ "('2100',.85)");
		//Reservation
		statement.executeUpdate("drop table if exists Reservation");
		statement.executeUpdate("create table Reservation (ID integer primary key, CustomerID integer, FlightID integer, FlightClass text)");
		statement.executeUpdate("insert into Reservation values"
														+ "(1,1,1,'FirstClass'),"
														+ "(2,2,10,'FirstClass'),"
														+ "(3,3,20,'FirstClass'),"
														+ "(4,4,30,'FirstClass'),"
														+ "(5,5,40,'FirstClass'),"
														+ "(6,6,50,'FirstClass'),"
														+ "(7,1,60,'Economy'),"
														+ "(8,2,70,'Economy'),"
														+ "(9,3,80,'Economy'),"
														+ "(10,4,90,'Economy'),"
														+ "(11,5,100,'Economy'),"
														+ "(12,6,110,'Economy'),"
														+ "(13,1,120,'FirstClass'),"
														+ "(14,2,130,'FirstClass'),"
														+ "(15,3,140,'FirstClass'),"
														+ "(16,4,150,'FirstClass'),"
														+ "(17,5,160,'FirstClass'),"
														+ "(18,6,170,'FirstClass'),"
														+ "(19,1,180,'Economy'),"
														+ "(20,2,190,'Economy'),"
														+ "(21,3,200,'Economy'),"
														+ "(22,4,210,'Economy'),"
														+ "(23,5,220,'Economy'),"
														+ "(24,6,230,'Economy')");
		//Airline
		statement.executeUpdate("drop table if exists Airline");
		statement.executeUpdate("create table Airline (Name text primary key)");
		statement.executeUpdate("insert into Airline values "
														+ "('Delta'),"
														+ "('Southwest'),"
														+ "('American')");
		//User
		statement.executeUpdate("drop table if exists User");
		statement.executeUpdate("create table User "
														+ "(ID integer primary key, "
														+ "FirstName text, "
														+ "LastName text, "
														+ "Email text, "
														+ "Password text,"
														+ "IsAdmin integer)");
		statement.executeUpdate("insert into User values"
														+ "(1,'Alex','Honeycutt','alex@email.com','password',1),"
														+ "(2,'Kenny','Kingery','kenny@email.com','password',1),"
														+ "(3,'Roger','Oliver','roger@email.com','password',1),"
														+ "(4,'Nathan','Pelt','nathan@email.com','password',1),"
														+ "(5,'Tommy','Schnabel','tommy@email.com','password',1),"
														+ "(6,'Yu','Yang','yu@email.com','password',1)");
	
	
	}
		
	@Test
	public void generateFlights(){
		try{
			List<Flight> flights = new ArrayList<Flight>();
			List<City> locations=new ArrayList<City>(
					Arrays.asList(City.Atlanta,City.Chicago,City.NewYork,City.Dallas,City.SanFrancisco));
			List<String> times = new ArrayList<String>(
					Arrays.asList("0900","1300","1700","2100"));
			List<Airline> airline = new ArrayList<Airline>(
					Arrays.asList(Airline.Delta, Airline.Southwest, Airline.American));
			Random rand = new Random();
			int count = 1;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			startDate.setTime(format.parse("20141001"));
			endDate.setTime(format.parse("20150430"));
						
			while(Integer.valueOf(format.format(startDate.getTime())) < Integer.valueOf(format.format(endDate.getTime()))){
				for(int j=0;j<17;j++)//flights per day
					{
						Flight testFlight = new Flight(count,
													   format.format(startDate.getTime())+times.get((j/3)%4),
													   airline.get(j%3), 
													   locations.get(rand.nextInt(5)), 
													   locations.get((rand.nextInt(4)*j)%5),
													   0.0f,0,0,0.0f,0.0f);
								if (!testFlight.getStartingCity().equals(testFlight.getDestination()))
								{
									flights.add(testFlight);
									count++;
								}		
					}
				startDate.add(Calendar.DATE,1);
			}
				for (int i=0;i<flights.size();i++)
				{
					testDao.addFlight(flights.get(i));
				}
				List<Flight> flightList = testDao.getAllFlights();
				for(int i=0;i<flightList.size();i++){			
					Flight resultFlight = flightList.get(i);
					System.out.println(resultFlight.getId()+" "+
								   	   resultFlight.getDate()+" "+
								   	   resultFlight.getStartingCity()+" "+
								   	   resultFlight.getDestination()+" "+
								   	   resultFlight.getDistance()+" "+
								   	   resultFlight.getEconomyPrice()+" "+
								   	   resultFlight.getFirstClassPrice()+" "+
								   	   resultFlight.getSeatsInFirstClass()+" "+
								   	   resultFlight.getSeatsInEconomy());
				}
		}
		catch (SQLException|ParseException e){
			fail();
		}
	}
}

