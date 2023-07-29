package com.cos.todaymeet.common.firebase;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;

@Service
public class FCMInitializer {
//	@PostConstruct
//	public void initialize() throws IOException{
//		FileInputStream refreshToken = new FileInputStream("C:\\Users\\zxcv0\\git\\repository\\TodayMeet_repo\\TodayMeet-1\\src\\main\\resources");
//		
//		FirebaseOptions options = FirebaseOptions.builder()
//				.setCredentials(GoogleCredentials.fromStream(refreshToken))
//				.setProjectId("todaymeet-ac619")
//				.build();
//		FirebaseApp.initializeApp(options);
//	}
}