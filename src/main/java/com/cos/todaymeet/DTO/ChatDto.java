package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

public class ChatDto {
	private String userProfileImage;
	private String username;
	private int userNo;
	private int chatNo;
	private String content;
	private Timestamp time;
	private int meetNo;
	private boolean isUser;
	// 생성자
    public ChatDto() {
    }

    public ChatDto(String userProfileImage, String username, int userNo, int chatNo, String content, Timestamp time, int meetNo) {
        this.userProfileImage = userProfileImage;
        this.username = username;
        this.userNo = userNo;
        this.chatNo = chatNo;
        this.content = content;
        this.time = time;
        this.meetNo = meetNo;
    }

    // Getter 메서드들
    public String getUserProfileImage() {
        return userProfileImage;
    }

    public String getUsername() {
        return username;
    }

    public int getUserNo() {
        return userNo;
    }

    public int getChatNo() {
        return chatNo;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getMeetNo() {
        return meetNo;
    }

    // Setter 메서드들
    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public void setChatNo(int chatNo) {
        this.chatNo = chatNo;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }
}
