package edu.spsu.swe3613.reservations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class SQLiteReservationsDao implements ReservationsDao {
	
private Connection connection;

	@Inject
	public SQLiteReservationsDao(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Reservation> getAllReservations() throws SQLException{
		List<Reservation> reservations = new ArrayList<Reservation>();
		String query = 	"SELECT"
				+		"	Reservation.ID				id,"
				+	 	"	Reservation.CustomerID		customer,"
				+ 		"	Reservation.FlightID		flight,"
				+ 		"	Reservation.FlightClass		class"
				
				+ 		" FROM Reservation";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())
		{
			reservations.add(new Reservation(rs.getInt("id"),
                                             rs.getInt("customer"),
                                             rs.getInt("flight"),
                                             SeatClass.valueOf(rs.getString("class"))));
		}
		statement.close();
		return reservations;
	}

	@Override
	public Reservation getReservationById(int reservationId) throws SQLException{
		String query =  "SELECT"
				+ 		"	CustomerID		customer,"
				+		"	FlightID		flight,"
				+		"	FlightClass		class"
				
				+		"  FROM Reservation"
				+	    " WHERE ID= "+reservationId; 
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query); 
		
		Reservation resultReservation = new Reservation(reservationId,
                                                        rs.getInt("customer"),
                                                        rs.getInt("flight"),
                                                        SeatClass.valueOf(rs.getString("class")));
		statement.close();
		return resultReservation;
	}

	@Override
	public Reservation addReservation(Reservation reservation) throws SQLException{
		String query = 	"INSERT"
				+ 		" INTO Reservation(CustomerID,FlightID,FlightClass) Values("			
				+			reservation.getUserId()			+	"," 
				+			reservation.getFlightId()		+	","
				+		"'"+reservation.getFlightClass()+"'"+	")";
		String query2 = " SELECT * FROM Reservation WHERE "
				+ 		" (CustomerID="+reservation.getUserId()+" AND FlightID="+reservation.getFlightId()
						+ " AND FlightClass='"+reservation.getFlightClass()+"')";		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		ResultSet rs = statement.executeQuery(query2);
		Reservation resultReservation = new Reservation(rs.getInt(1),
                                                        rs.getInt(2),
                                                        rs.getInt(3),
                                                        SeatClass.valueOf(rs.getString(4)));
		
		String seatClass;
		int seats;
		if(reservation.getFlightClass().toString().equals("firstClass"))
			seatClass = "RemainingFirstClass";
		else
			seatClass = "RemainingEconomy";
		
		ResultSet rs2 = statement.executeQuery("SELECT "+seatClass+" FROM Flight "	
								 + " WHERE ID="+reservation.getFlightId());
		seats = rs2.getInt(1);
		seats--;
		statement.executeUpdate("UPDATE Flight SET "+seatClass+"="+seats
							+	" WHERE ID="+reservation.getFlightId());
		
		statement.close();
		return resultReservation;		
	}

	@Override
	public void updateReservation(Reservation reservation) throws SQLException{
		String query = 	"UPDATE "
				+		" Reservation SET"
				+ 			" CustomerID="		+reservation.getUserId()		+	","
				+ 			" FlightID="		+reservation.getFlightId()		+	","
				+ 			" FlightClass="		+"'"+reservation.getFlightClass()+"'"
				+			" WHERE ID="		+reservation.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteReservation(int reservationId) throws SQLException {
		String query = 	"DELETE FROM Reservation WHERE"
				+ 		" ID="	+	reservationId;
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();		
	}
	
	@Override
	public List<Flight> getAllFlights() throws SQLException, ParseException {
		List<Flight> flights = new ArrayList<Flight>();
		String query = 	"SELECT"
				+		"	Flight.ID					id,"
				+ 		"	Flight.Date					date,"
				+		"	Flight.AirlineName			airline,"
				+		"	Flight.StartLocation		start,"
				+ 		"	Flight.Destination			end,"
				+ 		"	Flight.Mileage				distance,"
				+ 		"	Flight.RemainingFirstClass	firstClass,"
				+ 		"	Flight.RemainingEconomy		economy,"
				+ 		"	Flight.EconomyPrice			economyPrice,"
				+ 		"	Flight.FirstClassPrice		firstClassPrice,"
				+ 		"	Flight.Duration				duration"
				+		"  FROM Flight";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		while(rs.next())	//For each row in the DB
		{
			Flight resultFlight = new Flight(rs.getInt("id"),
                    				  rs.getString("date"),
                    				  Airline.valueOf(rs.getString("airline")),
                    				  City.valueOf(rs.getString("start").replace(" ", "")),
                    				  City.valueOf(rs.getString("end").replace(" ", "")),
                    				  rs.getFloat("distance"),
                    				  rs.getInt("firstClass"),
                    				  rs.getInt("economy"),
                    				  rs.getFloat("economyPrice"),
                    				  rs.getFloat("firstClassPrice"));
			resultFlight.setDuration(rs.getString("duration"));
			flights.add(resultFlight);
		}
		statement.close();
		return flights;		
	}
	
	@Override
	public Flight getFlightById(int flightId) throws SQLException, ParseException {
		
		String query =  "SELECT"
				+		"	Flight.ID					id,"
				+ 		"	Flight.Date					date,"
				+		"	Flight.AirlineName			airline,"
				+		"	Flight.StartLocation		start,"
				+ 		"	Flight.Destination			end,"
				+ 		"	Flight.Mileage				distance,"
				+ 		"	Flight.RemainingFirstClass	firstClass,"
				+ 		"	Flight.RemainingEconomy		economy,"
				+ 		"	Flight.EconomyPrice			economyPrice,"
				+ 		"	Flight.FirstClassPrice		firstClassPrice,"
				+ 		"	Flight.Duration				duration"
				+		"  FROM Flight"
				+	    " WHERE ID = "+flightId;
		//String.format(query, flightId); 
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		Flight resultFlight = new Flight(rs.getInt("id"),
										 rs.getString("date"),
										 Airline.valueOf(rs.getString("airline")),
										 City.valueOf(rs.getString("start").replace(" ", "")),
										 City.valueOf(rs.getString("end").replace(" ", "")),
										 rs.getFloat("distance"),
										 rs.getInt("firstClass"),
										 rs.getInt("economy"),
										 rs.getFloat("economyPrice"),
										 rs.getFloat("firstClassPrice"));
		resultFlight.setDuration(rs.getString("duration"));
		statement.close();
		return resultFlight;
	}
	
	/*The addFlight method takes a Flight object as a parameter and:
	 * converts the date to a String, 
	 * extracts the time that is needed to calculate price,
	 * looks up distance and price rate
	 * Adds a record in the DB including id, date as string, price, and distance
	 * without needing id, price, or distance in the Flight object constructor
	*/
	@Override
	public Flight addFlight(Flight flight) throws SQLException {
		String date = flight.getDate();
		String time = date.substring(8);
		float distance =getDistance(flight.getStartingCity().toString(),flight.getDestination().toString());
		flight.setDistance(distance);
		float pricetotal = getPrice(time)*distance;
		flight.setEconomyPrice(pricetotal);
		flight.setFirstClassPrice(pricetotal*1.3f);
		flight.setDuration(getDuration(flight.getStartingCity().toString(),flight.getDestination().toString()));
		
			
		String query = 	"INSERT	INTO Flight "
				+		"(Date, AirlineName, StartLocation, Destination, Mileage, EconomyPrice, FirstClassPrice, Duration)" 
				+		" VALUES("
				+	"'"+flight.getDate()+"'"		+	"," 
				+	"'"+flight.getAirline()+"'"		+	","
				+	"'"+flight.getStartingCity()+"'"+	","
				+	"'"+flight.getDestination()+"'" +	","
				+	    flight.getDistance()    	+	","
				+	    flight.getEconomyPrice()   	+	","
				+		flight.getFirstClassPrice() +	","
				+	"'"+flight.getDuration()+"'"	+	")";
		String query2 = " SELECT * FROM Flight WHERE "
				+ 		" (Date='"+flight.getDate()+"' AND AirlineName='"+flight.getAirline()+"' AND "
						+ "StartLocation='"+flight.getStartingCity()+"' AND Destination='"+flight.getDestination()+"')";
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		ResultSet rs = statement.executeQuery(query2);
		Flight resultFlight = new Flight(rs.getInt(1),
                                         rs.getString(2),
                                         Airline.valueOf(rs.getString(3)),
                                         City.valueOf(rs.getString(4).replace(" ", "")),
				                         City.valueOf(rs.getString(5).replace(" ", "")),
                                         rs.getFloat(6),
                                         rs.getInt(7),
				                         rs.getInt(8),
                                         rs.getFloat(9),
                                         rs.getFloat(10));
		resultFlight.setDuration(rs.getString(11));
		statement.close();
		return resultFlight;
	}

	@Override
	public void updateFlight(Flight flight) throws SQLException {
		String query = 	"UPDATE Flight"
				+		" SET"
						+" Date="			+	"'"+flight.getDate()+"'"		+	","
						+" AirlineName="	+	"'"+flight.getAirline()+"'"		+	","
						+" StartLocation=" 	+	"'"+flight.getStartingCity()+"'"+	","
						+" Destination="	+	"'"+flight.getDestination()+"'"	+	","
						+" Mileage="		+	   	flight.getDistance()		+	","
						+" EconomyPrice="	+		flight.getEconomyPrice()	+	","
						+" FirstClassPrice="+		flight.getFirstClassPrice()	+	","
						+" Duration="		+	"'"+flight.getDuration()+"'"
				+	" WHERE ID="	+ 	flight.getId();
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}

	@Override
	public void deleteFlight(int flightId) throws SQLException{
		String query = 	"DELETE FROM Flight WHERE"
				+ 		" ID="	+	flightId;
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		statement.close();
	}


//	@Override
//	public Airline getAirline(String airlineName) throws SQLException {
//		String query = "SELECT"
//				+ " Airline.Name			name,"
//				+ " Airline.Information		info"
//				+ " FROM Airline "
//				+ " WHERE Name = "+"'"+airlineName+"'";
//		Statement statement = connection.createStatement();
//		ResultSet rs = statement.executeQuery(query);
//
//		Airline resultAirline = new Airline(rs.getString("name"), rs.getString("info"));
//		statement.close();
//		return resultAirline;
//	}

	@Override
	public float getPrice(String time) throws SQLException {
		String query = "SELECT"
				+ " PriceRate 	price "
				+ "	FROM Price "
				+ "	WHERE Time="+"'"+time+"'";
		
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		float resultPriceRate = rs.getFloat("price");
		statement.close();
		return resultPriceRate;
	}

	@Override
	public float getDistance(String start, String destination) throws SQLException {
		String query = "SELECT "
				+ 	"Distance 		distance "
				+ 	"FROM Mileage "
				+ 	"WHERE "
				+ 		"LocationA="+"'"+start+"'"+" and LocationB="+"'"+destination+"'"
				+ 		" or LocationB="+"'"+start+"'"+" and LocationA="+"'"+destination+"'";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		float resultDistance = rs.getFloat("distance");
		statement.close();
		return resultDistance;
	}

	@Override
	public String getDuration(String start, String destination) throws SQLException {
		String query = "SELECT "
				+ 	"Duration 		duration "	
				+ 	"FROM Mileage "
				+ 	"WHERE "
				+ 		"LocationA="+"'"+start+"'"+" and LocationB="+"'"+destination+"'"
				+ 		" or LocationB="+"'"+start+"'"+" and LocationA="+"'"+destination+"'";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		String resultDuration = rs.getString("duration");
		statement.close();
		return resultDuration;
	
	}
	

}

