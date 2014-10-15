package edu.spsu.swe3613.reservations;

public class SearchParams {
	
	 private int day;
     private int month;
     private int year;
     private int hour;
     private int minute;
     private String searchDate;
     private int searchDay;
     private int searchMonth;
     private int searchYear;
     private int searchHour;
     private int searchMinute;
     private float price;
     private String date;
     private String destination;
     private SearchType searchType;
     
	
	public enum SearchType {
	    Date, Time, Price, Destination, Test
	}
	
	
	public int getDay(){
		
		return this.day;
	}
	
	public void setDay(int value) {
		
		this.day = value;
	}
	
		
	public int getMonth(){
		
		return this.month;
	}
	
	public void setMonth(int value) {
		
		this.month = value;
	}
	
	public int getYear(){
		
		return this.year;
	}
	
	public void setYear(int value) {
		
		this.year = value;
	}
	
	public int getHour(){
		
		return this.hour;
	}
	
	public void setHour(int value) {
		
		this.hour = value;
	}
	
	public int getMinute(){
		
		return this.minute;
	}
	
	public void setMinute(int value){
		
		this.minute = value;
	}
	
	public String getSearchDate(){
		
		return this.searchDate;
	}
	
	public void setSearchDate(String value) {
		
		this.searchDate = value;
	}
	
	public int getSearchDay(){
		
		return this.searchDay;
	}
	
	public void setSearchDay(int value) {
		
		this.searchDay = value;
	}
	
	public int getSearchMonth(){
		
		return this.searchMonth;
	}
	
	public void setSearchMonth(int value) {
		
		this.searchMonth = value;
	}
	
	public int getSearchYear(){
		
		return this.searchYear;
	}
	
	public void setSearchYear(int value) {
		
		this.searchYear = value;
	}
	
	public int getSearchHour(){
		
		return this.searchHour;
	}
	
	public void setSearchHour(int value){
		
		this.searchHour = value;
	}
	
	public int getSearchMinute(){
		
		return this.searchMinute;
	}
	
	public void setSearchMinute(int value){
		
		this.searchMinute = value;
	}
	
	public float getPrice(){
		
		return this.price;
	}
	
	public void setPrice(float value){
		
		this.price = value;
	}
	
	public String getDate(){
		
		return this.date;
	}
	
	public void setDate(String value) {
		
		this.date = value;
	}
	
	public String getDestination(){
		
		return this.destination;
	}
	
	public void setDestination(String value){
		
		this.destination = value;
	}
	
	
	public SearchType getSearchType(){
		
		return this.searchType;
	}
	
	public void setSearchType(SearchType value){
		
		this.searchType = value;
	}

}
