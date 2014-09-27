package edu.spsu.swe3613.reservations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;

import edu.spsu.swe3613.examples.User;

public class SQLiteReservationsDao implements ReservationsDao {
	
private Connection connection;
	
	//We inject the connection so we can switch it up if we need to
	//And if we do that then we don't have to switch it in multiple places
	//Just in the module
	@Inject
	public SQLiteReservationsDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer getCustomerById(String customerId) throws SQLException {
		String query =  "SELECT" //Here we make our query
				+		"	Customer.Username		id"
				+ 		"	Customer.FirstName		fname"
				+		"	Customer.LastName		lname"
				+		"	Customer.Email			email"
				+		"	Customer.Password		password"
				
				+		"  FROM schema.Customer Customer"
				+	    " WHERE Customer.Username = %s";
		String.format(query, customerId); //Here we replace '%s with the userId that was passed in
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); //Here we execute the query and get back the results
		
		//Now we make our User and then return it
		Customer resultCustomer = new Customer(rs.getString("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"), rs.getString("password"));
		return resultCustomer;
	}

	@Override
	public void addCustomer(Customer customer) throws SQLException {
		String query = 	"INSERT into Customer Values"
				+	"("	+	customer.getId()	+	","
				+			customer.getFName()	+	"," 
				+			customer.getLName()	+	","
				+			customer.getEmail()	+	","
				+			customer.getPassword()+	")";
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteReservation(Reservation reservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Flight> getAllFlights() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flight getFlightById(int flightId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addFlight(Flight flight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFlight(Flight flight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFlight(Flight flight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFlightPrice(Flight flight, float price) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFlightDistance(Flight flight, float distance) {
		// TODO Auto-generated method stub
		
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
	public Float getPrice(String time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getDistance(String start, String destination) {
		// TODO Auto-generated method stub
		return null;
	}

}
