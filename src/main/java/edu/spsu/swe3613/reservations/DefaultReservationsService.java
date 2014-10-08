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
	
	@Inject
	public DefaultReservationsService(ReservationsDao resDao){
		this.dao = resDao;
		
		
		
	}
	
	
	public List<Flight> search(SearchParams searchParams) {
	    switch (searchParams.getSearchType()) {
	        case Date:
	            return searchByDate(searchParams);
	            break;
	        case Time:
	            return searchByTime(searchParams);
	            break;
	        case Price:
	            return searchByPrice(searchParams);
	            break;
	    }
	}
	
	
	//Search by Date (Day, Month, Year)
	
		private List<Flight> searchByDate(SearchParams searchParams) {
			
			
			
			//Search_Flights.clear();
			try {
				allFlights = dao.getAllFlights();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			searchParams.setSearchYear(Integer.parseInt(searchParams.getDate().substring(0, 4)));
			searchParams.setSearchMonth(Integer.parseInt(searchParams.getDate().substring(4, 6)));
			searchParams.setSearchDay(Integer.parseInt(searchParams.getDate().substring(6, 8)));
			
			for(int i=0; i < allFlights.size(); i++ )
			{
				
				searchParams.setSearchDate(allFlights.get(i).getDate());
				//DATE = All_Flights.get(i).getDate();
				searchParams.setSearchYear(Integer.parseInt(searchParams.getSearchDate().substring(0, 4)));
				//YEAR = Integer.parseInt(DATE.substring(0, 4));
				searchParams.setSearchMonth(Integer.parseInt(searchParams.getSearchDate().substring(4, 6)));
				//MONTH = Integer.parseInt(DATE.substring(4, 6));
				searchParams.setSearchDay(Integer.parseInt(searchParams.getSearchDate().substring(6, 8)));
				//DAY = Integer.parseInt(DATE.substring(6, 8));
				
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
		
		//Search by Time Hours/Minutes
		
			private List<Flight> searchByTime(SearchParams searchParams) {
				
				
				
				//Search_Flights.clear();
				try {
					allFlights = dao.getAllFlights();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				searchParams.setHour(Integer.parseInt(searchParams.getDate().substring(8, 10)));
				searchParams.setMinute(Integer.parseInt(searchParams.getDate().substring(10, 12)));
				//searchParams.setSearchDay(Integer.parseInt(searchParams.getDate().substring(6, 8)));
				
				for(int i=0; i < allFlights.size(); i++ )
				{
					
					searchParams.setSearchDate(allFlights.get(i).getDate());
					//DATE = All_Flights.get(i).getDate();
					searchParams.setSearchHour(Integer.parseInt(searchParams.getSearchDate().substring(8, 10)));
					//YEAR = Integer.parseInt(DATE.substring(0, 4));
					searchParams.setSearchMinute(Integer.parseInt(searchParams.getSearchDate().substring(10, 12)));
					//MONTH = Integer.parseInt(DATE.substring(4, 6));
					//searchParams.setSearchDay(Integer.parseInt(searchParams.getSearchDate().substring(6, 8)));
					//DAY = Integer.parseInt(DATE.substring(6, 8));
					
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
				// TODO Auto-generated catch block
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
				public int compare(Flight p1, Flight p2) {
					
					return Float.compare(p1.getPrice(), p2.getPrice());
				}
			});
			return searchFlights;
			
		}

}
