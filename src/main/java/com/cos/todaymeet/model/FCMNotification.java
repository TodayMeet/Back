package com.cos.todaymeet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FCMNotification {
    private String title;
    private String body;
    private String image;
}