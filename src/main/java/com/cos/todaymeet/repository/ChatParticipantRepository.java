package com.cos.todaymeet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.model.ChatParticipant;
@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant,Integer> {
	
	@Query("SELECT mu FROM ChatParticipant mu WHERE mu.meet.meetNo = :meetNo AND mu.user.userNo = :userNo")
	ChatParticipant findByUserNoAndMeetNoChat(@Param("userNo") int userNo, @Param("meetNo") int meetNo);
	
	@Query("SELECT mu FROM ChatParticipant mu WHERE mu.meet.meetNo = :meetNo ")
	List<ChatParticipant> findByMeetNoChat( @Param("meetNo") int meetNo);

}
