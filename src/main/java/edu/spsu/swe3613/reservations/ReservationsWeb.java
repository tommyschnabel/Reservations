package edu.spsu.swe3613.reservations;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.inject.Inject;
import com.sun.jersey.api.core.InjectParam;


@Path("reservations/")
public class ReservationsWeb {
	
	private ReservationsService reservationService;
	
	@Inject
	public ReservationsWeb(@InjectParam DefaultReservationsService reservationService) {
		this.reservationService = reservationService;
	}
	
	@GET
	@Path("search/")
	public List<Flight> search() {
		//Start with one search param then move on!!!
		return new ArrayList<Flight>();
	}
	
}
