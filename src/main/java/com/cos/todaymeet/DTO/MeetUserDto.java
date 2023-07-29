package com.cos.todaymeet.DTO;

public class MeetUserDto {
	private int userNo;
	private int meetNo;
	
	public MeetUserDto(int userNo, int meetNo) {
        this.userNo = userNo;
        this.meetNo = meetNo;
    }
    
    public int getUserNo() {
        return userNo;
    }
    
    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }
    
    public int getMeetNo() {
        return meetNo;
    }
    
    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }
}
