package edu.spsu.swe3613.reservations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class SQLiteReservationsDao implements ReservationsDao {
	
private Connection connection;

private DateFormat df = new SimpleDateFormat("MM/dd/yy'hh:mm a");

	public SQLiteReservationsDao() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:WebReserve.db");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public List<Customer> getAllCustomers() throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		String query = 	"SELECT"
				+		"	Customer.ID				id"
				+	 	"	Customer.FirstName		fname"
				+ 		"	Customer.LastName		lname"
				+ 		"	Customer.Email			email"
				+ 		"	Customer.Password		password"
				
				+ 		" FROM schema.Customer Customer";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			customers.add(new Customer(rs.getString("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password")));
		}
		return customers;
	}

	@Override
	public Customer getCustomerById(String customerId) throws SQLException {
		String query =  "SELECT" //Here we make our query
				+		"	Customer.ID				id"
				+ 		"	Customer.FirstName		fname"
				+		"	Customer.LastName		lname"
				+		"	Customer.Email			email"
				+		"	Customer.Password		password"
				
				+		"  FROM schema.Customer Customer"
				+	    " WHERE Customer.Username = %s";
		String.format(query, customerId); //Here we replace '%s with the userId that was passed in
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); //Here we execute the query and get back the results
		
		//Now we make our Customer and then return it
		Customer resultCustomer = new Customer(rs.getString("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
		return resultCustomer;
	}

	@Override
	public void addCustomer(Customer customer) throws SQLException {
		String query = 	"INSERT"
				+ 		" INTO Customer Values("
				+ 			customer.getId()		+	","
				+			customer.getFName()		+	"," 
				+			customer.getLName()		+	","
				+			customer.getEmail()		+	","
				+			customer.getPassword()	+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		String query = 	"UPDATE"
				+		"Customer SET"
				+ 			"FirstName="	+	customer.getFName()		+	","
				+ 			"LastName="		+	customer.getLName()		+	","
				+ 			"Email="		+	customer.getEmail()		+	","
				+ 			"Password="		+	customer.getPassword()
				+			"WHERE Id="		+	customer.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
	}

	@Override
	public void deleteCustomer(Customer customer) throws SQLException {
		String query = 	"DELETE FROM Customers WHERE"
				+ 		"Id="	+	customer.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		
	}

	@Override
	public List<Reservation> getAllReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation getReservationById(int reservationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addReservation(Reservation reservation) {
		// TODO needs to decrement RemainingSeats
		// reservation table needs quantity of seats and flightClass attributes for this to work
		
	}

	@Override
	public void updateReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		
	}

	/*
	I don't know if anybody needs the GregorianCalendar type in particular. To be safe, these methods
	for Flight are implemented in such a way that a Flight object contains the date and time as
	GregorianCalendar, and the DB field contains a String in the format of "01/02/03 01:00 PM".
	If having access to the String type is more convenient, I can easily change it.
	*/
	@Override
	public List<Flight> getAllFlights() throws SQLException, ParseException {
		List<Flight> flights = new ArrayList<Flight>();
		String query = 	"SELECT"
				+		"	Flight.FlightNumber		id"
				+ 		"	Flight.Date				date"
				+		"	Flight.Time				time"
				+		"	Flight.AirlineName		airline"
				+		"	Flight.StartLocation	start"
				+ 		"	Flight.Destination		end"
				+ 		"	Flight.Mileage			distance"
				+ 		"	Flight.RemainingFirstClass	firstClass"
				+ 		"	Flight.RemainingEconomy	economy"
				+ 		"	Flight.Price			price"
				
				+		"  FROM schema.Flight		Flight";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())	//For each row in the DB
		{
			GregorianCalendar date = new GregorianCalendar(); 			
			date.setTime(df.parse(rs.getString("date")));		//convert String to GregorianCalendar
			flights.add(new Flight(rs.getInt("id"),date,rs.getString("airline"),rs.getString("start"),
					 rs.getString("end"), rs.getFloat("distance"), rs.getInt("firstClass"),
					 rs.getInt("economy"),rs.getFloat("price")));
		}
		return flights;		
	}
	
	@Override
	public Flight getFlightById(int flightId) throws SQLException, ParseException {
		
		String query =  "SELECT"
				+		"	Flight.FlightNumber		id"
				+ 		"	Flight.Date				date"
				+		"	Flight.AirlineName		airline"
				+		"	Flight.StartLocation	start"
				+ 		"	Flight.Destination		end"
				+ 		"	Flight.Mileage			distance"
				+ 		"	Flight.RemainingFirstClass	firstClass"
				+ 		"	Flight.RemainingEconomy	economy"
				+ 		"	Flight.Price			price"
				
				+		"  FROM schema.Flight		Flight"
				+	    " WHERE Flight.FlightNumber = %s";
		String.format(query, flightId); 
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(df.parse(rs.getString("date")));		//Convert String date to GregorianCalendar
																
		Flight resultFlight = new Flight(rs.getInt("id"),gc,rs.getString("airline"),rs.getString("start"),
										 rs.getString("end"),rs.getFloat("distance"), rs.getInt("firstClass"),
										 rs.getInt("economy"),rs.getFloat("price"));
		return resultFlight;
	}
	
	/*The addFlight method takes a Flight object as a parameter and:
	 * converts the date to a String, 
	 * extracts the time that is needed to calculate price,
	 * looks up distance and price rate
	 * Adds a record in the DB including id, date as string, price, and distance
	 * without needing id, price, or distance in the Flight object constructor
	*/
	@Override
	public void addFlight(Flight flight) throws SQLException {
		GregorianCalendar gc = flight.getDate();
		String date = df.format(gc);			
		DateFormat tf = new SimpleDateFormat("hh:mm a");												
		GregorianCalendar timeCal = new GregorianCalendar(gc.get(GregorianCalendar.HOUR_OF_DAY),
													 gc.get(GregorianCalendar.MINUTE),
													 0); //Extract only Time without date from Calendar
		String time = tf.format(timeCal);	//convert Calendar to String in format of DB table Price
		float distance =getDistance(flight.getStartingCity(),flight.getDestination()); 		
		flight.setDistance(distance);
		flight.setPrice(getPrice(time)*distance);		
				
		String query = 	"INSERT	INTO Flight "
				+		"(Date,AirlineName,StartLocation,Destination,Mileage,Price)" 
				+		"VALUES("
				+		date					+	"," 
				+		flight.getAirline()		+	","
				+		flight.getStartingCity()+	","
				+		flight.getDestination() +	","
				+		flight.getDistance()	+	","
				+		flight.getPrice()		+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
	}

	@Override
	public void updateFlight(Flight flight) throws SQLException {
		GregorianCalendar gc = flight.getDate();
		String date = df.format(gc);
		
		String query = 	"UPDATE Flight"
				+		"SET"
						+ 	"Date="			+	date					+	","
						+ 	"AirlineName="	+	flight.getAirline()		+	","
						+	"StartLocation=" +	flight.getStartingCity()+	","
						+ 	"Destination="	+	flight.getDestination()	+	","
						+	"Mileage="		+	flight.getDistance()	+	","
						+	"Price="		+	flight.getPrice()		
				+	"WHERE FlightNumber="	+ 	flight.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
	}

	@Override
	public void deleteFlight(Flight flight) throws SQLException{
		String query = 	"DELETE FROM Flight WHERE"
				+ 		"FlightNumber="	+	flight.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);		
	}

	@Override
	public List<AirlineAdmin> getAllAirlineAdmin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AirlineAdmin getAirlineAdminById(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAirlineAdmin(AirlineAdmin admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAirlineAdmin(AirlineAdmin admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAirlineAdmin(AirlineAdmin admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Airline getAirline(String airlineName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getPrice(String time) throws SQLException {
		String query = "SELECT"
				+ " Price.PriceRate 	price "
				+ "	FROM schema.Price "
				+ "	WHERE Time="+time;
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		float resultPriceRate = rs.getFloat("price");
		return resultPriceRate;
	}

	@Override
	public float getDistance(String start, String destination) throws SQLException {
		String query = "SELECT"
				+ 	"Mileage.Distance 		distance "
				+ 	"FROM schema.Mileage "
				+ 	"WHERE "
				+ 		"LocationA="+start+" and LocationB="+destination+""
				+ 		" or LocationB="+start+" and LocationA="+destination;
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		float resultDistance = rs.getFloat("distance");
		return resultDistance;
	}

}
