package edu.spsu.swe3613.examples;

public class User {

	private final Integer id;
	private String name;
	
	public User(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}
}
