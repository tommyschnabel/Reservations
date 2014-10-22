package edu.spsu.swe3613.reservations;

public class Reservation {

	private int id;
	private int userId;
	private int flightId;
	private SeatClass flightClass;
	
	public Reservation(int id, int userId, int flightId, SeatClass flightClass){
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
	
	public int getUserId(){
		return userId;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public int getFlightId(){
		return flightId;
	}
	
	public void setFlightId(int flightId){
		this.flightId = flightId;
	}

	public SeatClass getFlightClass() {
		return flightClass;
	}

	public void setFlightClass(SeatClass flightClass) {
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
