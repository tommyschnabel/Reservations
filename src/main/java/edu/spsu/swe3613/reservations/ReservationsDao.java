package edu.spsu.swe3613.reservations;

import java.util.List;

public interface ReservationsDao {
	
	//Methods for common CRUD operations for each table in the database
	
	//Methods for Customer
	public List<Customer> getAllCustomers();
	public Customer getCustomerById(String customerId);
	public void addCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public void deleteCustomer(Customer customer);
	
	//Methods for Reservation
	public List<Reservation> getAllReservations();
	public Reservation getReservationById(int reservationId);
	public void addReservation(Reservation reservation);
	public void updateReservation(Reservation reservation);
	public void deleteReservation(Reservation reservation);
	
	//Methods for Flight
	public List<Flight> getAllFlights();
	public Flight getFlightById(int flightId);
	public void addFlight(Flight flight);
	public void updateFlight(Flight flight);
	public void deleteFlight(Flight flight);
	//need methods to calculate and set Price and Distance, not really sure here
	public void setFlightPrice(Flight flight, float price);		
	public void setFlightDistance(Flight flight, float distance); 
	
	
	
	//Methods for AirlineAdmin
	public List<AirlineAdmin> getAllAirlineAdmin();
	public AirlineAdmin getAirlineAdminById(String userId);
	public void addAirlineAdmin(AirlineAdmin admin);
	public void updateAirlineAdmin(AirlineAdmin admin);
	public void deleteAirlineAdmin(AirlineAdmin admin);
		
	// The data stored in the Airline, Price, and Mileage isn't going to change for
	// release 1, so probably don't need CRUD but just gets that return objects...I think
	public Airline getAirline(String airlineName);
	public Price getPrice(String time);
	public Distance getDistance(String start, String destination);
	
	// TODO finish classes for Customer, Flight, Reservation, AirlineAdmin, Airline, Price, Mileage
}
