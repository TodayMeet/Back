package com.cos.todaymeet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.todaymeet.model.MeetComment;

public interface MeetCommentRepository extends JpaRepository<MeetComment, Integer> {

	MeetComment findByMeetCommentNo(int meetCommentNo);
}
