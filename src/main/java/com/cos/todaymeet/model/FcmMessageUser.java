package com.cos.todaymeet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessageUser {
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
    	private String NotiType;
    	private String userNo;
    	private String name;
    	//private String comment;
    	private String imageLink;
    }
}