package edu.spsu.swe3613.reservations;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("reservations/")
public class ReservationsWeb {
	
	private ReservationsService reservationService;
	
	@Inject
	public ReservationsWeb(ReservationsService reservationService) {
		this.reservationService = reservationService;
	}

    @POST
	@Path("search/")
	public List<Flight> search(SearchParams searchParams) {
		return reservationService.search(searchParams);
		
	}

	@POST
    @Path("create/")
	public Response.Status create(CreateReservationParams createReservationParams) {
		return reservationService.createReservation(createReservationParams.getFlightId(),
                                             createReservationParams.getUserId(),
                                             createReservationParams.getSeatClass());
	}

	@DELETE
    @Path("delete/")
	public Response.Status delete(DeleteParams deleteParams) {
		return reservationService.deleteReservation(deleteParams.getReservationId(), deleteParams.getUserId());
	}
	
}
