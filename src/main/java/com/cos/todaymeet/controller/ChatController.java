package com.cos.todaymeet.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.todaymeet.DTO.ChatDto;
import com.cos.todaymeet.DTO.ChatParticipantList;
import com.cos.todaymeet.DTO.MeetUserDto;
import com.cos.todaymeet.Service.ChatParticipantService;
import com.cos.todaymeet.Service.FirebaseCloudMessageService;
import com.cos.todaymeet.model.Chat;
import com.cos.todaymeet.model.ChatParticipant;
import com.cos.todaymeet.model.Meet;
import com.cos.todaymeet.model.User;
import com.cos.todaymeet.repository.BlockRepository;
import com.cos.todaymeet.repository.ChatParticipantRepository;
import com.cos.todaymeet.repository.ChatRepository;
import com.cos.todaymeet.repository.FollowRepository;
import com.cos.todaymeet.repository.MeetRepository;
import com.cos.todaymeet.repository.UserRepository;


@RestController
public class ChatController {
	@Autowired 
	private UserRepository userRepostiroy;
	@Autowired
	private MeetRepository meetRepository;
	@Autowired
	private ChatRepository chatRepository;
	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private ChatParticipantRepository chatParticipantRepository;
	@Autowired
	private ChatParticipantService chatParticipantService;
	@Autowired
	private FirebaseCloudMessageService fcmService;
	@Autowired
	private BlockRepository blockRepository;
	/*************************************************************************************************************/
	//API 채팅 작성 
	//req : meetNo, userNo, content
	// Postman firebase알림/채팅추가
	//채팅방페이지
	@PostMapping("/chat/add")
	public ResponseEntity<String> addchat(@RequestBody Chat chat ) throws IOException{
		/***채팅 데이터를 데이터베이스에 저장한다.*******/
		//받은 meetNo, userNo를 이용하여 user와 meet 정보를 받아온다.
		User user = userRepostiroy.findByUserNo(chat.getUser().getUserNo());
		Meet meet= meetRepository.findByMeetNo(chat.getMeet().getMeetNo());
		//chat을 저장
		Chat newchat = new Chat(meet,user,(String)chat.getContent());
		chatRepository.save(newchat);
		
		/***채팅방에 있는 사람들에게 fcm을 이용하여 알림을 보낸다.**************/
		//채팅방에 있는 참가자들의 리스트를 가져온다
		//한 meet 에 하나의 채팅방만 가능하다.
		List<ChatParticipant> partList = chatParticipantRepository.findByMeetNoChat(meet.getMeetNo());
		for (ChatParticipant part : partList) {
			//참가자의 정보를 가져온 후
			User partuser = userRepostiroy.findByUserNo(part.getUser().getUserNo());
			if(user.getUserNo()==partuser.getUserNo()) {
				//만약 참가자와 본인이 같다면 알림을 보내지 않는다.
				continue;
			}
			//알림을 받을 사람이 현재 사용자(나)를 차단한 상태라면
			Integer isBlock = blockRepository.isBlock(partuser.getUserNo(), user.getUserNo());
			if(isBlock>0) {
				//알림을 보내지 않는다.
				continue;
			}
			Timestamp time = Timestamp.from(Instant.now());
			long adTime=time.getTime() + 32400000L;
			Timestamp adTimestamp = new Timestamp(adTime);
			//참가자의 user데이터베이스에 저장된 token 값을 이용하여 알림을 보낸다.
			// username : 입력한 사용자 이름, comment : 채팅 내용, profileImage : 입력한 사용자의 프로필이미지
			// time : 입력시간, userNo : 입력한 사용자의 userNo, meetNo : 채팅방이 있는 모임의 meetNo
			fcmService.sendChatTo(partuser.getToken(), meet.getTitle(), user.getUsername()+"님이 채팅을 작성하였습니다.",
					user.getUsername(),user.getProfileImage(),chat.getContent(),adTimestamp,user.getUserNo(),meet.getMeetNo());
		}
		
		return null;
	
	}
	/************************************************************************/
	//API 채팅방 내용 출력
	// 모임의 채팅방의 내용을 출력한다.
	//postman firebase알림/채팅출력
	//채팅방 페이지
	@GetMapping("/chat/{meetNo}")
	public List<ChatDto> chatlist(@PathVariable("meetNo") int meetNo){
		List<Object[]> chats = chatRepository.findChatMeetNo(meetNo);
		//System.out.println(chats);
		List<ChatDto> clist = new ArrayList<>();
		for(Object[] chat : chats) {
			Timestamp time = (Timestamp)chat[5];
			long adTime=time.getTime() + 32400000L;
			Timestamp adTimestamp = new Timestamp(adTime);
			ChatDto ch = new ChatDto((String)chat[0],(String)chat[1],(int)chat[2],(int)chat[3],
					(String)chat[4],adTimestamp,(int)chat[6]);
			clist.add(ch);
		}
		// 출력 리스트 내용
		// userProfileImage,username,userNo,chatNo,content,time,meetNo를 출력한다.
		return clist;
		
	}
	/************************************************************************/
	//API 채팅방 내용 출력- 차단 기능 추가
	// 모임의 채팅방의 내용을 출력한다.
	//postman firebase알림/채팅출력
	//채팅방 페이지
	@GetMapping("/chat")
	public List<ChatDto> chatlistBlock(@RequestParam("userNo") int userNo , @RequestParam("meetNo") int meetNo){
		List<Object[]> chats = chatRepository.findChatMeetNo(meetNo);
		//System.out.println(chats);
		List<ChatDto> clist = new ArrayList<>();
		for(Object[] chat : chats) {
			Timestamp time = (Timestamp)chat[5];
			long adTime=time.getTime() + 32400000L;
			Timestamp adTimestamp = new Timestamp(adTime);
			int blockedUserNo = (int)chat[2];
			//만약 차단된 사용자라면
			Integer isBlock = blockRepository.isBlock(userNo, blockedUserNo);
			String content = (String)chat[4];
			if(isBlock>0) {
				//채팅 내용을 "차단된 사용자입니다"를 출력하도록 한다.
				content = "차단된 사용자입니다.";
			}
			ChatDto ch = new ChatDto((String)chat[0],(String)chat[1],(int)chat[2],(int)chat[3],
					content,adTimestamp,(int)chat[6]);
			clist.add(ch);
		}
		// 출력 리스트 내용
		// userProfileImage,username,userNo,chatNo,content,time,meetNo를 출력한다.
		return clist;
		
	}
	/************************************************************************/
	//API 채팅 참가자 리스트 출력
	//req : userNo - 현재 유저 follow여부를 판단하기 위해, meetNo - 채팅방 모임 정보
	//채팅방 - 참가자 페이지
	//postman firebase알림/채팅방 참가자 리스트
	@PostMapping("/chat-participant")
	public List<ChatParticipantList> partlist(@RequestBody MeetUserDto meetuser){
		//채팅 참가자들의 userNo를 가져온다
		List<ChatParticipant> participantlist = chatParticipantRepository.findByMeetNoChat(meetuser.getMeetNo());
		List<ChatParticipantList> partiList = new ArrayList<>();
		for (ChatParticipant participant : participantlist) {
			//참가자들의 userNo를 이용해서 참가자 정보를 가져온다
			User user = userRepostiroy.findByUserNo(participant.getUser().getUserNo());
			//해당 참가자를 현재 유저가 팔로우하는지 판단한다.
			Integer count = followRepository.isFollow(meetuser.getUserNo(), user.getUserNo());
			System.out.println(count);
			boolean isFollow = count > 0;
			ChatParticipantList c = new ChatParticipantList(user.getUsername(),user.getProfileImage(),user.getUserNo(),isFollow);
			partiList.add(c);
		}
		//출력 데이터
		//username, profileImage, userNo, follow(팔로우 여부 - boolean)
		return partiList;
		
	}
	/*******************************************************************************/
	//API 채팅 참가
	//req : userNo - 참가하는 사람, meetNo-참가할 채팅방의 모임 meetNo
	//모임상세페이지 - 채팅방 입장 버튼
	//postman firebase알림/채팅방 참가/탈퇴
	@PostMapping("/chat/participant")
	public ResponseEntity<String> participate(@RequestBody ChatParticipant chat ) throws IOException{
		Meet meet= meetRepository.findByMeetNo(chat.getMeet().getMeetNo());
		User user = userRepostiroy.findByUserNo(chat.getUser().getUserNo());
		//만약 이미 참가한 상태이면 데이터베이스에 추가하지 않는다.
		if(chatParticipantRepository.findByUserNoAndMeetNoChat(chat.getUser().getUserNo(), chat.getMeet().getMeetNo())!= null) {
			return ResponseEntity.ok("already participant");
		}
		else {
			//참가하지 않은 상태이면
			// 참가한 user와 meet의 정보를 ChatParticipant테이블에 저장한다.
			chatParticipantRepository.save(chat);
			//현재 채팅방에 참가중인 사용자의 리스트를 가져와서
			List<ChatParticipant> partList = chatParticipantRepository.findByMeetNoChat(meet.getMeetNo());
			for (ChatParticipant part : partList) {
				//참가자들에게 새로운 user가 참가함을 알림을 보낸다.
				User partuser = userRepostiroy.findByUserNo(part.getUser().getUserNo());
				if(user.getUserNo()==partuser.getUserNo()) {
					//단 새로 참가하게 된 당사자에게는 보내지 않는다.
					continue;
				}
				//알림 : 참가하는 username, 참가하는 profileimage, 입장과 채팅을 구분하기 위해 content, 참가시간, 참가하는 사용자 no, 모임 no
				fcmService.sendChatTo(partuser.getToken(), meet.getTitle(), user.getUsername()+"님이 "+meet.getTitle()+" 채팅방에 입장하셨습니다.",
						user.getUsername(),user.getProfileImage(),"입장하셨습니다.",Timestamp.from(Instant.now()),user.getUserNo(),meet.getMeetNo());
			}
			return ResponseEntity.ok("success");
		}
		
	}
	/********************************************************************************************/
	//API 채팅방 나가기
	//req : userNo - 나가는 참가자 , meetNo - 나갈 채팅방 모임no
	// 채팅방 나가기 버튼
	//postman firebase알림/채팅방 참가/탈퇴
	@PostMapping("/chat/remove-participant")
	public ResponseEntity<String> notparticipate(@RequestBody ChatParticipant chat ) throws IOException{
		Meet meet= meetRepository.findByMeetNo(chat.getMeet().getMeetNo());
		User user = userRepostiroy.findByUserNo(chat.getUser().getUserNo());
		//만약 참가하지 않은 상태라면
		if(chatParticipantRepository.findByUserNoAndMeetNoChat(chat.getUser().getUserNo(), chat.getMeet().getMeetNo())== null) {
			return ResponseEntity.ok("not participant");
		}
		else {
			//참가한 상태라면
			//채팅방에 참가한사람들의 리스트를 받은 후
			List<ChatParticipant> partList = chatParticipantRepository.findByMeetNoChat(meet.getMeetNo());
			for (ChatParticipant part : partList) {
				User partuser = userRepostiroy.findByUserNo(part.getUser().getUserNo());
				if(user.getUserNo()==partuser.getUserNo()) {
					//본인을 제외하고
					continue;
				}
				//채팅방의 모든 참가자들에게 채팅방을 나간다는 알림을 전송한다.
				fcmService.sendChatTo(partuser.getToken(), meet.getTitle(), user.getUsername()+"님이 "+meet.getTitle()+" 채팅방에서 나가셨습니다.",
						user.getUsername(),user.getProfileImage(),"나가셨습니다.",Timestamp.from(Instant.now()),user.getUserNo(),meet.getMeetNo());
			}
			//채팅방 참가 테이블(ChatParticipant)에서 해당 사용자의 참가 정보를 삭제한다
			chatParticipantService.deleteChatParticipant(chat.getUser().getUserNo(), chat.getMeet().getMeetNo());
			return ResponseEntity.ok("success");
		}		
	}
}
