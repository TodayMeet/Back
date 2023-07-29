package com.cos.todaymeet.DTO;

public class ReqReportsDto {
	private int meetcommentNo;
	private Integer type;
	private int userNo;
	
	 public ReqReportsDto(int meetcommentNo, Integer type, int userNo) {
	        this.meetcommentNo = meetcommentNo;
	        this.type = type;
	        this.userNo = userNo;
	    }

	    public int getMeetcommentNo() {
	        return meetcommentNo;
	    }

	    public void setMeetcommentNo(int meetcommentNo) {
	        this.meetcommentNo = meetcommentNo;
	    }

	    public Integer getType() {
	        return type;
	    }

	    public void setType(Integer type) {
	        this.type = type;
	    }

	    public int getUserNo() {
	        return userNo;
	    }

	    public void setUserNo(int userNo) {
	        this.userNo = userNo;
	    }
}
