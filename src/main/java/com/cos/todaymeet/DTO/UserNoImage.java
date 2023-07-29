package com.cos.todaymeet.DTO;

public class UserNoImage {
	private int userNo;
	private String profileImage;
	public UserNoImage() {
        // Default constructor
    }

    public UserNoImage(int userNo, String profileImage) {
        this.userNo = userNo;
        this.profileImage = profileImage;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
