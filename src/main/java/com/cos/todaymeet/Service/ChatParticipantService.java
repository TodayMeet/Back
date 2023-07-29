package com.cos.todaymeet.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.todaymeet.model.ChatParticipant;
import com.cos.todaymeet.repository.ChatParticipantRepository;
@Service
public class ChatParticipantService {
	
	
	@Autowired
	private ChatParticipantRepository chatParticipantRepository;
    
	public void deleteChatParticipant(int userId, int meetId) {
        ChatParticipant meetUser = chatParticipantRepository.findByUserNoAndMeetNoChat(userId, meetId);
        //System.out.println("meetUser"+meetUser.getMeetUserNo());
        if (meetUser != null) {
        	chatParticipantRepository.delete(meetUser);
        }
    }
}
