package edu.spsu.swe3613.admin;

import com.google.inject.Inject;

import edu.spsu.swe3613.reservations.Flight;
import edu.spsu.swe3613.reservations.Reservation;
import edu.spsu.swe3613.reservations.ReservationsDao;

import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class DefaultAdminService implements AdminService {

    private AdminDao adminDao;
    private ReservationsDao reservationsDao;

    @Inject
    public DefaultAdminService(AdminDao adminDao, 
    						   ReservationsDao reservationsDao) {
        this.adminDao = adminDao;
        this.reservationsDao = reservationsDao;
    }

    @Override
    public List<AirlineAdmin> getAllAirlineAdmins() {
        try {
            return adminDao.getAllAirlineAdmins();
        } catch (SQLException e) {
            System.out.println("Something went wrong while getting all airline admins");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AirlineAdmin getAirlineAdminById(String userId) {
        try {
            return adminDao.getAirlineAdminById(userId);
        } catch (SQLException e) {
            System.out.println("something went wrong while getting admin");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AirlineAdmin addAirlineAdmin(AirlineAdmin admin) {
        try {
            return adminDao.addAirlineAdmin(admin);
        } catch (SQLException e) {
            System.out.println("Something went wrong while adding admin");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response.Status updateAirlineAdmin(AirlineAdmin admin) {
        try {
            adminDao.updateAirlineAdmin(admin);
            return Response.Status.OK;
        } catch (SQLException e) {
            System.out.println("Something went wrong updating the admin");
            e.printStackTrace();
        }
        return Response.Status.CONFLICT;
    }

    @Override
    public Response.Status deleteAirlineAdmin(AirlineAdmin admin) {
        try {
            adminDao.deleteAirlineAdmin(admin);
            return Response.Status.OK;
        } catch (SQLException e) {
            System.out.println("Something went wrong while deleting admin");
            System.out.println("Admin was not deleted");
            e.printStackTrace();
        }
        return Response.Status.CONFLICT;
    }
    
    @Override
    public Response.Status createFlight(Flight flight) {
        int newFlightId;
        List<Flight> flights;

        try {
            flights = reservationsDao.getAllFlights();
        } catch (SQLException|ParseException e) {
            System.out.println(e.getMessage());
            return Response.Status.CONFLICT;
        }

        newFlightId = flights.get(flights.size() - 1).getId() + 1;
        Flight newFlight = new Flight(newFlightId);
        newFlight.setAirline(flight.getAirline());
        newFlight.setDate(flight.getDate());
        newFlight.setDestination(flight.getDestination());
        newFlight.setStartingCity(flight.getStartingCity());
        newFlight.setEconomyPrice(flight.getEconomyPrice());
        newFlight.setFirstClassPrice(flight.getFirstClassPrice());
        newFlight.setSeatsInEconomy(flight.getSeatsInEconomy());
        newFlight.setSeatsInFirstClass(flight.getSeatsInFirstClass());

        try {
            reservationsDao.addFlight(newFlight);
        	System.out.println("Created flight on date " + flight.getDate());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Response.Status.CONFLICT;
        }

        return Response.Status.ACCEPTED;
    }
    
    public Response.Status deleteFlight(int flightId) {
    	try {
    		reservationsDao.deleteFlight(flightId);
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    		return Response.Status.CONFLICT;
    	}
    	
    	return Response.Status.ACCEPTED;
    }
    

    public Response.Status deleteReservation(int reservationId) {
    	try {
    		reservationsDao.deleteReservation(reservationId);
    	} catch (SQLException e) {
    		System.out.println(e.getMessage());
    		return Response.Status.CONFLICT;
    	}
    	return Response.Status.ACCEPTED;
    }
    
    public List<Reservation> getAllReservations() {
    	try {
			return reservationsDao.getAllReservations();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    	return null;
    }
}
