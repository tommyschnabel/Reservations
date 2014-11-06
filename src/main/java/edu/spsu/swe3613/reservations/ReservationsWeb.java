package edu.spsu.swe3613.reservations;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
	public List<Flight> search(SimpleSearchParams searchParams) {
		return reservationService.search(searchParams);
	}

	@PUT
    @Path("create/")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response.Status create(CreateReservationParams createReservationParams) {
		return reservationService.createReservation(createReservationParams.getFlightId(),
                                             createReservationParams.getUserId(),
                                             createReservationParams.getSeatClass());
	}

	@DELETE
    @Path("delete/")
	public Response.Status delete(@QueryParam("uid") int userId, @QueryParam("resId") int reservationId) {
		return reservationService.deleteReservation(reservationId, userId);
	}

    @GET
    public List<Reservation> getReservationsForUser(@QueryParam("uid") int userId) {
        return reservationService.getReservationsForUser(userId);
    }

    @GET
    @Path("flight/")
    public Flight getFlightById(@QueryParam("flightId") int flightId) {
        return reservationService.getFlightById(flightId);
    }

    @POST
    @Path("flight/create/")
    public Response.Status createFlight(Flight flight) {
        return reservationService.createFlight(flight);
    }
	
}
