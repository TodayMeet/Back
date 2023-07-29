package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

public class UserProfileAllDTO {
	private String profileImage;
	private String username;
	private int gender;
	private Timestamp birth;
	
	public UserProfileAllDTO(String profileImage, String username, int gender, Timestamp birth) {
		this.profileImage=profileImage;
		this.username=username;
		this.gender=gender;
		this.birth = birth;
	}

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }
}
