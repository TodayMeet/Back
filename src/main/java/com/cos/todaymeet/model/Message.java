package com.cos.todaymeet.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Message {
    public FCMNotification FCMNotification;
    private String token;
}