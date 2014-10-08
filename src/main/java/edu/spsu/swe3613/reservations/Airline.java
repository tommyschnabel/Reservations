package edu.spsu.swe3613.reservations;

public class Airline {
	private final String name;
	private String info;
	
	public Airline(String name, String info){
		this.name = name;
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}