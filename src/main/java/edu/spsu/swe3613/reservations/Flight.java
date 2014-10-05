package edu.spsu.swe3613.reservations;

import java.sql.SQLException;

public class Flight {
<<<<<<< HEAD
	private int id;
	private String date;
	private String airline;
	private String startingCity;
	private String destination;
	private float distance;
	private int firstClass;
	private int economy;
	private float price;
	
	
	
	/*Need a constructor so that an administrator level user can add a flight with: 
	 * Date and Time, Airline, StartingCity and Destination.
	 * When saving the new Flight to the DB via addFlight method, the rest of the fields are calculated
	 * and saved to DB. 
	 */
	
	public Flight(){
		
	}
=======
	private String ID;
	private String Date; //This gives us Year, Month, Day, Hour, and Minutes.
	
	private float Price;
	private String Destination;
	private String Starting_City;
	private ReservationsDao DAO;
>>>>>>> Search by price and by date added
	
	public Flight(String date, String airline, String start, String destination)
	{
		this.date = date;
		this.airline = airline;
		this.startingCity = start;
		this.destination = destination;
	}
	
<<<<<<< HEAD
	
	public Flight(int id, String date, String airline, String start, String destination,
				  float distance, int firstClass, int economy, float price) throws SQLException
	{
		this.id = id;
		this.date = date;
		this.airline = airline;
		this.startingCity = start;
		this.destination = destination;
		this.distance = distance;
		this.firstClass = firstClass;
		this.economy = economy;
		this.price = price;
	}

	
	
	
	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}
	
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStartingCity() {
		return startingCity;
	}

	public void setStartingCity(String startingCity) {
		this.startingCity = startingCity;
	}

	public float getPrice(){
		return price;
	}
	
	public void setPrice(float price){
		this.price = price;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public int getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(int firstClass) {
		this.firstClass = firstClass;
	}

	public int getEconomy() {
		return economy;
	}

	public void setEconomy(int economy) {
		this.economy = economy;
	}
	
	@Override
	public boolean equals(Object obj) {
		Flight that;
		
		try {
			that = (Flight) obj;
		} catch (Exception e) {
			return false;
		}

		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (this.id == that.id 
				&& this.getDate().equals(that.getDate())
				&& this.getAirline().equals(that.getAirline())
				&& this.getStartingCity().equals(that.getStartingCity())
				&& this.getDestination().equals(that.getDestination())
				&& this.getDistance() == that.getDistance()
				&& this.getFirstClass() == that.getFirstClass()
				&& this.getEconomy() == that.getEconomy()
				&& this.getPrice() == that.getPrice()) 
		
		{
			return true;
		}
		
		return false;
=======
	public Flight(String id, String date, float price, String destination, String start)
	{
		this.ID = id;
		
		this.Date = date; //Date and time are now saved together.
		
		this.Price = price;
		this.Destination = destination;
		this.Starting_City = start;
	}
	
	public String getID(){
	
		return ID;
	}
	
	public String getDate(){
		
		return Date;
	}
	
	public float getPrice(){
		
		return Price;
	}
	
	public String getDestination(){
		
		return Destination;
	}
	
	public String getStarting_City(){
		
		return Starting_City;
	}
	
	@Inject
	public Flight(ReservationsDao dao){
		this.DAO = dao;
		
>>>>>>> Search by price and by date added
	}


	
	
}
