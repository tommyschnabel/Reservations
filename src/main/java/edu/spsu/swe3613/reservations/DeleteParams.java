package edu.spsu.swe3613.reservations;

public class DeleteParams {
    private Integer reservationId;
    private Integer userId;

    public DeleteParams(Integer reservationId, Integer userId) {
        this.reservationId = reservationId;
        this.userId = userId;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
