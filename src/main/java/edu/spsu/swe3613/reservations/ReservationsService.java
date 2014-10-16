package edu.spsu.swe3613.reservations;

import javax.ws.rs.core.Response;
import java.util.List;

public interface ReservationsService {
    public List<Flight> search(SimpleSearchParams searchParams);
    public Response.Status createReservation(int flightId, int userId, Reservation.SeatClass seatClass);
    public Response.Status deleteReservation(int id, int userId);
}
