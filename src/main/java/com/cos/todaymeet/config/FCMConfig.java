package com.cos.todaymeet.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FCMConfig {
	@Bean
	FirebaseMessaging firebaseMessaging() throws IOException{
		ClassPathResource resource = new ClassPathResource("firebase/today-meet-2-firebase-adminsdk-pa97n-724d12d386.json");
		InputStream refreshToken = resource.getInputStream();
		
		FirebaseApp firebaseApp = null;
		List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
		
		if(firebaseAppList!=null && !firebaseAppList.isEmpty()) {
			for(FirebaseApp app : firebaseAppList) {
				if(app.getName().equals(firebaseApp.DEFAULT_APP_NAME)){
					firebaseApp=app;
				}
			}
		}else {
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(refreshToken))
					.build();
			firebaseApp=firebaseApp.initializeApp(options);
			
		}
		return FirebaseMessaging.getInstance(firebaseApp);
		
	}

}
