package com.cos.todaymeet.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.todaymeet.DTO.UserTokenDto;
import com.cos.todaymeet.Service.FCMNotificationService;
import com.cos.todaymeet.Service.FirebaseCloudMessageService;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/fcm")
public class FCMNotificatinoApiController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private FirebaseCloudMessageService fcmService;
	private final FCMNotificationService fcmntoi;
	@PostMapping()
	public String sendNotifi() {
		return fcmntoi.sendNotificationByToken();
	}
	/**************************************************************************************/
	//API 사용자 token 업데이트 
	//사용자의 기기의 알림을 보내기 위한 토큰 정보를 업데이트한다.
	//postman firebase알림/token 저장
	@PostMapping("/token")
	public ResponseEntity<String> token(@RequestBody UserTokenDto user) {
	    int userNo = user.getUserNo();
	    String token = user.getToken();
	    User u = userRepository.findByUserNo(userNo);
	    //기존에 저장된 token과 같으면
	    if(token==u.getToken()) {
	    	//변경하지 않는다
	    	return ResponseEntity.ok("already updated");
	    }else {
	    	//token이 update되었으면 테이블의 값을 변경한다.
	    	userRepository.UpdateToken(userNo, token);
	    	return ResponseEntity.ok("success");
	    }
	}
	//TEST FCM 알림 전송 
	@PostMapping("/send")
	public ResponseEntity<String> DataFrame() throws IOException {
		String token = "f-B8naUAQ26eiqQtfZGZQh:APA91bGcgOTkjFFa3Fvp4IAOwOoqzedWMCqKJER7Tm2zOfj3v2oVKmF6hAEqScr9COU-EW-iC7v9UgLcGCVat0rpCNuJwo5Mydktzk9iPXWsyniJ0NW2U6gdEeTOFBPdihC6MxanwdN-";
	    fcmService.sendMessageTo(token, "data test", "data test","name","","number","notiType","image");
	    return ResponseEntity.ok("success");
	}
	//TEST FCM 채팅 알림 전송
	@PostMapping("/sendChat")
	public ResponseEntity<String> chat() throws IOException {
		String token = "dSCWDEeNTz29VG5DI92DMt:APA91bFdVG9XeTEWusX2riQB3s4ky-w0LhjyZvtHCWJLjp3fhyRhQGwkq8Wq2knCjfQcYwh-eNsdBMskM3J7W6oiyA6AK-EjWJk5owUCyLpzsLuirwncviqzZKuPilE3LrNXhGrYpklD";
		fcmService.sendChatTo(token, "data test", "data test","namin","HI Application","http://todaymeet.shop:8080/meetimage/25",Timestamp.from(Instant.now()),1,1);
	    return ResponseEntity.ok("success");
	}
}
