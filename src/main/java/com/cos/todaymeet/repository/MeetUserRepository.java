package com.cos.todaymeet.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.todaymeet.DTO.UserNoImage;
import com.cos.todaymeet.model.MeetUser;
import com.cos.todaymeet.model.User;

@Repository
public interface MeetUserRepository extends JpaRepository<MeetUser, Integer> {
//	@Query (value ="SELECT u.userNo, u.profileImage AS userProfileImage, u.username  "
//	 		+ "FROM user u INNER JOIN MeetUSer m ON m.userNo = u.userNo WHERE m.meetNo = :meetNo;" )
//	 List<UserDTO> findListMeetUser(@Param("meetNo") int meetNo);
//	
	//void deleteByUserNoAndMeetNo(int userNo, int meetNo);
	
	//특정 사용자가 모임에 참가했는지 여부를 판단하기 위해
	@Query("SELECT mu FROM MeetUser mu WHERE mu.meet.meetNo = :meetNo AND mu.user.userNo = :userNo")
	MeetUser findByUserNoAndMeetNo1(@Param("userNo") int userNo, @Param("meetNo") int meetNo);
	
	@Query(value = "SELECT c.name , COUNT(mu.userNo) AS userCount " 
			+ "FROM MeetUser AS mu "
			+ "JOIN Meet AS m ON mu.meetNo = m.meetNo "
			+ "JOIN Category AS c ON m.categoryNo = c.categoryNo  "
			+ "WHERE mu.participateTime >= DATE_SUB(NOW(), INTERVAL 4 DAY)  "
			+ "GROUP BY c.name  "
			+ "ORDER BY userCount DESC", nativeQuery = true)
	List<Object[]> popularCategory();
	
	@Query("SELECT mu.user FROM MeetUser mu WHERE mu.meet.meetNo = :meetNo")
	List<User> selectByMeetNo(int meetNo);
	
	@Query(value = "SELECT mu.userNo , u.profileImage FROM MeetUser AS mu JOIN User AS u ON mu.userNo = u.userNo WHERE mu.meetNo = :meetNo", nativeQuery = true)
	List<Object[]> selectUserNoProfileImageByMeetNo(int meetNo);
}
