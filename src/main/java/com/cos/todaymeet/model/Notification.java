package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationNo;
	@ManyToOne
	@JoinColumn(name = "userNo")
	private User user;
	
	@CreationTimestamp
	private Timestamp time;
	//1 : 팔로우, 2 : 참가승인됨 , 3 : 참가거절 , 4 : 댓글달림 
	private Integer notiType;
	private Integer userNumber;
	private Integer meetNumber;
	private String name;//내용
	private boolean confirmed;//확인여부
	private String imageLink; // 출력할 이미지 링크
	private boolean isProcessed; // 승인/거절 처리여부
	
    public Notification(User user, Integer notiType, Integer userNumber, Integer meetNumber, String name, String imageLink) {
        this.user = user;
        this.notiType = notiType;
        this.userNumber = userNumber;
        this.meetNumber = meetNumber;
        this.name = name;
        this.imageLink = imageLink;
    }

	
	public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }
    
    public int getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(int notificationNo) {
        this.notificationNo = notificationNo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getNotiType() {
        return notiType;
    }

    public void setNotiType(Integer notiType) {
        this.notiType = notiType;
    }

    public Integer getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Integer userNumber) {
        this.userNumber = userNumber;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
