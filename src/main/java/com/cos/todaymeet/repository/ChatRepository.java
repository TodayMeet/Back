package com.cos.todaymeet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.todaymeet.model.Chat;

public interface ChatRepository extends JpaRepository<Chat,Integer>{

	@Query(value= "SELECT u.profileImage,u.username, u.userNo, c.chatNo, c.content, c.createTime, m.meetNo, m.title FROM Chat c INNER JOIN User u ON c.userNo = u.userNo \r\n"
			+ " INNER JOIN Meet m ON c.meetNo = m.meetNo WHERE c.meetNo = :meetNo",nativeQuery=true)
	List<Object[]> findChatMeetNo(int meetNo);
}
