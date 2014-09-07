package main.java.examples;

public interface ExampleObject {
	//Interfaces are good when doing dependency injection because they allow you to expect certain methods out of 
	//different classes. See ExampleA.java and ExampleB.java as well as ExampleModule.java
	public Integer getInt();
	public Boolean getBool();
	public Float getFloat();
}
