package edu.spsu.swe3613.reservations;

import java.text.ParseException;
import java.util.List;
import java.sql.SQLException;

public interface ReservationsDao {
	
	//Methods for common CRUD operations for each table in the database
	
	//Methods for Reservation
	public List<Reservation> getAllReservations() throws SQLException;
	public Reservation getReservationById(int reservationId) throws SQLException;
	public Reservation addReservation(Reservation reservation) throws SQLException;
	public void updateReservation(Reservation reservation) throws SQLException;
	public void deleteReservation(Reservation reservation) throws SQLException;
	
	//Methods for Flight
	public List<Flight> getAllFlights() throws SQLException, ParseException;
	public Flight getFlightById(int flightId) throws SQLException, ParseException;
	public Flight addFlight(Flight flight) throws SQLException;
	public void updateFlight(Flight flight) throws SQLException;
	public void deleteFlight(Flight flight) throws SQLException;
		
	
	//Methods for AirlineAdmin
	public List<AirlineAdmin> getAllAirlineAdmins() throws SQLException;
	public AirlineAdmin getAirlineAdminById(String userId) throws SQLException;
	public void addAirlineAdmin(AirlineAdmin admin) throws SQLException;
	public void updateAirlineAdmin(AirlineAdmin admin) throws SQLException;
	public void deleteAirlineAdmin(AirlineAdmin admin) throws SQLException;
		
	// The data stored in the Airline, Price, and Mileage isn't going to change for
	// release 1, so probably don't need CRUD but just gets that return objects...I think
	public Airline getAirline(String airlineName) throws SQLException;
	public float getPrice(String time) throws SQLException;
	public float getDistance(String start, String destination)throws SQLException;
	
	// TODO finish classes for Customer, Flight, Reservation, AirlineAdmin, Airline, Price, Distance
}
