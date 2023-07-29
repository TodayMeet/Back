package com.cos.todaymeet.DTO;

import java.sql.Timestamp;
import java.util.List;

public class MeetImageListDTO {
	private String categoryName;
	private Timestamp time;
	private String title;
	private int meetNo;
	private int userNo;
	private String userProfileImage;
	private String username;
	private String lat;
	private String lon;
	private List<String> meetImage;
	private Integer commentNum;
	private Integer peopleLimit;
	private Integer peopleNum;
	private String address;
	private boolean personClosed;
	private List<UserNoImage> participantUserImage;
	
	public MeetImageListDTO(Object categoryName, Object title, Object time, Object meetNo, Object userNo, String userProfileImage,
			Object username, Object commentNum, Object peopleLimit,
			Object personClosed, Object peopleNum, Object lon,Object lat, Object address,List<String> meetImage,List<UserNoImage> participantUserImage) {
	    this.categoryName = (String) categoryName;
	    this.title = (String) title;
	    this.time = (Timestamp) time;
	    this.meetNo = (int) meetNo;
	    this.userNo = (int) userNo;
	    this.userProfileImage = (String) userProfileImage;
	    this.username = (String) username;
	    this.commentNum = (Integer) commentNum;
	    this.peopleLimit = (Integer) peopleLimit;
	    this.personClosed = (Boolean) personClosed;
	    this.peopleNum = (Integer) peopleNum;
	    this.address = (String) address;
	    this.lat = (String) lat;
	    this.lon=(String)lon;
	    this.meetImage = meetImage;
	    this.participantUserImage=participantUserImage;
	}
	public List<UserNoImage> getParticipantUserImage() {
	    return participantUserImage;
	}

	public void setParticipantUserImage(List<UserNoImage> participantUserImage) {
	    this.participantUserImage = participantUserImage;
	}
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMeetNo() {
		return meetNo;
	}

	public void setMeetNo(int meetNo) {
		this.meetNo = meetNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserProfileImage() {
		return userProfileImage;
	}

	public void setUserProfileImage(String userProfileImage) {
		this.userProfileImage = userProfileImage;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getMeetImage() {
		return meetImage;
	}

	public void setMeetImage(List<String> meetImage) {
		this.meetImage = meetImage;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getPeopleLimit() {
		return peopleLimit;
	}

	public void setPeopleLimit(Integer peopleLimit) {
		this.peopleLimit = peopleLimit;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
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


	public boolean isPeopleClosed() {
		return personClosed;
	}

	public void setPeopleClosed(boolean peopleClosed) {
		this.personClosed = peopleClosed;
	}
}
