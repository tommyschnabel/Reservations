package edu.spsu.swe3613.reservations;

public class Customer {

	private final String id;
	private String fName;
	private String lName;
	private String email;
	private String password;
	
	public Customer(String id, String fName, String lName, String email, String password) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
	}

	public String getId(){
		return id;
	}
	
	public String getFName() {
		return fName;
	}
	
	public void setFName(String fName){
		this.fName = fName;
	}
	
	public String getLName(){
		return lName;
	}

	public void setLName(String lName) {
		this.lName = lName;
	}
	
	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
}
