package edu.spsu.swe3613.admin;

public class AirlineAdmin {
	private String id;
	private String airline;
	private String password;
	
	public AirlineAdmin(String id, String airline, String password){
		this.id = id;
		this.airline = airline;
		this.password = password;
	}
	public AirlineAdmin(){
		
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
	
	@Override
	public boolean equals(Object obj) {
		AirlineAdmin that;
		
		try {
			that = (AirlineAdmin) obj;
		} catch (Exception e) {
			return false;
		}

		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.id.equals(that.id) 
				&& this.airline.equals(that.airline)
				&& this.password.equals(that.password))
		{
			return true;
		}
		
		return false;
	}
}
