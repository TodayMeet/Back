package com.cos.todaymeet.DTO;

import java.util.List;

public class ChatListDto {
	private String meetNo;
	private List<ChatDto> chats;
	 // 생성자
    public ChatListDto() {
    }

    public ChatListDto(String meetNo, List<ChatDto> chats) {
        this.meetNo = meetNo;
        this.chats = chats;
    }

    // Getter 메서드들
    public String getMeetNo() {
        return meetNo;
    }

    public List<ChatDto> getChats() {
        return chats;
    }

    // Setter 메서드들
    public void setMeetNo(String meetNo) {
        this.meetNo = meetNo;
    }

    public void setChats(List<ChatDto> chats) {
        this.chats = chats;
    }
}
