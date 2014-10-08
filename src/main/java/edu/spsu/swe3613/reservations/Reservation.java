package edu.spsu.swe3613.reservations;

public class Reservation {
	private final int id;
	private String userId;
	private int flightId;
	private int seatQuantity;
	private String flightClass;
	
	public Reservation(int id, String userId, int flightId, int seatQuantity, String flightClass){
		this.id = id;
		this.userId = userId;
		this.flightId = flightId;
		this.seatQuantity = seatQuantity;
		this.flightClass = flightClass;
	}

	public int getId(){
		return id;
	}
	
	public String getUserId(){
		return userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public int getFlightId(){
		return flightId;
	}
	
	public void setFlightId(int flightId){
		this.flightId = flightId;
	}

	public int getSeatQuantity() {
		return seatQuantity;
	}

	public void setSeatQuantity(int seatQuantity) {
		this.seatQuantity = seatQuantity;
	}

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}
}
