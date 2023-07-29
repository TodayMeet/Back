package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

public class MapPinDto {
	private int meetNo;
	private String categoryname;
	private String lat;
	private String lon;
	private Timestamp time;
	// 생성자
    public MapPinDto() {
    }

    public MapPinDto(int meetNo, String categoryname, String lat, String lon, Timestamp time) {
        this.meetNo = meetNo;
        this.categoryname = categoryname;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    // Getter 메서드
    public int getMeetNo() {
        return meetNo;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public Timestamp getTime() {
        return time;
    }

    // Setter 메서드
    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
