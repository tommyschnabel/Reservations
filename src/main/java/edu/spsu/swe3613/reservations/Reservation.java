package edu.spsu.swe3613.reservations;

public class Reservation {
	private int id;
	private Integer userId;
	private int flightId;
	private String flightClass;
	
	public Reservation(int id, Integer userId, int flightId, String flightClass){
		this.id = id;
		this.userId = userId;
		this.flightId = flightId;
		this.flightClass = flightClass;
	}

	public Reservation(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public Integer getUserId(){
		return userId;
	}
	
	public void setUserId(Integer userId){
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

	@Override
	public boolean equals(Object obj) {
		Reservation that;
		
		try {
			that = (Reservation) obj;
		} catch (Exception e) {
			return false;
		}

		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.id == that.id 
				&& this.getFlightId()==that.getFlightId()
				&& this.getUserId()==that.getUserId()
				&& this.getFlightClass().equals(that.getFlightClass()))
		{
			return true;
		}
		
		return false;
	}
}
