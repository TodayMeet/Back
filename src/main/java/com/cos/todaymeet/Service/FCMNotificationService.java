package com.cos.todaymeet.Service;

import org.springframework.stereotype.Service;

import com.cos.todaymeet.repository.UserRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {
	private final FirebaseMessaging firebaseMessaging;
	private final UserRepository userRepository;
	public String sendNotificationByToken() {
		String token = "fXY9ItNVSIaUNlqZkvAa1k:APA91bEgy_qfb92V1BFBRndlYyfx53FmwSIB7bNmA_S3VJJqoGy2PEe5yDsdZV2KK-ShvuxW5Nj_bjDdhO7MeTN289up8OppzLogoKRwZkZptkckXy3e1-twzFoSDj0Bfs1UyENCtVq7";
		Notification notification = Notification.builder()
				.setTitle("spring boot tset")
				.setBody("TEst!!!")
				.build();
		Message message  = Message.builder()
				.setToken(token)
				.setNotification(notification)
				.build();
		try {
			firebaseMessaging.send(message);
			return "success send message";
			
		}catch(FirebaseMessagingException e){
			e.printStackTrace();
			return " fail send message";
		}
				

	}
}
