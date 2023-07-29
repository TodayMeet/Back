package com.cos.todaymeet.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDTO {
	@JsonProperty("userNo")
	private int userNo;
	@JsonProperty("userProfileImage")
	private String userProfileImage;
	 @JsonProperty("username")
	private String username;
	
	public UserDTO( int userNo, String userProfileImage,String username) {
		this.userNo = userNo;
		this.userProfileImage=userProfileImage;
		this.username =username;
	}
}
