package edu.spsu.swe3613.reservations;

public class AirlineAdmin {
	private final String id;
	private String airline;
	private String password;
	
	public AirlineAdmin(String id, String airline, String password){
		this.id = id;
		this.airline = airline;
		this.password = password;
	}
	
	public String getId(){
		return id;
	}
	
	public String getAirline(){
		return airline;
	}
	
	public void setAirline(String airline){
		this.airline = airline;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
}
