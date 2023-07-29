package com.cos.todaymeet.DTO;

import java.sql.Timestamp;
import java.util.List;

public class NoticeDetailDto {
	private int noticeNo;
	private String title;
	private String content;
	private Timestamp time;
	private List<String> images;
	public NoticeDetailDto() {
    }

    public NoticeDetailDto(int noticeNo, String title, String content, Timestamp time, List<String> images) {
        this.noticeNo = noticeNo;
        this.title = title;
        this.content = content;
        this.time = time;
        this.images = images;
    }

    public int getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(int noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
