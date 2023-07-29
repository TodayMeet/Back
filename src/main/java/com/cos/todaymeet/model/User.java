package com.cos.todaymeet.model;

import java.sql.Timestamp;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userNo;
	private String username;
	private String password;
	private String email;
	private String phoneNumber;
	private Integer gender;
	private Timestamp birth;
	private String profileImage= "http://todaymeet.shop:8080/imagetest/1";
	@OneToOne(
	    	   mappedBy = "user",
	    	   cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
	    	   orphanRemoval = true
	    )
	private Photo photo;
	private Integer followNum=0;
	private Integer followeeNum=0;
	private boolean isDeleted=false;
	private Timestamp deletedTime;
	private String role; //ROLE_USER, ROLE_ADMIN
	// OAuth를 위해 구성한 추가 필드 2개
	private String provider;
	private String providerId;
	@CreationTimestamp
	private Timestamp createDate;
	private String token;
	

	@Builder
	public User(String username, Integer gender,Timestamp birth) {
		this.username=username;
		this.gender = gender;
		this.birth=birth;
	}
	public void update(String username, Integer gender,Timestamp birth) {
		this.username=username;
		this.gender = gender;
		this.birth=birth;
	}
	// Board에서 파일 처리 위함
    public void addPhoto(Photo photo) {
        this.photo=photo;

	// 게시글에 파일이 저장되어있지 않은 경우
        if(photo.getUser() != this)
            // 파일 저장
            photo.setUser(this);
    }
	public User(String username, String password, String email, String role, String provider, String providerId,
			Timestamp createDate,String phoneNumber,Integer gender, Timestamp birth,String profileImage, Integer followNum,
			int followeeNum, boolean isDeleted, Timestamp deletedTime)
	{
		this.username=username;
		this.password = password;
		this.email=email;
		this.phoneNumber=phoneNumber;
		this.gender = gender;
		this.birth = birth;
		this.profileImage=profileImage;
		this.followeeNum=followeeNum;
		this.followNum = followNum;
		this.isDeleted=isDeleted;
		this.deletedTime = deletedTime;
		this.role=role;
		this.provider=provider;
		this.providerId = providerId;
		this.createDate=createDate;
	}
	public int getUserNo() {
	    return userNo;
	}

	public void setUserNo(int userNo) {
	    this.userNo = userNo;
	}

	public String getUsername() {
	    return username;
	}

	public void setUsername(String username) {
	    this.username = username;
	}

	public String getPassword() {
	    return password;
	}

	public void setPassword(String password) {
	    this.password = password;
	}

	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getPhoneNumber() {
	    return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
	    this.phoneNumber = phoneNumber;
	}

	public Integer getGender() {
	    return gender;
	}

	public void setGender(Integer gender) {
	    this.gender = gender;
	}

	public Timestamp getBirth() {
	    return birth;
	}

	public void setBirth(Timestamp birth) {
	    this.birth = birth;
	}

	public String getProfileImage() {
	    return profileImage;
	}

	public void setProfileImage(String profileImage) {
	    this.profileImage = profileImage;
	}

	public Integer getFollowNum() {
	    return followNum;
	}

	public void setFollowNum(Integer followNum) {
	    this.followNum = followNum;
	}

	public Integer getFolloweeNum() {
	    return followeeNum;
	}

	public void setFolloweeNum(Integer followeeNum) {
	    this.followeeNum = followeeNum;
	}

	public boolean isDeleted() {
	    return isDeleted;
	}

	public void setDeleted(boolean deleted) {
	    isDeleted = deleted;
	}

	public Timestamp getDeletedTime() {
	    return deletedTime;
	}

	public void setDeletedTime(Timestamp deletedTime) {
	    this.deletedTime = deletedTime;
	}

	public String getRole() {
	    return role;
	}

	public void setRole(String role) {
	    this.role = role;
	}

	public String getProvider() {
	    return provider;
	}

	public void setProvider(String provider) {
	    this.provider = provider;
	}

	public String getProviderId() {
	    return providerId;
	}

	public void setProviderId(String providerId) {
	    this.providerId = providerId;
	}

	public Timestamp getCreateDate() {
	    return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
	    this.createDate = createDate;
	}
	public String getToken() {
	    return token;
	}

	public void setToken(String token) {
	    this.token = token;
	}
	@Override
	public String toString() {
	    return "User{" +
	            "id=" + userNo +
	            ", username='" + username + '\'' +
	            ", email='" + email + '\'' +
	            '}';
	}
	@Override
	public int hashCode() {
	    return Objects.hash(userNo, username, password, email, phoneNumber, gender, birth, profileImage, photo,
	            followNum, followeeNum, isDeleted, deletedTime, role, provider, providerId, createDate);
	}
}
