package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfileDTO {

	private int userNo;

	private String userProfileImage;

	private String username;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Your_Timezone")
	private Timestamp birth;

	private Integer gender;

	private Integer followNum;

	private Integer followeeNum;

	public UserProfileDTO(int userNo, String userProfileImage, String username, Timestamp birth,
            Integer gender, Integer followNum, Integer followeeNum) {
		this.userNo = userNo;
		this.userProfileImage = userProfileImage;
		this.username = username;
		this.birth = birth;
		this.gender = gender;
		this.followNum = followNum;
		this.followeeNum = followeeNum;
		}
	// 게터(getter)와 세터(setter) 메서드 추가

    public Integer getUserNo() {
        return userNo;
    }

    public void setUserNo(Integer userNo) {
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

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getFolloweeNum() {
        return followeeNum;
    }

    public void setFolloweeNum(Integer followeeNum) {
        this.followeeNum = followeeNum;
    }
}
