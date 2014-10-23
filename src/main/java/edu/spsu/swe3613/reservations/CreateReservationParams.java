package edu.spsu.swe3613.reservations;

public class CreateReservationParams {

    private Integer flightId;
    private Integer userId;
    private SeatClass seatClass;

    public CreateReservationParams(Integer flightId, Integer userId, SeatClass seatClass) {
        this.flightId = flightId;
        this.userId = userId;
        this.seatClass = seatClass;
    }

    public CreateReservationParams() {}

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

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }
}
