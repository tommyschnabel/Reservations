package edu.spsu.swe3613.examples;



import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.inject.Inject;

import edu.spsu.swe3613.reservations.Flight;
import edu.spsu.swe3613.reservations.ReservationsDao;

public class Search {
	
	private ReservationsDao DAO;
	private List<Flight> All_Flights;
	private List<Flight> Search_Flights;
	private int DAY;
	private int MONTH;
	private int YEAR;
	private int SEARCH_DAY;
	private int SEARCH_MONTH;
	private int SEARCH_YEAR;
	private String DATE;
	//private GregorianCalendar test;
	//private Flight temp_Flight;
	
	@Inject
	public Search(ReservationsDao dao){
		this.DAO = dao;
		
		
		
	}
	//Search by Price then Sorts them ascending order.
	public List<Flight> Search_By_Price(float price){
		
		Search_Flights.clear();
		try {
			All_Flights = DAO.getAllFlights();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i=0; i < All_Flights.size(); i++ )
		{
			
			
			if(All_Flights.get(i).getPrice() <= price)
			{
				Search_Flights.add(All_Flights.get(i));
			}
			
			
		}
		
		Collections.sort(Search_Flights,  new Comparator<Flight>() {
			public int compare(Flight p1, Flight p2) {
				//return (int) ((int) p1.getPrice()-p2.getPrice());
				return Float.compare(p1.getPrice(), p2.getPrice());
			}
		});
		return Search_Flights;
		
	}
	
	
	//Search by Date (Day, Month, Year)
	
	public List<Flight> Search_By_Date(String date) {
		
		Search_Flights.clear();
		try {
			All_Flights = DAO.getAllFlights();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SEARCH_YEAR = Integer.parseInt(date.substring(0, 4));
		SEARCH_MONTH = Integer.parseInt(date.substring(4, 6));
		SEARCH_DAY = Integer.parseInt(date.substring(6, 8));
		
		for(int i=0; i < All_Flights.size(); i++ )
		{
			DATE = All_Flights.get(i).getDate();
			YEAR = Integer.parseInt(DATE.substring(0, 4));
			MONTH = Integer.parseInt(DATE.substring(4, 6));
			DAY = Integer.parseInt(DATE.substring(6, 8));
			
			if(SEARCH_YEAR == YEAR){
				if(SEARCH_MONTH == MONTH){
					if(SEARCH_DAY == DAY){
						Search_Flights.add(All_Flights.get(i));
					}
				}
			}
			
			
			
		}
		
		
		return Search_Flights;
	}
	

}
