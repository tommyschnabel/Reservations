package edu.spsu.swe3613.reservations;

public class Flight {
	private int id; //changed from String to int CHECK HERE FOR ERRORS 10.9.2014
	private String date; //This gives us Year, Month, Day, Hour, and Minutes.
	
	private float price;
    private float distance;
	private City destination;
	private City startingCity;
    private int seatsInFirstClass;
    private int seatsInEconomy;
    private Airline airline;

	public Flight(int id,
                  String date,
                  Airline airline,
                  City start,
                  City end,
                  Float distance,
                  int seatsInFirstClass,
                  int seatsInEconomy,
                  float price) {
		this.id = id;
		this.date = date; //Date and time are now saved together.
		this.price = price;
		this.destination = end;
		this.startingCity = start;
        this.distance = distance;
        this.seatsInEconomy = seatsInEconomy;
        this.seatsInFirstClass = seatsInFirstClass;
        this.airline = airline;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public City getStartingCity() {
        return startingCity;
    }

    public void setStartingCity(City startingCity) {
        this.startingCity = startingCity;
    }

    public int getSeatsInFirstClass() {
        return seatsInFirstClass;
    }

    public void setSeatsInFirstClass(int seatsInFirstClass) {
        this.seatsInFirstClass = seatsInFirstClass;
    }

    public int getSeatsInEconomy() {
        return seatsInEconomy;
    }

    public void setSeatsInEconomy(int seatsInEconomy) {
        this.seatsInEconomy = seatsInEconomy;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
}
