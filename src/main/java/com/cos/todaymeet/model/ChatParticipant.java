package com.cos.todaymeet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatParticipant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int chatParticipantNo;
	@ManyToOne
	@JoinColumn(name = "meetNo")
	private Meet meet;
	
	@ManyToOne
	@JoinColumn(name = "userNo")
	private User user;
	
	public ChatParticipant() {
    }

    public ChatParticipant(Meet meet, User user) {
        this.meet = meet;
        this.user = user;
    }

    public int getChatParticipantNo() {
        return chatParticipantNo;
    }

    public void setChatParticipantNo(int chatParticipantNo) {
        this.chatParticipantNo = chatParticipantNo;
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
}
