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



	public SQLiteReservationsDao() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:Test.db");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public List<Customer> getAllCustomers() throws SQLException {
		List<Customer> customers = new ArrayList<Customer>();
		String query = 	"SELECT"
				+		"	Customer.ID				id,"
				+	 	"	Customer.FirstName		fname,"
				+ 		"	Customer.LastName		lname,"
				+ 		"	Customer.Email			email,"
				+ 		"	Customer.Password		password"
				
				+ 		" FROM Customer";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			customers.add(new Customer(rs.getString("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password")));
		}
		statement.close();
		return customers;
	}

	@Override
	public Customer getCustomerById(String customerId) throws SQLException {
		String query =  "SELECT" //Here we make our query
				+ 		"	Customer.FirstName		fname,"
				+		"	Customer.LastName		lname,"
				+		"	Customer.Email			email,"
				+		"	Customer.Password		password"
				
				+		"  FROM Customer"
				+	    " WHERE ID = "+"'"+customerId+"'";
		//String.format(query, customerId); //Here we replace '%s with the userId that was passed in
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); //Here we execute the query and get back the results
		
		//Now we make our Customer and then return it
		Customer resultCustomer = new Customer(customerId, rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
		statement.close();
		return resultCustomer;
		
	}

	@Override
	public void addCustomer(Customer customer) throws SQLException {
		String query = 	"INSERT"
				+ 		" INTO Customer Values("
				+ 		"'"+customer.getId()+"'"	+	","
				+		"'"+customer.getFName()+"'"	+	"," 
				+		"'"+customer.getLName()+"'"	+	","
				+		"'"+customer.getEmail()+"'"	+	","
				+		"'"+customer.getPassword()+"'"	+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException {
		String query = 	"UPDATE "
				+		"Customer SET "
				+ 			" FirstName="	+"'"+customer.getFName()+"'"		+	","
				+ 			" LastName="	+"'"+customer.getLName()+"'"		+	","
				+ 			" Email="		+"'"+customer.getEmail()+"'"		+	","
				+ 			" Password="	+"'"+customer.getPassword()+"'"
				+			" WHERE Id="	+	"'"+customer.getId()+"'";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteCustomer(Customer customer) throws SQLException {
		String query = 	"DELETE FROM Customer WHERE"
				+ 		" Id="	+	customer.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public List<Reservation> getAllReservations() throws SQLException{
		List<Reservation> reservations = new ArrayList<Reservation>();
		String query = 	"SELECT"
				+		"	Reservation.ID				id,"
				+	 	"	Reservation.CustomerID		customer,"
				+ 		"	Reservation.FlightID		flight,"
				+ 		"	Reservation.SeatQuantity	seats,"
				+ 		"	Reservation.FlightClass		class"
				
				+ 		" FROM Reservation";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			reservations.add(new Reservation(rs.getInt("id"), rs.getString("customer"), rs.getInt("flight"), rs.getInt("seats"), rs.getString("class")));
		}
		statement.close();
		return reservations;
	}

	@Override
	public Reservation getReservationById(int reservationId) throws SQLException{
		String query =  "SELECT"
				+ 		"	CustomerID		customer,"
				+		"	FlightID		flight,"
				+		"	SeatQuantity	seats,"
				+		"	FlightClass		class"
				
				+		"  FROM Reservation"
				+	    " WHERE ID= "+reservationId; 
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); 
		
		Reservation resultReservation = new Reservation(reservationId, rs.getString("customer"), rs.getInt("flight"), rs.getInt("seats"), rs.getString("class"));
		statement.close();
		return resultReservation;
	}

	@Override
	public void addReservation(Reservation reservation) throws SQLException{
		String query = 	"INSERT"
				+ 		" INTO Reservation(CustomerID,FlightID,SeatQuantity,FlightClass) Values("			
				+		"'"+reservation.getUserId()+"'"		+	"," 
				+			reservation.getFlightId()		+	","
				+			reservation.getSeatQuantity()	+	","
				+		"'"+reservation.getFlightClass()+"'"+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void updateReservation(Reservation reservation) throws SQLException{
		String query = 	"UPDATE "
				+		" Reservation SET"
				+ 			" CustomerID="		+	"'"+reservation.getUserId()+"'"			+	","
				+ 			" FlightID="		+	"'"+reservation.getFlightId()+"'"		+	","
				+ 			" SeatQuantity="	+	"'"+reservation.getSeatQuantity()+"'"	+	","
				+ 			" FlightClass="		+	"'"+reservation.getFlightClass()+"'"
				+			" WHERE ID="		+	"'"+reservation.getId()+"'";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteReservation(Reservation reservation) throws SQLException {
		String query = 	"DELETE FROM Reservation WHERE"
				+ 		" ID="	+	reservation.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();		
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
				+		"	Flight.ID				id,"
				+ 		"	Flight.Date				date,"
				+		"	Flight.AirlineName		airline,"
				+		"	Flight.StartLocation	start,"
				+ 		"	Flight.Destination		end,"
				+ 		"	Flight.Mileage			distance,"
				+ 		"	Flight.RemainingFirstClass	firstClass,"
				+ 		"	Flight.RemainingEconomy	economy,"
				+ 		"	Flight.Price			price"
				
				+		"  FROM Flight";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())	//For each row in the DB
		{
			
			flights.add(new Flight(rs.getInt("id"),rs.getString("date"),rs.getString("airline"),rs.getString("start"),
					 rs.getString("end"), rs.getFloat("distance"), rs.getInt("firstClass"),
					 rs.getInt("economy"),rs.getFloat("price")));
		}
		statement.close();
		return flights;		
	}
	
	@Override
	public Flight getFlightById(int flightId) throws SQLException, ParseException {
		
		String query =  "SELECT"
				+		"	Flight.ID				id,"
				+ 		"	Flight.Date				date,"
				+		"	Flight.AirlineName		airline,"
				+		"	Flight.StartLocation	start,"
				+ 		"	Flight.Destination		end,"
				+ 		"	Flight.Mileage			distance,"
				+ 		"	Flight.RemainingFirstClass	firstClass,"
				+ 		"	Flight.RemainingEconomy	economy,"
				+ 		"	Flight.Price			price"
				
				+		"  FROM Flight"
				+	    " WHERE ID = "+flightId;
		//String.format(query, flightId); 
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		Flight resultFlight = new Flight(rs.getInt("id"),rs.getString("date"),rs.getString("airline"),rs.getString("start"),
										 rs.getString("end"),rs.getFloat("distance"), rs.getInt("firstClass"),
										 rs.getInt("economy"),rs.getFloat("price"));
		statement.close();
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
		String date = flight.getDate();
		String time = date.substring(9);
		float distance =getDistance(flight.getStartingCity(),flight.getDestination()); 		
		flight.setDistance(distance);
		float pricetotal = getPrice(time)*distance;
		flight.setPrice(pricetotal);		
				
		String query = 	"INSERT	INTO Flight "
				+		"(Date,AirlineName,StartLocation,Destination,Mileage,Price)" 
				+		" VALUES("
				+	"'"+flight.getDate()+"'"		+	"," 
				+	"'"+flight.getAirline()+"'"		+	","
				+	"'"+flight.getStartingCity()+"'"+	","
				+	"'"+flight.getDestination()+"'" +	","
				+	"'"+flight.getDistance()+"'"	+	","
				+	"'"+flight.getPrice()+"'"		+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void updateFlight(Flight flight) throws SQLException {
		String query = 	"UPDATE Flight"
				+		" SET"
						+ 	" Date="		+	"'"+flight.getDate()+"'"		+	","
						+ 	" AirlineName="	+	"'"+flight.getAirline()+"'"		+	","
						+	" StartLocation=" +	"'"+flight.getStartingCity()+"'"+	","
						+ 	" Destination="	+	"'"+flight.getDestination()+"'"	+	","
						+	" Mileage="		+	"'"+flight.getDistance()+"'"	+	","
						+	" Price="		+	"'"+flight.getPrice()+"'"		
				+	"WHERE ID="	+ 	flight.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteFlight(Flight flight) throws SQLException{
		String query = 	"DELETE FROM Flight WHERE"
				+ 		" ID="	+	flight.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public List<AirlineAdmin> getAllAirlineAdmins() throws SQLException{
		List<AirlineAdmin> admins = new ArrayList<AirlineAdmin>();
		String query = 	"SELECT"
				+		"	AirlineAdmin.ID				id,"
				+ 		"	AirlineAdmin.Airline		airline,"
				+ 		"	AirlineAdmin.Password		password"
				
				+ 		" FROM AirlineAdmin";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			admins.add( new AirlineAdmin(rs.getString("id"), rs.getString("airline"), rs.getString("password")));
		}
		statement.close();
		return admins;
	}

	@Override
	public AirlineAdmin getAirlineAdminById(String userId) throws SQLException{
		String query =  "SELECT" //Here we make our query
				+		"	AirlineAdmin.ID				id,"
				+ 		"	AirlineAdmin.Airline		airline,"
				+ 		"	AirlineAdmin.Password		password"
								
				+		"  FROM AirlineAdmin"
				+	    " WHERE ID = "+"'"+userId+"'";
		String.format(query, userId); 
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); 
		
		AirlineAdmin resultAdmin = new AirlineAdmin(rs.getString("id"), rs.getString("airline"), rs.getString("password"));
		statement.close();
		return resultAdmin;
	}

	@Override
	public void addAirlineAdmin(AirlineAdmin admin) throws SQLException{
		String query = 	"INSERT"
				+ 		" INTO AirlineAdmin Values("
				+ 		"'"+admin.getId()+"'"		+	","
				+		"'"+admin.getAirline()+"'"	+	"," 
				+		"'"+admin.getPassword()+"'"	+	")";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void updateAirlineAdmin(AirlineAdmin admin) throws SQLException{
		String query = 	"UPDATE "
				+		" AirlineAdmin SET"
				+ 			" Airline	="	+	"'"+admin.getAirline()+"'"		+	","
				+ 			" Password="	+	"'"+admin.getPassword()+"'"
							
				+			" WHERE Id="	+	"'"+admin.getId()+"'";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteAirlineAdmin(AirlineAdmin admin) throws SQLException{
		String query = 	"DELETE FROM AirlineAdmin WHERE"
				+ 		" Id="	+"'"+admin.getId()+"'";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public Airline getAirline(String airlineName) throws SQLException{
		String query = "SELECT"
				+ " Airline.Name			name,"
				+ " Airline.Information		info"
				+ " FROM Airline "
				+ " WHERE Name = "+"'"+airlineName+"'";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); 
		
		Airline resultAirline = new Airline(rs.getString("name"), rs.getString("info"));
		statement.close();
		return resultAirline;
	}

	@Override
	public float getPrice(String time) throws SQLException {
		String query = "SELECT"
				+ " PriceRate 	price "
				+ "	FROM Price "
				+ "	WHERE Time="+"'"+time+"'";
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		float resultPriceRate = rs.getFloat("price");
		statement.close();
		return resultPriceRate;
	}

	@Override
	public float getDistance(String start, String destination) throws SQLException {
		String query = "SELECT "
				+ 	"Distance 		distance "
				+ 	"FROM Mileage "
				+ 	"WHERE "
				+ 		"LocationA="+"'"+start+"'"+" and LocationB="+"'"+destination+"'"
				+ 		" or LocationB="+"'"+start+"'"+" and LocationA="+"'"+destination+"'";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		float resultDistance = rs.getFloat("distance");
		statement.close();
		return resultDistance;
	}

}
