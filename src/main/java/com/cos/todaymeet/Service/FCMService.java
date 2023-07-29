package com.cos.todaymeet.Service;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
public class FCMService {
	public String sendMessage(int requestId,String registrationToken)throws FirebaseMessagingException {
		Message message=Message.builder()
				.setAndroidConfig(AndroidConfig.builder()
						.setTtl(3600*1000)
						.setPriority(AndroidConfig.Priority.HIGH)
						.setRestrictedPackageName("어플리케이션 패키지 이름")
						.setDirectBootOk(true)
						.setNotification(AndroidNotification.builder()
								.setTitle("Test")
								.setBody("Notice Test from server")
								.build())
						.build())
				.putData("requestId", Integer.toString(requestId))
				.setToken(registrationToken)
				.build();
		String response = FirebaseMessaging.getInstance().send(message);
		return response;
	}
}
