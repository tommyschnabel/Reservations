package edu.spsu.swe3613.reservations;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.inject.Inject;

import javax.ws.rs.core.Response;

public class DefaultReservationsService implements ReservationsService {
	
	private ReservationsDao dao;
	private List<Flight> allFlights;
	private List<Flight> searchFlights;
	private List<Reservation> allReservations;
	
	@Inject
	public DefaultReservationsService(ReservationsDao resDao){

        this.dao = resDao;
    }
	
	@Override
    public List<Flight> search(SimpleSearchParams searchParams) {

        System.out.println("Starting Search");
        System.out.println(searchParams.getStartCity() + " " + searchParams.getStartDate()
                            + " -> " + searchParams.getEndCity() + " " + searchParams.getEndDate());

        List<Flight> flights;
        List<Flight> searchResults = new ArrayList<Flight>();

        Long startDate = Long.valueOf(searchParams.getStartDate());
        Long endDate = Long.valueOf(searchParams.getEndDate());
        try {
            flights = dao.getAllFlights();
        } catch (Exception e) {
            System.out.println("Something went wrong while retrieving the flights");
            System.out.println(e.getMessage());
            return null;
        }

        for (Flight f : flights) {
            Long flightDate = Long.valueOf(f.getDate());

            if (flightDate >= startDate
                && flightDate <= endDate
                && f.getStartingCity().toString().equals(searchParams.getStartCity().toString())
                && f.getDestination().toString().equals(searchParams.getEndCity().toString())) {

                searchResults.add(f);
            }
        }

        System.out.println("Returning " + searchResults.size() + " flights");
        return searchResults;
	}
	
	//Create a new flight and add it to the list of flights.
	@Override
    public Response.Status createReservation(int flightId, int userId, SeatClass seatClass) {

        try {
            int newReservationId = dao.getAllReservations().size();
            Reservation newReservation = new Reservation(newReservationId, userId, flightId, seatClass);
            dao.addReservation(newReservation);
            return Response.Status.ACCEPTED;
        } catch (SQLException e) {
            System.out.println("Something went wrong while creating the reservation");
            System.out.println(e.getMessage());
            return Response.Status.CONFLICT;
        }
		
	}

    @Override
	public Response.Status deleteReservation(int id, int userId) {
		
		try {
			if (dao.getReservationById(id).getUserId() == userId) {
                dao.deleteReservation(id);
                System.out.println("User " + userId + " is deleted reservation " + id);
                return Response.Status.OK;
            }
        } catch (SQLException e) {
            System.out.println("something went wrong while deleting the reservation");
            System.out.println(e.getMessage());
        }
        return Response.Status.CONFLICT;
	}
	
	@Override
    public List<Reservation> getReservationsForUser(int userId) {
        List<Reservation> reservations;
        List<Reservation> reservationsForUser = new ArrayList<Reservation>();

        try {
            reservations = dao.getAllReservations();
        } catch (SQLException e) {
            System.out.println("something went wrong while getting the reservations for user " + userId);
            System.out.println(e.getMessage());
            return reservationsForUser;
        }

        for (Reservation r : reservations) {
            if (r.getUserId() == userId) {
                reservationsForUser.add(r);
            }
        }

        return reservationsForUser;
    }

    @Override
    public Flight getFlightById(int flightId) {
        try {
            return dao.getFlightById(flightId);
        } catch (SQLException|ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
	
}


