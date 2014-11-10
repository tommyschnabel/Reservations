package edu.spsu.swe3613.user;

public class User {

	private Integer id;
	private String fName;
	private String lName;
	private String email;
	private String password;
	private boolean isAdmin; 
	
	public User(Integer id, String fName, String lName, String email, String password) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
		this.isAdmin = false;
	}
	
	public User() {
		
	}

	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public boolean equals(Object obj) {
		User that;
		
		try {
			that = (User) obj;
		} catch (Exception e) {
			return false;
		}

		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.id == that.id 
				&& this.getEmail() == that.getEmail()
				&& this.getPassword() == that.getPassword()
				&& this.getFName() == that.getFName()
				&& this.getLName() == that.getLName()
				&& this.isAdmin() == that.isAdmin()) {
			return true;
		}
		
		return false;
	}
}
