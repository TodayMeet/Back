package com.cos.todaymeet.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.todaymeet.DTO.UserDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor	
@Table(name = "meetcomment")
public class MeetComment {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetCommentNo;
	private int meetNo;
	private Integer parentNo;
	private String content;
	@ManyToOne
	@JoinColumn(name = "userNo")
	private User user;
	@CreationTimestamp
	private Timestamp createDate;
	
	//신고 기능
	private boolean isReported;
	
	public MeetComment(int meetNo, Integer parentNo, String content, User user) {
		this.meetNo=meetNo;
		this.parentNo=parentNo;
		this.content=content;
		this.user=user;
	}
    // Getters and setters
	// 신고 기능 추가
	public boolean getIsReported() {
		return isReported;
	}
	public void setIsReported(boolean isReported) {
		this.isReported=isReported;
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

    public User getUserNo() {
        return user;
    }

    public void setUserNo(User userNo) {
        this.user = userNo;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}
