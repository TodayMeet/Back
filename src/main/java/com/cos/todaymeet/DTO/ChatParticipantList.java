package com.cos.todaymeet.DTO;

public class ChatParticipantList {
	private String username ;
	private String profileImage;
	private int userNo;
	private boolean follow;
	 public ChatParticipantList(String username, String profileImage, int userNo, boolean follow) {
	        this.username = username;
	        this.profileImage = profileImage;
	        this.userNo = userNo;
	        this.follow = follow;
	    }
	    
	    public String getUsername() {
	        return username;
	    }
	    
	    public void setUsername(String username) {
	        this.username = username;
	    }
	    
	    public String getProfileImage() {
	        return profileImage;
	    }
	    
	    public void setProfileImage(String profileImage) {
	        this.profileImage = profileImage;
	    }
	    
	    public int getUserNo() {
	        return userNo;
	    }
	    
	    public void setUserNo(int userNo) {
	        this.userNo = userNo;
	    }
	    
	    public boolean isFollow() {
	        return follow;
	    }
	    
	    public void setFollow(boolean follow) {
	        this.follow = follow;
	    }
}
