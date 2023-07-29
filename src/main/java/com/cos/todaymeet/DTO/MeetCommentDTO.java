package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


public class MeetCommentDTO {
	private int meetCommentNo;
	private int meetNo;
	private Integer parentNo;
	private String content;
	private int userNo;
	private String userProfileImage;
	private String username;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Your_Timezone")
	private Timestamp createDate;
	
	
	public MeetCommentDTO(int meetCommentNo,int meetNo,
			Integer parentNo,Timestamp createDate
			, String content, int userNo,String userProfileImage,String username) {
		this.meetCommentNo=meetCommentNo;
		this.meetNo=meetNo;
		this.parentNo=parentNo;
		this.content=content;
		this.userNo=userNo;
		this.createDate=createDate;
		this.userProfileImage=userProfileImage;
		this.username = username;
	}
	public int getMeetCommentNo() {
		return meetCommentNo;
	}

	public void setMeetCommentNo(int meetCommentNo) {
		this.meetCommentNo = meetCommentNo;
	}

	public int getMeetNo() {
		return meetNo;
	}

	public void setMeetNo(int meetNo) {
		this.meetNo = meetNo;
	}

	public Integer getParentNo() {
		return parentNo;
	}

	public void setParentNo(Integer parentNo) {
		this.parentNo = parentNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
}
