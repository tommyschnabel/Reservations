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
	public void deleteReservation(int reservationId) throws SQLException;

	//Methods for Flight
	public List<Flight> getAllFlights() throws SQLException, ParseException;
	public Flight getFlightById(int flightId) throws SQLException, ParseException;
	public Flight addFlight(Flight flight) throws SQLException;
	public void updateFlight(Flight flight) throws SQLException;
	public void deleteFlight(int flightId) throws SQLException;
		
	public float getPrice(String time) throws SQLException;
	public float getDistance(String start, String destination)throws SQLException;
	public String getDuration(String start, String destination) throws SQLException;
}
