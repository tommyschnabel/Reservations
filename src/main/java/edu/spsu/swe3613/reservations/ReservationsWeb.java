package edu.spsu.swe3613.reservations;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.inject.Inject;

@Path("reservations/")
public class ReservationsWeb {
	
	private ReservationsService reservationService;
	
	@Inject
	public ReservationsWeb(DefaultReservationsService reservationService) {
		this.reservationService = reservationService;
	}
	
	@GET
	@Path("search/")
	public List<Flight> search() {
		//Start with one search param then move on!!!
		return new ArrayList<Flight>();
	}
	
}
