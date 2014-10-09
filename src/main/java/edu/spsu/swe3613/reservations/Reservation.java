package edu.spsu.swe3613.reservations;

public class Reservation {
	private final int id;
	private String userId;
	private int flightId;
	private String flightClass;
	
	public Reservation(int id, String userId, int flightId, String flightClass){
		this.id = id;
		this.userId = userId;
		this.flightId = flightId;
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

	public String getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}
}
