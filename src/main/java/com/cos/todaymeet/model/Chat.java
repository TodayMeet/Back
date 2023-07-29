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
public class Chat {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int chatNo;
		
		@ManyToOne
		@JoinColumn(name = "meetNo")
		private Meet meet;
		
		@ManyToOne
		@JoinColumn(name = "userNo")
		private User user;
		
		private String content;
		

		@CreationTimestamp
		private Timestamp createTime;
		// 생성자
	    public Chat(Meet meet, User user, String content) {
	        this.meet = meet;
	        this.user = user;
	        this.content = content;
	    }
	    
	    // Getter 및 Setter 메서드
	    public int getChatNo() {
	        return chatNo;
	    }

	    public void setChatNo(int chatNo) {
	        this.chatNo = chatNo;
	    }

	    public Meet getMeet() {
	        return meet;
	    }

	    public void setMeet(Meet meet) {
	        this.meet = meet;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public Timestamp getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Timestamp createTime) {
	        this.createTime = createTime;
	    }
		
}
