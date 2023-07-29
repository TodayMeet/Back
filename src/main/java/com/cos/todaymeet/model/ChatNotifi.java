package com.cos.todaymeet.model;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ChatNotifi {
	private boolean validate_only;
    private Message message;
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message{
    	private Notification notification;
    	private String token;
    	private data data;
    	

    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification{
    	private String title;
    	private String body;
    	private String image;
    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class data{
    	private String Type;
    	private String username;
    	private String profileImage;
    	private String comment;
    	private String userNo;
    	private String meetNo;
    	private Timestamp time;
    }
}
