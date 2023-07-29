package com.cos.todaymeet.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.cos.todaymeet.model.ChatNotifi;
import com.cos.todaymeet.model.FcmMessage;
import com.cos.todaymeet.model.FcmMessageUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@Component
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
	
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/today-meet-2/messages:send";
    private final ObjectMapper objectMapper;
    
    public void sendMessageTo(String targetToken, String title, String body,String name,String userNo, String number, String notiType, String imageLink) throws IOException {
        String message = makeMessage(targetToken, title, body,name,userNo,number,notiType,imageLink);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, message);
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();
        System.out.println(response.body().string());
    }
    public void sendMessageToUserNo(String targetToken, String title, String body,String name, String number, String notiType, String imageLink) throws IOException {
        String message = makeMessageuserNo(targetToken, title, body,name,number,notiType,imageLink);
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, message);
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        Response response = client.newCall(request)
                .execute();
        System.out.println(response.body().string());
    }
      private String makeMessage(String targetToken, String title, String body,String name,String userNo, String number, String notiType, String imageLink) throws JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
        		.message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                            .title(title)
                            .body(body)
                            .image(null)
                            .build()
                        )
                        .data(FcmMessage.data.builder()
                        		.name(name)
                        		.meetNo(number)
                        		.userNo(userNo)
                        		.NotiType(notiType)
                        		.Type("0")
                        		.imageLink(imageLink)
                        		.build()
                        		)
                        .build()
                    )
                    .validate_only(false)
                    .build();
        return objectMapper.writeValueAsString(fcmMessage);
    }
      private String makeMessageuserNo(String targetToken, String title, String body,String name, String number, String notiType, String imageLink) throws JsonProcessingException {
    	  FcmMessageUser fcmMessage = FcmMessageUser.builder()
          		.message(FcmMessageUser.Message.builder()
                          .token(targetToken)
                          .notification(FcmMessageUser.Notification.builder()
                              .title(title)
                              .body(body)
                              .image(imageLink)
                              .build()
                          )
                          .data(FcmMessageUser.data.builder()
                          		.name(name)
                          		.userNo(number)
                          		.NotiType(notiType)
                          		.Type("0")
                          		.imageLink(imageLink)
                          		.build()
                          		)
                          .build()
                      )
                      .validate_only(false)
                      .build();
          return objectMapper.writeValueAsString(fcmMessage);
      }
      public void sendChatTo(String targetToken, String title, String body,String username, String profileImage, String comment, Timestamp time,int userNo, int meetNo) throws IOException {
          String message = makeChat(targetToken, title, body,username,profileImage,comment,time,userNo,meetNo);
          OkHttpClient client = new OkHttpClient();
          MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
          RequestBody requestBody = RequestBody.create(mediaType, message);
          Request request = new Request.Builder()
                  .url(API_URL)
                  .post(requestBody)
                  .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                  .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                  .build();

          Response response = client.newCall(request)
                  .execute();
          System.out.println(response.body().string());
      }
        private String makeChat(String targetToken, String title, String body,String username, String profileImage, String comment, Timestamp time
        		,int userNo, int meetNo) throws JsonProcessingException {
          
        	ChatNotifi fcmMessage = ChatNotifi.builder()
          		.message(ChatNotifi.Message.builder()
                          .token(targetToken)
                          .notification(ChatNotifi.Notification.builder()
                              .title(title)
                              .body(body)
                              .image(null)
                              .build()
                          )
                          .data(ChatNotifi.data.builder()
                        		.Type("1")
                          		.username(username)
                          		.comment(comment)
                          		.profileImage(profileImage)
                          		.time(time)
                          		.userNo(String.valueOf(userNo))
                          		.meetNo(String.valueOf(meetNo))
                          		.build()
                          		)
                          .build()
                      )
                      .validate_only(false)
                      .build();
          return objectMapper.writeValueAsString(fcmMessage);
      }
	private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/today-meet-2-firebase-adminsdk-pa97n-724d12d386.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
