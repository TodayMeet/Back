package com.cos.todaymeet.DTO;

import java.sql.Timestamp;

public class NoticeListDto {
	private int noticeNo;
	private String title;
	private String image;
	private Timestamp time;
	public NoticeListDto() {
    }

    public NoticeListDto(int noticeNo,String title, String image, Timestamp time) {
    	this.noticeNo = noticeNo;
        this.title = title;
        this.image = image;
        this.time = time;
    }
    public int getNoticeNo() {
    	return noticeNo;
    }
    public void setNoticeNo(int noticeNo) {
    	this.noticeNo=noticeNo;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
