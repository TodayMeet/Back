package com.cos.todaymeet.DTO;

public class MapDTO {
	private String address_name;
	private String lat;
	private String lon;
	
	 public MapDTO() {
	    }
	    
	    public MapDTO(String address_name, String lat, String lon) {
	        this.address_name = address_name;
	        this.lat = lat;
	        this.lon = lon;
	    }
	    
	    public String getAddress_name() {
	        return address_name;
	    }
	    
	    public void setAddress_name(String address_name) {
	        this.address_name = address_name;
	    }
	    
	    public String getLat() {
	        return lat;
	    }
	    
	    public void setLat(String lat) {
	        this.lat = lat;
	    }
	    
	    public String getLon() {
	        return lon;
	    }
	    
	    public void setLon(String lon) {
	        this.lon = lon;
	    }
	
}
