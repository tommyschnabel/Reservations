package edu.spsu.swe3613.reservations;

public class CreateReservationParams {

    private Integer flightId;
    private Integer userId;
    private Reservation.SeatClass seatClass;

    public CreateReservationParams(Integer flightId, Integer userId, Reservation.SeatClass seatClass) {
        this.flightId = flightId;
        this.userId = userId;
        this.seatClass = seatClass;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Reservation.SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(Reservation.SeatClass seatClass) {
        this.seatClass = seatClass;
    }
}
