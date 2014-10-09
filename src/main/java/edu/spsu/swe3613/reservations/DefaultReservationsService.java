package edu.spsu.swe3613.reservations;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.inject.Inject;

public class DefaultReservationsService implements ReservationsService {
	
	private ReservationsDao dao;
	private List<Flight> allFlights;
	private List<Flight> searchFlights;
	private List<Reservation> allReservations;
	
	@Inject
	public DefaultReservationsService(ReservationsDao resDao){
		this.dao = resDao;
		}
	
	//Error Code does not want breaks "This method must return a result of type List<Flight>. Note that a problem regarding missing 'default:' on 'switch' has been suppressed, which is perhaps related to this problem"
	public List<Flight> search(SearchParams searchParams) throws SQLException {
	    switch (searchParams.getSearchType()) {
	        case Date:
	            return searchByDate(searchParams);
	            //break;
	        case Time:
	            return searchByTime(searchParams);
	           // break;
	        case Price:
	            return searchByPrice(searchParams);
	            //break;
	        case Destination:
	        	return searchByDestination(searchParams);
	         default:
	        	return dao.getAllFlights();
	        
	    }
	}
	
	//Search by Date (Day, Month, Year)
	
		private List<Flight> searchByDate(SearchParams searchParams) {
			
			try {
				allFlights = dao.getAllFlights();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			searchParams.setSearchYear(Integer.parseInt(searchParams.getDate().substring(0, 4)));
			searchParams.setSearchMonth(Integer.parseInt(searchParams.getDate().substring(4, 6)));
			searchParams.setSearchDay(Integer.parseInt(searchParams.getDate().substring(6, 8)));
			
			for(int i=0; i < allFlights.size(); i++ )
			{
				searchParams.setSearchDate(allFlights.get(i).getDate());
				searchParams.setSearchYear(Integer.parseInt(searchParams.getSearchDate().substring(0, 4)));
				searchParams.setSearchMonth(Integer.parseInt(searchParams.getSearchDate().substring(4, 6)));
				searchParams.setSearchDay(Integer.parseInt(searchParams.getSearchDate().substring(6, 8)));
				
				if(searchParams.getSearchYear() == searchParams.getYear()){
					if(searchParams.getSearchMonth() == searchParams.getMonth()){
						if(searchParams.getSearchDay() == searchParams.getDay()){
							searchFlights.add(allFlights.get(i));
						}
					}
				}
			}
			return searchFlights;
		}
		private List<Flight> searchByTime(SearchParams searchParams) {
			
			try {
					allFlights = dao.getAllFlights();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				searchParams.setHour(Integer.parseInt(searchParams.getDate().substring(8, 10)));
				searchParams.setMinute(Integer.parseInt(searchParams.getDate().substring(10, 12)));
								
				for(int i=0; i < allFlights.size(); i++ )
				{
					searchParams.setSearchDate(allFlights.get(i).getDate());
					searchParams.setSearchHour(Integer.parseInt(searchParams.getSearchDate().substring(8, 10)));
					searchParams.setSearchMinute(Integer.parseInt(searchParams.getSearchDate().substring(10, 12)));
					
					if(searchParams.getSearchHour() == searchParams.getHour()){
						if(searchParams.getSearchMinute() == searchParams.getMinute()){
							
								searchFlights.add(allFlights.get(i));
							
						}
					}
				}
				return searchFlights;
			}
	
	//Search by Price
	private List<Flight> searchByPrice(SearchParams searchParams){
			
			searchFlights.clear();
			try {
				allFlights = dao.getAllFlights();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			for(int i=0; i < allFlights.size(); i++ )
			{
				if(allFlights.get(i).getPrice() <= searchParams.getPrice())
				{
					searchFlights.add(allFlights.get(i));
				}
			}
			
			Collections.sort(searchFlights,  new Comparator<Flight>() {
				public int compare(Flight p1, Flight p2) 
				{
					return Float.compare(p1.getPrice(), p2.getPrice());
				}
			});
			return searchFlights;
			
		}
	
	// search by destination
	private List<Flight> searchByDestination(SearchParams searchParams){
		searchFlights.clear();
		try {
			allFlights = dao.getAllFlights();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i < allFlights.size(); i++ )
		{
			if(allFlights.get(i).getDestination() == searchParams.getDestination())
			{
				searchFlights.add(allFlights.get(i));
			}
		}
		
		return searchFlights;
		}
	
	//Create a new flight and add it to the list of flights.
	public void createReservation(int id, String userId) throws SQLException{
		
		int last = dao.getAllReservations().size();
		Reservation newReservation = new Reservation( dao.getAllReservations().get(last).getId()+1, userId, id);
		dao.addReservation(newReservation);
		
	}
	
	public void registerUser(String id, String fName, String lName, String email, String password) throws SQLException{
		Customer newCustomer = new Customer(id, fName, lName, email, password);
		dao.addCustomer(newCustomer);
	}
	
	
	public void deleteReservation(int id, String userId) throws SQLException{
		
		try {
			allReservations = dao.getAllReservations();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(int i=0; i < allReservations.size(); i++ )
		{
			if(allReservations.get(i).getUserId() == userId)
			{
				if(allReservations.get(i).getFlightId() == id)
				{
					 dao.deleteReservation(dao.getAllReservations().get(i));
				}
			}
		}
		
	}
	
	
	
	}


