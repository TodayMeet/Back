package com.cos.todaymeet.DTO;

import java.sql.Timestamp;
import java.util.List;

import com.cos.todaymeet.model.MeetComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MeetDetailDTO {
	private int meetNo;
	private String categoryName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Your_Timezone")
	private Timestamp time;
	private String title;
	@JsonProperty("age")
	private String age; // 변경해서 넣기 
	private int userNo;
	private String userProfileImage;
	private String username;
	private String address;
	private List<String> meetImage;
	private Integer commentNum;
	private Integer peopleLimit;
	private Integer peopleNum;
	private String lat;
	private String lon;
	private boolean personClosed;
	private Integer fee;
	private String content;
	private boolean approval;
	private boolean timeClosed;
	private UserDTO hostUser;
	private List<MeetCommentDTO> comments;
	private List<UserDTO> userList;
	private boolean isInsert;
	
	public MeetDetailDTO(int meetNo, String categoryName, Timestamp time, String title, String age, int userNo,
			String userProfileImage, String username, String address, List<String>  meetImage, Integer commentNum,
            Integer peopleLimit, Integer peopleNum, String lat,String lon, boolean personClosed, Integer fee, String content,
            boolean approval, boolean timeClosed, UserDTO hostUser, List<MeetCommentDTO> comments, List<UserDTO> userList,boolean isInsert) {
        this.meetNo = meetNo;
        this.categoryName = categoryName;
        this.time = time;
        this.title = title;
        this.age = age;
        this.userNo = userNo;
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.address = address;
        this.meetImage = meetImage;
        this.commentNum = commentNum;
        this.peopleLimit = peopleLimit;
        this.peopleNum = peopleNum;
        this.lat = lat;
        this.lon = lon;
        this.personClosed = personClosed;
        this.fee = fee;
        this.content = content;
        this.approval = approval;
        this.timeClosed = timeClosed;
        this.hostUser = hostUser;
        this.comments = comments;
        this.userList = userList;
        this.isInsert = isInsert;
    }
    // Getters
    public int getMeetNo() {
        return meetNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String isAge() {
        return age;
    }

    public int getUserNo() {
        return userNo;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public List<String>  getMeetImage() {
        return meetImage;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public boolean isPersonClosed() {
        return personClosed;
    }

    public Integer getFee() {
        return fee;
    }
    public boolean getIsInsert() {
        return isInsert;
    }
    public String getContent() {
        return content;
    }

    public boolean isApproval() {
        return approval;
    }

    public boolean isTimeClosed() {
        return timeClosed;
    }

    public UserDTO getHostUser() {
        return hostUser;
    }

    public List<MeetCommentDTO> getComments() {
        return comments;
    }

    public List<UserDTO> getUserList() {
        return userList;
    }

    // Setters
    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMeetImage(List<String>  meetImage) {
        this.meetImage = meetImage;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setPersonClosed(boolean personClosed) {
        this.personClosed = personClosed;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public void setTimeClosed(boolean timeClosed) {
        this.timeClosed = timeClosed;
    }

    public void setHostUser(UserDTO hostUser) {
        this.hostUser = hostUser;
    }

    public void setComments(List<MeetCommentDTO> comments) {
        this.comments = comments;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }
    public void setIsInsert(boolean isInsert) {
        this.isInsert = isInsert;
    }

    }
