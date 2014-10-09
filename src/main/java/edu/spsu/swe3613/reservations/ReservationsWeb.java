package edu.spsu.swe3613.reservations;


import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("reservations/")
public class ReservationsWeb {
	
	private ReservationsService reservationService;
	
	@Inject
	public ReservationsWeb(ReservationsService reservationService) {
		this.reservationService = reservationService;
	}
	
	@GET
	@Path("search/")
	public List<Flight> search(SearchParams searchParams) throws SQLException {
		//Start with one search param then move on!!!
		return service.search(searchParams);
		
	}
	
	@GET
	@POST
	public void create(int id, String userId) throws SQLException {
		service.createReservation(id, userId);
	}
	
	
	@GET
	@POST
	public void register(String id, String fName, String lName, String email, String password) throws SQLException {
		service.registerUser(id, fName, lName, email, password);
	}
	
	@GET
	@DELETE
	public void delete(int id, String userId) throws SQLException {
		service.deleteReservation(id, userId);
	}
	
}
