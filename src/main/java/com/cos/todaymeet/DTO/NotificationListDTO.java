package com.cos.todaymeet.DTO;

public class NotificationListDTO {

	private String time;
	private Integer userNumber;
	private Integer meetNumber;
	private String name;
	private String imageLink;
	private Integer notiType;
	private boolean isProcessed;
	public NotificationListDTO() {
    }

    public NotificationListDTO(String time, Integer userNumber,Integer meetNo, String name, String imageLink,Integer notiType,boolean isProcessed) {
        this.time = time;
        this.userNumber = userNumber;
        this.meetNumber = meetNo;
        this.name = name;
        this.imageLink = imageLink;
        this.notiType = notiType;
        this.isProcessed=isProcessed;
    }
    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNo) {
        this.userNumber = userNo;
    }

    public Integer getMeetNumber() {
        return meetNumber;
    }

    public void setMeetNumber(Integer meetNumber) {
        this.meetNumber = meetNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getNotiType() {
        return notiType;
    }

    public void setNotiType(Integer notiType) {
        this.notiType = notiType;
    }

}
