package com.cos.todaymeet.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cos.todaymeet.model.MeetUser;
import com.cos.todaymeet.repository.MeetUserRepository;

import jakarta.persistence.EntityManager;

@Service
public class MeetUserService {
	@Autowired
    private MeetUserRepository meetUserRepository;
   

    public void deleteMeetUser(int userId, int meetId) {
        MeetUser meetUser = meetUserRepository.findByUserNoAndMeetNo1(userId, meetId);
        System.out.println("meetUser"+meetUser.getMeetUserNo());
        if (meetUser != null) {
        	 meetUserRepository.delete(meetUser);
        }
    }
}