package edu.spsu.swe3613.reservations;

public class Reservation {
	private final int id;
	private String userId;
	private int flightId;
	
	public Reservation(int id, String userId, int flightId){
		this.id = id;
		this.userId = userId;
		this.flightId = flightId;
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
}
