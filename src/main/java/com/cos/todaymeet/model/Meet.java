package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Meet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetNo;
	
	@ManyToOne
	@JoinColumn(name = "categoryNo")
	private Category category;
	
	private String address;
	private String lat;
	private String lon;
	private Timestamp time;
	private boolean age;
	private Integer peopleLimit;
	private Integer fee;
	private String title;
	private String content;
	private boolean approval;
	private Integer peopleNum;
	private boolean timeClosed;
	private boolean personClosed;
	private Integer commentNum;
	private String meetImage;
	// image 
	@OneToMany(
	    	   mappedBy = "meet",
	    	   cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
	    	   orphanRemoval = true
	    )
	private List<MeetPhoto> photo = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "userNo")
	private User user;
	
	@CreationTimestamp
	private Timestamp createTime;

	// 생성자 수정
	public Meet(Category category, String address, String lat,String lon, Timestamp time, boolean age, Integer peopleLimit,
			Integer fee, String title, String content, boolean approval, Integer peopleNum, boolean timeClosed,
			boolean personClosed, Integer commentNum, User user, Timestamp createTime,String meetImage) {
		this.category = category;
		this.address = address;
		this.lat = lat;
		this.lon = lon;
		this.time = time;
		this.age = age;
		this.peopleLimit = peopleLimit;
		this.fee = fee;
		this.title = title;
		this.content = content;
		this.approval = approval;
		this.peopleNum = peopleNum;
		this.timeClosed = timeClosed;
		this.personClosed = personClosed;
		this.commentNum = commentNum;
		this.user = user;
		this.createTime = createTime;
		this.meetImage=meetImage;
		
	}
	// Update method
    public void update(Meet updatedMeet) {
        this.category = updatedMeet.getCategory();
        this.address = updatedMeet.getAddress();
        this.lat = updatedMeet.getLat();
        this.lon = updatedMeet.getLon();
        this.time = updatedMeet.getTime();
        this.age = updatedMeet.isAge();
        this.peopleLimit = updatedMeet.getPeopleLimit();
        this.fee = updatedMeet.getFee();
        this.title = updatedMeet.getTitle();
        this.content = updatedMeet.getContent();
        this.approval = updatedMeet.isApproval();
        this.peopleNum = updatedMeet.getPeopleNum();
        this.timeClosed = updatedMeet.isTimeClosed();
        this.personClosed = updatedMeet.isPersonClosed();
        this.commentNum = updatedMeet.getCommentNum();
        this.meetImage=updatedMeet.getMeetImage();
    }
	 // Board에서 파일 처리 위함
    public void addPhoto(MeetPhoto photo) {
        this.photo.add(photo);

	// 게시글에 파일이 저장되어있지 않은 경우
    if(photo.getMeet() != this)
            // 파일 저장
            photo.setMeet(this);
    }
    @Override
    public int hashCode() {
        return Objects.hash(meetNo, category, address, lat, lon, time, age, peopleLimit, fee, title, content, approval,
                peopleNum, timeClosed, personClosed, commentNum, meetImage, photo, user, createTime);
    }	
    // Getters and Setters
    public int getMeetNo() {
        return meetNo;
    }

    public void setMeetNo(int meetNo) {
        this.meetNo = meetNo;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isAge() {
        return age;
    }

    public void setAge(boolean age) {
        this.age = age;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
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

    public boolean isApproval() {
        return approval;
    }

    public void setApproval(boolean approval) {
        this.approval = approval;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public boolean isTimeClosed() {
        return timeClosed;
    }

    public void setTimeClosed(boolean timeClosed) {
        this.timeClosed = timeClosed;
    }

    public boolean isPersonClosed() {
        return personClosed;
    }

    public void setPersonClosed(boolean personClosed) {
        this.personClosed = personClosed;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }
    @Override
    public String toString() {
        return "Meet{" +
                "meetNo=" + meetNo +
                ", category=" + category +
                ", address='" + address + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", time=" + time +
                ", age=" + age +
                ", peopleLimit=" + peopleLimit +
                ", fee=" + fee +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", approval=" + approval +
                ", peopleNum=" + peopleNum +
                ", timeClosed=" + timeClosed +
                ", personClosed=" + personClosed +
                ", commentNum=" + commentNum +
                ", meetImage='" + meetImage + '\'' +
                ", photo=" +  photo.size() +
                ", user=" + user +
                ", createTime=" + createTime +
                '}';
    }
}

	