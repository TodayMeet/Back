package com.cos.todaymeet.model;

import java.sql.Timestamp;

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
public class MeetUser {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int meetUserNo;
	@ManyToOne
	@JoinColumn(name = "meetNo")
	private Meet meet;
	@ManyToOne
	@JoinColumn(name = "userNo")
	private User user;
	private boolean host;
	@CreationTimestamp
	private Timestamp participateTime;
	
	public MeetUser(Meet meetNo, User userNo, boolean host) {
		this.meet=meetNo;
		this.user = userNo;
		this.host=host;
	}
	 public int getMeetUserNo() {
	        return meetUserNo;
	    }

	    public void setMeetUserNo(int meetUserNo) {
	        this.meetUserNo = meetUserNo;
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

	    public boolean isHost() {
	        return host;
	    }

	    public void setHost(boolean host) {
	        this.host = host;
	    }

	    public Timestamp getParticipateTime() {
	        return participateTime;
	    }

	    public void setParticipateTime(Timestamp participateTime) {
	        this.participateTime = participateTime;
	    }
}
